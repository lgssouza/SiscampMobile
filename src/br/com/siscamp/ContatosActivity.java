package br.com.siscamp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class ContatosActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contatos);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

	}

	public void listarFuncionarios(View view) {
		new LoadingAsyncFuncionarios().execute();

	}

	public void listarPrestadores(View view) {
		new LoadingAsyncPrestadores().execute();

	}

	private class LoadingAsyncFuncionarios extends AsyncTask<Void, Void, String> {
		ProgressDialog progressDialog = new ProgressDialog(ContatosActivity.this);

		@Override
		protected void onPreExecute() {
			progressDialog.setMessage("Carregando...");
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(String msg) {
			progressDialog.dismiss();

			Intent i = new Intent(ContatosActivity.this, ListaFuncionariosActivity.class);
			startActivity(i);

		}

		@Override
		protected String doInBackground(Void... params) {
			return null;

		}

	}

	private class LoadingAsyncPrestadores extends AsyncTask<Void, Void, String> {
		ProgressDialog progressDialog = new ProgressDialog(ContatosActivity.this);

		@Override
		protected void onPreExecute() {
			progressDialog.setMessage("Carregando...");
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(String msg) {
			progressDialog.dismiss();

			Intent i = new Intent(ContatosActivity.this, ListaPrestadoresActivity.class);
			startActivity(i);

		}

		@Override
		protected String doInBackground(Void... params) {
			return null;

		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Id correspondente ao botão Up/Home da actionbar
		case android.R.id.home:
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

}
