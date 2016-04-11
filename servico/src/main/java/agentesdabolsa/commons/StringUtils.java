package agentesdabolsa.commons;

import java.text.Normalizer;

public class StringUtils {

	public static String normalize(String value) {
		if (value == null){
			return null;
		}
		return Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

}
