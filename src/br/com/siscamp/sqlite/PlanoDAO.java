package br.com.siscamp.sqlite;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import br.com.siscamp.comum.Constantes;
import br.com.siscamp.model.Plano;
import br.com.siscamp.network.NetworkQueue;
import br.com.siscamp.network.NetworkRequestCallback;
import br.com.siscamp.util.ConverterUtil;
import br.com.siscamp.util.FormatterUtil;

public class PlanoDAO {
	private static SQLiteDatabase bd;
	private File dir;

	public PlanoDAO(Context context) {
		DatabaseCreate auxBd = new DatabaseCreate(context);
		bd = auxBd.getWritableDatabase();
		dir = FormatterUtil.getDirFromSDCard();

	}

	public void mockPopulaPlano() throws Exception {
		final List<Plano> planos = new ArrayList<Plano>();
		final Plano plano = new Plano();

		NetworkQueue mNetworkQueue = NetworkQueue.getInstance();

		mNetworkQueue.doGetArray(Constantes.URL_JSON_PLANO, Constantes.TAG_PLANO,
				new NetworkRequestCallback<JSONArray>() {
					@Override
					public void onRequestResponse(JSONArray jsonObject) {

						for (int i = 0; i < jsonObject.length(); i++) {
							try {
								JSONObject jsonObjLinha = jsonObject.getJSONObject(i);
								Plano planoLocal = mockVerificaPlano(jsonObjLinha.getInt("idLinha"));
								if (planoLocal.getIdLinhaDist() == 0) {
									if (jsonObjLinha.getInt("idLinha") != 0) {
										ContentValues values = new ContentValues();
										values.put("FK_PLANO_ID_LINHADIST", jsonObjLinha.getInt("idLinha"));
										values.put("PLANO_NOME",
												ConverterUtil.JsonToString(jsonObjLinha.getString("Arquivo")));
										values.put("PLANO_VERSAO", jsonObjLinha.getInt("versao"));
										bd.insert("TB_PLANO", null, values);

										plano.setIdLinhaDist(jsonObjLinha.getInt("idLinha"));
										plano.setArquivo(ConverterUtil.JsonToString(jsonObjLinha.getString("Arquivo")));
										plano.setVersao(jsonObjLinha.getInt("versao"));

										// buscaPDFPlano(jsonObjLinha.getInt("idLinha"),
										// ConverterUtil.JsonToString(jsonObjLinha.getString("Arquivo")));

									}
								} else {
									if (jsonObjLinha.getInt("idLinha") != 0) {
										if (planoLocal.getVersao() < jsonObjLinha.getInt("versao")) {
											ContentValues values = new ContentValues();
											values.put("PLANO_NOME",
													ConverterUtil.JsonToString(jsonObjLinha.getString("Arquivo")));
											values.put("PLANO_VERSAO", jsonObjLinha.getInt("versao"));
											bd.update("TB_PLANO", values,
													"FK_PLANO_ID_LINHADIST=" + jsonObjLinha.getInt("idLinha"), null);

											plano.setIdLinhaDist(jsonObjLinha.getInt("idLinha"));
											plano.setArquivo(
													ConverterUtil.JsonToString(jsonObjLinha.getString("Arquivo")));
											plano.setVersao(jsonObjLinha.getInt("versao"));

											// buscaPDFPlano(jsonObjLinha.getInt("idLinha"),
											// ConverterUtil.JsonToString(jsonObjLinha.getString("Arquivo")));

										} else {
											if (Environment.getExternalStorageState()
													.equals(Environment.MEDIA_MOUNTED)) {
												File sdcard = Environment.getExternalStorageDirectory()
														.getAbsoluteFile();
												File dir = new File(sdcard, "Siscamp" + File.separator + "planosfolder"
														+ File.separator + planoLocal.getArquivo());
												if (!dir.exists()) {

													plano.setIdLinhaDist(jsonObjLinha.getInt("idLinha"));
													plano.setArquivo(ConverterUtil
															.JsonToString(jsonObjLinha.getString("Arquivo")));
													plano.setVersao(jsonObjLinha.getInt("versao"));

													// buscaPDFPlano(jsonObjLinha.getInt("idLinha"),
													// ConverterUtil
													// .JsonToString(jsonObjLinha.getString("Arquivo")));

												}
											}
										}
									}
								}

							} catch (JSONException e) {
								e.printStackTrace();
							}

							planos.add(plano);

						}

					}

					@Override
					public void onRequestError(Exception error) {
						Log.d(Constantes.TAG_PLANO, "GET ALL onRequestError!" + "\n" + error.getMessage());
					}
				});

		for (int b = 0; b < planos.size(); b++) {
			buscaPDFPlano(planos.get(b).getIdLinhaDist(), planos.get(b).getArquivo());
		}
	}

