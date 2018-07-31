package mastermind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
	
	public static final String URL_START = "http://172.16.37.129/api/start";
	public static final String URL_TEST = "http://172.16.37.129/api/test";
	public static final String TOKEN = "test2";
	public static final String NOMBRE = "12345";

	public static void main(String[] args) {
		try {
			Map<String, Object> mapResult = new Main().sendWithMsgBody(URL_START, "POST", "{\"token\" : \"" + TOKEN + "\"}");
			System.out.println(mapResult.toString());
			mapResult = new Main().sendWithMsgBody(URL_TEST, "POST", "{\"token\" : \"" + TOKEN + "\",	\"result\" : \"" + NOMBRE + "\"}");
			System.out.println(mapResult.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * @param url
	 * @param methode
	 * @param msgCorps
	 * @return
	 * @throws IOException
	 */
	public Map<String, Object> sendWithMsgBody(String url, String methode, String msgCorps) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod(methode);
		if (!"GET".equalsIgnoreCase(methode)) {
			con.setDoOutput(true);
		}
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");

		OutputStreamWriter out = null;
		if (!"GET".equalsIgnoreCase(methode)) {
			out = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			out.write(msgCorps);
			out.flush();
			out.close();
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		Map<String, Object> linkedHashMapFromString = getLinkedHashMapFromString(response.toString());

		in.close();
		con.disconnect();
		return linkedHashMapFromString;
	}
	
	/**
     * 
     * @param value
     * @return
     * @throws JsonParseException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getLinkedHashMapFromString(String value) throws JsonParseException, IOException {
        // objet qui sert à convertir en java object
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(value, LinkedHashMap.class);
    }

}
