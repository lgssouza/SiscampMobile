//package br.com.siscamp.sqlite;
//
//import java.util.Locale;
//import java.util.ResourceBundle;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import br.com.siscamp.comum.Constantes;
//
//public class LoginOpenHelper extends SQLiteOpenHelper {
//
//	private static ResourceBundle config = ResourceBundle.getBundle(Constantes.DB_CONFIG_PROPERTIES,Locale.getDefault());
//
//	public LoginOpenHelper(Context context) {
//		super(context, config.getString(Constantes.DB_CONFIG_NOME), null,
//				Integer.parseInt(config.getString(Constantes.DB_CONFIG_VERSAO)));			
//
//	}
//
//	@Override
//	public void onCreate(SQLiteDatabase db) {
//		StringBuilder sql = new StringBuilder();
//		sql.append("CREATE TABLE TB_USUARIO (");
//		sql.append(" ID_USUARIO INTEGER PRIMARY KEY AUTOINCREMENT,");
//		sql.append(" LOGIN TEXT NOT NULL,");
//		sql.append(" SENHA TEXT NOT NULL)");
//
//		db.execSQL(sql.toString());
//		
//		sql = new StringBuilder();
//		sql.append("CREATE TABLE TB_LINHADISTRIBUICAO (");
//		sql.append(" ID_LINHADIST INTEGER PRIMARY KEY AUTOINCREMENT,");
//		sql.append(" LINHADIST_NOMENCLATURA TEXT NOT NULL,");
//		sql.append(" LINHADIST_CABOPRAIO TEXT,");
//		sql.append(" LINHADIST_CABOCOND TEXT,");
//		sql.append(" LINHADIST_LARGFAIXA INT NOT NULL)");
//		
//		db.execSQL(sql.toString());
//		
//		mockPopulaUsuarios(db);
//		
//
//	}
//
//	private void mockPopulaUsuarios(SQLiteDatabase db) {
//		StringBuilder sql = new StringBuilder();
//		sql.append("INSERT INTO TB_USUARIO(LOGIN,SENHA) VALUES ('luiz','123')");
//		db.execSQL(sql.toString());
//
//		ContentValues values = new ContentValues();
//		values.put("login", "sandro");
//		values.put("senha", "123");
//		db.insert("TB_USUARIO", null, values);
//
//	}
//
//	@Override
//	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		onCreate(db);
//
//	}
//
//	public boolean validarLogin(String usuario, String senha) {
//
//		SQLiteDatabase db = getReadableDatabase();
//		Cursor cursor = db.query("TB_USUARIO", null, "LOGIN = ? AND SENHA = ?", new String[] { usuario, senha }, null,
//				null, null);
//
//		if (cursor.moveToFirst()) {
//			return true;
//		}
//		return false;
//	}
//
//}
