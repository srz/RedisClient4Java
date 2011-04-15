package com.handinfo.redis4j.impl.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ParameterConvert
{
	public static String[] mapToStringArray(Map<String, String> map)
	{
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
		String[] result = new String[map.size()*2];
		int i=0;
		while (iterator.hasNext())
		{
			Map.Entry<String, String> entry = iterator.next();
			result[i] = entry.getKey();
			result[i+1] = entry.getValue();
			i+=2;
		}
		return result;
	}
	
	public static Map<String, String> stringArrayToMap(String[] result)
	{
		Map<String, String> map = new HashMap<String, String>();
		
		for(int i=0; i<result.length; i+=2)
		{
			map.put(result[i], result[i+1]);
		}
		return map;
	}
}
