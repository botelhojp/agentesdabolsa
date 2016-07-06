package agentesdabolsa.business;

public class Random {

	private static long count = 1;
	
	private static ConfigBC config = ConfigBC.getInstance();

	public static double getNumer() {
		count++;
		String s = "" + (count / (config.getConfig().getRandomSeed()*1.0));
		return Double.parseDouble("" + s.charAt(s.length()-1) + s.charAt(s.length()-2))/100;
	}
	
	public static void reset() {
		count = 1;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 3000; i++) {
			System.out.println("--" + i);
			System.out.println(Math.random());
			System.out.println(Random.getNumer());
		}

	}

}
