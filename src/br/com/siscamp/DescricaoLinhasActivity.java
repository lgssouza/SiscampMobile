package br.com.siscamp;

import java.io.File;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import br.com.siscamp.model.Plano;
import br.com.siscamp.sqlite.PlanoDAO;
import br.com.siscamp.util.MensagemUtil;

public class DescricaoLinhasActivity extends Activity {

	private EditText edtCaboRaio;
	private EditText edtCaboCond;
	private EditText edtLargFaixa;
	private TextView txtNomenclatura;
	private int idLinha;
	private String nomenclatura;
	String caboraio;
	String cabocond;
	String largfaixa;

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
			idLinha = dados.getInt("ID_LINHADIST");
			nomenclatura = dados.getString("LINHADIST_NOMENCLATURA");
			caboraio = dados.getString("LINHADIST_CABOPRAIO");
			cabocond = dados.getString("LINHADIST_CABOCOND");
			largfaixa = dados.getString("LINHADIST_LARGFAIXA");
		}

		txtNomenclatura = (TextView) findViewById(R.id.txt_nomenclatura);
		edtCaboRaio = (EditText) findViewById(R.id.edt_cabopraio);
		edtCaboCond = (EditText) findViewById(R.id.edt_cabocond);
		edtLargFaixa = (EditText) findViewById(R.id.edt_largfaixa);

		txtNomenclatura.setText(nomenclatura.toString());
		edtCaboRaio.setText(caboraio.toString());
		edtCaboCond.setText(cabocond.toString());
		edtLargFaixa.setText(largfaixa.toString());

	}

	public void mostrarEstruturas(View view) {
		Intent i = new Intent(DescricaoLinhasActivity.this, ListaEstruturasActivity.class);
		i.putExtra("idLinha", idLinha);
		i.putExtra("nomenclatura", nomenclatura);
		startActivity(i);

	}

	public void mostrarVaos(View view) {
		Intent i = new Intent(DescricaoLinhasActivity.this, ListaVaosActivity.class);
		i.putExtra("idLinha", idLinha);
		i.putExtra("nomenclatura", nomenclatura);
		startActivity(i);

	}

	public void mostrarPlano(View view) {

		PlanoDAO planoDAO = new PlanoDAO(DescricaoLinhasActivity.this);
		Plano plano = new Plano();
		plano = planoDAO.mockVerificaPlano(idLinha);
		String caminho = "";

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File sdcard = Environment.getExternalStorageDirectory().getAbsoluteFile();
			File dir = new File(sdcard,
					"Siscamp" + File.separator + "planosfolder" + File.separator + plano.getArquivo());
			if (dir.exists())
				caminho = String.valueOf(dir);

		}

		File file = new File(caminho);
		if (file.exists()) {
			Uri filepath = Uri.fromFile(file);
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(filepath, "application/pdf");
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			try {
				startActivity(intent);
			} catch (Exception e) {
				MensagemUtil.AddMsgFast(DescricaoLinhasActivity.this, "Não existe plano de contigência!");
				Log.e("error", "" + e);
			}

		} else {
			MensagemUtil.AddMsgFast(DescricaoLinhasActivity.this, "Arquivo não encontrado, contate a central!");

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
