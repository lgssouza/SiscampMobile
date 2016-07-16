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
import br.com.siscamp.model.Linha;
import br.com.siscamp.network.NetworkQueue;
import br.com.siscamp.network.NetworkRequestCallback;
import br.com.siscamp.util.ConverterUtil;

public class LinhaDAO {
	private static SQLiteDatabase bd;

	public LinhaDAO(Context context) {
		DatabaseCreate auxBd = new DatabaseCreate(context);
		bd = auxBd.getWritableDatabase();
	}

	public void limpaDadosLinhas() {
		bd.delete("TB_LINHADISTRIBUICAO", null, null);
		bd.close();
	}

	public List<Linha> mockFiltraLinhas(String criterio) {
		List<Linha> lista = new ArrayList<Linha>();

		Cursor cursor = bd.query("TB_LINHADISTRIBUICAO", null, "LINHADIST_NOMENCLATURA like '%" + criterio + "%'", null,
				null, null, null);

		while (cursor.moveToNext()) {
			Linha linhasDTO = new Linha();
			linhasDTO.setIdLinha(cursor.getInt(cursor.getColumnIndex("ID_LINHADIST")));
			linhasDTO.setNomenclatura(cursor.getString(cursor.getColumnIndex("LINHADIST_NOMENCLATURA")));
			linhasDTO.setCaboRaio(cursor.getString(cursor.getColumnIndex("LINHADIST_CABOPRAIO")));
			linhasDTO.setCaboCond(cursor.getString(cursor.getColumnIndex("LINHADIST_CABOCOND")));
			linhasDTO.setLargFaixa(cursor.getInt(cursor.getColumnIndex("LINHADIST_LARGFAIXA")));

			lista.add(linhasDTO);
		}
		return lista;
	}

	public List<Linha> mockListarLinhas() {
		List<Linha> lista = new ArrayList<Linha>();

		Cursor cursor = bd.query("TB_LINHADISTRIBUICAO", null, null, null, null, null, "ID_LINHADIST");

		while (cursor.moveToNext()) {
			Linha linhasDTO = new Linha();
			linhasDTO.setIdLinha(cursor.getInt(cursor.getColumnIndex("ID_LINHADIST")));
			linhasDTO.setNomenclatura(cursor.getString(cursor.getColumnIndex("LINHADIST_NOMENCLATURA")));
			linhasDTO.setCaboRaio(cursor.getString(cursor.getColumnIndex("LINHADIST_CABOPRAIO")));
			linhasDTO.setCaboCond(cursor.getString(cursor.getColumnIndex("LINHADIST_CABOCOND")));
			linhasDTO.setLargFaixa(cursor.getInt(cursor.getColumnIndex("LINHADIST_LARGFAIXA")));

			lista.add(linhasDTO);
		}
		return lista;
	}

	public Cursor mockTeste() {

		Cursor cursor = bd.query("TB_LINHADISTRIBUICAO", null, null, null, null, null, "ID_LINHADIST");

		return cursor;
	}

	public Linha mockBuscaLinha(int position) {
		Cursor cursor = bd.query("TB_LINHADISTRIBUICAO", null, "ID_LINHADIST=?",
				new String[] { String.valueOf(position) }, null, null, "ID_LINHADIST");
		Linha linha = new Linha();

		if (cursor.moveToFirst()) {
			linha.setIdLinha(cursor.getInt(cursor.getColumnIndex("ID_LINHADIST")));
			linha.setNomenclatura(cursor.getString(cursor.getColumnIndex("LINHADIST_NOMENCLATURA")));
			linha.setCaboRaio(cursor.getString(cursor.getColumnIndex("LINHADIST_CABOPRAIO")));
			linha.setCaboCond(cursor.getString(cursor.getColumnIndex("LINHADIST_CABOCOND")));
			linha.setLargFaixa(cursor.getInt(cursor.getColumnIndex("LINHADIST_LARGFAIXA")));
		}
		return linha;
	}

	public void buscarLinhas() {

		NetworkQueue mNetworkQueue = NetworkQueue.getInstance();

		mNetworkQueue.doGetArray(Constantes.URL_JSON_LINHA, Constantes.TAG_LINHA,
				new NetworkRequestCallback<JSONArray>() {
					@Override
					public void onRequestResponse(JSONArray jsonObject) {

						for (int i = 0; i < jsonObject.length(); i++) {
							try {
								JSONObject jsonObjLinha = jsonObject.getJSONObject(i);
								ContentValues values = new ContentValues();
								values.put("ID_LINHADIST", jsonObjLinha.getInt("idLinha"));
								values.put("LINHADIST_NOMENCLATURA",
										ConverterUtil.JsonToString(jsonObjLinha.getString("nomenclatura")));
								values.put("LINHADIST_CABOPRAIO",
										ConverterUtil.JsonToString(jsonObjLinha.getString("caboRaio")));
								values.put("LINHADIST_CABOCOND",
										ConverterUtil.JsonToString(jsonObjLinha.getString("caboCond")));
								values.put("LINHADIST_LARGFAIXA", jsonObjLinha.getInt("largFaixa"));

								bd.insert("TB_LINHADISTRIBUICAO", null, values);

							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

					}

					@Override
					public void onRequestError(Exception error) {
						Log.d(Constantes.TAG_LINHA, "GET ALL onRequestError!" + "\n" + error.getMessage());
					}
				});

	}
}
