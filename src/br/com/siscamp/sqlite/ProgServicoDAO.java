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
import br.com.siscamp.model.ProgServico;
import br.com.siscamp.network.NetworkQueue;
import br.com.siscamp.network.NetworkRequestCallback;
import br.com.siscamp.util.ConverterUtil;

public class ProgServicoDAO {
	private static SQLiteDatabase bd;

	public ProgServicoDAO(Context context) {
		DatabaseCreate auxBd = new DatabaseCreate(context);
		bd = auxBd.getWritableDatabase();
	}

	public void limpaDadosProgServico() {
		bd.delete("TB_PROGSERVICO", null, null);
		bd.close();
	}

	public List<ProgServico> mockBuscaProgServico(String param, String tipo) {
		List<ProgServico> lista = new ArrayList<ProgServico>();
		Cursor cursor = null;
		if (tipo.equals("diario")) {
			String sql = "select * from tb_progservico a "
					+ "inner join tb_linhadistribuicao b on a.fk_progs_id_linhadist = b.id_linhadist "
					+ "where progs_dataexec = ?";
			cursor = bd.rawQuery(sql, new String[] { param });

		} else if (tipo.equals("semanal")) {
			String sql = "select * from tb_progservico a "
					+ "inner join tb_linhadistribuicao b on a.fk_progs_id_linhadist = b.id_linhadist "
					+ "where progs_semanaexec = ?";
			cursor = bd.rawQuery(sql, new String[] { param });
		} else if (tipo.equals("mensal")) {
			String sql = "select * from tb_progservico a "
					+ "inner join tb_linhadistribuicao b on a.fk_progs_id_linhadist = b.id_linhadist "
					+ "where strftime('%m', progs_dataexec) = ?";
			cursor = bd.rawQuery(sql, new String[] { param });
		}

		while (cursor.moveToNext()) {
			ProgServico progServico = new ProgServico();
			progServico.setIdProgServico(cursor.getInt(cursor.getColumnIndex("ID_PROGSERVICO")));
			progServico.setIdLinhaDist(cursor.getInt(cursor.getColumnIndex("FK_PROGS_ID_LINHADIST")));
			progServico.setDescServico(cursor.getString(cursor.getColumnIndex("PROGS_DESCSERVICO")));
			progServico.setDataExec(cursor.getString(cursor.getColumnIndex("PROGS_DATAEXEC")));
			progServico.setNomeLinhaDist(cursor.getString(cursor.getColumnIndex("LINHADIST_NOMENCLATURA")));
			progServico.setQtdPessoas(cursor.getInt(cursor.getColumnIndex("PROGS_QTDPESSOAS")));
			progServico.setIdPle(cursor.getInt(cursor.getColumnIndex("FK_PROGS_ID_PLE")));
			lista.add(progServico);
		}
		return lista;
	}

	public void buscarProgServico() {

		NetworkQueue mNetworkQueue = NetworkQueue.getInstance();

		mNetworkQueue.doGetArray(Constantes.URL_JSON_PROG, Constantes.TAG_PROG,
				new NetworkRequestCallback<JSONArray>() {
					@Override
					public void onRequestResponse(JSONArray jsonObject) {

						for (int i = 0; i < jsonObject.length(); i++) {
							try {
								JSONObject jsonObjLinha = jsonObject.getJSONObject(i);
								ContentValues values = new ContentValues();
								values.put("ID_PROGSERVICO", jsonObjLinha.getInt("idProgServico"));
								values.put("FK_PROGS_ID_LINHADIST", jsonObjLinha.getInt("idLinhaDist"));
								values.put("PROGS_DESCSERVICO",
										ConverterUtil.JsonToString(jsonObjLinha.getString("descServico")));
								values.put("PROGS_SEMANAEXEC", jsonObjLinha.getInt("semanaExec"));
								values.put("PROGS_DATAEXEC",
										ConverterUtil.JsonToString(jsonObjLinha.getString("dataExec")));
								values.put("PROGS_QTDPESSOAS", jsonObjLinha.getInt("qtdPessoas"));
								values.put("FK_PROGS_ID_PLE", jsonObjLinha.getInt("idPle"));

								bd.insert("TB_PROGSERVICO", null, values);

							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

					}

					@Override
					public void onRequestError(Exception error) {
						Log.d(Constantes.TAG_PROG, "GET ALL onRequestError!" + "\n" + error.getMessage());
					}
				});

	}

}
