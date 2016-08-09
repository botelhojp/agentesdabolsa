package agentesdabolsa.business;

import agentesdabolsa.entity.Config;

public class Random {

	private java.util.Random rm;

	private Config cfg;

	private double currentIteration;
	private double total;
	private int range;
	private int phase;
	private GameBC gameBC;

	public Random(int seed, GameBC _gameBC) {
		rm = new java.util.Random(seed);
		gameBC = _gameBC;
	}

	public double getNumer() {
		return rm.nextDouble();
	}

	public boolean isAbove(double base) {
		if (revert()) {
			base = revert(base);
		}
		return getNumer() > base;
	}

	private double revert(double base) {
		cfg = ConfigBC.getInstance().getConfig();
		if (cfg.getChangeType().equals("AB")) {
			return (base - 1.0) * (-1.0);
		}
		if (cfg.getChangeType().equals("GR")) {
			if (base > 0.5) {
				return base - (currentIteration / 2 / total);
			}
			return base + (currentIteration / 2 / total);
		}
		throw new RuntimeException("invalid option " + cfg.getChangeType());
	}

	private boolean revert() {
		cfg = ConfigBC.getInstance().getConfig();
		int changes = cfg.getChanges();
		if (changes > 0) {
			currentIteration = gameBC.getCurrentIteration() - 1;
			total = cfg.getIterationTotal();
			range = (int) total / (changes + 1);
			phase = (int) currentIteration / range;
			return (phase % 2 != 0);
		}
		return false;
	}

//	public static void main(String[] args) {
//		Random r = new Random(7);
//		for (int i = 0; i < 3000; i++) {
//			System.out.println(i + ": " + r.getNumer());
//		}
//	}
	
}
