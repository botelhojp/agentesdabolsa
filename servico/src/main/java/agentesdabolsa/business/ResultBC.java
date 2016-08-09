package agentesdabolsa.business;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ResultBC {
	
	private static ArrayList<Hashtable<String, Double>> resultValues = new ArrayList<Hashtable<String, Double>>();
	private static ArrayList<String> resultKeys = new ArrayList<String>();
	
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

}
