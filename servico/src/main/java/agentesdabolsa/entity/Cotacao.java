package agentesdabolsa.entity;

public class Cotacao extends JSONBean {

	private long idAcao;
	private String datapre;
	private Float preabe;
	private Float premax;
	private Float premin;
	private Float premed;
	private Float preult;

	// PREOFC PREÇO DA MELHOR OFERTA DE COMPRA DO PAPEL-MERCADO
	private Float preofc;
	// PREOFV - PREÇO DA MELHOR OFERTA DE VENDA DO PAPEL-MERCADO
	private Float preofv;
	// TOTNEG - NEG. - NÚMERO DE NEGÓCIOS EFETUADOS COM O PAPELMERCADO NO PREGÃO
	private Long totneg;
	// QUATOT - QUANTIDADE TOTAL DE TÍTULOS NEGOCIADOS NESTE PAPELMERCADO
	private Long quatot;
	// VOLTOT - VOLUME TOTAL DE TÍTULOS NEGOCIADOS NESTE PAPEL-MERCADO
	private Long votot;


	/** default constructor */
	public Cotacao() {
	}

	/** full constructor */
	public Cotacao(Acao acao, String datapre, Float preabe, Float premax, Float premin, Float premed, Float preult, Float preofc, Float preofv, Long totneg, Long quatot, Long votot) {
		this.idAcao = acao.getId();
		this.datapre = datapre;
		this.preabe = preabe;
		this.premax = premax;
		this.premin = premin;
		this.premed = premed;
		this.preult = preult;
		this.preofc = preofc;
		this.preofv = preofv;
		this.totneg = totneg;
		this.quatot = quatot;
		this.votot = votot;
	}

	public Cotacao(String datapre) {
		this.datapre = datapre;
	}

	public long getIdAcao() {
		return idAcao;
	}

	public void setIdAcao(long idAcao) {
		this.idAcao = idAcao;
	}

	public String getDatapre() {
		return this.datapre;
	}

	public void setDatapre(String datapre) {
		this.datapre = datapre;
	}

	public Float getPreabe() {
		return this.preabe;
	}

	public void setPreabe(Float preabe) {
		this.preabe = preabe;
	}

	public Float getPremax() {
		return this.premax;
	}

	public void setPremax(Float premax) {
		this.premax = premax;
	}

	public Float getPremin() {
		return this.premin;
	}

	public void setPremin(Float premin) {
		this.premin = premin;
	}

	public Float getPremed() {
		return this.premed;
	}

	public void setPremed(Float premed) {
		this.premed = premed;
	}

	public Float getPreult() {
		return this.preult;
	}

	public void setPreult(Float preult) {
		this.preult = preult;
	}

	public Float getPreofc() {
		return this.preofc;
	}

	public void setPreofc(Float preofc) {
		this.preofc = preofc;
	}

	public Float getPreofv() {
		return this.preofv;
	}

	public void setPreofv(Float preofv) {
		this.preofv = preofv;
	}

	public Long getTotneg() {
		return this.totneg;
	}

	public void setTotneg(Long totneg) {
		this.totneg = totneg;
	}

	public Long getQuatot() {
		return this.quatot;
	}

	public void setQuatot(Long quatot) {
		this.quatot = quatot;
	}

	public Long getVotot() {
		return this.votot;
	}

	public void setVotot(Long votot) {
		this.votot = votot;
	}

	public int compareTo(Cotacao c) {
		int r = getPreult().compareTo(c.getPreult());
		return r;
	}
}
