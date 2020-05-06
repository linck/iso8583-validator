package htools.utils;

import htools.validator.FieldValidatorTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TLVTranslator {

	public static Map<String, String> translate(String string, List<FieldValidatorTO> subfieldsValidators) {
		Map<String, String> subfieldsContentMap = new HashMap<>();
		String tag;
		int length;
		String value = "";

		int i = 0;
		while (i < string.length()) {

			tag = string.substring(i, i + 3);
			i = i + 3;

			String filterTag = tag;
			FieldValidatorTO subfield = subfieldsValidators.stream().filter(sf -> sf.getId().equals(filterTag)).findFirst().orElse(null);

			length = Integer.parseInt(string.substring(i, i + 3));
			i = i + 3;

			if (subfield != null && subfield.getContentType() != null && subfield.getContentType().equals("binary")) {
				length *= 2;
			}

			if (length > 0) {
				value = string.substring(i, i + length);
				i = i + length;
			}

			subfieldsContentMap.put(tag, value);
		}
		return subfieldsContentMap;
	}

}
