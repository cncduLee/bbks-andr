package com.funger.ebook.utils;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Listƴ���Ƚ���
 * 
 * @author
 * 
 */
public class PinyinListComparator implements
		Comparator<HashMap<String, Object>> {

	public int compare(HashMap<String, Object> m1, HashMap<String, Object> m2) {
		String com1 = PinyinUtil.getPingYin((String) m1.get("com"));
		String com2 = PinyinUtil.getPingYin((String) m2.get("com"));
		return com1.compareTo(com2);
	}
}
