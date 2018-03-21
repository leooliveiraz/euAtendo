package rocha.euAtendo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import rocha.euAtendo.dto.ExameImagemDTO;
import rocha.euAtendo.dto.ExameLaboratorialDTO;
import rocha.euAtendo.model.Empresa;
import rocha.euAtendo.model.ExameLaboratorial;
import rocha.euAtendo.model.Usuario;
import rocha.euAtendo.repository.ExameLaboratorialRepository;

@Service
public class ExameLaboratorialService {
	@Autowired
	ExameLaboratorialRepository exameLabRepository;
	public void cadastrar(ExameLaboratorial exameLab,Usuario usuario) throws Exception {
		exameLab.setAtivo(Boolean.TRUE);
		exameLab.setEmpresa(usuario.getEmpresa());
		exameLab.setDt_registro(new Date());
		String erros = exameLab.validaObj();
		if(verificaSeExiste(exameLab)) {
			erros = erros+ "Especialidade já Cadastrada";
			throw new Exception(erros);
		}
		if(erros == null || erros.isEmpty()) {
			exameLabRepository.save(exameLab);
		}else {
			throw new Exception(erros);
		}
	}
	
	public Boolean verificaSeExiste(ExameLaboratorial exameLab) {
		try {
			List<ExameLaboratorial> comparacao = exameLabRepository.findByNomeAndEmpresa(exameLab.getNome(), exameLab.getEmpresa());
			if(comparacao!= null && !comparacao.isEmpty()) {
				return Boolean.TRUE;				
			}else {
				return Boolean.FALSE;
			}
		}catch (NoResultException e) {
			return Boolean.FALSE;
		} 
		
	}

	public List<ExameLaboratorialDTO> listarDtos(Empresa empresa) {
		List<ExameLaboratorial> lista = exameLabRepository.findByEmpresa(empresa);
		List<ExameLaboratorialDTO> dtos = new ArrayList<>();
		lista.stream().sorted(Comparator.comparing(ExameLaboratorial::getNome)).forEach(esp -> dtos.add(novoDto(esp)));
		
		return dtos;
	}

	public void remover(ExameImagemDTO exameDTO, Empresa empresa) throws NotFoundException {
		List<ExameLaboratorial> lista = exameLabRepository.findByIdAndEmpresa(exameDTO.getId(),empresa);
		if(lista == null || lista.isEmpty()) {
			throw new NotFoundException("Registro não encontrado.");
		}else {
			lista.forEach(esp -> exameLabRepository.delete(esp));
		}
	}
	
	public ExameLaboratorialDTO novoDto(ExameLaboratorial esp) {
		ExameLaboratorialDTO dto = new ExameLaboratorialDTO();
		dto.preencher(esp);
		return dto;
	}
}
