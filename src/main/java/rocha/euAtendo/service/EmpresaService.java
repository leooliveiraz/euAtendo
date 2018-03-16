package rocha.euAtendo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
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
			empresa.setPath_img(salvarimagem(imagem, usuario.getId(), path));
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
				empresa_.setPath_img(salvarimagem(dto.getFotoempresa(),empresa.getId(), path));
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
					(pesquisa,pesquisa,pesquisa,pesquisa,pageable);
		}
		List<ApresentacaoEmpresaDTO> dtos = new ArrayList<ApresentacaoEmpresaDTO>();
		for(Empresa e : page.getContent()) {
			ApresentacaoEmpresaDTO dto = novoApresentacaoEmpresaDTO(e);
			List<ConvenioDTO> convenios = convenioService.listarDtos(e);
			dto.setConvenios(convenios);
			List<EspecialidadeDTO> especialidades = especialidadeService.listarDtos(e);
			dto.setEspecialidades(especialidades);
			dtos.add(dto);
		}
		return dtos;
	}

	private ApresentacaoEmpresaDTO novoApresentacaoEmpresaDTO(Empresa empresa) {
		ApresentacaoEmpresaDTO dto =  new ApresentacaoEmpresaDTO();
		dto.preencher(empresa);
		return dto;
	}


	private String salvarimagem(String fotoempresa, Long id , String path) throws IOException {
		File folder = new File(path);
		folder.mkdirs();

		String nomearquivo = id.toString()+"_";

		String arrayImg[] = fotoempresa.split(",");
		String base64ImageEmpresa = arrayImg[1];
		String extension = fotoempresa.replace(";", "/").split("/")[1];
		String fileName = nomearquivo + "." + extension;
		String patharquivo = path+fileName;
		byte[] decodedByteEmpresa = Base64.getMimeDecoder().decode(base64ImageEmpresa);
		FileOutputStream fos = new FileOutputStream(patharquivo);
		fos.write(decodedByteEmpresa);
		fos.close();

		return patharquivo;
	}


}
