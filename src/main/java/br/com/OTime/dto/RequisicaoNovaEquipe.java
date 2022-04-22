package br.com.OTime.dto;

import br.com.OTime.model.Equipe;

public class RequisicaoNovaEquipe {

	private String descricao;
	
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Equipe toEquipe() {
		Equipe equipe = new Equipe();
		
		equipe.setDescricao(descricao);
		
		return equipe;
	}
}
