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
import br.com.siscamp.model.Estrutura;
import br.com.siscamp.network.NetworkQueue;
import br.com.siscamp.network.NetworkRequestCallback;
import br.com.siscamp.util.ConverterUtil;

public class EstruturaDAO {
	private SQLiteDatabase bd;

	public EstruturaDAO(Context context) {
		DatabaseCreate auxBd = new DatabaseCreate(context);
		bd = auxBd.getWritableDatabase();
	}

	// public void limpaDadosEstruturas() {
	// bd.delete("TB_ESTRUTURA", null, null);
	// bd.close();
	// }

	

	public List<Estrutura> mockListarEstruturas() {
		List<Estrutura> lista = new ArrayList<Estrutura>();

		Cursor cursor = bd.query("TB_ESTRUTURA", null, null, null, null, null, "ID_ESTRUTURA");

		while (cursor.moveToNext()) {
			Estrutura estDTO = new Estrutura();
			estDTO.setIdEstrutura(cursor.getInt(cursor.getColumnIndex("ID_ESTRUTURA")));
			estDTO.setNumero(cursor.getString(cursor.getColumnIndex("EST_NUMERO")));
			estDTO.setModelo(cursor.getString(cursor.getColumnIndex("EST_MODELO")));
			estDTO.setTipo(cursor.getString(cursor.getColumnIndex("EST_TIPO")));
			estDTO.setAltura(cursor.getString(cursor.getColumnIndex("EST_ALTURA")));

			lista.add(estDTO);
		}
		return lista;
	}

	public Estrutura mockBuscaEstruturas(int position) {
		Cursor cursor = bd.query("TB_ESTRUTURA", null, "ID_ESTRUTURA=?", new String[] { String.valueOf(position) },
				null, null, "ID_ESTRUTURA");
		Estrutura est = new Estrutura();

		if (cursor.moveToFirst()) {
			est.setIdEstrutura(cursor.getInt(cursor.getColumnIndex("ID_ESTRUTURA")));
			est.setNumero(cursor.getString(cursor.getColumnIndex("EST_NUMERO")));
			est.setModelo(cursor.getString(cursor.getColumnIndex("EST_MODELO")));
			est.setTipo(cursor.getString(cursor.getColumnIndex("EST_TIPO")));
			est.setAltura(cursor.getString(cursor.getColumnIndex("EST_ALTURA")));
		}
		return est;
	}

	public List<Estrutura> mockBuscaEstruturasLinha(int id) {

		Cursor cursor = innerJoinBusca(id);

		List<Estrutura> lista = new ArrayList<Estrutura>();

		while (cursor.moveToNext()) {
			Estrutura estDTO = new Estrutura();
			estDTO.setIdEstrutura(cursor.getInt(cursor.getColumnIndex("ID_ESTRUTURA")));
			estDTO.setNumero(cursor.getString(cursor.getColumnIndex("EST_NUMERO")));
			estDTO.setModelo(cursor.getString(cursor.getColumnIndex("EST_MODELO")));
			estDTO.setTipo(cursor.getString(cursor.getColumnIndex("EST_TIPO")));
			estDTO.setAltura(cursor.getString(cursor.getColumnIndex("EST_ALTURA")));

			lista.add(estDTO);
		}
		return lista;
	}

	public List<Estrutura> mockFiltraEstruturasLinha(int id, CharSequence criterio) {

		Cursor cursor = innerJoinFiltra(id, criterio);

		List<Estrutura> lista = new ArrayList<Estrutura>();

		while (cursor.moveToNext()) {
			Estrutura estDTO = new Estrutura();
			estDTO.setIdEstrutura(cursor.getInt(cursor.getColumnIndex("ID_ESTRUTURA")));
			estDTO.setNumero(cursor.getString(cursor.getColumnIndex("EST_NUMERO")));
			estDTO.setModelo(cursor.getString(cursor.getColumnIndex("EST_MODELO")));
			estDTO.setTipo(cursor.getString(cursor.getColumnIndex("EST_TIPO")));
			estDTO.setAltura(cursor.getString(cursor.getColumnIndex("EST_ALTURA")));

			lista.add(estDTO);
		}
		return lista;
	}

	public Cursor innerJoinBusca(int id) {

		String sql = "SELECT * FROM tb_estrutura a "
				+ "inner join mov_est_linha b on b.FK_MOV_ID_ESTRUTURA = a.ID_ESTRUTURA "
				+ "inner join tb_linhadistribuicao c on c.id_linhadist = b.fk_mov_id_linhadist "
				+ "where c.id_linhadist=?";

		return bd.rawQuery(sql, new String[] { String.valueOf(id) });

	}

	public Cursor innerJoinFiltra(int id, CharSequence criterio) {

		String sql = "SELECT * FROM tb_estrutura a "
				+ "inner join mov_est_linha b on b.FK_MOV_ID_ESTRUTURA = a.ID_ESTRUTURA "
				+ "inner join tb_linhadistribuicao c on c.id_linhadist = b.fk_mov_id_linhadist "
				+ "where c.id_linhadist=? and a.est_numero=?";

		return bd.rawQuery(sql, new String[] { String.valueOf(id), String.valueOf(criterio) });

	}

	public void buscarEstruturas() {

		NetworkQueue mNetworkQueue = NetworkQueue.getInstance();

		mNetworkQueue.doGetArray(Constantes.URL_JSON_ESTRUTURA, Constantes.TAG_ESTRUTURA,
				new NetworkRequestCallback<JSONArray>() {
					@Override
					public void onRequestResponse(JSONArray jsonObject) {

						for (int i = 0; i < jsonObject.length(); i++) {
							try {
								JSONObject jsonObjLinha = jsonObject.getJSONObject(i);

								ContentValues values = new ContentValues();
								values.put("ID_ESTRUTURA", jsonObjLinha.getInt("idEstrutura"));
								values.put("EST_NUMERO", ConverterUtil.JsonToString(jsonObjLinha.getString("numero")));
								values.put("EST_MODELO", ConverterUtil.JsonToString(jsonObjLinha.getString("modelo")));
								values.put("EST_TIPO", ConverterUtil.JsonToString(jsonObjLinha.getString("tipo")));
								values.put("EST_ALTURA", ConverterUtil.JsonToString(jsonObjLinha.getString("altura")));

								bd.insert("TB_ESTRUTURA", null, values);

							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

					}

					@Override
					public void onRequestError(Exception error) {
						Log.d(Constantes.TAG_ESTRUTURA, "GET ALL onRequestError!" + "\n" + error.getMessage());
					}
				});

	}

}
