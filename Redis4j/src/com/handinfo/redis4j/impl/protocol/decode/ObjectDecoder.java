package com.handinfo.redis4j.impl.protocol.decode;

import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.handinfo.redis4j.api.DataWrapper;

public class ObjectDecoder
{
	@SuppressWarnings("unchecked")
	public static Object getObject(byte[] objectByteArray)
	{
		Schema<DataWrapper> schema = RuntimeSchema.getSchema(DataWrapper.class);
		DataWrapper data = new DataWrapper();
		ProtostuffIOUtil.mergeFrom(objectByteArray, data, schema);
		return data.getOriginal();
	}
}
