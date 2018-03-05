package rocha.euAtendo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class PlanoConvenio {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private Boolean ativo;
	private Date dt_registro;
	@ManyToOne(targetEntity=Convenio.class)
	@JoinColumn(name="id_convenio",referencedColumnName="id")
	private Convenio convenio;
}
