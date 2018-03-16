package rocha.euAtendo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
	private String site;
	private Date dt_nascimento;
	private Date dt_cadastro; 
	@OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
	private List<Especialidade> especialidades;
	@OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
	private List<Convenio> convenios;

	public String validaObj() {
		String validacao = "";
		String fim = "\n";
		if(nome == null || nome.isEmpty()) 
			validacao = validacao+"Informe o nome da empresa."+fim;

		if(nome !=null && nome.length() <= 3)
			validacao = validacao+"O nome da empresa deve conter no mínimo 3 caracteres."+fim;

		if(nome !=null && nome.length() <= 3)
			validacao = validacao+"O nome da empresa deve conter no mínimo 3 caracteres."+fim;

		if(cnpj == null || cnpj.length() != 14)
			validacao = validacao+"Informe um CNPJ com pelo menos 14 caracteres."+fim;

		if(cep == null || cep.length() != 8)
			validacao = validacao+"Informe um CEP com pelo menos 8 caracteres."+fim;

		if(uf == null || uf.length() != 2)
			validacao = validacao+"Informe um estado."+fim;

		if(cidade == null || cidade .length() <3 )
			validacao = validacao+"Informe uma cidade com pelo menos 3 caracteres."+fim;

		if(bairro == null || bairro .length() <3 )
			validacao = validacao+"Informe um bairro com pelo menos 3 caracteres."+fim;

		if(endereco == null || endereco .length() <10 )
			validacao = validacao+"Informe um bairro com pelo menos 10 caracteres."+fim;

		if(numero == null || numero .length() <1 )
			validacao = validacao+"Informe um numero com pelo menos 1 dígito."+fim;

		if(telefone_contato == null || telefone_contato .isEmpty() )
			validacao = validacao+"Informe ao menos um telefone de contato ."+fim;

		if(email_contato == null || email_contato .isEmpty() )
			validacao = validacao+"Informe ao menos um email de contato."+fim;

		if(responsavel == null || responsavel .isEmpty() )
			validacao = validacao+"Informe o nome do responsável pelo cadastro da empresa."+fim;

		if(cpf == null || cpf .isEmpty() )
			validacao = validacao+"Informe o CPF responsável pelo cadastro da empresa."+fim;

		if(dt_nascimento == null )
			validacao = validacao+"Informe a data de nascimento do  responsável pelo cadastro da empresa."+fim;

		if(dt_cadastro == null  )
			System.out.println("É necessário informar a data do cadastro do registro.");


		return validacao;
	}

	public Empresa(String nome, String cnpj, String cep, String uf, String cidade, String bairro, String endereco,
			String numero, String path_img, String telefone_contato, String email_contato, String responsavel,
			String cpf, Date dt_nascimento, Date dt_cadastro,String site) {
		super();
		this.nome = nome;
		this.cnpj = cnpj;
		this.cep = cep;
		this.uf = uf;
		this.cidade = cidade;
		this.bairro = bairro;
		this.endereco = endereco;
		this.numero = numero;
		this.path_img = path_img;
		this.telefone_contato = telefone_contato;
		this.email_contato = email_contato;
		this.responsavel = responsavel;
		this.cpf = cpf;
		this.dt_nascimento = dt_nascimento;
		this.dt_cadastro = dt_cadastro;
		this.site = site;
	}

	public Empresa() {
		super();
	}

}
