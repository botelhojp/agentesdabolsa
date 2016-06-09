package agentesdabolsa.business;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import agentesdabolsa.dao.CotacaoDAO;
import agentesdabolsa.entity.Agente;
import agentesdabolsa.entity.Config;
import agentesdabolsa.entity.Cotacao;
import agentesdabolsa.entity.Game;

public class GameBC {
	private static GameBC instance;
	private static Hashtable<String, Game> games = new Hashtable<String, Game>();
	private CotacaoDAO cotacaoDao = CotacaoDAO.getInstance();
	private ConfigBC configBC = ConfigBC.getInstance();
	public static Config config;
	public static String[] acoes;
	public static Boolean random;

	private static List<Agente> agents;
	private static Thread time;

	private GameBC(int iterations) {
		agents = new ArrayList<Agente>();
		time = new Thread(new Time(agents, iterations));
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
			newGame.setCarteira(100000);
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
		time.start();
	}
}
