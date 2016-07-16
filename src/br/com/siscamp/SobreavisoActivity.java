package br.com.siscamp;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ExpandableListView;
import br.com.siscamp.comum.ExpandableListSobreavisoAdapter;
import br.com.siscamp.model.Funcionario;
import br.com.siscamp.sqlite.SobreavisoDAO;
import br.com.siscamp.util.ConverterUtil;
import br.com.siscamp.util.FormatterUtil;

public class SobreavisoActivity extends Activity {

	private SobreavisoDAO bdSobreaviso;
	private List<Funcionario> lista;
	private ExpandableListView lstSobreaviso;
	private CalendarView calendarView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sobreaviso);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		lstSobreaviso = (ExpandableListView) findViewById(R.id.listSobreaviso);
		calendarView = (CalendarView) findViewById(R.id.calSobreaviso);
		bdSobreaviso = new SobreavisoDAO(this);
		String datadehoje = ConverterUtil.getDateTime();
		listarSobreaviso(datadehoje);
		calendarView.setOnDateChangeListener(new OnDateChangeListener() {

			@SuppressLint("DefaultLocale")
			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
				// month + 1 é porque aqui ele
				// entrega um vetor de 0 a 11,
				// então na conversão trabalha de 1 a 12
				String dataClick = dayOfMonth + "/" + (month + 1) + "/" + year;
				String data = ConverterUtil.converterData(dataClick, "yyyy-MM-dd");
				listarSobreaviso(data.toLowerCase());

			}
		});
	}

	private void listarSobreaviso(String data) {

		lista = bdSobreaviso.mockFiltraSobreaviso(data);
		List<CharSequence> valores = new ArrayList<CharSequence>();

		if (lista.isEmpty()) {
			valores.add("N/A SOBREAVISO");
		}
		for (Funcionario linhas : lista) {

			valores.add(linhas.getNome());
		}

		List<CharSequence> subvalores = new ArrayList<CharSequence>();
		if (lista.isEmpty()) {
			subvalores.add("");
		}
		for (Funcionario linhas : lista) {
			subvalores.add(linhas.getTel());
		}

		// Conversão de Lista de String para Vetor;
		String[] itens = new String[valores.size()];
		itens = valores.toArray(itens);

		String[] subitens = new String[subvalores.size()];
		subitens = subvalores.toArray(subitens);

		// Conversão de Vetor para Matriz;
		String[][] matriz = FormatterUtil.dimensionar(subitens, 1);

		lstSobreaviso = (ExpandableListView) findViewById(R.id.listSobreaviso);
		lstSobreaviso.setGroupIndicator(null);
		lstSobreaviso.setAdapter(new ExpandableListSobreavisoAdapter(this, itens, matriz));
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