	public Plano mockVerificaPlano(int criterio) {
		Plano plano = new Plano();
		Cursor cursor = bd.query("TB_PLANO", null, "FK_PLANO_ID_LINHADIST =" + criterio, null, null, null, null);

		if (cursor.moveToNext()) {

			plano.setIdLinhaDist(cursor.getInt(cursor.getColumnIndex("FK_PLANO_ID_LINHADIST")));
			plano.setArquivo(cursor.getString(cursor.getColumnIndex("PLANO_NOME")));
			plano.setVersao(cursor.getInt(cursor.getColumnIndex("PLANO_VERSAO")));

		}
		return plano;
	}

	private void buscaPDFPlano(int linha, String nomeArquivo) {
		int count;
		try {
			URL url = new URL(Constantes.URL_JSON_PLANO_PDF + "?linha=" + linha);
			URLConnection conection = url.openConnection();
			conection.connect();

			// getting file length
			// int lenghtOfFile = conection.getContentLength();

			// input stream to read file - with 8k buffer
			InputStream input = new BufferedInputStream(url.openStream(), 8192);

			// Output stream to write file
			OutputStream output = new FileOutputStream(dir + "/" + nomeArquivo);

			byte data[] = new byte[1024];

			while ((count = input.read(data)) != -1) {
				// writing data to file
				output.write(data, 0, count);
			}

			// flushing output
			output.flush();

			// closing streams
			output.close();
			input.close();

		} catch (Exception e) {
			Log.e("Error: ", e.getMessage());
		}

	}

	@SuppressWarnings("unused")
	private Plano buscarPlano(int idLinha) {
		final Plano plano = new Plano();
		NetworkQueue mNetworkQueue = NetworkQueue.getInstance();
		mNetworkQueue.doGet(Constantes.URL_JSON_PLANO + "?linha=" + idLinha, Constantes.TAG_PLANO,
				new NetworkRequestCallback<JSONObject>() {

					@Override
					public void onRequestError(Exception error) {
						Log.d(Constantes.TAG_PLANO, "GET ALL onRequestError!" + "\n" + error.getMessage());
					}

					@Override
					public void onRequestResponse(JSONObject response) {
						try {

							plano.setIdLinhaDist(response.getInt("idLinha"));
							plano.setArquivo(ConverterUtil.JsonToString(response.getString("Arquivo")));
							plano.setVersao(response.getInt("versao"));

						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				});

		return plano;
	}

	public List<Plano> buscarListPlanos() {
		final List<Plano> Planos = new ArrayList<Plano>();
		NetworkQueue mNetworkQueue = NetworkQueue.getInstance();

		mNetworkQueue.doGetArray(Constantes.URL_JSON_PLANO, Constantes.TAG_PLANO,
				new NetworkRequestCallback<JSONArray>() {
					@Override
					public void onRequestResponse(JSONArray jsonObject) {

						for (int i = 0; i < jsonObject.length(); i++) {
							try {
								JSONObject jsonObjLinha = jsonObject.getJSONObject(i);
								Plano plano = new Plano();
								plano.setIdLinhaDist(jsonObjLinha.getInt("idLinha"));
								plano.setArquivo(ConverterUtil.JsonToString(jsonObjLinha.getString("Arquivo")));
								plano.setVersao(jsonObjLinha.getInt("versao"));
								Planos.add(plano);

							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

					}

					@Override
					public void onRequestError(Exception error) {
						Log.d(Constantes.TAG_PLANO, "GET ALL onRequestError!" + "\n" + error.getMessage());
					}
				});
		return Planos;

	}

}
