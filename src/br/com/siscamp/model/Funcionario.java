package br.com.siscamp.model;

import java.io.Serializable;

public class Funcionario implements Serializable {

	private static final long serialVersionUID = 6944478591694492378L;
	private Integer matricula;
	private String nome;
	private String tel;

	public Funcionario() {

	}

	public Funcionario(Integer matricula, String nome, String tel) {

		this.matricula = matricula;
		this.nome = nome;
		this.tel = tel;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}
