package rocha.euAtendo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rocha.euAtendo.model.Empresa;
import rocha.euAtendo.model.Especialidade;

@Repository
public interface EspecialidadeRepository  extends CrudRepository<Especialidade, Long> {	
	List<Especialidade> findByNome(String nome);
	List<Especialidade> findByNomeAndEmpresa(String nome,Empresa empresa);
	List<Especialidade> findByEmpresa(Empresa empresa);
	List<Especialidade> findByIdAndEmpresa(Long id, Empresa empresa);
}












