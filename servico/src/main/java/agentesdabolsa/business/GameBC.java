package agentesdabolsa.business;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import agentesdabolsa.config.AppConfig;
import agentesdabolsa.dao.CotacaoDAO;
import agentesdabolsa.entity.Agente;
import agentesdabolsa.entity.Config;
import agentesdabolsa.entity.Cotacao;
import agentesdabolsa.entity.Game;
import agentesdabolsa.metric.IMetric;
import agentesdabolsa.metric.OperationMetric;
import jade.core.AID;

public class GameBC {

	private Hashtable<String, Game> users = new Hashtable<String, Game>();
	private CotacaoDAO cotacaoDao = CotacaoDAO.getInstance();
	private ConfigBC configBC = ConfigBC.getInstance();
	private IMetric metric;
	private Config config;
	private List<Agente> agents;
	private Hashtable<AID, Agente> agentsAID;
	private Integer runnerId;
	private Boolean enabledRandom;
	private Random random;
	private int iterations;
	private int currentIteration;
	private Thread thread;
	private Time time;

	public GameBC(Integer runnerId) {
		this.runnerId = runnerId;
		agentsAID = new Hashtable<AID, Agente>();
		users.clear();
		agents = new ArrayList<Agente>();
		config = configBC.getConfig();
		iterations = config.getIterationTotal();
	}

//	public GameBC(String user) {
//		start(user);
//	}

//	public static GameBC getInstance() {
//		if (instance == null) {
//			configure(0);
//		}
//		return instance;
//	}

	public Game start(String user) {
		try {
			enabledRandom = config.getRandom();
			random = new Random(config.getRandomSeed(), this);

			Game newGame = new Game();
			newGame.setCarteira(AppConfig.INITIAL_VALUE);
			newGame.setUser(user);
			users.put(user, newGame);
			return newGame;
		} catch (Exception e) {
			throw new RuntimeException("Erro ao criar novo jogo", e);
		}
	}

	public Game getGame(String user) {
		return users.get(user);
	}

	public void buy(Game game) {
		Cotacao cotacaoD = cotacaoDao.getCotacao(game.getAcao().getNomeres(), game.getFrom() - config.getStop());
		buy(game, game.getCarteira(), cotacaoD);
	}

	public void buy(Game game, Double value, Cotacao cotacaoD) {
		if (value == null) {
			value = game.getCarteira();
		}
		game.setAcaoAnterior(game.getAcao());
		game.setNovaCotacao(cotacaoD);
		Float antes = game.getCotacao().getPreult();
		Float depois = cotacaoD.getPreult();
		Float diff = Math.abs((depois - antes) / antes);
		game.setDiff(diff);
		if (depois >= antes) {
			game.setResultado(true);
			game.setCarteira((game.getCarteira() - value) + (value * (1 + diff)));
		} else {
			game.setResultado(false);
			game.setCarteira((game.getCarteira() - value) + (value * (1 - diff)));
		}
	}

	public void sell(Game game) {
		Cotacao cotacaoD = cotacaoDao.getCotacao(game.getAcao().getNomeres(), game.getFrom() - config.getStop());
		sell(game, game.getCarteira(), cotacaoD);
	}

	public void sell(Game game, Double value, Cotacao cotacaoD) {
		if (value == null) {
			value = game.getCarteira();
		}
		game.setAcaoAnterior(game.getAcao());

		game.setNovaCotacao(cotacaoD);
		Float antes = game.getCotacao().getPreult();
		Float depois = cotacaoD.getPreult();
		Float diff = Math.abs((depois - antes) / antes);
		game.setDiff(diff);
		if (depois <= antes) {
			game.setResultado(true);
			game.setCarteira((game.getCarteira() - value) + (value * (1 + diff)));
		} else {
			game.setResultado(false);
			game.setCarteira((game.getCarteira() - value) + (value * (1 - diff)));
		}
	}

	
	public Boolean isRandom() {
		return enabledRandom;
	}

	public void wait(Game game) {
		game.setAcaoAnterior(game.getAcao());
		game.setResultado(false);
	}

	public void add(Agente agente) {
		agents.add(agente);
		agentsAID.put(agente.getAID(), agente);
	}

	public List<Agente> getAgents() {
		return agents;
	}

	public  void start() {
		if (thread == null || !thread.isAlive()) {
			time = new Time(iterations, this);
			thread = new Thread(time);
			thread.start();
		}
	}

	public Agente getAgent(AID agentAID) {
		OperationMetric.count();
		return agentsAID.get(agentAID);
	}

	public void setMetric(IMetric metric) {
		this.metric = metric;		
	}

	public IMetric getMetric() {
		return metric;
	}

	public void stop() {
		if (thread != null && thread.isAlive()){
			time.finish();
		}
	}

	public void setCurrentIteration(int iteration) {
		currentIteration = iteration;
	}

	public int getCurrentIteration() {
		return currentIteration;
	}

	public double getNumber() {
		return random.getNumer();
	}

	public Integer getRunnerId() {
		return runnerId;
	}

	public Random getRandom() {
		return random;
	}
	
}
