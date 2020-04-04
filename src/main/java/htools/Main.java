package htools;

import java.io.IOException;

import htools.analyzer.XmlIsoAnalyzer;

public class Main {

	public static void main(String[] args) throws IOException {
		new XmlIsoAnalyzer().analyze("logiso.xml", args[0]);
	}

}
