package rocha.euAtendo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rocha.euAtendo.model.Empresa;
import rocha.euAtendo.model.ExameImagem;

@Repository
public interface ExameImagemRepository  extends CrudRepository<ExameImagem, Long> {	
	List<ExameImagem> findByNome(String nome);
	List<ExameImagem> findByNomeAndEmpresa(String nome,Empresa empresa);
	List<ExameImagem> findByEmpresa(Empresa empresa);
	List<ExameImagem> findByIdAndEmpresa(Long id, Empresa empresa);
}












