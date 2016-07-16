package br.com.siscamp.comum;

import android.content.Context;
import br.com.siscamp.model.MensagemAtualizar;
import br.com.siscamp.sqlite.ContatoDAO;
import br.com.siscamp.sqlite.EstruturaDAO;
import br.com.siscamp.sqlite.FuncionarioDAO;
import br.com.siscamp.sqlite.LimpaDAO;
import br.com.siscamp.sqlite.LinhaDAO;
import br.com.siscamp.sqlite.MovEstLinhaDAO;
import br.com.siscamp.sqlite.MovFunProgDAO;
import br.com.siscamp.sqlite.PlanoDAO;
import br.com.siscamp.sqlite.PleDAO;
import br.com.siscamp.sqlite.ProgServicoDAO;
import br.com.siscamp.sqlite.SobreavisoDAO;
import br.com.siscamp.sqlite.VaoDAO;
import br.com.siscamp.util.WebServiceUtil;

public class Atualizar {
	private static Context oContext;
	MensagemAtualizar update = new MensagemAtualizar();

	public Atualizar(Context context) {
		oContext = context;
	}

	public MensagemAtualizar atualizar() {

		boolean status = WebServiceUtil.statusWebService();
		if (status) {
			try {

				ContatoDAO contatoDAO = new ContatoDAO(oContext);
				EstruturaDAO estruturaDAO = new EstruturaDAO(oContext);
				FuncionarioDAO funcionarioDAO = new FuncionarioDAO(oContext);
				LinhaDAO linhaDAO = new LinhaDAO(oContext);
				MovEstLinhaDAO movestlinhaDAO = new MovEstLinhaDAO(oContext);
				MovFunProgDAO movfunprogDAO = new MovFunProgDAO(oContext);
				PleDAO pleDAO = new PleDAO(oContext);
				ProgServicoDAO progservicoDAO = new ProgServicoDAO(oContext);
				SobreavisoDAO sobreavisoDAO = new SobreavisoDAO(oContext);
				VaoDAO vaoDAO = new VaoDAO(oContext);
				LimpaDAO limpaDAO = new LimpaDAO(oContext);
				PlanoDAO planoDAO = new PlanoDAO(oContext);

				limpaDAO.limpaDados();
				contatoDAO.buscarContatos();
				estruturaDAO.buscarEstruturas();
				funcionarioDAO.buscarFuncionarios();
				linhaDAO.buscarLinhas();
				movestlinhaDAO.buscarMovEstLinha();
				movfunprogDAO.buscarMovFunProg();
				pleDAO.buscarPle();
				progservicoDAO.buscarProgServico();
				sobreavisoDAO.buscarSobreaviso();
				vaoDAO.buscarVao();
				planoDAO.mockPopulaPlano();

				update.setMensagem("Atualizando...");
				update.setErro(false);

			} catch (Exception e) {
				e.printStackTrace();
				update.setMensagem("Falha na Atualização!");
				update.setErro(true);
			}
			return update;
		} else {

			update.setMensagem("Sem Conexão!");
			update.setErro(true);
			return update;
		}

	}

}
