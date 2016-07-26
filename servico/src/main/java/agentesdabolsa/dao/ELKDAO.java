package agentesdabolsa.dao;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;

import com.google.gson.Gson;

import agentesdabolsa.config.AppConfig;
import agentesdabolsa.entity.JSONBean;
import agentesdabolsa.exception.AppException;

public class ELKDAO<T extends JSONBean> {

	public static String SERVER = AppConfig.getInstance().getELKServer();

	private static boolean enableCache = true;
	private static Hashtable<String, String> cache = new Hashtable<String, String>();

	public static String DB = "agdb";

	protected Gson json = new Gson();

	/**
	 * Retorna a versao do ELK
	 * 
	 * @return
	 */
	public String getVersion() {
		return httpGet("");
	}

	public String get(String resouce) {
		return httpGet(resouce);
	}

	public String get(String resouce, String order) {
		return http(resouce, "POST", order);
	}

	public String insert(String resouce, T bean) {
		return http(resouce + "/" + bean.getId(), "POST", bean.toString());
	}

	public String update(String resouce, T bean) {
		return http(resouce + "/" + bean.getId(), "PUT", bean.toString());
	}

	public String delete(String resouce, long id) {
		return http(resouce + "/" + id, "DELETE", null);
	}

	public String httpGet(String _url) {
		return http(_url, "GET", null);
	}

	public String http(String resource, String method, String content) {
		HttpURLConnection conn = null;
		InputStream is = null;
		try {
			URL url;
			if (resource != null && resource.length() > 0) {
				url = new URL(SERVER + "/" + DB + "/" + resource);
			} else {
				url = new URL(SERVER + "/" + resource);
			}

			if (enableCache) {
				String key = getKey(url, method, content);
				if (cache.containsKey(key)) {
					return cache.get(key);
				} else {
					System.out.println(method + " - " + url);
				}
			} else {
				System.out.println(method + " - " + url);
			}
			conn = (HttpURLConnection) url.openConnection();
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod(method);
			conn.setRequestProperty("Content-Type", "application/json");

			if (content != null) {
				DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
				wr.writeBytes(content);
				wr.flush();
				wr.close();
			}

			if (conn.getResponseCode() == 200) {
				is = new BufferedInputStream(conn.getInputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader((is)));
				String responseBody = "";
				String output;
				while ((output = br.readLine()) != null) {
					responseBody += output;
				}
				if (enableCache) {
					cache.put(getKey(url, method, content), responseBody);
				}
				return responseBody;
			}
			throw new AppException("Connection error, code: " + conn.getResponseCode());
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	private String getKey(URL url, String method, String content) {
		String key = "";
		key += (url != null) ? url.toString() + "_" : "";
		key += (method != null) ? method.toString() + "_" : "";
		key += (content != null) ? "" + content.hashCode() : "";
		return key;
	}

	public static void cleanCache() {
		cache.clear();
	}
}
