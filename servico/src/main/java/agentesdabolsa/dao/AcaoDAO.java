package agentesdabolsa.dao;

import agentesdabolsa.entity.Acao;

public class AcaoDAO extends GenericDAO<Acao>{
	private static AcaoDAO dao = new AcaoDAO();
	
	String[] indiceBovespa = {"ABEV3","BBAS3","BBDC3","BBDC4","BBSE3","BRAP4","BRFS3","BRKM5","BRML3","BVMF3","CCRO3","CESP6","CIEL3","CMIG4","CPFE3","CPLE6","CSAN3","CSNA3","CTIP3","CYRE3","ECOR3","EMBR3","ENBR3","EQTL3","ESTC3","FIBR3","GGBR4","GOAU4","HGTX3","HYPE3","ITSA4","ITUB4","JBSS3","KLBN11","KROT3","LAME4","LREN3","MRFG3","MRVE3","MULT3","NATU3","OIBR3","PCAR4","PETR3","PETR4","QUAL3","RADL3","RENT3","RUMO3","SANB11","SBSP3","SMLE3","SUZB5","TBLE3","TIMP3","UGPA3","USIM5","VALE3","VALE5","VIVT4","WEGE3"};

	@Override
	protected String getResouce() {
		return "acao";
	}

	public static AcaoDAO getInstance() {
		return dao;
	}

	public Acao findByName(String nomeres) {
		return findByFieldUniqueResult("nomeres", nomeres);
	}

	public Acao getRandom() {		
		long from = Math.round(indiceBovespa.length * Math.random());
		return findByName(indiceBovespa[(int) from]);
	}
	
	
}
