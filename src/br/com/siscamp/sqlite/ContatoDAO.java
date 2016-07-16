package br.com.siscamp.sqlite;

import java.nio.charset.Charset;
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
import br.com.siscamp.model.Contato;
import br.com.siscamp.network.NetworkQueue;
import br.com.siscamp.network.NetworkRequestCallback;
import br.com.siscamp.util.ConverterUtil;

public class ContatoDAO {
	private SQLiteDatabase bd;
	Charset utf8charset = Charset.forName("UTF-8");

	public ContatoDAO(Context context) {
		DatabaseCreate auxBd = new DatabaseCreate(context);
		bd = auxBd.getWritableDatabase();

	}

	public void limpaDadosContatos() {
		bd.delete("TB_CONTATOS", null, null);
		bd.close();
	}

	public List<Contato> mockListarContatos() {
		List<Contato> lista = new ArrayList<Contato>();

		Cursor cursor = bd.query("TB_CONTATOS", null, null, null, null, null, "ID_CONTATO");

		while (cursor.moveToNext()) {
			Contato contDTO = new Contato();
			contDTO.setIdContato(cursor.getInt(cursor.getColumnIndex("ID_CONTATO")));
			contDTO.setNome(cursor.getString(cursor.getColumnIndex("CONTATO_NOME")));
			contDTO.setEspecialidade(cursor.getString(cursor.getColumnIndex("CONTATO_ESPEC")));
			contDTO.setTel1(cursor.getString(cursor.getColumnIndex("CONTATO_TEL1")));
			contDTO.setTel2(cursor.getString(cursor.getColumnIndex("CONTATO_TEL2")));
			contDTO.setCidade(cursor.getString(cursor.getColumnIndex("CONTATO_CIDADE")));
			contDTO.setEndereco(cursor.getString(cursor.getColumnIndex("CONTATO_END")));

			lista.add(contDTO);
		}
		return lista;
	}

	public List<Contato> mockFiltraContatos(CharSequence criterio) {
		List<Contato> lista = new ArrayList<Contato>();

		Cursor cursor = bd.query("TB_CONTATOS", null, "CONTATO_NOME like '%" + criterio + "%'", null, null, null, null);

		while (cursor.moveToNext()) {
			Contato contDTO = new Contato();
			contDTO.setIdContato(cursor.getInt(cursor.getColumnIndex("ID_CONTATO")));
			contDTO.setNome(cursor.getString(cursor.getColumnIndex("CONTATO_NOME")));
			contDTO.setEspecialidade(cursor.getString(cursor.getColumnIndex("CONTATO_ESPEC")));
			contDTO.setTel1(cursor.getString(cursor.getColumnIndex("CONTATO_TEL1")));
			contDTO.setTel2(cursor.getString(cursor.getColumnIndex("CONTATO_TEL2")));
			contDTO.setCidade(cursor.getString(cursor.getColumnIndex("CONTATO_CIDADE")));
			contDTO.setEndereco(cursor.getString(cursor.getColumnIndex("CONTATO_END")));

			lista.add(contDTO);
		}
		return lista;
	}

	public Contato mockBuscaTelefoneContatos(int criterio) {
		Contato contDTO = new Contato();
		Cursor cursor = bd.query("TB_CONTATOS", null, "ID_CONTATO = " + criterio, null, null, null, null);
		Log.e("hoooooo", String.valueOf(criterio));
		if (cursor.moveToNext()) {
			contDTO.setTel1(cursor.getString(cursor.getColumnIndex("CONTATO_TEL1")));
			contDTO.setTel2(cursor.getString(cursor.getColumnIndex("CONTATO_TEL2")));
		}
		return contDTO;
	}

	public void buscarContatos() {

		NetworkQueue mNetworkQueue = NetworkQueue.getInstance();
		mNetworkQueue.doGetArray(Constantes.URL_JSON_CONTATOS, Constantes.TAG_CONTATOS,
				new NetworkRequestCallback<JSONArray>() {

					public void onRequestResponse(JSONArray jsonObject) {

						for (int i = 0; i < jsonObject.length(); i++) {
							try {
								JSONObject jsonObjLinha = jsonObject.getJSONObject(i);
								ContentValues values = new ContentValues();
								values.put("ID_CONTATO", jsonObjLinha.getInt("idContato"));
								values.put("CONTATO_NOME", ConverterUtil.JsonToString(jsonObjLinha.getString("nome")));
								values.put("CONTATO_ESPEC",
										ConverterUtil.JsonToString(jsonObjLinha.getString("especialidade")));
								values.put("CONTATO_TEL1", ConverterUtil.JsonToString(jsonObjLinha.getString("tel1")));
								values.put("CONTATO_TEL2", ConverterUtil.JsonToString(jsonObjLinha.getString("tel2")));
								values.put("CONTATO_CIDADE",
										ConverterUtil.JsonToString(jsonObjLinha.getString("cidade")));
								values.put("CONTATO_END", ConverterUtil.JsonToString(jsonObjLinha.getString("end")));

								bd.insert("TB_CONTATOS", null, values);

							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

					}

					@Override
					public void onRequestError(Exception error) {
						Log.d(Constantes.TAG_CONTATOS, "GET ALL onRequestError!" + "\n" + error.getMessage());
					}
				});

	}

}
