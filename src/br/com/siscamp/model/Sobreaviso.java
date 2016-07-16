package br.com.siscamp.model;

import java.io.Serializable;

public class Sobreaviso implements Serializable {

	private static final long serialVersionUID = 4804214854469976714L;
	private Integer idSobreaviso;
	private Integer matricula;
	private String data;

	public Sobreaviso() {

	}

	public Sobreaviso(Integer idSobreaviso, Integer matricula, String data) {

		this.idSobreaviso = idSobreaviso;
		this.matricula = matricula;
		this.data = data;
	}

	public Integer getIdSobreaviso() {
		return idSobreaviso;
	}

	public void setIdSobreaviso(Integer idSobreaviso) {
		this.idSobreaviso = idSobreaviso;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
