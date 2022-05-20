package br.com.OTime.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.OTime.model.Perfil;
import br.com.OTime.model.Usuario;

public class RequisicaoNovoUsuario {
	
	private String chapa;
	private String nome;
	private String email;
	private String senha;
	private String perfil;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
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
		this.senha = encoder.encode(senha);
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	public Usuario toUsuario() {
		Usuario usuario = new Usuario();
		
		usuario.setChapa(chapa);
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenha(senha);
		usuario.setPerfil(Perfil.valueOf(perfil));
		
		return usuario;
	}
}
