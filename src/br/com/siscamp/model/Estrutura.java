package br.com.siscamp.model;

import java.io.Serializable;

public class Estrutura implements Serializable {

	private static final long serialVersionUID = -4149741857030232885L;
	private Integer idEstrutura;
	private String numero;
	private String modelo;
	private String tipo;
	private String altura;

	public Estrutura() {

	}

	public Estrutura(Integer idEstrutura, String numero, String modelo, String tipo, String altura) {
		this.idEstrutura = idEstrutura;
		this.numero = numero;
		this.modelo = modelo;
		this.tipo = tipo;
		this.altura = altura;
	}

	public Integer getIdEstrutura() {
		return idEstrutura;
	}

	public void setIdEstrutura(Integer idEstrutura) {
		this.idEstrutura = idEstrutura;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}