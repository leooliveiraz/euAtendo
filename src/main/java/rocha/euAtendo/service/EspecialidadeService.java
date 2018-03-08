package rocha.euAtendo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import rocha.euAtendo.dto.EspecialidadeDTO;
import rocha.euAtendo.model.Empresa;
import rocha.euAtendo.model.Especialidade;
import rocha.euAtendo.model.Usuario;
import rocha.euAtendo.repository.EspecialidadeRepository;

@Service
public class EspecialidadeService {
	@Autowired
	EspecialidadeRepository especialidadeRepository;
	public void cadastrar(Especialidade especialidade,Usuario usuario) throws Exception {
		especialidade.setAtivo(Boolean.TRUE);
		especialidade.setEmpresa(usuario.getEmpresa());
		especialidade.setDt_registro(new Date());
		String erros = especialidade.validaObj();
		if(verificaSeExiste(especialidade)) {
			erros = erros+ "Especialidade já Cadastrada";
			throw new Exception(erros);
		}
		if(erros == null || erros.isEmpty()) {
			especialidadeRepository.save(especialidade);
		}else {
			throw new Exception(erros);
		}
	}
	
	public Boolean verificaSeExiste(Especialidade especialidade) {
		try {
			List<Especialidade> comparacao = especialidadeRepository.findByNomeAndEmpresa(especialidade.getNome(), especialidade.getEmpresa());
			if(comparacao!= null && !comparacao.isEmpty()) {
				return Boolean.TRUE;				
			}else {
				return Boolean.FALSE;
			}
		}catch (NoResultException e) {
			return Boolean.FALSE;
		} 
		
	}

	public List<EspecialidadeDTO> listarDtos(Empresa empresa) {
		List<Especialidade> lista = especialidadeRepository.findByEmpresa(empresa);
		List<EspecialidadeDTO> dtos = new ArrayList<>();
		lista.stream().sorted(Comparator.comparing(Especialidade::getNome)).forEach(esp -> dtos.add(novoDto(esp)));
		
		return dtos;
	}

	public void remover(EspecialidadeDTO especialidade, Empresa empresa) throws NotFoundException {
		List<Especialidade> lista = especialidadeRepository.findByIdAndEmpresa(especialidade.getId(),empresa);
		if(lista == null || lista.isEmpty()) {
			throw new NotFoundException("Registro não encontrado.");
		}else {
			lista.forEach(esp -> especialidadeRepository.delete(esp));
		}
	}
	
	public EspecialidadeDTO novoDto(Especialidade esp) {
		EspecialidadeDTO dto = new EspecialidadeDTO();
		dto.preencher(esp);
		return dto;
	}
}
