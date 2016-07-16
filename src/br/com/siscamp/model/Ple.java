package br.com.siscamp.model;

import java.io.Serializable;

public class Ple implements Serializable {

	private static final long serialVersionUID = -8144935569689310270L;
	private Integer idPle;
	private String supervisor;
	private String data;
	private String hrInicial;
	private String hrFinal;
	private Integer idLinhaDist;

	public Ple() {

	}

	public Ple(Integer idPle, String supervisor, String data, String hrInicial, String hrFinal, Integer idLinhaDist) {

		this.idPle = idPle;
		this.supervisor = supervisor;
		this.data = data;
		this.hrInicial = hrInicial;
		this.hrFinal = hrFinal;
		this.idLinhaDist = idLinhaDist;
	}

	public Integer getIdPle() {
		return idPle;
	}

	public void setIdPle(Integer idPle) {
		this.idPle = idPle;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHrInicial() {
		return hrInicial;
	}

	public void setHrInicial(String hrInicial) {
		this.hrInicial = hrInicial;
	}

	public String getHrFinal() {
		return hrFinal;
	}

	public void setHrFinal(String hrFinal) {
		this.hrFinal = hrFinal;
	}

	public Integer getIdLinhaDist() {
		return idLinhaDist;
	}

	public void setIdLinhaDist(Integer idLinhaDist) {
		this.idLinhaDist = idLinhaDist;
	}

}
