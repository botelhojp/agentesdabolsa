package agentesdabolsa.business;

import java.util.Hashtable;

import agentesdabolsa.entity.Game;

public class RunnerBC {
	
	private static RunnerBC runner = new RunnerBC();
	
	public Hashtable<Integer, GameBC> runners = new Hashtable<Integer, GameBC>();
	
	private RunnerBC(){}
	
	public static RunnerBC getInstance(){
		return runner;
	}

	public Game getGame(Integer runnerId, String user) {
		return runners.get(runnerId).getGame(user);
	}

	public GameBC getRunner(Integer runnerId) {
		if (runners.containsKey(runnerId)){
			return runners.get(runnerId);
		}else{
			GameBC gameBC = new GameBC(runnerId);
			runners.put(runnerId, gameBC);
			return gameBC;
		}
	}
}
