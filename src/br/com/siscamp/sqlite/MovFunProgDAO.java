package br.com.siscamp.sqlite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.siscamp.comum.Constantes;
import br.com.siscamp.network.NetworkQueue;
import br.com.siscamp.network.NetworkRequestCallback;

public class MovFunProgDAO {
	private static SQLiteDatabase bd;

	public MovFunProgDAO(Context context) {
		DatabaseCreate auxBd = new DatabaseCreate(context);
		bd = auxBd.getWritableDatabase();
	}

	public void limpaDadosMovFunProg() {
		bd.delete("MOV_FUN_PROG", null, null);
		bd.close();
	}

	public void buscarMovFunProg() {

		NetworkQueue mNetworkQueue = NetworkQueue.getInstance();

		mNetworkQueue.doGetArray(Constantes.URL_JSON_MOV_FUN_PROG, Constantes.TAG_MOV_FUN_PROG,
				new NetworkRequestCallback<JSONArray>() {
					@Override
					public void onRequestResponse(JSONArray jsonObject) {

						for (int i = 0; i < jsonObject.length(); i++) {
							try {
								JSONObject jsonObjLinha = jsonObject.getJSONObject(i);
								ContentValues values = new ContentValues();
								values.put("ID_MOV_FUN", jsonObjLinha.getInt("idMovFun"));
								values.put("FK_MOV_ID_PROGSERVICO", jsonObjLinha.getInt("idProgServico"));
								values.put("FK_MOV_MATRICULA", jsonObjLinha.getInt("idMatricula"));

								bd.insert("MOV_FUN_PROG", null, values);

							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

					}

					@Override
					public void onRequestError(Exception error) {
						Log.d(Constantes.TAG_MOV_FUN_PROG, "GET ALL onRequestError!" + "\n" + error.getMessage());
					}
				});

	}

}
