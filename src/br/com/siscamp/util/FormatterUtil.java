package br.com.siscamp.util;

import java.io.File;

import android.os.Environment;

public class FormatterUtil {

	public static String[][] dimensionar(String[] vetor, int largura) {

		int altura = vetor.length / largura;

		String[][] matriz = new String[altura][largura];

		for (int i = 0; i < vetor.length; i++) {

			matriz[i / largura][i % largura] = vetor[i];
		}
		return matriz;
	}

	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	public static String padLeft(String s, int n) {
		return String.format("%1$" + n + "s", s);
	}

	public static String onlyNumbers(String str) {
		if (str != null) {
			return str.replaceAll("[^0123456789]", "");

		} else {
			return "";

		}

	}

	public static File getDirFromSDCard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File sdcard = Environment.getExternalStorageDirectory().getAbsoluteFile();
			File dir = new File(sdcard, "Siscamp" + File.separator + "planosfolder");
			if (!dir.exists())
				dir.mkdirs();
			return dir;
		} else {
			return null;
		}
	}

	// File file = new File(Environment.getExternalStorageDirectory(),
	// "Report.pdf");
	// Uri path = Uri.fromFile(file);
	// Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
	// pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	// pdfOpenintent.setDataAndType(path, "application/pdf");
	// try {
	// startActivity(pdfOpenintent);
	// } catch (ActivityNotFoundException e) {
	//
	// }

}
