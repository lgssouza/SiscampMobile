package br.com.siscamp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class PleActivity extends Activity {
	int idPle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_descricao_linhas);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		Intent in = getIntent();
		Bundle dados = new Bundle();
		dados = in.getExtras();

		if (dados != null) {
			idPle = dados.getInt("ID_LINHADIST");

		}

	}
}
