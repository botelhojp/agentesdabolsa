package agentesdabolsa.business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import org.omg.CORBA.portable.ApplicationException;

import agentesdabolsa.commons.AppUtils;
import agentesdabolsa.dao.AcaoDAO;
import agentesdabolsa.dao.CotacaoDAO;
import agentesdabolsa.entity.Acao;
import agentesdabolsa.entity.Cotacao;

public class ImportSerie {

	private int cont;

	public static Hashtable<String, String> currentStatus = null;

	static {
		currentStatus = new Hashtable<String, String>();
		currentStatus.put("value", "sleep");
	}

	private AcaoDAO acaoDao = AcaoDAO.getInstance();
	private CotacaoDAO cotacaoDao = CotacaoDAO.getInstance();

	public void importFile(File fileZip) {
		File fileTxt = AppUtils.descompacta(fileZip);
		loadFile(fileTxt, Long.MAX_VALUE);
		currentStatus.put("value", "done");
		fileTxt.delete();
		fileZip.delete();

	}

	public void loadFile(File dataFile, long lines) {
		BufferedReader file = null;
		try {
			file = new BufferedReader(new FileReader(dataFile));
			String sLine;
			cont = 1;
			while ((sLine = file.readLine()) != null && cont < lines) {
				process(sLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void process(String linha) throws ApplicationException, Exception {
		String nomeres = linha.substring(12, 24).trim();
		if (isCorpo(linha)) {
			cont++;
			System.out.print(".");
			if (cont % 100 == 0)
				System.out.print("\n" + cont + ": " + AppUtils.makeData(AppUtils.makeData(linha.substring(02, 10))));

			String nomefull = (linha.substring(27, 39).trim() + " " + linha.substring(39, 49).trim()).replaceAll("�","");

			agentesdabolsa.entity.Acao acao = acaoDao.findByName(nomeres);
			if (acao == null) {
				acao = new Acao();
				acao.setNomeres(nomeres);
				acao.setNomefull(nomefull);
				acaoDao.insert(acao);
			}

			String dt = AppUtils.makeData(AppUtils.makeData(linha.substring(02, 10)));

			List<Cotacao> r = cotacaoDao.findByField("idAcao", "" + acao.getId(), "datapre", dt);
			if (r.size() == 0) {
				Cotacao cotacao = new Cotacao();
				cotacao.setDatapre(AppUtils.makeData(AppUtils.makeData(linha.substring(02, 10))));
				cotacao.setPreabe(AppUtils.makeFloat(linha.substring(56, 69)) / 100);
				cotacao.setPremax(AppUtils.makeFloat(linha.substring(69, 82)) / 100);
				cotacao.setPremin(AppUtils.makeFloat(linha.substring(82, 95)) / 100);
				cotacao.setPremed(AppUtils.makeFloat(linha.substring(95, 108)) / 100);
				cotacao.setPreult(AppUtils.makeFloat(linha.substring(108, 121)) / 100);
				cotacao.setPreofc(AppUtils.makeFloat(linha.substring(121, 134)) / 100);
				cotacao.setPreofv(AppUtils.makeFloat(linha.substring(134, 147)) / 100);
				cotacao.setTotneg(AppUtils.makeLong(linha.substring(147, 152)));
				cotacao.setQuatot(AppUtils.makeLong(linha.substring(152, 170)));
				cotacao.setVotot(AppUtils.makeLong(linha.substring(170, 188)));
				cotacao.setIdAcao(acao.getId());
				cotacaoDao.insert(cotacao);
			}
			currentStatus.put("value", dt + " itens:" + cont);
		}
	}

	/**
	 * Informa se a linhas faz parte do corpo
	 * 
	 * @param linha
	 * @return
	 */
	private boolean isCorpo(String linha) {
		return (linha.substring(0, 2).equals("01"));
	}

	/**
	 * Carrega o arquivo com um número parametrizado de lihas
	 * 
	 * @param file
	 * @param linhas
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public void carregarFile(File file, long linhas) throws ApplicationException, Exception {
		if (!file.exists()) {
			throw new Exception("Arquivo nao encontrado (" + file.getAbsolutePath().toString() + ")");
		}
		loadFile(file, linhas);
	}

	/**
	 * Carrega arquivo com um número definido de linhas
	 * 
	 * @param file
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public void carregarFile(File file) throws ApplicationException, Exception {
		carregarFile(file, Long.MAX_VALUE);
	}

}
