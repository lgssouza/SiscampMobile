package br.com.siscamp.model;

import java.io.Serializable;

public class MovEstLinha implements Serializable {

	private static final long serialVersionUID = 6146083906972247514L;
	private Integer idMovEstLinha;
	private Integer idLinhaDist;
	private Integer idEstrutura;

	public MovEstLinha() {

	}

	public MovEstLinha(Integer idMovEstLinha, Integer idLinhaDist, Integer idEstrutura) {
		this.idMovEstLinha = idMovEstLinha;
		this.idLinhaDist = idLinhaDist;
		this.idEstrutura = idEstrutura;
	}

	public Integer getIdMovEstLinha() {
		return idMovEstLinha;
	}

	public void setIdMovEstLinha(Integer idMovEstLinha) {
		this.idMovEstLinha = idMovEstLinha;
	}

	public Integer getIdLinhaDist() {
		return idLinhaDist;
	}

	public void setIdLinhaDist(Integer idLinhaDist) {
		this.idLinhaDist = idLinhaDist;
	}

	public Integer getIdEstrutura() {
		return idEstrutura;
	}

	public void setIdEstrutura(Integer idEstrutura) {
		this.idEstrutura = idEstrutura;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}