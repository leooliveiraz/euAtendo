package rocha.euAtendo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rocha.euAtendo.model.Especialidade;

@Repository
public interface EspecialidadeRepository  extends CrudRepository<Especialidade, Long> {	
	List<Especialidade> findByNome(String nome);
}












