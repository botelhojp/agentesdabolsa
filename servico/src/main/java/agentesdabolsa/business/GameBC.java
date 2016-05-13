package agentesdabolsa.business;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import agentesdabolsa.dao.CotacaoDAO;
import agentesdabolsa.entity.Agente;
import agentesdabolsa.entity.Cotacao;
import agentesdabolsa.entity.Game;

public class GameBC {
	private static GameBC instance;

	private Hashtable<String, Game> games = new Hashtable<String, Game>();
	
	private CotacaoDAO cotacaoDao = CotacaoDAO.getInstance();

	private static List<Agente> list;
	private static Thread time;

	

	private GameBC(int iterations) {
		list = new ArrayList<Agente>();
		time = new Thread(new Time(list, iterations));
	}

	public static GameBC getInstance() {
		if (instance == null){
			configure(0);
		}
		return instance;
	}

	public Game newGame(String user) {
		Game n = new Game();
		n.setCarteira(100000);
		n.setUser(user);
		games.put(user, n);
		return n;
	}

	public Game getGame(String user) {
		return games.get(user);
	}
	
	public void buy(Game game) {
		Cotacao cotacaoD = cotacaoDao.getCotacao(game.getAcao().getNomeres(), game.getFrom() - 20);
		buy(game, game.getCarteira(), cotacaoD);
	}

	public void buy(Game game, Double value, Cotacao cotacaoD) {
		if (value == null){
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
		Cotacao cotacaoD = cotacaoDao.getCotacao(game.getAcao().getNomeres(), game.getFrom() - 20);
		sell(game, game.getCarteira(), cotacaoD);
	}

	public void sell(Game game, Double value, Cotacao cotacaoD) {
		if (value == null){
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
		list.add(agente);
	}

	public static void configure(int iterations) {
		instance = new GameBC(iterations);
	}

	public static void start() {
		time.start();
	}
}
