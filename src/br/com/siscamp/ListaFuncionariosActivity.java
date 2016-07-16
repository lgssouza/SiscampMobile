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
import br.com.siscamp.comum.ExpandableListFuncionarioAdapter;
import br.com.siscamp.model.Funcionario;
import br.com.siscamp.sqlite.FuncionarioDAO;
import br.com.siscamp.util.FormatterUtil;

public class ListaFuncionariosActivity extends Activity {

	private ExpandableListView lstContatos;
	private FuncionarioDAO bdContatos;
	private List<Funcionario> lista;
	private EditText edtFiltro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_contatos_funcionarios);

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

		bdContatos = new FuncionarioDAO(this);
		this.listarContatos(null);

	}

	private void listarContatos(CharSequence param) {

		if (param != null) {
			lista = bdContatos.mockFiltraContatos(param);
		} else {
			lista = bdContatos.mockListarContatos();
		}

		List<String> valores = new ArrayList<String>();
		for (Funcionario linhas : lista) {
			valores.add(linhas.getNome());

		}

		List<String> subvalores = new ArrayList<String>();
		for (Funcionario linhas : lista) {
			subvalores.add("Matricula: " + linhas.getMatricula());
			subvalores.add("Telefone: " + linhas.getTel());

		}

		// Conversão de Lista de String para Vetor;
		String[] itens = new String[valores.size()];
		itens = valores.toArray(itens);

		String[] subitens = new String[subvalores.size()];
		subitens = subvalores.toArray(subitens);

		// Conversão de Vetor para Matriz;
		String[][] matriz = FormatterUtil.dimensionar(subitens, 2);

		lstContatos = (ExpandableListView) findViewById(R.id.listContatos);
		lstContatos.setAdapter(new ExpandableListFuncionarioAdapter(this, itens, matriz));
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
