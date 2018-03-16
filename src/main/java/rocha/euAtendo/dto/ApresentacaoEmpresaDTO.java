package rocha.euAtendo.dto;

import java.util.List;

import lombok.Data;
import rocha.euAtendo.model.Empresa;
@Data
public class ApresentacaoEmpresaDTO {
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
	
	private List<ConvenioDTO> convenios;
	private List<EspecialidadeDTO> especialidades;
	

	public void  preencher(Empresa e) {
		this.id = e.getId();
		this.nome = e.getNome();
		this.cnpj = e.getCnpj();
		this.cep = e.getCep();
		this.uf = e.getUf();
		this.cidade = e.getCidade();
		this.bairro = e.getBairro();
		this.endereco = e.getEndereco();
		this.numero = e.getNumero();
		this.telefone_contato = e.getTelefone_contato();
		this.email_contato = e.getEmail_contato();
		this.site = e.getSite();
		this.setPath_img(e.getPath_img());
	}

}
