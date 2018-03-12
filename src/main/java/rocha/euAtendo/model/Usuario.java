	package rocha.euAtendo.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Entity
public class Usuario implements UserDetails {
	
	private static final long serialVersionUID = 8057726859592646555L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String senha;
	@Transient
	private String senha_confirmacao;
	private String email;
	@ManyToOne(targetEntity=Empresa.class)
	@JoinColumn(name="id_empresa",referencedColumnName="id")
	private Empresa empresa;
	private Date dt_registro;
	private Boolean ativo;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	@Override
	public String getPassword() {
		return getSenha();
	}
	@Override
	public String getUsername() {
		return getEmail();
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return ativo;
	}
	public Usuario(String senha,String senha_confirmacao, String email, Empresa empresa, Date dt_registro, Boolean ativo) {
		super();
		this.senha = senha;
		this.senha_confirmacao  = senha_confirmacao;
		this.email = email;
		this.empresa = empresa;
		this.dt_registro = dt_registro;
		this.ativo = ativo;
	}

	public String validaObj() {
		String erros = "";
		String fim = "\n";
		if(email == null || email.length() <3 || !email.contains("@")) 
			erros = erros+"Informe um email válido com pelo menos 3 caracteres."+fim;
		if(senha == null || senha.isEmpty()) 
			erros = erros+"Informe uma senha."+fim;
		if(senha_confirmacao == null || senha_confirmacao.isEmpty()) 
			erros = erros+"Informe a confirmação da senha."+fim;
		if(senha != null && senha_confirmacao != null && !senha.equals(senha_confirmacao)) 
			erros = erros+"Informe senhas iguais."+fim;
		return erros;
		
	}
	public String validaSenha() {
		String erros = "";
		String fim = "\n";
		if(senha == null || senha.isEmpty()) 
			erros = erros+"Informe uma senha."+fim;
		if(senha_confirmacao == null || senha_confirmacao.isEmpty()) 
			erros = erros+"Informe a confirmação da senha."+fim;
		if(senha != null && senha_confirmacao != null && !senha.equals(senha_confirmacao)) 
			erros = erros+"Informe senhas iguais."+fim;
		return erros;
		
	}
	public Usuario() {
		super();
	}
	
	
}
