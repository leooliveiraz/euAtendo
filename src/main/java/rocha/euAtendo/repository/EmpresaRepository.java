package rocha.euAtendo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rocha.euAtendo.model.Empresa;

@Repository
public interface EmpresaRepository  extends PagingAndSortingRepository<Empresa, Long> {	
	@Query("select distinct e from Empresa e left join e.especialidades esp left join e.convenios con  left join e.examesImg exi left join e.examesLab exl where (e.nome like :nomempresa or esp.nome like :especialidade or  con.nome like :convenio or concat(e.cidade, ' ', e.bairro, ' ', e.endereco) like :endereco or exi.nome like :exameImagem or exl.nome like :exameLaboratorial)")
	Page<Empresa> findByPesquisa(@Param("nomempresa") String nomempresa,	
			@Param("especialidade") String especialidade,
			@Param("convenio") String convenio,
			@Param("endereco") String endereco,
			@Param("exameImagem") String exameImagem,
			@Param("exameLaboratorial") String exameLaboratorial,
			Pageable pageable);
	Page<Empresa> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);
	List<Empresa> findByCnpj(String cnpj);
}












