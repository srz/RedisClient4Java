package com.handinfo.redis4j.impl.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
	
	public static <T> List<Object> mapToObjectArray(Map<String, T> map)
	{
		Iterator<Entry<String, T>> iterator = map.entrySet().iterator();
		List<Object> list = new ArrayList<Object>(map.size()*2);
		while (iterator.hasNext())
		{
			Map.Entry<String, T> entry = iterator.next();
			list.add(entry.getKey());
			ObjectWrapper<T> obj = new ObjectWrapper<T>(entry.getValue());
			list.add(obj);
		}
		return list;
	}
	
	public static Map<String, String> stringArrayToMap(List<String> result)
	{
		Map<String, String> map = new HashMap<String, String>();
		
		for(int i=0; i<result.size(); i+=2)
		{
			map.put(result.get(i), result.get(i+1));
		}
		return map;
	}
	
	public static <T> Map<String, T> objectArrayToMap(byte[][] result)
	{
		Map<String, T> map = new HashMap<String, T>();
		
		for(int i=0; i<result.length; i+=2)
		{
			ObjectWrapper<T> obj = new ObjectWrapper<T>(result[i+1]);
			map.put(new String(result[i]), obj.getOriginal());
		}
		return map;
	}
	
	public static List<String> byteArrayToStringList(byte[][] result)
	{
		List<String> list = new ArrayList<String>(result.length);
		for(int i=0; i<result.length; i++)
		{
			list.add(new String(result[i], Charset.forName("UTF-8")));
		}
		return list;
	}
	
	public static <T> List<T> byteArrayToObjectList(byte[][] result)
	{
		List<T> list = new ArrayList<T>(result.length);
		for(int i=0; i<result.length; i++)
		{
			ObjectWrapper<T> obj = new ObjectWrapper<T>(result[i]);
			list.add(obj.getOriginal());
		}
		return list;
	}
}
