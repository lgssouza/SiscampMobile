package br.com.siscamp.util;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class ConverterUtil extends JsonArrayRequest {
	// private static Locale brasil = new Locale("pt", "br");
	public ConverterUtil(String url, Listener<JSONArray> listener, ErrorListener errorListener) {
		super(url, listener, errorListener);
	}

	@Override
	protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
		Response<JSONArray> array = null;

		try {
			String string = new String(response.data, "UTF-8");
			array = Response.success(new JSONArray(string), HttpHeaderParser.parseCacheHeaders(response));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return array;
	}

	public static String JsonToString(String bf) {
		byte ptext[] = bf.getBytes(ISO_8859_1);
		String aft = new String(ptext, UTF_8);
		return aft;
	}

	public static String converterData(String data, String formato) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date dataFmt1 = null;
		try {
			dataFmt1 = format.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat novoFormat = new SimpleDateFormat(formato);
		String dataFmt2 = novoFormat.format(dataFmt1);

		return dataFmt2;
	}

	public static String converterDataExibe(String data) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date d = formatter.parse(data);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(d);
	}

	public static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
