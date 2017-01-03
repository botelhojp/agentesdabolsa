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
	private static GameBC instance;
	private static Hashtable<String, Game> games = new Hashtable<String, Game>();
	private CotacaoDAO cotacaoDao = CotacaoDAO.getInstance();
	private ConfigBC configBC = ConfigBC.getInstance();
	private IMetric metric;
	public static Config config;
	public static String[] acoes;
	public static Boolean random;

	private static int iterations;
	private static int currentIteration;
	private static List<Agente> agents;
	private static Hashtable<AID, Agente> agentsAID;
	private static Thread thread;
	
	private static ArrayList<Hashtable<String, Double>> resultValues = new ArrayList<Hashtable<String, Double>>();
	private static ArrayList<String> resultKeys = new ArrayList<String>();
	private static Timer time;

	private GameBC(int _iterations) {
		iterations = _iterations;
		agents = new ArrayList<Agente>();
		agentsAID = new Hashtable<AID, Agente>();
		games.clear();
	}

	public static GameBC getInstance() {
		if (instance == null) {
			configure(0);
		}
		return instance;
	}

	public Game newGame(String user) {
		try {
			config = configBC.getConfig();
			acoes = config.getAcoes().trim().split(" ");
			random = config.getRandom();

			Game newGame = new Game();
			newGame.setCarteira(AppConfig.INITIAL_VALUE);
			newGame.setUser(user);
			games.put(user, newGame);
			return newGame;
		} catch (Exception e) {
			throw new RuntimeException("Erro ao criar novo jogo", e);
		}
	}

	public Game getGame(String user) {
		return games.get(user);
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

	public void wait(Game game) {
		game.setAcaoAnterior(game.getAcao());
		game.setResultado(false);
	}

	public void add(Agente agente) {
		agents.add(agente);
		agentsAID.put(agente.getAID(), agente);
	}

	public static List<Agente> getAgents() {
		return agents;
	}

	public static void setAgents(List<Agente> agents) {
		GameBC.agents = agents;
	}

	public static void configure(int iterations) {
		instance = new GameBC(iterations);
	}

	public static void start() {
		System.gc();
		time = new Timer(agents, iterations);
		thread = new Thread(time);
		thread.start();
	}

	public static Agente getAgent(AID agentAID) {
		OperationMetric.count();
		return agentsAID.get(agentAID);
	}

	public static void cleanResults() {
		resultValues.clear();
		resultKeys.clear();
	}

	public static void putResult(String trustName, double value, int iteration) {
		if (trustName == null) return;
		if (!resultKeys.contains(trustName)){
			resultKeys.add(trustName);
		}			
		if (resultValues.size() == iteration-1){
			Hashtable<String, Double> vl = new Hashtable<String, Double>();
			vl.put(trustName, value);
			resultValues.add(vl);
		}else{
			resultValues.get(iteration-1).put(trustName, value);
		}
	}

	@SuppressWarnings("rawtypes")
	public static Hashtable<String, List> getResult() {
		Hashtable<String, List> rs = new Hashtable<String, List>();
		rs.put("keys", resultKeys);
		rs.put("values", resultValues);
		return rs;
	}

	public void setMetric(IMetric metric) {
		this.metric = metric;		
	}

	public IMetric getMetric() {
		return metric;
	}

	public static void stop() {
		if (thread != null && thread.isAlive()){
			time.finish();
		}
	}

	public void setCurrentIteration(int iteration) {
		currentIteration = iteration;
	}

	public static int getCurrentIteration() {
		return currentIteration;
	}
	
}
