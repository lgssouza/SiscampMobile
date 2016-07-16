package br.com.siscamp;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ExpandableListView;
import br.com.siscamp.comum.ExpandableListPrestadorAdapter;
import br.com.siscamp.model.Contato;
import br.com.siscamp.sqlite.ContatoDAO;
import br.com.siscamp.util.FormatterUtil;

public class ListaPrestadoresActivity extends Activity {

	private ExpandableListView lstContatos;
	private ContatoDAO bdContatos;
	private List<Contato> lista;
	private EditText edtFiltro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_contatos_prestadores);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		edtFiltro = (EditText) findViewById(R.id.edt_pesqContatos);
		edtFiltro.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.toString().isEmpty()) {
					listarContatos(null);
				} else {
					listarContatos(s);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		bdContatos = new ContatoDAO(this);
		this.listarContatos(null);

	}

	private void listarContatos(CharSequence param) {

		if (param != null) {
			lista = bdContatos.mockFiltraContatos(param);
		} else {
			lista = bdContatos.mockListarContatos();
		}

		List<String> valores = new ArrayList<String>();
		for (Contato linhas : lista) {
			valores.add(linhas.getNome());

		}

		List<String> subvalores = new ArrayList<String>();
		for (Contato linhas : lista) {
			subvalores.add("Especialidade: " + linhas.getEspecialidade());
			subvalores.add("Telefone: " + linhas.getTel1());
			subvalores.add("Telefone: " + linhas.getTel2());
			subvalores.add("Endereço: " + linhas.getEndereco());
			subvalores.add("Cidade: " + linhas.getCidade());

		}

		// Conversão de Lista de String para Vetor;
		String[] itens = new String[valores.size()];
		itens = valores.toArray(itens);

		String[] subitens = new String[subvalores.size()];
		subitens = subvalores.toArray(subitens);

		// Conversão de Vetor para Matriz;
		String[][] matriz = FormatterUtil.dimensionar(subitens, 5);

		lstContatos = (ExpandableListView) findViewById(R.id.listContatos);
		lstContatos.setAdapter(new ExpandableListPrestadorAdapter(this, itens, matriz));
		lstContatos.setGroupIndicator(null);

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
