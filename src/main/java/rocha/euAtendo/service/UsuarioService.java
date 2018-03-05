package rocha.euAtendo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import rocha.euAtendo.model.Usuario;
import rocha.euAtendo.repository.UsuarioRepository;
import rocha.euAtendo.util.SenhaUtil;


@Service
public class UsuarioService {
	@Autowired 
	UsuarioRepository usuarioRepository;

	public UserDetails buscarPorEmail(String email) {
		Usuario u =	usuarioRepository.findByEmail(email).get(0);
		return u;
	}
	
	public void salvar(Usuario usuario) {
		try {
			usuario.setSenha(SenhaUtil.criptografarSHA2(usuario.getSenha()));
			usuarioRepository.save(usuario);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void alterar(Usuario usuario) {
		Usuario usuarioValidacao = encontrar(usuario.getEmail());
		if(!usuario.getSenha().equals(usuarioValidacao.getSenha())) {
			usuario.setSenha(SenhaUtil.criptografarSHA2(usuario.getSenha()));
		}
		usuarioRepository.save(usuario);
	}

	public List<Usuario> listar() {
		List<Usuario> usuarios = new ArrayList<>();
		usuarioRepository.findAll().forEach(usuario-> usuarios.add(usuario));
		return usuarios;
	}
	public Usuario encontrar(String email) {
		return usuarioRepository.findByEmail(email).get(0);
	}
	

	public Boolean verificaExistencia(Usuario usuario) {
		try {

			Usuario usuarioExiste = usuarioRepository.findByEmail(usuario.getEmail()).get(0);
			if(usuarioExiste !=null)
				if(usuarioExiste.getEmail()!=null)
					if(!usuarioExiste.getEmail().isEmpty())
						return Boolean.TRUE;
			return Boolean.FALSE;
		} catch (Exception e) {
			return Boolean.FALSE;
		}
		
	}
}
