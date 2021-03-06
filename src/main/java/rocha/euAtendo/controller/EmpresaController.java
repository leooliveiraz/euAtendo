package rocha.euAtendo.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rocha.euAtendo.dto.ApresentacaoEmpresaDTO;
import rocha.euAtendo.dto.EmpresaDTO;
import rocha.euAtendo.model.Empresa;
import rocha.euAtendo.model.Usuario;
import rocha.euAtendo.service.EmpresaService;
import rocha.euAtendo.util.AuthUtil;
import rocha.euAtendo.util.DateUtil;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

	@Autowired
	private ServletContext servletContext;
	@Autowired
	EmpresaService empresaService;

	@RequestMapping(value="/cadastrar", method=RequestMethod.POST)
	public ResponseEntity<String> home(@RequestBody EmpresaDTO dto) {
		try {
			String path = servletContext.getRealPath("/") + "resources/imagemempresa/";
			empresaService.processoNovaEmpresa(dto,path);
			return ResponseEntity.status(HttpStatus.ACCEPTED)                 
					.body("");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(e.getMessage());
		}

	}



	@RequestMapping(value="/empresaAlteracao", method=RequestMethod.GET)
	public EmpresaDTO empresaAlteracao( @RequestHeader(value="Authorization") String authorization) {
		try {
			Usuario usuario = AuthUtil.retornaUsuarioLogado(authorization);
			Empresa emp = usuario.getEmpresa();
			EmpresaDTO dto = new EmpresaDTO();
			dto.setId(emp.getId());
			dto.setNome(emp.getNome());
			dto.setCnpj(emp.getCnpj());
			dto.setCep(emp.getCep());
			dto.setCidade(emp.getCidade());
			dto.setUf(emp.getUf());
			dto.setBairro(emp.getBairro());
			dto.setEndereco(emp.getEndereco());
			dto.setNumero(emp.getNumero());
			dto.setTelefone_contato(emp.getTelefone_contato());
			dto.setEmail_contato(emp.getEmail_contato());
			dto.setResponsavel(emp.getResponsavel());
			dto.setCpf(emp.getCpf());
			dto.setDt_nascimento(DateUtil.dataParaString(emp.getDt_nascimento()));
			dto.setSite(emp.getSite());
			dto.setPath_img(emp.getPath_img());
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}


	@RequestMapping(value="/alterar", method=RequestMethod.POST)
	public ResponseEntity<String> mudar(@RequestBody EmpresaDTO dto, @RequestHeader(value="Authorization") String authorization) {
		try {
			Usuario usuario = AuthUtil.retornaUsuarioLogado(authorization);
			String path = servletContext.getRealPath("/") + "resources/imagemempresa/";
			empresaService.alterar(dto, usuario.getEmpresa(),path);
			return ResponseEntity.status(HttpStatus.ACCEPTED)                 
					.body("");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(e.getMessage());
		}

	}

	@RequestMapping(value="/listarempresas", method=RequestMethod.GET)
	public List<ApresentacaoEmpresaDTO> listar( Integer pagina, String pesquisa) {
		List<ApresentacaoEmpresaDTO> empresas = empresaService.listarEstabelecimentos(pagina,20,pesquisa);
		return empresas;
	}


}
