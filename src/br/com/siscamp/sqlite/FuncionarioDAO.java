package br.com.siscamp.sqlite;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.siscamp.comum.Constantes;
import br.com.siscamp.model.Funcionario;
import br.com.siscamp.network.NetworkQueue;
import br.com.siscamp.network.NetworkRequestCallback;
import br.com.siscamp.util.ConverterUtil;

public class FuncionarioDAO {
	private static SQLiteDatabase bd;

	public FuncionarioDAO(Context context) {
		DatabaseCreate auxBd = new DatabaseCreate(context);
		bd = auxBd.getWritableDatabase();
	}

	public void limpaDadosFuncionarios() {
		bd.delete("TB_FUNCIONARIO", null, null);
		bd.close();
	}

	

	public List<Funcionario> mockListarContatos() {
		List<Funcionario> lista = new ArrayList<Funcionario>();

		Cursor cursor = bd.query("TB_FUNCIONARIO", null, null, null, null, null, "MATRICULA");

		while (cursor.moveToNext()) {
			Funcionario contDTO = new Funcionario();
			contDTO.setMatricula(cursor.getInt(cursor.getColumnIndex("MATRICULA")));
			contDTO.setNome(cursor.getString(cursor.getColumnIndex("FUNC_NOME")));
			contDTO.setTel(cursor.getString(cursor.getColumnIndex("FUNC_TELPESSOAL")));

			lista.add(contDTO);
		}
		return lista;
	}

	public List<Funcionario> mockFiltraContatos(CharSequence criterio) {
		List<Funcionario> lista = new ArrayList<Funcionario>();

		Cursor cursor = bd.query("TB_FUNCIONARIO", null, "FUNC_NOME like '%" + criterio + "%'", null, null, null, null);

		while (cursor.moveToNext()) {
			Funcionario contDTO = new Funcionario();
			contDTO.setMatricula(cursor.getInt(cursor.getColumnIndex("MATRICULA")));
			contDTO.setNome(cursor.getString(cursor.getColumnIndex("FUNC_NOME")));
			contDTO.setTel(cursor.getString(cursor.getColumnIndex("FUNC_TELPESSOAL")));

			lista.add(contDTO);
		}
		return lista;
	}

	public Funcionario mockBuscaTelefoneContatos(int criterio) {
		Funcionario contDTO = new Funcionario();
		Cursor cursor = bd.query("TB_FUNCIONARIO", null, "MATRICULA = " + criterio, null, null, null, null);
		Log.e("hoooooo", String.valueOf(criterio));
		if (cursor.moveToNext()) {
			contDTO.setTel(cursor.getString(cursor.getColumnIndex("FUNC_TELPESSOAL")));
		}
		return contDTO;
	}

	public void buscarFuncionarios() {

		NetworkQueue mNetworkQueue = NetworkQueue.getInstance();

		mNetworkQueue.doGetArray(Constantes.URL_JSON_FUNCIONARIO, Constantes.TAG_FUNCIONARIO,
				new NetworkRequestCallback<JSONArray>() {
					@Override
					public void onRequestResponse(JSONArray jsonObject) {

						for (int i = 0; i < jsonObject.length(); i++) {
							try {
								JSONObject jsonObjLinha = jsonObject.getJSONObject(i);
								ContentValues values = new ContentValues();
								values.put("MATRICULA", jsonObjLinha.getInt("matricula"));
								values.put("FUNC_NOME", ConverterUtil.JsonToString(jsonObjLinha.getString("nome")));
								values.put("FUNC_TELPESSOAL",
										ConverterUtil.JsonToString(jsonObjLinha.getString("tel")));

								bd.insert("TB_FUNCIONARIO", null, values);

							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

					}

					@Override
					public void onRequestError(Exception error) {
						Log.d(Constantes.TAG_FUNCIONARIO, "GET ALL onRequestError!" + "\n" + error.getMessage());
					}
				});

	}

}
