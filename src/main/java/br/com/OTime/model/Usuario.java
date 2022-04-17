package br.com.OTime.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "usuarios")
public class Usuario {
	
	@Id
	private String chapa;
	
	private String nome;
	private String email;
	private String senha;
	
	@Enumerated(EnumType.STRING)
	private Perfil perfil;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Equipe equipe;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.LAZY)
	private List<HoraExtra> horasExtras;
	
	
	public String getChapa() {
		return chapa;
	}
	public void setChapa(String chapa) {
		this.chapa = chapa;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public Equipe getEquipe() {
		return equipe;
	}
	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}
	public List<HoraExtra> getHorasExtras() {
		return horasExtras;
	}
	public void setHorasExtras(List<HoraExtra> horasExtras) {
		this.horasExtras = horasExtras;
	}
	
	@Override
	public String toString() {
		return "Usuario [chapa=" + chapa + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", perfil="
				+ perfil + ", equipe=" + equipe + ", horasExtras=" + horasExtras + "]";
	}
	
}
