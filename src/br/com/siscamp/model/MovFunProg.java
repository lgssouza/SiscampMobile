package br.com.siscamp.model;

import java.io.Serializable;

public class MovFunProg implements Serializable {

	private static final long serialVersionUID = 8746154876313082016L;
	private Integer idMovFun;
	private Integer idProgServ;
	private Integer matricula;

	public MovFunProg() {

	}

	public MovFunProg(Integer idMovFun, Integer idProgServ, Integer matricula) {
		this.idMovFun = idMovFun;
		this.idProgServ = idProgServ;
		this.matricula = matricula;
	}

	public Integer getIdMovFun() {
		return idMovFun;
	}

	public void setIdMovFun(Integer idMovFun) {
		this.idMovFun = idMovFun;
	}

	public Integer getIdProgServ() {
		return idProgServ;
	}

	public void setIdProgServ(Integer idProgServ) {
		this.idProgServ = idProgServ;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

}
