package br.com.siscamp.model;

import java.io.Serializable;

public class Linha implements Serializable {

	private static final long serialVersionUID = 7203705236638795956L;
	private Integer idLinha;
	private String nomenclatura;
	private String caboRaio;
	private String caboCond;
	private Integer largFaixa;

	public Linha() {

	}

	public Linha(Integer idLinha, String nomenclatura, String caboRaio, String caboCond, Integer largFaixa) {
		this.idLinha = idLinha;
		this.nomenclatura = nomenclatura;
		this.caboRaio = caboRaio;
		this.caboCond = caboCond;
		this.largFaixa = largFaixa;
	}

	public Integer getIdLinha() {
		return idLinha;
	}

	public void setIdLinha(Integer idLinha) {
		this.idLinha = idLinha;
	}

	public String getNomenclatura() {
		return nomenclatura;
	}

	public void setNomenclatura(String nomenclatura) {
		this.nomenclatura = nomenclatura;
	}

	public String getCaboRaio() {
		return caboRaio;
	}

	public void setCaboRaio(String caboRaio) {
		this.caboRaio = caboRaio;
	}

	public String getCaboCond() {
		return caboCond;
	}

	public void setCaboCond(String caboCond) {
		this.caboCond = caboCond;
	}

	public Integer getLargFaixa() {
		return largFaixa;
	}

	public void setLargFaixa(Integer largFaixa) {
		this.largFaixa = largFaixa;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}