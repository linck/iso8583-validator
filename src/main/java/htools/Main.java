package htools;

import java.io.IOException;

import htools.analyzer.XmlIsoAnalyzer;

/**
 * 
 * @author humberto.machado
 *
 */
public class Main {

	public static void main(String[] args) throws IOException {
		new XmlIsoAnalyzer().analyze("logiso.xml", "logiso_original.xml", args[0]);
	}

}
