package br.com.siscamp.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import com.google.gson.Gson;

import br.com.siscamp.comum.Constantes;
import br.com.siscamp.model.Usuario;

public class WebServiceUtil {

	public static Usuario validarLoginRest(String login, String senha) {
		Usuario resultado = null;
		try {
			java.net.URL url = new java.net.URL(

					Constantes.CAMINHO_URL + "/SiscampRestful/funcionarios/logar?usuario=" + login + "&senha=" + senha);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			BufferedInputStream buffer = new BufferedInputStream(connection.getInputStream());

			String str = converterToString(buffer);
			resultado = new Gson().fromJson(str, Usuario.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultado;
	}

	public static boolean statusWebService() {
		boolean resultado = false;
		try {
			java.net.URL url = new java.net.URL(

					Constantes.CAMINHO_URL + "/SiscampRestful/servico/online");

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			BufferedInputStream buffer = new BufferedInputStream(connection.getInputStream());

			resultado = Boolean.parseBoolean(converterToString(buffer));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultado;
	}

	private static String converterToString(InputStream in) {
		BufferedReader buffer = new BufferedReader(new InputStreamReader(in));

		StringBuilder builder = new StringBuilder();
		String linha = null;
		try {
			while ((linha = buffer.readLine()) != null) {
				builder.append(linha);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return builder.toString();
	}

}
