package rocha.euAtendo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rocha.euAtendo.dto.EmpresaDTO;
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
	
	
	public void salvar( Empresa empresa,Usuario usuario) throws Exception {
		String erros = empresa.validaObj();
		erros = erros+usuario.validaObj();
		
		Usuario usuarioExistente = usuarioService.encontrar(usuario.getEmail());
		if(usuarioExistente!= null) {
			erros = erros+"Já existe usuário com esse email";
		}		
		if(erros.isEmpty()) {
			empresaRepository.save(empresa);
			usuarioService.salvar(usuario);
		}else {
			throw new  Exception(erros);
		}		
	}

	public void processoNovaEmpresa(EmpresaDTO dto) throws Exception {
		Empresa empresa = new Empresa(dto.getNome(),dto.getCnpj(),dto.getCep(),dto.getUf(),dto.getCidade(),dto.getBairro(),dto.getEndereco(),dto.getNumero(),dto.getPath_img(),dto.getTelefone_contato(),dto.getEmail_contato(),dto.getResponsavel(),dto.getCpf(),DateUtil.stringParaData(dto.getDt_nascimento()),new Date());
		Usuario usuario = new Usuario(dto.getSenha(),dto.getSenha_confirmacao(),dto.getEmail_login(),empresa,new Date(),Boolean.TRUE);
		
		salvar(empresa,usuario);
	}
	
	public void alterar(EmpresaDTO dto,Empresa empresa) throws Exception {
		if(dto.getId().equals(empresa.getId())) {
			String erros = empresa.validaObj();
			if(erros.isEmpty()) {
				Empresa empresa_ = new Empresa(dto.getNome(),dto.getCnpj(),dto.getCep(),dto.getUf(),dto.getCidade(),dto.getBairro(),dto.getEndereco(),dto.getNumero(),dto.getPath_img(),dto.getTelefone_contato(),dto.getEmail_contato(),dto.getResponsavel(),dto.getCpf(),DateUtil.stringParaData(dto.getDt_nascimento()),new Date());
				empresa_.setId(dto.getId());
				empresaRepository.save(empresa_);
			}else {
				throw new  Exception(erros);
			}				
		}else {
			throw new  Exception("Ops ocorreu um erro");			
		}
	}
}
