package org.elk.redis4j.impl.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.NopAnnotationIntrospector;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.smile.SmileFactory;

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
					objectMapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
					objectMapper.configure(SerializationConfig.Feature.USE_ANNOTATIONS, false);
					//忽略注解,上面一行对smile无效
					objectMapper.setAnnotationIntrospector(new NopAnnotationIntrospector());
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
	
	public ObjectWrapper(byte[] objectByteArray, org.elk.redis4j.api.cache.TypeReference<T> clazz)
	{
		if (objectByteArray == null)
		{
			original = null;
			return;
		}

		try
		{
			original = getMapper().readValue(Compress.unGZip(objectByteArray), TypeFactory.defaultInstance().constructType(clazz.getType()));
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
