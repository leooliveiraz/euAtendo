package rocha.euAtendo.dto;

import lombok.Data;
import rocha.euAtendo.model.ExameImagem;
@Data
public class ExameImagemDTO {
	private Long id;
	private String nome;
	
	public void preencher(ExameImagem exameImg) {
		this.id = exameImg.getId();
		this.nome = exameImg.getNome();
	}
	
	
}
