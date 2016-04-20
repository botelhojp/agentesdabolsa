package agentesdabolsa.entity;

public class Game {

	private double carteira;
	private Acao acao;
	private Cotacao cotacao;
	private Cotacao novaCotacao;
	private long from;
	private boolean resultado;
	private Acao acaoAnterior;
	private double diff;

	public Acao getAcaoAnterior() {
		return acaoAnterior;
	}

	public void setAcaoAnterior(Acao acaoAnterior) {
		this.acaoAnterior = acaoAnterior;
	}

	private String user;

	public double getCarteira() {
		return carteira;
	}

	public void setCarteira(double carteira) {
		this.carteira = carteira;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setAcao(Acao acao) {
		this.acao = acao;
	}

	public Acao getAcao() {
		return acao;
	}

	public Cotacao getCotacao() {
		return cotacao;
	}

	public void setCotacao(Cotacao cotacao) {
		this.cotacao = cotacao;
	}

	public long getFrom() {
		return from;
	}

	public void setFrom(long from) {
		this.from = from;
	}

	public boolean getResultado() {
		return resultado;
	}

	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}

	public Cotacao getNovaCotacao() {
		return novaCotacao;
	}

	public void setNovaCotacao(Cotacao novaCotacao) {
		this.novaCotacao = novaCotacao;
	}

	public double getDiff() {
		return diff;
	}

	public void setDiff(double diff) {
		this.diff = diff;
	}
}
