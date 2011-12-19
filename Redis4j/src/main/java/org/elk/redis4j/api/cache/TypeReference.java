package org.elk.redis4j.api.cache;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public abstract class TypeReference<T>
{
	final Type type;

	protected TypeReference()
	{
		Type superClass = getClass().getGenericSuperclass();
		if (superClass instanceof Class<?>)
		{
			throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
		}

		type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
	}

	public Type getType()
	{
		return type;
	}
}
