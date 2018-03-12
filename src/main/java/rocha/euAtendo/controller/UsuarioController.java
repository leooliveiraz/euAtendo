package rocha.euAtendo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rocha.euAtendo.dto.EmpresaDTO;
import rocha.euAtendo.model.Usuario;
import rocha.euAtendo.service.UsuarioService;
import rocha.euAtendo.util.AuthUtil;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	UsuarioService usuarioService;
	
	@RequestMapping(value="/verificaexistencia",method=RequestMethod.POST)
	public Boolean verificaUsuario(@RequestBody String email_login) throws UnsupportedEncodingException {
		email_login = URLDecoder.decode(email_login, "ASCII");
		email_login = email_login.replaceAll("=", "");
		Usuario usuario = new Usuario();
		usuario.setEmail(email_login);
		Boolean validacao = usuarioService.verificaExistencia(usuario);
		return validacao;
	}
	
	@RequestMapping(value="/buscarnome", method=RequestMethod.POST)
	public ResponseEntity<String> home(@RequestHeader(value="Authorization") String authorization) {
		try {
			String email = AuthUtil.retornaEmailLogado(authorization);
			Usuario usuario = (Usuario) usuarioService.buscarPorEmail(email);
			return ResponseEntity.status(HttpStatus.ACCEPTED)                 
		            .body(usuario.getEmpresa().getNome());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
		            .body("Empresa não encontrada");
		}
	}
	

	@RequestMapping(value="/alterarsenha", method=RequestMethod.POST)
	public ResponseEntity<String> alterarsenha(@RequestBody EmpresaDTO dto, @RequestHeader(value="Authorization") String authorization) {
		try {
			Usuario usuario = AuthUtil.retornaUsuarioLogado(authorization);
			usuarioService.alterarSenha(usuario,dto);			
			return ResponseEntity.status(HttpStatus.ACCEPTED)                 
		            .body("");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
		            .body(e.getMessage());
		}

	}
}
