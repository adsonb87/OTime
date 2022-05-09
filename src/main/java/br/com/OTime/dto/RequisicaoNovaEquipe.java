package br.com.OTime.dto;

import br.com.OTime.model.Equipe;

public class RequisicaoNovaEquipe {
	
	private Long id;
	private String descricao;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Equipe toEquipe() {
		Equipe equipe = new Equipe();
		
		equipe.setId(id);
		equipe.setDescricao(descricao);
		
		return equipe;
	}
}
