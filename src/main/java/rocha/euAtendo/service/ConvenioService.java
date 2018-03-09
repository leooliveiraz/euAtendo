package rocha.euAtendo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import rocha.euAtendo.dto.ConvenioDTO;
import rocha.euAtendo.model.Convenio;
import rocha.euAtendo.model.Empresa;
import rocha.euAtendo.model.Usuario;
import rocha.euAtendo.repository.ConvenioRepository;

@Service
public class ConvenioService {
	@Autowired
	ConvenioRepository convenioRepository;
	public void cadastrar(Convenio convenio,Usuario usuario) throws Exception {
		convenio.setAtivo(Boolean.TRUE);
		convenio.setEmpresa(usuario.getEmpresa());
		convenio.setDt_registro(new Date());
		String erros = convenio.validaObj();
		if(verificaSeExiste(convenio)) {
			erros = erros+ "Especialidade já Cadastrada";
			throw new Exception(erros);
		}
		if(erros == null || erros.isEmpty()) {
			convenioRepository.save(convenio);
		}else {
			throw new Exception(erros);
		}
	}
	
	public Boolean verificaSeExiste(Convenio convenio) {
		try {
			List<Convenio> comparacao = convenioRepository.findByNomeAndEmpresa(convenio.getNome(), convenio.getEmpresa());
			if(comparacao!= null && !comparacao.isEmpty()) {
				return Boolean.TRUE;				
			}else {
				return Boolean.FALSE;
			}
		}catch (NoResultException e) {
			return Boolean.FALSE;
		} 
		
	}

	public List<ConvenioDTO> listarDtos(Empresa empresa) {
		List<Convenio> lista = convenioRepository.findByEmpresa(empresa);
		List<ConvenioDTO> dtos = new ArrayList<>();
		lista.stream().sorted(Comparator.comparing(Convenio::getNome)).forEach(con -> dtos.add(novoDto(con)));
		
		return dtos;
	}

	public void remover(ConvenioDTO convenio, Empresa empresa) throws NotFoundException {
		List<Convenio> lista = convenioRepository.findByIdAndEmpresa(convenio.getId(),empresa);
		if(lista == null || lista.isEmpty()) {
			throw new NotFoundException("Registro não encontrado.");
		}else {
			lista.forEach(con -> convenioRepository.delete(con));
		}
	}
	
	public ConvenioDTO novoDto(Convenio esp) {
		ConvenioDTO dto = new ConvenioDTO();
		dto.preencher(esp);
		return dto;
	}
}
