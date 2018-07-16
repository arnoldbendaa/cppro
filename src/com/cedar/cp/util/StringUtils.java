package com.cedar.cp.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringUtils {
	public static final String HEX = "0123456789abcdef";
	private static final String[] sTrueValues = { "1", "y", "true", "t" };
	private static final String[] sFalseValues = { "0", "n", "false", "f" };

	public static String fixString(String originalString, int requiredLength, char pad, char justify) {
		StringBuilder returnString = new StringBuilder(originalString);
		int padSize = requiredLength - originalString.length();
		if (justify == 'L') {
			for (int i = 0; i < padSize; i++)
				returnString.append(pad);
		} else if (justify == 'R') {
			for (int i = 0; i < padSize; i++)
				returnString.insert(0, pad);
		} else {
			for (int i = 0; i < padSize / 2; i++)
				returnString.insert(0, pad);
			padSize = requiredLength - returnString.length();
			for (int i = 0; i < padSize; i++)
				returnString.append(pad);
		}
		return returnString.toString();
	}

	public static String fixLeft(String originalString, int requiredLength) {
		return fixString(originalString, requiredLength, ' ', 'L');
	}

	public static String fixRight(String originalString, int requiredLength) {
		return fixString(originalString, requiredLength, ' ', 'R');
	}

	public static String fixLeft(long originalNumber, int requiredLength) {
		return fixString(new StringBuilder().append("").append(originalNumber).toString(), requiredLength, ' ', 'L');
	}

	public static String fixLeft(int originalNumber, int requiredLength) {
		return fixString(new StringBuilder().append("").append(originalNumber).toString(), requiredLength, ' ', 'L');
	}

	public static String fixRight(long originalNumber, int requiredLength) {
		return fixString(new StringBuilder().append("").append(originalNumber).toString(), requiredLength, ' ', 'R');
	}

	public static String fixRight(int originalNumber, int requiredLength) {
		return fixString(new StringBuilder().append("").append(originalNumber).toString(), requiredLength, ' ', 'R');
	}

	public static String fixRightZero(long originalNumber, int requiredLength) {
		return fixString(new StringBuilder().append("").append(originalNumber).toString(), requiredLength, '0', 'R');
	}

	public static String fixRightZero(int originalNumber, int requiredLength) {
		return fixString(new StringBuilder().append("").append(originalNumber).toString(), requiredLength, '0', 'R');
	}

	public static String stringBefore(String originalString, String beforeString) {
		int ptr = originalString.indexOf(beforeString);
		if (ptr == -1) {
			return "";
		}
		return originalString.substring(0, ptr);
	}

	public static String stringAfter(String originalString, String afterString) {
		int ptr = originalString.indexOf(afterString);
		if (ptr == -1) {
			return "";
		}
		return originalString.substring(ptr + afterString.length());
	}

	public static String stringBetween(String originalString, String after, String before) {
		int ptr = originalString.indexOf(after);
		if (ptr == -1) {
			return "";
		}
		String s = originalString.substring(ptr + after.length());

		ptr = s.indexOf(before);
		if (ptr == -1) {
			return "";
		}
		return s.substring(0, ptr);
	}

	public static String replaceAll(String originalString, String replace, String with) {
		String s = originalString;
		int ptr = -1;
		while (true) {
			int rl = replace.length();
			ptr = s.indexOf(replace, ptr);
			if (ptr == -1)
				return s;
			StringBuilder sb = new StringBuilder(s);
			sb = sb.replace(ptr, ptr + rl, with);
			s = sb.toString();
			ptr += with.length();
		}
	}

	public static String arrayToString(String[] array, String separator) {
		return arrayToString(array, separator, "");
	}

	public static String arrayToString(String[] array, String separator, String surround) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			sb.append(new StringBuilder().append(surround).append(array[i]).append(surround).toString());
			if (i < array.length - 1)
				sb.append(separator);
		}
		return sb.toString();
	}

	public static String arrayToString(List array, String separator) {
		return arrayToString(array, separator, "");
	}

	public static String arrayToString(List array, String separator, String surround) {
		StringBuilder sb = new StringBuilder();
		int len = array.size();
		for (int i = 0; i < len; i++) {
			String s = array.get(i).toString();
			sb.append(new StringBuilder().append(surround).append(s).append(surround).toString());
			if (i < len - 1)
				sb.append(separator);
		}
		return sb.toString();
	}

	public static String fill(String s, int copies) {
		if (copies < 1) {
			return "";
		}
		StringBuilder sb = new StringBuilder(s.length() * copies);
		for (int i = 0; i < copies; i++) {
			sb.append(s);
		}
		return sb.toString();
	}

	public static String fill(char c, int length) {
		if (length < 1) {
			return "";
		}
		StringBuilder sb = new StringBuilder(length);
		while (sb.length() < length) {
			sb.append(c);
		}
		return sb.substring(0, length);
	}

	public static String fill(int length) {
		return fill(' ', length);
	}

	public static String boxText(String[] source) {
		int maxLength = 0;
		for (int i = 0; i < source.length; i++) {
			if (source[i] != null) {
				if (source[i].length() > maxLength)
					maxLength = source[i].length();
			}
		}
		if (maxLength == 0) {
			return "";
		}
		String nl = "\n";

		StringBuilder sb = new StringBuilder();

		sb.append(new StringBuilder().append(fill("-", maxLength)).append(nl).toString());
		for (int i = 0; i < source.length; i++) {
			if (source[i] != null) {
				sb.append(new StringBuilder().append(source[i]).append(nl).toString());
			}
		}
		sb.append(fill("-", maxLength));

		return sb.toString();
	}

	public static String toHex(String s) throws Exception {
		StringBuilder sb = new StringBuilder(s.length() * 2);
		for (int i = 0; i < s.length(); i++) {
			int c = s.charAt(i);
			if (c < 0)
				c = 256 + c;
			int left = c / 16;
			int right = c - left * 16;
			while (left > 16)
				left -= 16;
			sb.append("0123456789abcdef".charAt(left));
			sb.append("0123456789abcdef".charAt(right));
		}
		return sb.toString();
	}

	public static String toHex(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int c = b[i];
			if (c < 0)
				c = 256 + c;
			int left = c / 16;
			int right = c - left * 16;
			while (left > 16)
				left -= 16;
			sb.append("0123456789abcdef".charAt(left));
			sb.append("0123456789abcdef".charAt(right));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(new StringBuilder().append("'Wibble  ' gives [").append(rtrim("Wibble  ")).append("]").toString());
		System.out.println(new StringBuilder().append("'Wobble' gives [").append(rtrim("Wobble")).append("]").toString());
		System.out.println(new StringBuilder().append("'Wobble   i' gives [").append(rtrim("Wobble   i")).append("]").toString());
	}

	public static String rtrim(String str) {
		if (str == null)
			return null;
		int len = str.length();
		int rightMostWhiteSpacesChars = 0;
		for (int i = len - 1; i >= 0; i--) {
			if (!Character.isWhitespace(str.charAt(i)))
				break;
			rightMostWhiteSpacesChars++;
		}
		return rightMostWhiteSpacesChars == 0 ? str : str.substring(0, len - rightMostWhiteSpacesChars);
	}

	public static String left(String text, int nChars) {
		if (text == null)
			return null;
		int maxIndex = Math.min(nChars, text.length());
		return text.substring(0, maxIndex);
	}

	public static String checkMaxSize(String s, int maxSize) {
		if (s == null) {
			return "";
		}
		int l = s.length();

		if (l > maxSize) {
			s = s.substring(0, maxSize);
		}
		return s;
	}

	public static boolean differ(String s1, String s2) {
		return ((s1 != null) && (s2 == null)) || ((s1 == null) && (s2 != null)) || ((s1 != null) && (s2 != null) && (!s1.equals(s2)));
	}

	public static Boolean parseBoolean(String s) {
		if ((s == null) || (s.trim().length() == 0)) {
			return Boolean.valueOf(false);
		}
		for (String tv : sTrueValues) {
			if (s.equalsIgnoreCase(tv))
				return Boolean.valueOf(true);
		}
		for (String fv : sFalseValues) {
			if (s.equalsIgnoreCase(fv))
				return Boolean.valueOf(false);
		}
		return null;
	}

	public static String[] mungeArraysNoDups(String[][] arrays) {
		Set newList = new HashSet();
		for (String[] array : arrays) {
			for (String s : array) {
				newList.add(s);
			}
		}

		String[] returnArray = new String[newList.size()];
		int i = 0;
		for (Object s : newList.toArray()) {
			returnArray[i] = (String) s;
			i++;
		}

		return returnArray;
	}
}