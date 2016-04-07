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
	
	
	public long update(T bean) {
		try {
			JSONArray jsonArray = new JSONArray("[" + update(getResouce(), bean) + "]");
			return jsonArray.getJSONObject(0).getLong("_id");
		} catch (JSONException e) {
			throw new AppException("erro no parse json", e);
		}
	}

	public long delete(long id) {
		try {
			JSONArray jsonArray = new JSONArray("[" + delete(getResouce(), id) + "]");
			return jsonArray.getJSONObject(0).getLong("_id");
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

	public List<T> findByField(String field, String value) {
		ArrayList<T> rl = new ArrayList<T>();
		String r = get(getResouce() + "/_search?q=" + field + ":" + value + "&pretty=true");
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

}
