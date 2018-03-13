package rocha.euAtendo.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import rocha.euAtendo.model.Empresa;

@Repository
public interface EmpresaRepository  extends PagingAndSortingRepository<Empresa, Long> {	
	List<Empresa> findByNome(String nome);
	List<Empresa> findByCnpj(String cnpj);
}












