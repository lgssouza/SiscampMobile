package br.com.siscamp.model;

import java.io.Serializable;

public class ProgServico implements Serializable {

	private static final long serialVersionUID = -2174786157570867517L;
	private Integer idProgServico;
	private Integer idLinhaDist;
	private String nomeLinhaDist;

	private String descServico;
	private Integer semanaExec;
	private String dataExec;
	private Integer qtdPessoas;
	private Integer idPle;

	public ProgServico() {

	}

	public ProgServico(Integer idProgServico, Integer idLinhaDist, String descServico, Integer semanaExec,
			String dataExec, Integer qtdPessoas, Integer idPle) {

		this.idProgServico = idProgServico;
		this.idLinhaDist = idLinhaDist;
		this.descServico = descServico;
		this.semanaExec = semanaExec;
		this.dataExec = dataExec;
		this.qtdPessoas = qtdPessoas;
		this.idPle = idPle;
	}

	public Integer getIdProgServico() {
		return idProgServico;
	}

	public void setIdProgServico(Integer idProgServico) {
		this.idProgServico = idProgServico;
	}

	public Integer getIdLinhaDist() {
		return idLinhaDist;
	}

	public void setIdLinhaDist(Integer idLinhaDist) {
		this.idLinhaDist = idLinhaDist;
	}

	public String getDescServico() {
		return descServico;
	}

	public void setDescServico(String descServico) {
		this.descServico = descServico;
	}

	public Integer getSemanaExec() {
		return semanaExec;
	}

	public void setSemanaExec(Integer semanaExec) {
		this.semanaExec = semanaExec;
	}

	public String getDataExec() {
		return dataExec;
	}

	public void setDataExec(String data) {
		this.dataExec = data;
	}

	public Integer getQtdPessoas() {
		return qtdPessoas;
	}

	public void setQtdPessoas(Integer qtdPessoas) {
		this.qtdPessoas = qtdPessoas;
	}

	public Integer getIdPle() {
		return idPle;
	}

	public void setIdPle(Integer idPle) {
		this.idPle = idPle;
	}

	public String getNomeLinhaDist() {
		return nomeLinhaDist;
	}

	public void setNomeLinhaDist(String nomeLinhaDist) {
		this.nomeLinhaDist = nomeLinhaDist;
	}

}
