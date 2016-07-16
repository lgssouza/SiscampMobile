package br.com.siscamp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.siscamp.comum.Constantes;

public class DatabaseCreate extends SQLiteOpenHelper {

	public DatabaseCreate(Context ctx) {
		super(ctx, Constantes.DB_CONFIG_NOME, null, Constantes.DB_CONFIG_VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase bd) {
		StringBuilder sql;
		// Tipos de dados aceitos no SQLite 3.
		// TEXT
		// NUMERIC
		// INTEGER
		// REAL
		// BLOB

		// Criando as Estruturas Base do Banco de Dados

		// Tabela de Movimentação das Estruturas nas Linhas de Transmissão
		sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS MOV_EST_LINHA (");
		sql.append(" ID_MOVESTLINHA INTEGER PRIMARY KEY AUTOINCREMENT,");
		sql.append(" FK_MOV_ID_LINHADIST INTEGER NOT NULL,");
		sql.append(" FK_MOV_ID_ESTRUTURA INTEGER NOT NULL)");

		bd.execSQL(sql.toString());

		// Tabela de Movimentação dos Funcionários nas Programações
		sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS MOV_FUN_PROG (");
		sql.append(" IF_MOV_FUN INTEGER PRIMARY KEY AUTOINCREMENT,");
		sql.append(" FK_MOV_ID_PROGSERVICO INTEGER NOT NULL,");
		sql.append(" FK_MOV_MATRICULA INTEGER NOT NULL)");

		bd.execSQL(sql.toString());

		// Tabela de Contatos
		sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS TB_CONTATOS (");
		sql.append(" ID_CONTATO INTEGER PRIMARY KEY AUTOINCREMENT,");
		sql.append(" CONTATO_NOME TEXT NOT NULL,");
		sql.append(" CONTATO_ESPEC TEXT,");
		sql.append(" CONTATO_TEL1 TEXT,");
		sql.append(" CONTATO_TEL2 TEXT,");
		sql.append(" CONTATO_CIDADE TEXT,");
		sql.append(" CONTATO_END TEXT)");

		bd.execSQL(sql.toString());

		// Tabela de Estruturas
		sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS TB_ESTRUTURA (");
		sql.append(" ID_ESTRUTURA INTEGER PRIMARY KEY AUTOINCREMENT,");
		sql.append(" EST_NUMERO TEXT NOT NULL,");
		sql.append(" EST_MODELO TEXT,");
		sql.append(" EST_TIPO TEXT,");
		sql.append(" EST_ALTURA TEXT)");

		bd.execSQL(sql.toString());

		// Tabela de Funcionários
		sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS TB_FUNCIONARIO (");
		sql.append(" MATRICULA INTEGER PRIMARY KEY AUTOINCREMENT,");
		sql.append(" FUNC_NOME TEXT NOT NULL,");
		sql.append(" FUNC_TELPESSOAL TEXT,");
		sql.append(" FUNC_LOGIN TEXT,");
		sql.append(" FUNC_SENHA TEXT)");

		bd.execSQL(sql.toString());

		// Tabela de Linha de Distribuição
		// * LINHADIST_PLANOCONT -> campo ainda não implementado,
		// por se tratar de um arquivo que iremos baixar
		sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS TB_LINHADISTRIBUICAO (");
		sql.append(" ID_LINHADIST INTEGER PRIMARY KEY AUTOINCREMENT,");
		sql.append(" LINHADIST_NOMENCLATURA TEXT NOT NULL,");
		sql.append(" LINHADIST_CABOPRAIO TEXT,");
		sql.append(" LINHADIST_CABOCOND TEXT,");
		sql.append(" LINHADIST_LARGFAIXA INTEGER NOT NULL)");

		bd.execSQL(sql.toString());

		// Tabela de PLE
		sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS TB_PLE (");
		sql.append(" ID_PLE INTEGER PRIMARY KEY AUTOINCREMENT,");
		sql.append(" PLE_SUPERVISOR TEXT NOT NULL,");
		sql.append(" PLE_DATA TEXT,");
		sql.append(" PLE_HRINICIAL TEXT,");
		sql.append(" PLE_HRFINAL TEXT,");
		sql.append(" FK_PLE_ID_LINHADIST INTEGER NOT NULL)");

		bd.execSQL(sql.toString());

		// Tabela de Programação de Serviços
		sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS TB_PROGSERVICO (");
		sql.append(" ID_PROGSERVICO INTEGER PRIMARY KEY AUTOINCREMENT,");
		sql.append(" FK_PROGS_ID_LINHADIST INTEGER NOT NULL,");
		sql.append(" PROGS_DESCSERVICO TEXT,");
		sql.append(" PROGS_SEMANAEXEC INTEGER,");
		sql.append(" PROGS_DATAEXEC TEXT,");
		sql.append(" PROGS_QTDPESSOAS INTEGER,");
		sql.append(" FK_PROGS_ID_PLE INTEGER NOT NULL)");

		bd.execSQL(sql.toString());

		// Tabela de Sobreavisos

		sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS TB_SOBREAVISO (");
		sql.append(" ID_SOBREAVISO INTEGER PRIMARY KEY AUTOINCREMENT,");
		sql.append(" FK_FUNC_MATRICULA INTEGER NOT NULL,");
		sql.append(" SOBREAVISO_DATA TEXT NOT NULL)");

		bd.execSQL(sql.toString());

		// Tabela de Vão
		sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS TB_VAO (");
		sql.append(" ID_VAO INTEGER PRIMARY KEY AUTOINCREMENT,");
		sql.append(" FK_ID_EST_INICIAL INTEGER NOT NULL,");
		sql.append(" FK_ID_EST_FINAL INTEGER NOT NULL,");
		sql.append(" VAO_DISTANCIA REAL,");
		sql.append(" VAO_PROGRESSIVA REAL,");
		sql.append(" VAO_REGRESSIVA REAL)");

		bd.execSQL(sql.toString());

		// Tabela de Planos
		sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS TB_PLANO (");
		sql.append(" FK_PLANO_ID_LINHADIST INTEGER PRIMARY KEY,");
		sql.append(" PLANO_NOME TEXT NOT NULL,");
		sql.append(" PLANO_VERSAO INTEGER NOT NULL)");
		bd.execSQL(sql.toString());

	}

	@Override
	public void onUpgrade(SQLiteDatabase bd, int arg1, int arg2) {
		bd.execSQL("drop table MOV_EST_LINHA;");
		bd.execSQL("drop table MOV_FUN_PROG;");
		bd.execSQL("drop table TB_CONTATOS;");
		bd.execSQL("drop table TB_ESTRUTURA;");
		bd.execSQL("drop table TB_FUNCIONARIO;");
		bd.execSQL("drop table TB_LINHADISTRIBUICAO;");
		bd.execSQL("drop table TB_PLE;");
		bd.execSQL("drop table TB_PROGSERVICO;");
		bd.execSQL("drop table TB_SOBREAVISO;");
		bd.execSQL("drop table TB_VAO;");

		onCreate(bd);
	}

}
