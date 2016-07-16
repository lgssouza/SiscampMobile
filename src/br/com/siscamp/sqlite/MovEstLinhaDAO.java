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
import br.com.siscamp.model.MovEstLinha;
import br.com.siscamp.network.NetworkQueue;
import br.com.siscamp.network.NetworkRequestCallback;

public class MovEstLinhaDAO {
	private static SQLiteDatabase bd;

	public MovEstLinhaDAO(Context context) {
		DatabaseCreate auxBd = new DatabaseCreate(context);
		bd = auxBd.getWritableDatabase();
	}

	public void limpaDadosMovEstLinha() {
		bd.delete("MOV_EST_LINHA", null, null);
		bd.close();
	}

	public List<MovEstLinha> mockListarMovEstLinha() {
		List<MovEstLinha> lista = new ArrayList<MovEstLinha>();

		Cursor cursor = bd.query("MOV_EST_LINHA", null, null, null, null, null, "ID_MOVESTLINHA");

		while (cursor.moveToNext()) {
			MovEstLinha mvoDTO = new MovEstLinha();
			mvoDTO.setIdMovEstLinha(cursor.getInt(cursor.getColumnIndex("ID_MOVESTLINHA")));
			mvoDTO.setIdLinhaDist(cursor.getInt(cursor.getColumnIndex("FK_MOV_ID_LINHADIST")));
			mvoDTO.setIdEstrutura(cursor.getInt(cursor.getColumnIndex("FK_MOV_ID_ESTRUTURA")));

			lista.add(mvoDTO);
		}
		return lista;
	}

	public MovEstLinha mockBuscarMovEstLinha(int position) {
		Cursor cursor = bd.query("MOV_EST_LINHA", null, "FK_MOV_ID_LINHADIST=?",
				new String[] { String.valueOf(position) }, null, null, "FK_MOV_ID_ESTRUTURA");
		MovEstLinha est = new MovEstLinha();

		if (cursor.moveToFirst()) {
			est.setIdMovEstLinha(cursor.getInt(cursor.getColumnIndex("ID_MOVESTLINHA")));
			est.setIdLinhaDist(cursor.getInt(cursor.getColumnIndex("FK_MOV_ID_LINHADIST")));
			est.setIdEstrutura(cursor.getInt(cursor.getColumnIndex("FK_MOV_ID_ESTRUTURA")));

		}
		return est;
	}

	public void buscarMovEstLinha() {

		NetworkQueue mNetworkQueue = NetworkQueue.getInstance();

		mNetworkQueue.doGetArray(Constantes.URL_JSON_MOV_EST_LINHA, Constantes.TAG_MOV_EST_LINHA,
				new NetworkRequestCallback<JSONArray>() {
					@Override
					public void onRequestResponse(JSONArray jsonObject) {

						for (int i = 0; i < jsonObject.length(); i++) {
							try {
								JSONObject jsonObjLinha = jsonObject.getJSONObject(i);
								ContentValues values = new ContentValues();
								values.put("ID_MOVESTLINHA", jsonObjLinha.getInt("idMovEstLinha"));
								values.put("FK_MOV_ID_LINHADIST", jsonObjLinha.getInt("idLinhaDist"));
								values.put("FK_MOV_ID_ESTRUTURA", jsonObjLinha.getInt("idEstrutura"));

								bd.insert("MOV_EST_LINHA", null, values);

							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

					}

					@Override
					public void onRequestError(Exception error) {
						Log.d(Constantes.TAG_MOV_EST_LINHA, "GET ALL onRequestError!" + "\n" + error.getMessage());
					}
				});

	}
}
