package agentesdabolsa.dao;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import agentesdabolsa.config.AppConfig;
import agentesdabolsa.entity.JSONBean;
import agentesdabolsa.exception.AppException;

public class ELKDAO<T extends JSONBean> {

	public static String SERVER = AppConfig.getInstance().getELKServer();

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

	public void deleteAll(String resouce) {
		http(resouce + "/" + "_search?q=*", "DELETE", null);
	}

	public String get(String resouce) {
		return httpGet(resouce);
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
			System.out.println(method + " - "+ url);
			// System.out.println(method + " " +url.toString());
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
			is = new BufferedInputStream(conn.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader((is)));
			String responseBody = "";
			String output;
			while ((output = br.readLine()) != null) {
				responseBody += output;
			}
			return responseBody;
		} catch (FileNotFoundException e) {
			return null;
		} catch (Exception e) {
			throw new AppException("Erro no conexao ao servidor", e);
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

}
