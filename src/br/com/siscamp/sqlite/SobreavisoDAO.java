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

public class SobreavisoDAO {
	private static SQLiteDatabase bd;

	public SobreavisoDAO(Context context) {
		DatabaseCreate auxBd = new DatabaseCreate(context);
		bd = auxBd.getWritableDatabase();
	}

	public void limpaDadosSobreaviso() {
		bd.delete("TB_SOBREAVISO", null, null);
		bd.close();
	}

	public Funcionario mockBuscaTelefoneFuncionario(int criterio) {
		Funcionario funcDTO = new Funcionario();
		Cursor cursor = bd.query("TB_FUNCIONARIO", null, "MATRICULA = " + criterio, null, null, null, null);
		if (cursor.moveToNext()) {
			funcDTO.setTel(cursor.getString(cursor.getColumnIndex("FUNC_TELPESSOAL")));

		}
		return funcDTO;
	}

	public List<Funcionario> mockFiltraSobreaviso(String criterio) {

		Cursor cursor = innerJoinFiltra(criterio);

		List<Funcionario> lista = new ArrayList<Funcionario>();

		// if (!cursor.moveToNext()) {
		// Funcionario sobreavisoDTO = new Funcionario();
		// sobreavisoDTO.setNome("N/A SOBREAVISO!");
		// lista.add(sobreavisoDTO);
		// }

		while (cursor.moveToNext()) {
			Funcionario sobreavisoDTO = new Funcionario();
			sobreavisoDTO.setMatricula(cursor.getInt(cursor.getColumnIndex("MATRICULA")));
			sobreavisoDTO.setNome(cursor.getString(cursor.getColumnIndex("FUNC_NOME")));
			sobreavisoDTO.setTel(cursor.getString(cursor.getColumnIndex("FUNC_TELPESSOAL")));

			lista.add(sobreavisoDTO);
		}
		return lista;
	}

	public Cursor innerJoinFiltra(String criterio) {

		String sql = "SELECT * FROM tb_sobreaviso a "
				+ "inner join tb_funcionario b on a.fk_func_matricula = b.matricula where a.sobreaviso_data = ?";

		return bd.rawQuery(sql, new String[] { String.valueOf(criterio) });

	}

	public void buscarSobreaviso() {

		NetworkQueue mNetworkQueue = NetworkQueue.getInstance();

		mNetworkQueue.doGetArray(Constantes.URL_JSON_SOBREAVISO, Constantes.TAG_SOBREAVISO,
				new NetworkRequestCallback<JSONArray>() {
					@Override
					public void onRequestResponse(JSONArray jsonObject) {

						for (int i = 0; i < jsonObject.length(); i++) {
							try {
								JSONObject jsonObjLinha = jsonObject.getJSONObject(i);
								ContentValues values = new ContentValues();
								values.put("ID_SOBREAVISO", jsonObjLinha.getInt("idSobreaviso"));
								values.put("FK_FUNC_MATRICULA", jsonObjLinha.getInt("funcMatricula"));
								values.put("SOBREAVISO_DATA",
										ConverterUtil.JsonToString(jsonObjLinha.getString("data")));
								bd.insert("TB_SOBREAVISO", null, values);

							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

					}

					@Override
					public void onRequestError(Exception error) {
						Log.d(Constantes.TAG_SOBREAVISO, "GET ALL onRequestError!" + "\n" + error.getMessage());
					}
				});

	}

}
