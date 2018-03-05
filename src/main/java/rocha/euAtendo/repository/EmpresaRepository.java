package rocha.euAtendo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rocha.euAtendo.model.Empresa;

@Repository
public interface EmpresaRepository  extends CrudRepository<Empresa, Long> {	
	List<Empresa> findByNome(String nome);
	List<Empresa> findByCnpj(String cnpj);
}












