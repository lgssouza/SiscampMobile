package br.com.siscamp;

import java.util.ArrayList;
import java.util.List;

import br.com.siscamp.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import br.com.siscamp.model.Linha;
import br.com.siscamp.sqlite.LinhaDAO;

@SuppressLint("DefaultLocale")
public class ListaLinhasActivity extends Activity {

	private ListView lstLinhas;
	private LinhaDAO bdLinha;
	private List<Linha> lista;
	private Long posicao;
	private EditText edtFiltro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_linhas);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		edtFiltro = (EditText) findViewById(R.id.edt_pesqLinhas);
		edtFiltro.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.toString().isEmpty()) {
					listarLinhas(null);
				} else {
					listarLinhas(s.toString().toUpperCase());
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		bdLinha = new LinhaDAO(this);
		this.listarLinhas(null);

	}

	private void listarLinhas(String param) {
		// lista.clear();
		if (param != null) {
			lista = bdLinha.mockFiltraLinhas(param);
		} else {
			lista = bdLinha.mockListarLinhas();
		}

		lstLinhas = (ListView) findViewById(R.id.listLinhas);

		List<CharSequence> valores = new ArrayList<CharSequence>();
		for (Linha linhas : lista) {
			valores.add(linhas.getNomenclatura());
		}

		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1,
				valores);
		lstLinhas.setAdapter(adapter);

		lstLinhas.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				LinhaDAO linhasDAO = new LinhaDAO(ListaLinhasActivity.this);

				Linha objLinha = lista.get(position);
				int idLinha = objLinha.getIdLinha();

				Linha linha = new Linha();
				linha = linhasDAO.mockBuscaLinha(idLinha);

				Intent intent = new Intent(ListaLinhasActivity.this, DescricaoLinhasActivity.class);
				Bundle dados = new Bundle();

				dados.putInt("ID_LINHADIST", linha.getIdLinha());
				dados.putString("LINHADIST_NOMENCLATURA", linha.getNomenclatura().toString());
				dados.putString("LINHADIST_CABOPRAIO", linha.getCaboRaio().toString());
				dados.putString("LINHADIST_CABOCOND", linha.getCaboCond().toString());
				dados.putString("LINHADIST_LARGFAIXA", linha.getLargFaixa().toString());

				intent.putExtras(dados);

				startActivity(intent);

			}
		});

	}

	public Long getPosicao() {
		return posicao;
	}

	public void setPosicao(Long posicao) {
		this.posicao = posicao;
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
