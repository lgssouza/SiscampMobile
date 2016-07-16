package br.com.siscamp.model;

import java.io.Serializable;

public class Vao implements Serializable {

	private static final long serialVersionUID = -7217791332819481880L;
	private Integer idVao;
	private Integer idEstInicial;
	private Integer idEstFinal;
	private Double distancia;
	private Double progressiva;
	private Double regressiva;

	public Vao() {

	}

	public Vao(Integer idVao, Integer idEstInicial, Integer idEstFinal, Double distancia, Double progressiva,
			Double regressiva) {

		this.idVao = idVao;
		this.idEstInicial = idEstInicial;
		this.idEstFinal = idEstFinal;
		this.distancia = distancia;
		this.progressiva = progressiva;
		this.regressiva = regressiva;
	}

	public Integer getIdVao() {
		return idVao;
	}

	public void setIdVao(Integer idVao) {
		this.idVao = idVao;
	}

	public Integer getIdEstInicial() {
		return idEstInicial;
	}

	public void setIdEstInicial(Integer idEstInicial) {
		this.idEstInicial = idEstInicial;
	}

	public Integer getIdEstFinal() {
		return idEstFinal;
	}

	public void setIdEstFinal(Integer idEstFinal) {
		this.idEstFinal = idEstFinal;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

	public Double getProgressiva() {
		return progressiva;
	}

	public void setProgressiva(Double progressiva) {
		this.progressiva = progressiva;
	}

	public Double getRegressiva() {
		return regressiva;
	}

	public void setRegressiva(Double regressiva) {
		this.regressiva = regressiva;
	}

}
