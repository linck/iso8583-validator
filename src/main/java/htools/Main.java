package htools;

import java.io.IOException;

import htools.analyzer.XmlIsoAnalyzer;

/**
 * 
 * @author humberto.machado
 *
 */
public class Main {

	private static final String DEFAULT_VALIDATOR_PATH = "validator.json";

	public static void main(String[] args) throws IOException {
		String validatorName = getArgValue(args, 0);
		String validatorFilePath = getArgValue(args, 1, DEFAULT_VALIDATOR_PATH);

		if (validatorName == null) {
			System.out.println("Nome do validador nao pode ser null");
			return;
		}

		new XmlIsoAnalyzer().analyze("logiso.xml", "logiso_original_req.xml", "logiso_original_resp.xml", validatorName,
				validatorFilePath);
	}

	private static String getArgValue(String[] args, int index, String defaultValue) {
		if (args != null && args.length > index) {
			return args[index];
		}
		return defaultValue;
	}

	private static String getArgValue(String[] args, int index) {
		return getArgValue(args, index, null);
	}
}
