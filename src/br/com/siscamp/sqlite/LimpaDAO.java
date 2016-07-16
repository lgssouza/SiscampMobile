package br.com.siscamp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class LimpaDAO {
	private SQLiteDatabase bd;

	public LimpaDAO(Context context) {
		DatabaseCreate auxBd = new DatabaseCreate(context);
		bd = auxBd.getWritableDatabase();
	}

	public void limpaDados() {

		bd.delete("TB_ESTRUTURA", null, null);
		bd.delete("TB_FUNCIONARIO", null, null);
		bd.delete("TB_CONTATOS", null, null);
		bd.delete("TB_LINHADISTRIBUICAO", null, null);
		bd.delete("MOV_EST_LINHA", null, null);
		bd.delete("MOV_FUN_PROG", null, null);
		bd.delete("TB_PLE", null, null);
		bd.delete("TB_PROGSERVICO", null, null);
		bd.delete("TB_SOBREAVISO", null, null);
		bd.delete("TB_VAO", null, null);
		bd.close();
	}

}
