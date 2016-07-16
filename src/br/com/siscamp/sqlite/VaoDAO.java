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
import br.com.siscamp.model.Vao;
import br.com.siscamp.network.NetworkQueue;
import br.com.siscamp.network.NetworkRequestCallback;

public class VaoDAO {
	private static SQLiteDatabase bd;

	public VaoDAO(Context context) {
		DatabaseCreate auxBd = new DatabaseCreate(context);
		bd = auxBd.getWritableDatabase();
	}

	public void limpaDadosVao() {
		bd.delete("TB_VAO", null, null);
		bd.close();
	}

	public List<Vao> mockBuscaVaoLinha(int id) {

		Cursor cursor = innerJoinBusca(id);

		List<Vao> lista = new ArrayList<Vao>();

		while (cursor.moveToNext()) {
			Vao vaoDTO = new Vao();
			vaoDTO.setIdVao(cursor.getInt(cursor.getColumnIndex("ID_VAO")));
			vaoDTO.setIdEstInicial(cursor.getInt(cursor.getColumnIndex("FK_ID_EST_INICIAL")));
			vaoDTO.setIdEstFinal(cursor.getInt(cursor.getColumnIndex("FK_ID_EST_FINAL")));
			vaoDTO.setDistancia(cursor.getDouble(cursor.getColumnIndex("VAO_DISTANCIA")));
			vaoDTO.setProgressiva(cursor.getDouble(cursor.getColumnIndex("VAO_PROGRESSIVA")));
			vaoDTO.setRegressiva(cursor.getDouble(cursor.getColumnIndex("VAO_REGRESSIVA")));

			lista.add(vaoDTO);
		}
		return lista;
	}

	public List<Vao> mockFiltraVaoLinha(int id, CharSequence criterio) {

		Cursor cursor = innerJoinFiltra(id, criterio);

		List<Vao> lista = new ArrayList<Vao>();

		while (cursor.moveToNext()) {
			Vao vaoDTO = new Vao();
			vaoDTO.setIdVao(cursor.getInt(cursor.getColumnIndex("ID_VAO")));
			vaoDTO.setIdEstInicial(cursor.getInt(cursor.getColumnIndex("FK_ID_EST_INICIAL")));
			vaoDTO.setIdEstFinal(cursor.getInt(cursor.getColumnIndex("FK_ID_EST_FINAL")));
			vaoDTO.setDistancia(cursor.getDouble(cursor.getColumnIndex("VAO_DISTANCIA")));
			vaoDTO.setProgressiva(cursor.getDouble(cursor.getColumnIndex("VAO_PROGRESSIVA")));
			vaoDTO.setRegressiva(cursor.getDouble(cursor.getColumnIndex("VAO_REGRESSIVA")));

			lista.add(vaoDTO);
		}
		return lista;
	}

	public Cursor innerJoinBusca(int id) {

		String sql = " SELECT a.id_vao,a.fk_id_est_inicial,a.fk_id_est_final,"
				+ "a.vao_distancia,a.vao_progressiva,a.vao_regressiva " + "FROM tb_vao a "
				+ "inner join mov_est_linha b on a.fk_id_est_inicial = b.fk_mov_id_estrutura "
				+ "inner join mov_est_linha c on a.fk_id_est_final = c.fk_mov_id_estrutura "
				+ "where c.fk_mov_id_linhadist = ?";

		return bd.rawQuery(sql, new String[] { String.valueOf(id) });

	}

	public Cursor innerJoinFiltra(int id, CharSequence criterio) {

		String sql = " SELECT a.id_vao,a.fk_id_est_inicial,a.fk_id_est_final,"
				+ "a.vao_distancia,a.vao_progressiva,a.vao_regressiva " + "FROM tb_vao a "
				+ "inner join mov_est_linha b on a.fk_id_est_inicial = b.fk_mov_id_estrutura "
				+ "inner join mov_est_linha c on a.fk_id_est_final = c.fk_mov_id_estrutura "
				+ "where c.fk_mov_id_linhadist = ? and a.fk_id_est_inicial=? or c.fk_mov_id_linhadist = ? and a.fk_id_est_final=? ";

		return bd.rawQuery(sql, new String[] { String.valueOf(id), String.valueOf(criterio), String.valueOf(id),
				String.valueOf(criterio) });

	}

	public void buscarVao() {

		NetworkQueue mNetworkQueue = NetworkQueue.getInstance();

		mNetworkQueue.doGetArray(Constantes.URL_JSON_VAO, Constantes.TAG_VAO, new NetworkRequestCallback<JSONArray>() {
			@Override
			public void onRequestResponse(JSONArray jsonObject) {
				for (int i = 0; i < jsonObject.length(); i++) {
					try {
						JSONObject jsonObjLinha = jsonObject.getJSONObject(i);
						ContentValues values = new ContentValues();
						values.put("ID_VAO", jsonObjLinha.getInt("idVao"));
						values.put("FK_ID_EST_INICIAL", jsonObjLinha.getInt("idEstInicial"));
						values.put("FK_ID_EST_FINAL", jsonObjLinha.getInt("idEstFinal"));
						values.put("VAO_DISTANCIA", jsonObjLinha.getDouble("distancia"));
						values.put("VAO_PROGRESSIVA", jsonObjLinha.getDouble("progressiva"));
						values.put("VAO_REGRESSIVA", jsonObjLinha.getDouble("regressiva"));

						bd.insert("TB_VAO", null, values);

					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

			}

			@Override
			public void onRequestError(Exception error) {
				Log.d(Constantes.TAG_VAO, "GET ALL onRequestError!" + "\n" + error.getMessage());
			}
		});

	}

}
