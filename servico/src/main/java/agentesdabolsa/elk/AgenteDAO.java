package agentesdabolsa.elk;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import agentesdabolsa.entity.Agente;
import agentesdabolsa.entity.elk.AgenteELK;
import agentesdabolsa.entity.elk.SearchResult;

public class AgenteDAO extends ELKDAO {
	
	private static final String RESOURCE = "agente";
	
	private static AgenteDAO dao = new AgenteDAO();
	
	
	private AgenteDAO(){
	}
	
	public static AgenteDAO getInstance() {
		return dao;
	}

	public AgenteELK insert(Agente agente) {
		return json.fromJson(insert(RESOURCE, agente.toString()), AgenteELK.class);
	}

	public String find(Agente agente) {
		return null;
	}
	
	public void deleteALL(){
		
	}

	public Agente findByID(String id) {
		String r = get(RESOURCE+ "/" + id);
		return json.fromJson(r, AgenteELK.class).get_source();
	}
	
	public Agente findByName(String value) {
		String field = "name";
		String r = get(RESOURCE + "/_search?q="+field+":"+value+"&pretty=true");
		try {
			JSONArray  jsonArray = new JSONArray("[" + r + "]");
			System.out.println(jsonArray.getJSONObject(0).getJSONObject("hits"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SearchResult o = json.fromJson(r, SearchResult.class);
//		return json.fromJson(r, SearchResult.class).get_source();
		return null;
	}
}
