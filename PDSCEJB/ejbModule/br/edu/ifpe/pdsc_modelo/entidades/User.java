package br.edu.ifpe.pdsc_modelo.entidades;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuario", catalog = "CartorioProtesto")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@JsonbTransient //Essa annotation impede que o attributo seja inserido na resposta
	@Column(name = "senha", nullable = false)
	private String senha;

	@JsonbTransient //Essa annotation impede que o attributo seja inserido na resposta
	@Column(name = "token", nullable = false)
	private String token;
	
	@Column(name = "login", nullable = false)
	private String login;
	
	@Column(name = "email", nullable = false)
	private String email;

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
}