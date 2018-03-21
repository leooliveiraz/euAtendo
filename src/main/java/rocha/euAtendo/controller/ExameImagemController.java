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

import rocha.euAtendo.dto.ExameImagemDTO;
import rocha.euAtendo.model.ExameImagem;
import rocha.euAtendo.model.Usuario;
import rocha.euAtendo.service.ExameImagemService;
import rocha.euAtendo.service.UsuarioService;
import rocha.euAtendo.util.AuthUtil;

@RestController
@RequestMapping("/examesimg")
public class ExameImagemController {
	@Autowired
	ExameImagemService exameImgService;
	@Autowired
	UsuarioService usuarioService;

	@RequestMapping(value="/cadastrar",method=RequestMethod.POST)
	public ResponseEntity<String> cadastrar( @RequestBody ExameImagem exameImg, @RequestHeader(value="Authorization") String authorization) {
		try {
			Usuario usuario = AuthUtil.retornaUsuarioLogado(authorization);
			exameImgService.cadastrar(exameImg, usuario);
			return ResponseEntity.status(HttpStatus.ACCEPTED)                 
					.body("");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(e.getMessage());
		}
	}

	@RequestMapping(value="/listar",method=RequestMethod.GET)
	public List<ExameImagemDTO> listar(@RequestHeader(value="Authorization") String authorization) {
		try {
			Usuario usuario =  AuthUtil.retornaUsuarioLogado(authorization);
			List<ExameImagemDTO> dtos = exameImgService.listarDtos(usuario.getEmpresa());
			return dtos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value="/remover",method=RequestMethod.POST)
	public ResponseEntity<String> remover(@RequestBody ExameImagemDTO dto, @RequestHeader(value="Authorization") String authorization) {
		try {	
			Usuario usuario =  AuthUtil.retornaUsuarioLogado(authorization);
			exameImgService.remover(dto,usuario.getEmpresa());
			return ResponseEntity.status(HttpStatus.ACCEPTED)                 
					.body("");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}
	}
}
