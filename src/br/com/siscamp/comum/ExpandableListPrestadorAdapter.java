package br.com.siscamp.comum;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import br.com.siscamp.util.FormatterUtil;
import br.com.siscamp.util.MensagemUtil;

@SuppressLint("InflateParams")
public class ExpandableListPrestadorAdapter extends BaseExpandableListAdapter {

	Context context;
	String[] listaPai;
	String[][] listafilho;

	public ExpandableListPrestadorAdapter(Context context, String[] listaPai, String[][] listafilho) {
		this.context = context;
		this.listaPai = listaPai;
		this.listafilho = listafilho;

	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return listafilho[groupPosition][childPosition];
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		final int tel = childPosition;
		final String childText = (String) getChild(groupPosition, childPosition);
		final String numero;
		String auxNumero = childText;

		auxNumero = FormatterUtil.onlyNumbers(childText);
		numero = auxNumero;

		// Criei um TextView que conter� as informa��es da sublista que
		TextView textViewSubLista = new TextView(context);
		// Setando texto
		textViewSubLista.setText(childText);
		// Definimos um alinhamento
		textViewSubLista.setPadding(10, 5, 0, 5);
		// tamanho do texto
		textViewSubLista.setTextSize(18);
		textViewSubLista.setTextIsSelectable(true);
		textViewSubLista.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (tel == 1 || tel == 2) {
					try {
						Intent chamada = new Intent(Intent.ACTION_DIAL);

						if (numero.equals("N/A") || numero == null || numero.isEmpty()) {
							MensagemUtil.AddMsgFast((Activity) context, "N�o h� telefone cadastrado!");
						} else {
							chamada.setData(Uri.parse("tel:" + numero));
							context.startActivity(chamada);
						}

					} catch (ActivityNotFoundException act) {
						Log.e("Chamada", "falha", act);
					}
				}

			}

		});

		return textViewSubLista;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return listafilho[groupPosition].length;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return listaPai[groupPosition];
	}

	@Override
	public int getGroupCount() {
		return listaPai.length;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

		// Criei um TextView que conter� as informa��es da List Principal
		TextView textViewCategorias = new TextView(context);
		textViewCategorias.setText(listaPai[groupPosition]);
		// alinhamento
		textViewCategorias.setPadding(10, 5, 0, 5);
		// tamanho do texto
		textViewCategorias.setTextSize(20);
		// texto em negrito
		textViewCategorias.setTypeface(null, Typeface.BOLD);

		return textViewCategorias;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// Defina o return como sendo true se vc desejar que sua sublista seja
		// selecion�vel
		return true;
	}

}