package rocha.euAtendo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rocha.euAtendo.dto.ConvenioDTO;
import rocha.euAtendo.model.Convenio;
import rocha.euAtendo.model.Usuario;
import rocha.euAtendo.service.ConvenioService;
import rocha.euAtendo.service.UsuarioService;
import rocha.euAtendo.util.AuthUtil;

@RestController
@RequestMapping("/convenio")
public class ConvenioController {
	@Autowired
	ConvenioService convenioService;
	@Autowired
	UsuarioService usuarioService;

	@RequestMapping(value="/cadastrar",method=RequestMethod.POST)
	public ResponseEntity<String> cadastrar( @RequestBody Convenio convenio, @RequestHeader(value="Authorization") String authorization) {
		try {
			Usuario usuario = AuthUtil.retornaUsuarioLogado(authorization);
			convenioService.cadastrar(convenio, usuario);
			return ResponseEntity.status(HttpStatus.ACCEPTED)                 
		            .body("");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
		            .body(e.getMessage());
		}
	}

	@RequestMapping(value="/listar",method=RequestMethod.GET)
	public List<ConvenioDTO> listar(@RequestHeader(value="Authorization") String authorization) {
		try {
			Usuario usuario =  AuthUtil.retornaUsuarioLogado(authorization);
			List<ConvenioDTO> dtos = convenioService.listarDtos(usuario.getEmpresa());
			return dtos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value="/remover",method=RequestMethod.POST)
	public ResponseEntity<String> remover(@RequestBody ConvenioDTO dto, @RequestHeader(value="Authorization") String authorization) {
		try {	
			Usuario usuario =  AuthUtil.retornaUsuarioLogado(authorization);
			convenioService.remover(dto,usuario.getEmpresa());
			return ResponseEntity.status(HttpStatus.ACCEPTED)                 
		            .body("");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		            .body(e.getMessage());
		}
	}
}
