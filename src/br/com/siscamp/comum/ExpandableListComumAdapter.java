package br.com.siscamp.comum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import br.com.siscamp.R;

@SuppressLint("InflateParams")
public class ExpandableListComumAdapter extends BaseExpandableListAdapter {

	Context context;
	String[] listaPai;
	String[][] listafilho;

	public ExpandableListComumAdapter(Context context, String[] listaPai, String[][] listafilho) {
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

		final String childText = (String) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_item, null);
		}

		TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
		txtListChild.setText(childText);

		return convertView;
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

		// Criei um TextView que conterá as informações da List Principal
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
		// selecionável
		return true;
	}

}