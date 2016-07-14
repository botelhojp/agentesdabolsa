package agentesdabolsa.business;

public class Random {

	private static java.util.Random rm;

	public static double getNumer() {
		return rm.nextDouble();
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
