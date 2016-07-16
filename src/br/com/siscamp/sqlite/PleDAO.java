package br.com.siscamp.sqlite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.siscamp.comum.Constantes;
import br.com.siscamp.model.Ple;
import br.com.siscamp.network.NetworkQueue;
import br.com.siscamp.network.NetworkRequestCallback;
import br.com.siscamp.util.ConverterUtil;

public class PleDAO {
	private static SQLiteDatabase bd;

	public PleDAO(Context context) {
		DatabaseCreate auxBd = new DatabaseCreate(context);
		bd = auxBd.getWritableDatabase();
	}

	public void limpaDadosPle() {
		bd.delete("TB_PLE", null, null);
		bd.close();
	}

	public Ple mockBuscaPle(int param) {
		Cursor cursor = bd.query("TB_PLE", null, "ID_PLE=?", new String[] { String.valueOf(param) }, null, null,
				"ID_PLE");
		Ple ple = new Ple();

		if (cursor.moveToFirst()) {
			ple.setIdPle(cursor.getInt(cursor.getColumnIndex("ID_PLE")));
			ple.setData(cursor.getString(cursor.getColumnIndex("PLE_DATA")));
			ple.setHrInicial(cursor.getString(cursor.getColumnIndex("PLE_HRINICIAL")));
			ple.setHrFinal(cursor.getString(cursor.getColumnIndex("PLE_HRFINAL")));
			ple.setIdLinhaDist(cursor.getInt(cursor.getColumnIndex("FK_PLE_ID_LINHADIST")));
			ple.setSupervisor(cursor.getString(cursor.getColumnIndex("PLE_SUPERVISOR")));
		}
		return ple;
	}

	public void buscarPle() {

		NetworkQueue mNetworkQueue = NetworkQueue.getInstance();

		mNetworkQueue.doGetArray(Constantes.URL_JSON_PLE, Constantes.TAG_PLE, new NetworkRequestCallback<JSONArray>() {
			@Override
			public void onRequestResponse(JSONArray jsonObject) {

				for (int i = 0; i < jsonObject.length(); i++) {
					try {
						JSONObject jsonObjLinha = jsonObject.getJSONObject(i);
						ContentValues values = new ContentValues();
						values.put("ID_PLE", jsonObjLinha.getInt("idPle"));
						values.put("PLE_SUPERVISOR", ConverterUtil.JsonToString(jsonObjLinha.getString("supervisor")));
						values.put("PLE_DATA", ConverterUtil.JsonToString(jsonObjLinha.getString("data")));
						values.put("PLE_HRINICIAL", ConverterUtil.JsonToString(jsonObjLinha.getString("hrInicial")));
						values.put("PLE_HRFINAL", ConverterUtil.JsonToString(jsonObjLinha.getString("hrFinal")));
						values.put("FK_PLE_ID_LINHADIST", jsonObjLinha.getInt("idLinhaDist"));

						bd.insert("TB_PLE", null, values);

					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

			}

			@Override
			public void onRequestError(Exception error) {
				Log.d(Constantes.TAG_PLE, "GET ALL onRequestError!" + "\n" + error.getMessage());
			}
		});
	}

}
