package twd.conseil.util;

import java.util.List;

import twd.conseil.Survivor;

public class StringUtils {


	public static String toStringSurvivants(List<Survivor> survivors) {
		String str = "";
		for (Survivor s : survivors) {
			str += s.getName() + ", ";
		}
		return str.substring(0, str.length() - 2);
	}


}