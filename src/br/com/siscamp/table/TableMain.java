package br.com.siscamp.table;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class TableMain extends Activity {

	public static String PARAM_DATA;
	public static String PARAM_TIPO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		Intent in = getIntent();
		Bundle dados = new Bundle();
		dados = in.getExtras();

		if (dados != null) {
			PARAM_DATA = dados.getString("data");
			PARAM_TIPO = dados.getString("tipo");
		}

		setContentView(new TableMainLayout(this));
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