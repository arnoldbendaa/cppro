package com.cedar.cp.util;

import java.net.URLClassLoader;
import java.util.Arrays;

public class ClasspathDumper {
	public static void main(String... args) {
		dumpClasspath(ClasspathDumper.class.getClassLoader());
	}

	public static void dumpClasspath(ClassLoader loader) {
		System.out.println("Classloader " + loader + ":");

		if (loader instanceof URLClassLoader) {
			URLClassLoader ucl = (URLClassLoader) loader;
			System.out.println("\t" + Arrays.toString(ucl.getURLs()));
		} else
			System.out
					.println("\t(cannot display components as not a URLClassLoader)");

		if (loader.getParent() != null)
			dumpClasspath(loader.getParent());
	}
}
