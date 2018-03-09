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
public class Convenio {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private Boolean ativo;
	private Date dt_registro;
	@ManyToOne(targetEntity=Empresa.class)
	@JoinColumn(name="empresa_id",referencedColumnName="id")
	private Empresa empresa;
	
	public String validaObj() {
		return null;
	}
}
