package br.com.siscamp.model;

import java.io.Serializable;

public class Plano implements Serializable {

	private static final long serialVersionUID = 4139231737609639295L;

	private int idLinhaDist;
	private String arquivo;
	private int versao;

	public Plano() {

	}

	public Plano(int idLinhaDist, String arquivo, int versao) {

		this.idLinhaDist = idLinhaDist;
		this.arquivo = arquivo;
		this.versao = versao;
	}

	public int getIdLinhaDist() {
		return idLinhaDist;
	}

	public void setIdLinhaDist(int idLinhaDist) {
		this.idLinhaDist = idLinhaDist;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public int getVersao() {
		return versao;
	}

	public void setVersao(int versao) {
		this.versao = versao;
	}

}
