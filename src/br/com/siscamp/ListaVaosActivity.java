package br.com.siscamp;

import java.util.ArrayList;
import java.util.List;

import br.com.siscamp.R;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import br.com.siscamp.comum.ExpandableListComumAdapter;
import br.com.siscamp.model.Vao;
import br.com.siscamp.sqlite.VaoDAO;
import br.com.siscamp.util.FormatterUtil;

public class ListaVaosActivity extends Activity {

	private ExpandableListView lstVao;
	private EditText edtFiltro;
	private VaoDAO bdVao;
	private List<Vao> lista;
	private int idLinha;
	private String nomenclatura;
	private TextView txtNomenclatura;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_vaos);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		idLinha = getIntent().getExtras().getInt("idLinha");
		nomenclatura = getIntent().getExtras().getString("nomenclatura");
		txtNomenclatura = (TextView) findViewById(R.id.txt_nomenclatura);
		txtNomenclatura.setText(nomenclatura.toString());

		edtFiltro = (EditText) findViewById(R.id.edt_pesqVao);
		edtFiltro.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.toString().isEmpty()) {
					listarVaos(null);
				} else {
					listarVaos(s);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		bdVao = new VaoDAO(this);
		this.listarVaos(null);

	}

	private void listarVaos(CharSequence param) {

		if (param != null) {
			lista = bdVao.mockFiltraVaoLinha(idLinha, param);
		} else {
			lista = bdVao.mockBuscaVaoLinha(idLinha);
		}

		// Conversão de Lista de Estrutura para Lista de String;
		List<String> valores = new ArrayList<String>();
		for (Vao linhas : lista) {
			valores.add(FormatterUtil.padRight("Inicial: " + linhas.getIdEstInicial(), 20) + "Final: "
					+ linhas.getIdEstFinal());

		}

		List<String> subvalores = new ArrayList<String>();
		for (Vao linhas : lista) {
			subvalores.add("Distância: " + linhas.getDistancia());
			subvalores.add("Progressiva: " + linhas.getProgressiva());
			subvalores.add("Regressiva: " + linhas.getRegressiva());

		}

		// Conversão de Lista de String para Vetor;
		String[] itens = new String[valores.size()];
		itens = valores.toArray(itens);

		String[] subitens = new String[subvalores.size()];
		subitens = subvalores.toArray(subitens);

		// Conversão de Vetor para Matriz;
		String[][] matriz = FormatterUtil.dimensionar(subitens, 3);

		lstVao = (ExpandableListView) findViewById(R.id.listVao);
		lstVao.setGroupIndicator(null);
		lstVao.setAdapter(new ExpandableListComumAdapter(this, itens, matriz));

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
