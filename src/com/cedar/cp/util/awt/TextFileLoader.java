package com.cedar.cp.util.awt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class TextFileLoader {
	public static String loadAsSingleString(String path) {
		File f = new File(path);
		return loadAsSingleString(f);
	}

	public static String loadAsSingleString(InputStream stream) {
		StringBuilder sb = new StringBuilder();
		Scanner scanner = new Scanner(stream);
		while (scanner.hasNext()) {
			sb.append(scanner.nextLine());
		}
		scanner.close();
		return sb.toString();
	}

	public static String loadAsSingleString(File textFile) {
		StringBuilder sb = new StringBuilder();
		Scanner scanner = null;
		try {
			scanner = new Scanner(textFile);
			while (scanner.hasNext())
				sb.append(scanner.nextLine());
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} finally {
			scanner.close();
		}

		return sb.toString();
	}
}