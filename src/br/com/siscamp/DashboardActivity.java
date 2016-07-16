package br.com.siscamp;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import br.com.siscamp.comum.Atualizar;
import br.com.siscamp.model.MensagemAtualizar;
import br.com.siscamp.table.TableMain;
import br.com.siscamp.util.ConverterUtil;
import br.com.siscamp.util.MensagemUtil;

public class DashboardActivity extends Activity {
	Context oContext;
	NumberPicker npServicos;
	AlertDialog alertdialog;
	static final int DATE_DIALOG_ID = 0;
	int idBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		oContext = this;

		Intent in = getIntent();
		Bundle dados = new Bundle();
		dados = in.getExtras();

		if (dados != null) {
			String msg = dados.getString("msg");
			MensagemUtil.AddMsgFast(DashboardActivity.this, msg);
		}

	}

	public void listarLinhas(View view) {
		new LoadingAsyncLinhas().execute();

	}

	public void listarContatos(View view) {
		new LoadingAsyncContatos().execute();

	}

	public void listarSobreaviso(View view) {
		new LoadingAsyncSobreaviso().execute();

	}

	public void listarProgServico(View view) {
		final Dialog dialog = new Dialog(DashboardActivity.this);
		dialog.setContentView(R.layout.dialog_servicos);
		dialog.setTitle("Selecione o Filtro");
		Button btnDiario = (Button) dialog.findViewById(R.id.btn_diario);
		Button btnSemanal = (Button) dialog.findViewById(R.id.btn_semanal);
		Button btnMensal = (Button) dialog.findViewById(R.id.btn_mensal);

		btnDiario.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				idBtn = 1;
				mostrarServicos();
				dialog.dismiss();

			}
		});
		btnSemanal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				idBtn = 2;
				mostrarServicos();
				dialog.dismiss();

			}
		});
		btnMensal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				idBtn = 3;
				mostrarServicos();
				dialog.dismiss();

			}
		});
		dialog.show();

	}

	private class LoadingAsyncLinhas extends AsyncTask<Void, Void, String> {
		ProgressDialog progressDialog = new ProgressDialog(DashboardActivity.this);

		@Override
		protected void onPreExecute() {
			progressDialog.setMessage("Carregando...");
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(String msg) {
			progressDialog.dismiss();

			Intent i = new Intent(DashboardActivity.this, ListaLinhasActivity.class);
			startActivity(i);

		}

		@Override
		protected String doInBackground(Void... params) {
			return null;

		}

	}

	private class LoadingAsyncContatos extends AsyncTask<Void, Void, String> {
		ProgressDialog progressDialog = new ProgressDialog(DashboardActivity.this);

		@Override
		protected void onPreExecute() {
			progressDialog.setMessage("Carregando...");
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(String msg) {
			progressDialog.dismiss();

			Intent i = new Intent(DashboardActivity.this, ContatosActivity.class);
			startActivity(i);

		}

		@Override
		protected String doInBackground(Void... params) {
			return null;

		}

	}

	private class LoadingAsyncSobreaviso extends AsyncTask<Void, Void, String> {
		ProgressDialog progressDialog = new ProgressDialog(DashboardActivity.this);

		@Override
		protected void onPreExecute() {
			progressDialog.setMessage("Carregando...");
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(String msg) {
			progressDialog.dismiss();

			Intent i = new Intent(DashboardActivity.this, SobreavisoActivity.class);
			startActivity(i);

		}

		@Override
		protected String doInBackground(Void... params) {
			return null;

		}

	}

	// Parte dos Serviços
	@SuppressWarnings("deprecation")
	public void mostrarServicos() {
		if (idBtn == 1) {
			showDialog(DATE_DIALOG_ID);
		} else if (idBtn == 2) {
			showNumberPicker();
		} else if (idBtn == 3) {
			showDialog(DATE_DIALOG_ID);
		}

	}

	private void showNumberPicker() {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v1 = inflater.inflate(R.layout.numberpicker, null);
		npServicos = (NumberPicker) v1.findViewById(R.id.np_servicos);

		npServicos.setMaxValue(54);
		npServicos.setMinValue(1);
		npServicos.setWrapSelectorWheel(true);

		AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);

		builder.setView(v1);
		builder.setTitle("Selecione o Número da Semana");
		builder.setPositiveButton("Selecionar", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.e("valor do np", String.valueOf(npServicos.getValue()));
				System.out.println(npServicos.getValue());
				int semana = npServicos.getValue();
				novaActivity(String.valueOf(semana), "semanal");

			}
		});

		builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				alertdialog.dismiss();

			}
		});

		alertdialog = builder.create();
		alertdialog.show();

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Calendar calendario = Calendar.getInstance();
		int ano = calendario.get(Calendar.YEAR);
		int mes = calendario.get(Calendar.MONTH);
		int dia = calendario.get(Calendar.DAY_OF_MONTH);

		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, ano, mes, dia);
		}
		return null;

	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			String data = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
			// view.issShown é para não deixar o ouvinte ser chamado duas vezes
			if (view.isShown()) {
				if (idBtn == 1)
					novaActivity(ConverterUtil.converterData(data, "yyyy-MM-dd"), "diario");
				else if (idBtn == 3)
					novaActivity(String.valueOf(monthOfYear + 1), "mensal");
			}
		}
	};

	public void novaActivity(String dataParam, String tipoParam) {

		Intent intent = new Intent(DashboardActivity.this, TableMain.class);
		Bundle dados = new Bundle();
		dados.putString("data", dataParam);
		dados.putString("tipo", tipoParam);
		intent.putExtras(dados);

		startActivity(intent);

	}

	// Fim dos Servicos

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.menu, menu);
		getMenuInflater().inflate(R.menu.dashboard, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int idMenuItem = item.getItemId();
		switch (idMenuItem) {
		case R.id.menuSair:
			MensagemUtil.AddMsgConfirm(DashboardActivity.this, getText(R.string.lbl_sair), getText(R.string.msg_sair),
					R.drawable.logout, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});

			break;
		case R.id.menuAtualizar:
			MensagemUtil.AddMsgConfirm(DashboardActivity.this, getText(R.string.lbl_atualizar),
					getText(R.string.msg_atualizar), R.drawable.update, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							new LoadingAsyncAtualizar().execute();
						}
					});
			break;

		}

		return true;
	}

	private class LoadingAsyncAtualizar extends AsyncTask<Void, Void, MensagemAtualizar> {
		ProgressDialog progressDialog = new ProgressDialog(DashboardActivity.this);

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(DashboardActivity.this);
			progressDialog.setTitle("Aguarde..");
			progressDialog.setMessage("Atualizando...");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		protected void onPostExecute(MensagemAtualizar update) {

			if (progressDialog.isShowing()) {
				progressDialog.dismiss();
			}

			if (!update.isErro()) {
				MensagemUtil.AddMsgOk(DashboardActivity.this, "Advertência", update.getMensagem(), 0);
			} else {
				MensagemUtil.AddMsgOk(DashboardActivity.this, "Advertência", update.getMensagem(), 0);
			}

		}

		@Override
		protected MensagemAtualizar doInBackground(Void... params) {
			Atualizar novoupdate = new Atualizar(DashboardActivity.this);
			MensagemAtualizar retornoupdate = novoupdate.atualizar();

			return retornoupdate;

		}

	}

}
