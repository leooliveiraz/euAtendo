package rocha.euAtendo.dto;

import lombok.Data;
import rocha.euAtendo.model.Especialidade;
@Data
public class EspecialidadeDTO {
	private Long id;
	private String nome;
	
	public EspecialidadeDTO(Especialidade especialidade) {
		this.id = especialidade.getId();
		this.nome = especialidade.getNome();
	}
	public EspecialidadeDTO(Long id) {
		this.id = id;
	}
	public EspecialidadeDTO() {
		super();
	}
	
	
}
