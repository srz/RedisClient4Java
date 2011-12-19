package org.elk.redis4j.impl.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.smile.SmileFactory;
import org.codehaus.jackson.type.JavaType;

public class ObjectWrapper<T>
{
	private T original;

	private static volatile ObjectMapper objectMapper = null;

	private static ObjectMapper getMapper()
	{
		if (objectMapper == null)
		{
			synchronized (ObjectMapper.class)
			{
				if (objectMapper == null)
				{
					objectMapper = new ObjectMapper(new SmileFactory());
				}
			}
		}
		return objectMapper;
	}

	public ObjectWrapper(byte[] objectByteArray, Class<T> clazz)
	{
		if (objectByteArray == null)
		{
			original = null;
			return;
		}

		try
		{
			original = getMapper().readValue(Compress.unGZip(objectByteArray), clazz);
		} catch (JsonParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * @param original
	 */
	public ObjectWrapper(T original)
	{
		this.original = original;
	}

	/**
	 * @return the original
	 */
	public T getOriginal()
	{
		return original;
	}

	public byte[] getByteArray()
	{
		try
		{
			return Compress.gZip(getMapper().writeValueAsBytes(original));
		} catch (JsonGenerationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
