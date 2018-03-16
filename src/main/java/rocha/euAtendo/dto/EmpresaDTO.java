package rocha.euAtendo.dto;

import lombok.Data;
@Data
public class EmpresaDTO {
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
	private String site;
	private String fotoempresa;
	
	private String responsavel;
	private String cpf;
	private String dt_nascimento;
	
	private String email_login;
	private String senha;
	private String senha_confirmacao;
		
}
