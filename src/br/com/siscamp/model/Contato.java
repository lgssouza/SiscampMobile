package br.com.siscamp.model;

import java.io.Serializable;

public class Contato implements Serializable {

	private static final long serialVersionUID = -112096255708893279L;
	private Integer idContato;
	private String nome;
	private String especialidade;
	private String tel1;
	private String tel2;
	private String cidade;
	private String endereco;

	public Contato() {

	}

	public Contato(Integer idContato, String nome, String especialidade, String tel1, String tel2, String cidade,
			String endereco) {

		this.idContato = idContato;
		this.nome = nome;
		this.especialidade = especialidade;
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.cidade = cidade;
		this.endereco = endereco;
	}

	public Integer getIdContato() {
		return idContato;
	}

	public void setIdContato(Integer idContato) {
		this.idContato = idContato;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public String getTel1() {
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

}
