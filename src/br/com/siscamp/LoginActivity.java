package br.com.siscamp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import br.com.siscamp.comum.Logar;
import br.com.siscamp.dominio.ValidacaoLogin;
import br.com.siscamp.util.MensagemUtil;

public class LoginActivity extends Activity {

	private EditText edtLogin;
	private EditText edtSenha;
	private Logar loginBO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		edtLogin = (EditText) findViewById(R.id.edt_login);
		edtSenha = (EditText) findViewById(R.id.edt_senha);

	}

	public void logar(View view) {
		new LoadingAsync().execute();

	}

	private class LoadingAsync extends AsyncTask<Void, Void, ValidacaoLogin> {
		ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);

		@Override
		protected void onPreExecute() {
			progressDialog.setMessage("Carregando...");
			progressDialog.show();
		}

		@Override
		protected ValidacaoLogin doInBackground(Void... params) {

			String login = edtLogin.getText().toString();
			String senha = edtSenha.getText().toString();

			loginBO = new Logar(LoginActivity.this);
			return loginBO.validarLogin(login, senha);

		}

		@Override
		protected void onPostExecute(ValidacaoLogin Validacao) {
			progressDialog.dismiss();

			if (Validacao.isValido()) {

				Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
				i.putExtra("msg", Validacao.getMensagem());
				startActivity(i);
				finish();

			} else {
				MensagemUtil.AddMsgFast(LoginActivity.this, Validacao.getMensagem());
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int idMenuItem = item.getItemId();
		switch (idMenuItem) {
		case R.id.menuSair:
			MensagemUtil.AddMsgConfirm(LoginActivity.this, getText(R.string.lbl_sair), getText(R.string.msg_sair),
					R.drawable.logout, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});

			break;
		case R.id.menuSobre:
			MensagemUtil.AddMsgOk(LoginActivity.this, getString(R.string.lbl_sobre), getString(R.string.msg_desc_sobre),
					R.drawable.about);
			break;
		}

		return true;
	}

}
