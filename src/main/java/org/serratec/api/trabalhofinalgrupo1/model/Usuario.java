package org.serratec.api.trabalhofinalgrupo1.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long id;
	
	@Column(length = 20)
	@NotBlank(message = "O Nome é obrigatório")
	private String nome;
	
	@Column(length = 30)
	@NotBlank(message = "O Sobrenome é obrigatório")
	private String sobrenome;
	
	@Email
	@Column(length = 60)
	@NotBlank(message = "O Email é obrigatório")
	private String email;
	
	@Column(length = 20)
	@NotBlank(message = "A senha é obrigatório")
	@Size(min = 8, max = 20, message = "A senha deve ter entre {min} e {max} caracteres")
	private String senha;
	
	@Column (name = "data_nascimento")
	private Date dataNascimento;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private Set<Postagem> postagens = new HashSet<>();
	
	@OneToMany(mappedBy = "id.seguido", cascade = CascadeType.ALL)
	private Set<Relacao> relacao = new HashSet<>();
	
	public Usuario() {
	}
	
	public Usuario(Long id, String nome, String sobrenome, String email, String senha, Date dataNascimento) {
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.email = email;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Set<Postagem> getPostagens() {
		return postagens;
	}

	public void setRelacao(Set<Relacao> relacao) {
		this.relacao = relacao;
	}
	
	public void setPostagens(Set<Postagem> postagens) {
		this.postagens = postagens;
	}
	
	public Set<Relacao> getRelacao() {
		return relacao;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(dataNascimento, email, id, nome, senha, sobrenome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(dataNascimento, other.dataNascimento) && Objects.equals(email, other.email)
				&& Objects.equals(id, other.id) && Objects.equals(nome, other.nome)
				&& Objects.equals(senha, other.senha) && Objects.equals(sobrenome, other.sobrenome);
	}
}