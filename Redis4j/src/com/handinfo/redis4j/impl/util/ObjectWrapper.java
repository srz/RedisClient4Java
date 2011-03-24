package com.handinfo.redis4j.impl.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class ObjectWrapper<T>
{
	private T original;
	
	@SuppressWarnings("unchecked")
	public ObjectWrapper(byte[] objectByteArray)
	{
		Schema<ObjectWrapper> schema = RuntimeSchema.getSchema(ObjectWrapper.class);
		ProtostuffIOUtil.mergeFrom(objectByteArray, this, schema);
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
	
	@SuppressWarnings("unchecked")
	public byte[] getByteArray()
	{
		byte[] bytes = null;
		Schema<ObjectWrapper> schema = RuntimeSchema.getSchema(ObjectWrapper.class);
		LinkedBuffer tmpBuffer = LinkedBuffer.allocate(256);

		bytes = ProtostuffIOUtil.toByteArray(this, schema, tmpBuffer);
		tmpBuffer.clear();
		return bytes;
	}
}
