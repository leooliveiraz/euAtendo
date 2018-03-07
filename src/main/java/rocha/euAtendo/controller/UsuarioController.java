package rocha.euAtendo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rocha.euAtendo.model.Usuario;
import rocha.euAtendo.service.UsuarioService;

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
	public ResponseEntity<String> home(@RequestBody String email) {
		try {
			email = URLDecoder.decode(email, "ASCII");
			email = email.replaceAll("=", "");
			Usuario usuario = (Usuario) usuarioService.buscarPorEmail(email);
			return ResponseEntity.status(HttpStatus.ACCEPTED)                 
		            .body(usuario.getEmpresa().getNome());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
		            .body("Empresa não encontrada");
		}

	}
}
