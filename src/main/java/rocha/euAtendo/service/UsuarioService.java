package rocha.euAtendo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import rocha.euAtendo.dto.EmpresaDTO;
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
		List<Usuario> usuarios = usuarioRepository.findByEmail(email);
		if(usuarios != null && !usuarios.isEmpty()) {
			Usuario usuario = usuarios.get(0);
			return usuario;
		}else {
			return null;
		}
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

	public void alterarSenha(Usuario usuario, EmpresaDTO dto) throws Exception {
		usuario.setSenha(dto.getSenha());
		usuario.setSenha_confirmacao(dto.getSenha_confirmacao());
		String erros = usuario.validaSenha();
		if(erros != null && !erros.isEmpty()) {
			throw new  Exception(erros);
		}
		usuario.setSenha(SenhaUtil.criptografarSHA2(usuario.getSenha()));
		usuarioRepository.save(usuario);
	}
}
