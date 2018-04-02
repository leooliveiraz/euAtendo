package rocha.euAtendo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import rocha.euAtendo.dto.ApresentacaoEmpresaDTO;
import rocha.euAtendo.dto.ConvenioDTO;
import rocha.euAtendo.dto.EmpresaDTO;
import rocha.euAtendo.dto.EspecialidadeDTO;
import rocha.euAtendo.dto.ExameImagemDTO;
import rocha.euAtendo.dto.ExameLaboratorialDTO;
import rocha.euAtendo.model.Empresa;
import rocha.euAtendo.model.Usuario;
import rocha.euAtendo.repository.EmpresaRepository;
import rocha.euAtendo.util.DateUtil;


@Service
public class EmpresaService {
	@Autowired
	EmpresaRepository empresaRepository;
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	ConvenioService convenioService;
	@Autowired
	EspecialidadeService especialidadeService;
	@Autowired
	ExameImagemService examesImgService;
	@Autowired
	ExameLaboratorialService examesLabService;


	public void salvar( Empresa empresa,Usuario usuario,String imagem,String path) throws Exception {
		String erros = empresa.validaObj();
		erros = erros+usuario.validaObj();

		Usuario usuarioExistente = usuarioService.encontrar(usuario.getEmail());
		if(usuarioExistente!= null) {
			erros = erros+"Já existe usuário com esse email";
		}		
		if(erros.isEmpty()) {
			empresaRepository.save(empresa);
			usuarioService.salvar(usuario);
			empresa.setPath_img(salvarImagem(imagem));
			empresaRepository.save(empresa);
		}else {
			throw new  Exception(erros);
		}		
	}

	public void processoNovaEmpresa(EmpresaDTO dto,String path) throws Exception {
		Empresa empresa = new Empresa(dto.getNome(),dto.getCnpj(),dto.getCep(),dto.getUf(),dto.getCidade(),dto.getBairro(),dto.getEndereco(),dto.getNumero(),dto.getPath_img(),dto.getTelefone_contato(),dto.getEmail_contato(),dto.getResponsavel(),dto.getCpf(),DateUtil.stringParaData(dto.getDt_nascimento()),new Date(),dto.getSite());
		Usuario usuario = new Usuario(dto.getSenha(),dto.getSenha_confirmacao(),dto.getEmail_login(),empresa,new Date(),Boolean.TRUE);

		salvar(empresa,usuario,dto.getFotoempresa(),path);
	}

	public void alterar(EmpresaDTO dto,Empresa empresa,String path) throws Exception {
		if(dto.getId().equals(empresa.getId())) {
			String erros = empresa.validaObj();
			if(erros.isEmpty()) {
				Empresa empresa_ = new Empresa(dto.getNome(),dto.getCnpj(),dto.getCep(),dto.getUf(),
						dto.getCidade(),dto.getBairro(),dto.getEndereco(),dto.getNumero(),dto.getPath_img(),
						dto.getTelefone_contato(),dto.getEmail_contato(),dto.getResponsavel(),dto.getCpf(),
						DateUtil.stringParaData(dto.getDt_nascimento()),new Date(),dto.getSite());
				empresa_.setId(dto.getId());
				empresaRepository.save(empresa_);
				empresa_.setPath_img(salvarImagem(dto.getFotoempresa()));
				empresaRepository.save(empresa_);

			}else {
				throw new  Exception(erros);
			}				
		}else {
			throw new  Exception("Ops ocorreu um erro");			
		}
	}


	public List<ApresentacaoEmpresaDTO> listarEstabelecimentos(Integer paginaAtual,Integer tamanho,String pesquisa) {
		PageRequest pageable = new PageRequest(paginaAtual, tamanho);
		Page<Empresa> page = null;
		if(pesquisa.isEmpty()) {
			page = empresaRepository.findAll(pageable);
		}else {
			pesquisa="%"+pesquisa+"%";
			page = empresaRepository.findByPesquisa
					(pesquisa,pesquisa,pesquisa,pesquisa,pesquisa,pesquisa,pageable);
		}
		List<ApresentacaoEmpresaDTO> dtos = new ArrayList<ApresentacaoEmpresaDTO>();
		for(Empresa e : page.getContent()) {
			ApresentacaoEmpresaDTO dto = novoApresentacaoEmpresaDTO(e);
			List<ConvenioDTO> convenios = convenioService.listarDtos(e);
			dto.setConvenios(convenios);
			List<EspecialidadeDTO> especialidades = especialidadeService.listarDtos(e);
			dto.setEspecialidades(especialidades);
			List<ExameImagemDTO> examesImg = examesImgService.listarDtos(e);
			dto.setExamesImg(examesImg);
			List<ExameLaboratorialDTO> examesLab = examesLabService.listarDtos(e);
			dto.setExamesLab(examesLab);
			dtos.add(dto);
		}
		return dtos;
	}

	private ApresentacaoEmpresaDTO novoApresentacaoEmpresaDTO(Empresa empresa) {
		ApresentacaoEmpresaDTO dto =  new ApresentacaoEmpresaDTO();
		dto.preencher(empresa);
		return dto;
	}


	public String salvarImagem(String imagem64 ) {
		String retorno = sendPost("https://bdimagens.herokuapp.com/imagem/salvar", imagem64);
		return retorno;
	}

	public String sendPost(String url, String json) {

	    try {
	        // Cria um objeto HttpURLConnection:
	        HttpURLConnection request = (HttpURLConnection) new URL(url).openConnection();

	        try {
	            // Define que a conexão pode enviar informações e obtê-las de volta:
	            request.setDoOutput(true);
	            request.setDoInput(true);

	            // Define o content-type:
	            request.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

	            // Define o método da requisição:
	            request.setRequestMethod("POST");

	            // Conecta na URL:
	            request.connect();

	            // Escreve o objeto JSON usando o OutputStream da requisição:
	            try (OutputStream outputStream = request.getOutputStream()) {
	                outputStream.write(json.getBytes());	                
	            }

	            // Caso você queira usar o código HTTP para fazer alguma coisa, descomente esta linha.
	            //int response = request.getResponseCode();

	            return readResponse(request);
	        } finally {
	            request.disconnect();
	        }
	    } catch (IOException ex) {
	        ex.printStackTrace();
	        return null;
	    }
	}

	private String readResponse(HttpURLConnection request) throws IOException {
	    ByteArrayOutputStream os;
	    try (InputStream is = request.getInputStream()) {
	        os = new ByteArrayOutputStream();
	        int b;
	        while ((b = is.read()) != -1) {
	            os.write(b);
	        }
	    }
	    String response = new String(os.toByteArray());
	    return response;
	}


}
