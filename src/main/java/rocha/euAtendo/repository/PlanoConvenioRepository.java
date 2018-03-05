package rocha.euAtendo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rocha.euAtendo.model.PlanoConvenio;

@Repository
public interface PlanoConvenioRepository  extends CrudRepository<PlanoConvenio, Long> {	
	List<PlanoConvenio> findByNome(String nome);
}












