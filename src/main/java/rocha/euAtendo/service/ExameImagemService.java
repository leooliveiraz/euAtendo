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
import rocha.euAtendo.model.Empresa;
import rocha.euAtendo.model.ExameImagem;
import rocha.euAtendo.model.Usuario;
import rocha.euAtendo.repository.ExameImagemRepository;

@Service
public class ExameImagemService {
	@Autowired
	ExameImagemRepository exameImgRepository;
	public void cadastrar(ExameImagem exameImg,Usuario usuario) throws Exception {
		exameImg.setAtivo(Boolean.TRUE);
		exameImg.setEmpresa(usuario.getEmpresa());
		exameImg.setDt_registro(new Date());
		String erros = exameImg.validaObj();
		if(verificaSeExiste(exameImg)) {
			erros = erros+ "Especialidade já Cadastrada";
			throw new Exception(erros);
		}
		if(erros == null || erros.isEmpty()) {
			exameImgRepository.save(exameImg);
		}else {
			throw new Exception(erros);
		}
	}
	
	public Boolean verificaSeExiste(ExameImagem exameImg) {
		try {
			List<ExameImagem> comparacao = exameImgRepository.findByNomeAndEmpresa(exameImg.getNome(), exameImg.getEmpresa());
			if(comparacao!= null && !comparacao.isEmpty()) {
				return Boolean.TRUE;				
			}else {
				return Boolean.FALSE;
			}
		}catch (NoResultException e) {
			return Boolean.FALSE;
		} 
		
	}

	public List<ExameImagemDTO> listarDtos(Empresa empresa) {
		List<ExameImagem> lista = exameImgRepository.findByEmpresa(empresa);
		List<ExameImagemDTO> dtos = new ArrayList<>();
		lista.stream().sorted(Comparator.comparing(ExameImagem::getNome)).forEach(esp -> dtos.add(novoDto(esp)));
		
		return dtos;
	}

	public void remover(ExameImagemDTO exameDTO, Empresa empresa) throws NotFoundException {
		List<ExameImagem> lista = exameImgRepository.findByIdAndEmpresa(exameDTO.getId(),empresa);
		if(lista == null || lista.isEmpty()) {
			throw new NotFoundException("Registro não encontrado.");
		}else {
			lista.forEach(esp -> exameImgRepository.delete(esp));
		}
	}
	
	public ExameImagemDTO novoDto(ExameImagem esp) {
		ExameImagemDTO dto = new ExameImagemDTO();
		dto.preencher(esp);
		return dto;
	}
}
