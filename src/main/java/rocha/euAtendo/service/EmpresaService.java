package rocha.euAtendo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rocha.euAtendo.repository.EmpresaRepository;


@Service
public class EmpresaService {
	@Autowired
	EmpresaRepository empresaRepository;
	
	public void salvar() {
		
	}
}
