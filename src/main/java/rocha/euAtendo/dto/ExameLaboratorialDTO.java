package rocha.euAtendo.dto;

import lombok.Data;
import rocha.euAtendo.model.ExameLaboratorial;
@Data
public class ExameLaboratorialDTO {
	private Long id;
	private String nome;
	
	public void preencher(ExameLaboratorial exameLab) {
		this.id = exameLab.getId();
		this.nome = exameLab.getNome();
	}
	
	
}
