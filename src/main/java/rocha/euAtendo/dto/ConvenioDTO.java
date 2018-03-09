package rocha.euAtendo.dto;

import lombok.Data;
import rocha.euAtendo.model.Convenio;
@Data
public class ConvenioDTO {
	private Long id;
	private String nome;
	
	public void preencher(Convenio convenio) {
		this.id = convenio.getId();
		this.nome = convenio.getNome();
	}
	
	
}
