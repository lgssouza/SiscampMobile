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
import br.com.siscamp.model.Estrutura;
import br.com.siscamp.sqlite.EstruturaDAO;
import br.com.siscamp.util.FormatterUtil;

public class ListaEstruturasActivity extends Activity {

	private ExpandableListView lstEstruturas;
	private EstruturaDAO bdEstrutura;
	private List<Estrutura> lista;
	private int idLinha;
	private EditText edtFiltro;
	private String nomenclatura;
	private TextView txtNomenclatura;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_estruturas);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		idLinha = getIntent().getExtras().getInt("idLinha");
		nomenclatura = getIntent().getExtras().getString("nomenclatura");
		txtNomenclatura = (TextView) findViewById(R.id.txt_nomenclatura);
		txtNomenclatura.setText(nomenclatura.toString());

		edtFiltro = (EditText) findViewById(R.id.edt_pesqEstrutura);
		edtFiltro.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.toString().isEmpty()) {
					listarEstruturas(null);
				} else {
					listarEstruturas(s);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		bdEstrutura = new EstruturaDAO(this);
		this.listarEstruturas(null);

	}

	private void listarEstruturas(CharSequence param) {

		if (param != null) {
			lista = bdEstrutura.mockFiltraEstruturasLinha(idLinha, param);
		} else {
			lista = bdEstrutura.mockBuscaEstruturasLinha(idLinha);
		}

		// Conversão de Lista de Estrutura para Lista de String;
		List<String> valores = new ArrayList<String>();
		for (Estrutura linhas : lista) {
			valores.add(FormatterUtil.padRight("Número: " + linhas.getNumero(), 20) + "Tipo: " + linhas.getTipo());

		}

		List<String> subvalores = new ArrayList<String>();
		for (Estrutura linhas : lista) {
			subvalores.add("Modelo: " + linhas.getModelo());
			subvalores.add("Altura: " + linhas.getAltura() + "m");

		}

		// Conversão de Lista de String para Vetor;
		String[] itens = new String[valores.size()];
		itens = valores.toArray(itens);

		String[] subitens = new String[subvalores.size()];
		subitens = subvalores.toArray(subitens);

		// Conversão de Vetor para Matriz;
		String[][] matriz = FormatterUtil.dimensionar(subitens, 2);

		lstEstruturas = (ExpandableListView) findViewById(R.id.listEstruturas);
		lstEstruturas.setGroupIndicator(null);
		lstEstruturas.setAdapter(new ExpandableListComumAdapter(this, itens, matriz));

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
