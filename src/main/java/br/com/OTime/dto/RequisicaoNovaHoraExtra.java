package br.com.OTime.dto;

import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.com.OTime.model.HoraExtra;
import br.com.OTime.model.Status;
import br.com.OTime.model.Tipo;

public class RequisicaoNovaHoraExtra {
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private String descricao;
	private String data;
	private String horas;
	private String tipo;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHoras() {
		return horas;
	}
	public void setHoras(String horas) {
		this.horas = horas;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public HoraExtra toHoraExtra() {
		HoraExtra horaExtra = new HoraExtra();
		
		horaExtra.setDescricao(descricao);
		horaExtra.setData(LocalDate.parse(data, formatter));
		horaExtra.setHoras(Time.valueOf(horas));
		horaExtra.setTipo(Tipo.valueOf(tipo));
		horaExtra.setStatus(Status.AGUARDANDO);
		
		return horaExtra;
	}
	
}
