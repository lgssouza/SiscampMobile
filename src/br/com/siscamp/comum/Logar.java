package br.com.siscamp.comum;

import android.content.Context;
import br.com.siscamp.R;

import br.com.siscamp.dominio.ValidacaoLogin;
import br.com.siscamp.model.Usuario;
import br.com.siscamp.util.WebServiceUtil;

//Bussines Object - Classes de Objetos de Negócio
public class Logar {

	private Context context;

	public Logar(Context context) {
		this.context = context;
	}

	public ValidacaoLogin validarLogin(String login, String senha) {
		ValidacaoLogin retorno = new ValidacaoLogin();
		Usuario usuario = null;
		if (login == null || login.equals("")) {
			retorno.setValido(false);
			retorno.setMensagem(context.getString(R.string.msg_login_obrig));
		} else if (senha == null || senha.equals("")) {
			retorno.setValido(false);
			retorno.setMensagem(context.getString(R.string.msg_senha_obrig));
		} else if ((usuario = WebServiceUtil.validarLoginRest(login, senha)) != null && usuario.isLogado()) {
			retorno.setValido(true);
			retorno.setMensagem(context.getString(R.string.msg_login_valido));
		} else {
			retorno.setValido(false);
			retorno.setMensagem(context.getString(R.string.msg_login_invalido));
		}
		retorno.setUsuario(usuario);
		return retorno;
	}

}
