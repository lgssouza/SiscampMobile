package br.com.siscamp.comum;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {
	private Context context;
	private String[] lista;

	public GridViewAdapter(Context ctx, String[] lista2) {
		this.context = ctx;
		this.lista = lista2;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lista.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lista[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView textGrid = new TextView(context);
		textGrid.setText(lista[position]);
		textGrid.setTextSize(20);
		textGrid.setTypeface(null, Typeface.BOLD);
		textGrid.setPadding(10, 5, 0, 5);

		return textGrid;
	}
}
