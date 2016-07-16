package br.com.siscamp;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import br.com.siscamp.comum.GridViewAdapter;
import br.com.siscamp.model.ProgServico;
import br.com.siscamp.sqlite.ProgServicoDAO;
import br.com.siscamp.util.ConverterUtil;
import br.com.siscamp.util.MensagemUtil;

public class DescricaoProgServicosActivity extends Activity {
	private String data;
	private String tipo;
	private String dataSearch;
	private ProgServicoDAO db;
	private List<ProgServico> lstProgServico;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_descricao_progservico);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		Intent in = getIntent();
		Bundle dados = new Bundle();
		dados = in.getExtras();

		data = dados.getString("data");
		tipo = dados.getString("tipo");

		db = new ProgServicoDAO(this);

		if (tipo.equals("diario")) {
			dataSearch = ConverterUtil.converterData(data, "yyyy-MM-dd");
		} else if (tipo.equals("semanal")) {
			dataSearch = data;
		} else if (tipo.equals("mensal")) {
			dataSearch = ConverterUtil.converterData(data, "yyyy-MM");

		}

		MensagemUtil.AddMsgFast(DescricaoProgServicosActivity.this, dataSearch);

		lstProgServico = db.mockBuscaProgServico(dataSearch, tipo);

		// Conversão de Lista de Estrutura para Lista de String;
		List<String> valores = new ArrayList<String>();
		for (ProgServico progServico : lstProgServico) {

			valores.add(String.valueOf(progServico.getIdLinhaDist()));
			valores.add(progServico.getDescServico());
			valores.add(String.valueOf(progServico.getQtdPessoas()));

			if (progServico.getIdPle() != null) {
				valores.add(String.valueOf(progServico.getIdPle()));
			} else {
				valores.add("N/A PLE");
			}

		}

		String[] itens = new String[valores.size()];
		itens = valores.toArray(itens);

		GridView gv = new GridView(this);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		gv.setLayoutParams(lp);
		gv.setNumColumns(4);
		// gv.setColumnWidth((int)
		// getResources().getDimension(R.dimen.sessenta_dp));
		// gv.setVerticalSpacing((int)
		// getResources().getDimension(R.dimen.dez_dp));
		// gv.setHorizontalSpacing((int)
		// getResources().getDimension(R.dimen.dez_dp));

		gv.setAdapter(new GridViewAdapter(this, itens));

		gv.setOnItemClickListener(new GridView.OnItemClickListener() {
			@SuppressWarnings("rawtypes")
			@Override
			public void onItemClick(AdapterView parent, View view, int position, long id) {

			}

		});

		LinearLayout ll = (LinearLayout) findViewById(R.id.linearServico);
		ll.addView(gv);
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
