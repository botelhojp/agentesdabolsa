package agentesdabolsa.dao;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import agentesdabolsa.entity.JSONBean;
import agentesdabolsa.exception.AppException;

@SuppressWarnings("unchecked")
public abstract class GenericDAO<T extends JSONBean> extends ELKDAO<T> {

	protected abstract String getResouce();
	
	protected abstract String getOrder();

	private Class<T> clazz;

	protected GenericDAO() {
		this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public long insert(T bean) {
		try {
			long timeStamp = GregorianCalendar.getInstance().getTimeInMillis();
			bean.setId(timeStamp);
			JSONArray jsonArray = new JSONArray("[" + insert(getResouce(), bean) + "]");
			return jsonArray.getJSONObject(0).getLong("_id");
		} catch (JSONException e) {
			throw new AppException("erro no parse json", e);
		}
	}
	
	public long count(){
		try {
			JSONArray jsonArray = new JSONArray("[" + get(getResouce() + "/_count") + "]");
			return jsonArray.getJSONObject(0).getLong("count");
		} catch (JSONException e) {
			throw new AppException("erro no parse json", e);
		}
	}
	public long update(T bean) {
		try {
			JSONArray jsonArray = new JSONArray("[" + update(getResouce(), bean) + "]");
			return jsonArray.getJSONObject(0).getLong("_id");
		} catch (JSONException e) {
			throw new AppException("erro no parse json", e);
		}
	}

	public long delete(long id) {
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray("[" + delete(getResouce(), id) + "]");
			if (jsonArray.getJSONObject(0).getBoolean("found")){
				return jsonArray.getJSONObject(0).getLong("_id");
			}
			return 0;
		} catch (JSONException e) {
			throw new AppException("erro no parse json", e);
		}
	}
	
	public List<T> list(){
		if ( getOrder() == null){
			return list(get(getResouce() + "/_search"));
		}
		return list(get(getResouce() + "/_search", "{\"sort\" : [{ \"" + getOrder()+ "\" : {\"order\" : \"asc\"}}]}"));		
	}
	
	public List<T> listOrderDesc(String field){
		String filtro = "{ \"sort\" : [{ \""+field+"\" : \"desc\" }]}";
		String jsonResult = http(getResouce()+ "/_search", "POST", filtro);
		return list(jsonResult);
	}
	
	public T getEntinty(long from, long size){
		String filtro = "{\"from\" : "+from+", \"size\" : "+size+"}";
		String jsonResult = http(getResouce()+ "/_search", "POST", filtro);
		return list(jsonResult).get(0);
	}
	
	public List<T> listOrderAsc(String field){
		String filtro = "{\"from\" : 0, \"size\" : 500, \"sort\" : [{ \""+field+"\" : \"asc\" }]}";
		String jsonResult = http(getResouce()+ "/_search", "POST", filtro);
		return list(jsonResult);
	}
	
	
	
	public List<T> list(String jsonResult){
		ArrayList<T> rl = new ArrayList<T>();
		try {
			JSONArray jsonArray = new JSONArray("[" + jsonResult + "]");
			JSONArray hits = jsonArray.getJSONObject(0).getJSONObject("hits").getJSONArray("hits");
			for (int i = 0; i < hits.length(); i++) {
				rl.add(json.fromJson(hits.getJSONObject(i).getJSONObject("_source").toString(), clazz));
			}
			return rl;
		} catch (JSONException e) {
			throw new AppException("erro no parse json", e);
		}	
	}
	
	

	public T findByID(long id) {
		try {
			String r = get(getResouce() + "/" + id);
			if (r == null){
				return null;
			}
			JSONArray jsonArray = new JSONArray("[" + r + "]");
			String result = jsonArray.getJSONObject(0).getString("_source");
			return (T) json.fromJson(result, clazz);
		} catch (JSONException e) {
			throw new AppException("erro no parse json", e);
		}
	}
	
	public List<T> findByField(String... params) {
		String consult = "q=";
		for (int i = 0; i < params.length; i= i + 2) {
			consult += "+" + params[i] + ":" + params[i+1];
			if (i + 2 < params.length){
				consult += "%20";
			}
		}
		consult+="&default_operator=AND";
		ArrayList<T> rl = new ArrayList<T>();
		String r = get(getResouce() + "/_search?" + consult +  "&pretty=true");
		try {
			JSONArray jsonArray = new JSONArray("[" + r + "]");
			JSONArray hits = jsonArray.getJSONObject(0).getJSONObject("hits").getJSONArray("hits");
			for (int i = 0; i < hits.length(); i++) {
				rl.add(json.fromJson(hits.getJSONObject(i).getJSONObject("_source").toString(), clazz));
			}
			return rl;
		} catch (JSONException e) {
			throw new AppException("erro no parse json", e);
		}
	}
	
	
	public long deleteAll() {
		long count = 0;
		boolean done = false;
		while (!done) {
			List<T> list = list();
			if (list.isEmpty()) {
				done = true;
			} else {
				for (T t : list) {
					count++;
					try{
						delete(t.getId());
					}catch(AppException e ){
						
					}
				}
			}
		}
		return count;
	}
	
	
	public T findByFieldUniqueResult(String field, String value) {
		List<T> rl = findByField(field, value);
		if (rl.size() >= 1){
			return rl.get(0);
		}
		return null;
	}

}
