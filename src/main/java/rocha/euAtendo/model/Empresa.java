	package rocha.euAtendo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Empresa  {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String cnpj;
	private String cep;	
	private String uf;
	private String cidade;
	private String bairro;
	private String endereco;	
	private String numero;	
	private String path_img;
	private String telefone_contato;
	private String email_contato;
	private String responsavel;
	private String cpf;
	private Date dt_nascimento;
	private Date dt_cadastro; 
	
}
