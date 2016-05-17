package agentesdabolsa.entity;

public class Advice {
	private Action advice;
	private Agente agente;

	public Advice(Action advice, Agente agente) {
		super();
		this.advice = advice;
		this.agente = agente;
	}

	public Action getAdvice() {
		return advice;
	}

	public void setAdvice(Action advice) {
		this.advice = advice;
	}

	public Agente getAgente() {
		return agente;
	}

	public void setAgente(Agente agente) {
		this.agente = agente;
	}

}
