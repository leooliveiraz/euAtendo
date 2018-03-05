package rocha.euAtendo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rocha.euAtendo.model.Convenio;

@Repository
public interface ConvenioRepository  extends CrudRepository<Convenio, Long> {	
	List<Convenio> findByNome(String nome);
}












