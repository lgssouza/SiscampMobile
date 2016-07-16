package br.com.siscamp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.Toast;

public class MensagemUtil {

	/**
	 * Método de criação de mensagem rápidas usando Toast
	 * 
	 * @param activity
	 * @param Msg
	 */
	public static void AddMsgFast(Activity activity, String Msg) {
		Toast.makeText(activity, Msg, Toast.LENGTH_SHORT).show();

	}
	
	public static void AddMsgOk(Activity activity, String title, String msg, int icon){
		AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(activity);
		builderAlertDialog.setTitle(title);
		builderAlertDialog.setMessage(msg);
		builderAlertDialog.setNeutralButton("Ok", null);
		builderAlertDialog.setIcon(icon);
		builderAlertDialog.show();
	}
	
	public static void AddMsgConfirm(Activity activity, CharSequence title, CharSequence msg, int icone, android.content.DialogInterface.OnClickListener listener){
		AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(activity);
		builderAlertDialog.setTitle(title);
		builderAlertDialog.setMessage(msg);
		builderAlertDialog.setPositiveButton("Sim", listener);
		builderAlertDialog.setNegativeButton("Não", null);
		builderAlertDialog.setIcon(icone);
		builderAlertDialog.show();
	}
}
