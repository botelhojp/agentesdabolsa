package agentesdabolsa.elk;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.GregorianCalendar;

import com.google.gson.Gson;

import agentesdabolsa.config.AppConfig;
import agentesdabolsa.exception.AppExceptions;

public class ELKDAO {

	public static String SERVER = AppConfig.getInstance().getELKServer();

	public static String DB = "agdb";
	
	protected Gson json = new Gson();

	/**
	 * Retorna a versao do ELK
	 * 
	 * @return
	 */
	public String getVersion() {
		return httpGet(null);
	}

	public String deleteAll() {
		return http("?pretty=true", "DELETE", null);
	}
	
	public String get(String table) {
		return httpGet(table);
	}

	public String insert(String table, String content) {
		long timeStamp = GregorianCalendar.getInstance().getTimeInMillis();
		return http(table + "/" + timeStamp, "PUT", content);
	}

	public String httpGet(String _url) {
		return http(_url, "GET", null);
	}

	public String http(String table, String method, String content) {
		try {
			URL url;
			if (table != null) {
				url = new URL(SERVER + "/" + DB + "/" + table);
			} else {
				url = new URL(SERVER + "/" + table);
			}
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod(method);
			connection.setRequestProperty("Content-Type", "application/json");

			if (content != null) {
				DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
				wr.writeBytes(content);
				wr.flush();
				wr.close();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
			String responseBody = "";
			String output;
			while ((output = br.readLine()) != null) {
				responseBody += output;
			}
			//System.out.println(responseBody);
			return responseBody;
		} catch (Exception e) {
			throw new AppExceptions("Erro no conexao ao servidor", e);
		}
	}

}
