package htools.utils;

import java.util.HashMap;
import java.util.Map;

public class TLVTranslator {

	public static Map<String, String> translate(String string) {
		Map<String, String> subfieldsContentMap = new HashMap<>();
		String tag = "";
		Integer length = 0;
		String value = "";

		int i = 0;
		while (i < string.length()) {
			tag = "";
			length = 0;
			value = "";

			tag = string.substring(i, i + 3);
			i = i + 3;

			length = Integer.valueOf(string.substring(i, i + 3));
			i = i + 3;

			if (length > 0) {
				value = string.substring(i, i + length);
				i = i + length;
			}

			subfieldsContentMap.put(tag, value);
		}
		return subfieldsContentMap;
	}

}
