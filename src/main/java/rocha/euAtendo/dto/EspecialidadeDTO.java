package rocha.euAtendo.dto;

import lombok.Data;
import rocha.euAtendo.model.Especialidade;
@Data
public class EspecialidadeDTO {
	private Long id;
	private String nome;
	
	public void preencher(Especialidade especialidade) {
		this.id = especialidade.getId();
		this.nome = especialidade.getNome();
	}
	
	
}
