package rocha.euAtendo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rocha.euAtendo.model.Convenio;
import rocha.euAtendo.model.Empresa;

@Repository
public interface ConvenioRepository  extends CrudRepository<Convenio, Long> {	
	List<Convenio> findByNome(String nome);
	List<Convenio> findByNomeAndEmpresa(String nome,Empresa empresa);
	List<Convenio> findByEmpresa(Empresa empresa);
	List<Convenio> findByIdAndEmpresa(Long id, Empresa empresa);

}












