package agentesdabolsa.business;

import agentesdabolsa.entity.Config;

public class Random {

	private static java.util.Random rm;

	private static Config cfg;

	private static double currentIteration;
	private static double total;
	private static int range;
	private static int phase;

	public static double getNumer() {
		return rm.nextDouble();
	}

	public static boolean isAbove(double base) {
		if (revert()) {
			base = revert(base);
		}
		return getNumer() > base;
	}

	private static double revert(double base) {
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

	private static boolean revert() {
		cfg = ConfigBC.getInstance().getConfig();
		int changes = cfg.getChanges();
		if (changes > 0) {
			currentIteration = GameBC.getCurrentIteration() - 1;
			total = cfg.getIterationTotal();
			range = (int) total / (changes + 1);
			phase = (int) currentIteration / range;
			return (phase % 2 != 0);
		}
		return false;
	}

	public static void initSeed(int seed) {
		rm = new java.util.Random(seed);
	}

	public static void main(String[] args) {
		Random.initSeed(7);
		for (int i = 0; i < 3000; i++) {
			System.out.println(i + ": " + Random.getNumer());
		}
	}
}
