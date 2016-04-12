package agentesdabolsa.entity;

public class Acao  extends JSONBean{
	
    private String nomeres;
    private String nomefull;

    public Acao() {
    }

    public Acao(String nomeres) {
        this.nomeres = nomeres;
    }
    public String getNomeres() {
        return this.nomeres;
    }
    
    public void setNomeres(String nomeres) {
        this.nomeres = nomeres;
    }
   

	public String getNomefull() {
		return nomefull;
	}

	public void setNomefull(String nomefull) {
		this.nomefull = nomefull;
	}
}
