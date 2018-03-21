package rocha.euAtendo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rocha.euAtendo.model.Empresa;
import rocha.euAtendo.model.ExameLaboratorial;

@Repository
public interface ExameLaboratorialRepository  extends CrudRepository<ExameLaboratorial, Long> {	
	List<ExameLaboratorial> findByNome(String nome);
	List<ExameLaboratorial> findByNomeAndEmpresa(String nome,Empresa empresa);
	List<ExameLaboratorial> findByEmpresa(Empresa empresa);
	List<ExameLaboratorial> findByIdAndEmpresa(Long id, Empresa empresa);
}












