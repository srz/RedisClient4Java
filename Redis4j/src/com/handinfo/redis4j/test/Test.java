package com.handinfo.redis4j.test;

import java.io.IOException;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.impl.Redis4jClient;

public class Test
{

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		Redis4jClient client = new Redis4jClient("192.2.8.164", 6379);
		if (client.connect())
		{
			if (!client.ping())
			{
				client.quit();
				return;
			}
			// System.out.println("服务器返回=" + client.echo("test srz"));

			User user = new User();
			user.setId(123);
			user.setName("srz");
			user.setTimestamp(12345678901L);

			client.set("testKey", 56789);

			// User u1 = new User();
			// Integer i = new Integer(0);
			//StringBuilder s = new StringBuilder();
			// String s= new String();
			// Boolean b = new Boolean(false);
			Integer s = (Integer)client.get("testKey");
			System.out.println("服务器返回=" + s);

			client.quit();
		}

		// User user = new User();
		// user.setId(123);
		// user.setName("srz");
		// user.setTimestamp(System.currentTimeMillis());
		// System.out.println("服务器返回=" + user.getTimestamp());
		//		
		// String abc = "xxxxxx";
		// Schema<String> schema =
		// RuntimeSchema.getSchema(String.class);
		// LinkedBuffer buffer = LinkedBuffer.allocate(256);
		//				
		// // protostuff serialize
		// byte[] protostuff = ProtostuffIOUtil.toByteArray(abc,
		// schema, buffer);
		// buffer.clear();
		// //
		// // // protostuff deserialize
		// //User f = new User();
		// String v =new String();
		// ProtostuffIOUtil.mergeFrom(protostuff, v, schema);
		// System.out.println("服务器返回=" +v);

		// String test = "1233";

		// long start = System.currentTimeMillis();
		//		
		// for(int i=0; i<10000; i++)
		// {
		// test.getBytes();
		// }
		// System.out.println("java序列化时间=" +(System.currentTimeMillis()
		// - start));
		//		
		//		
		// Schema<String> schema =
		// RuntimeSchema.getSchema(String.class);
		// LinkedBuffer buffer = LinkedBuffer.allocate(256);
		//		
		// start = System.currentTimeMillis();
		//		
		// for(int i=0; i<10000; i++)
		// {
		// // protostuff serialize
		// byte[] protostuff = ProtostuffIOUtil.toByteArray(test,
		// schema, buffer);
		// buffer.clear();
		// }
		// System.out.println("protostuff序列化时间="
		// +(System.currentTimeMillis() - start));

		// protostuff deserialize
		// String v = new String();;
		// ProtostuffIOUtil.mergeFrom(protostuff, v, schema);
		// System.out.println("服务器返回=" +v);

		// User user = new User();
		// user.setId(1263);
		// user.setName("srz");
		// user.setTimestamp(System.currentTimeMillis());
		//
		//		
		//		
		// long start = System.currentTimeMillis();
		//
		// for (int i = 0; i < 1; i++)
		// {
		// // ByteArrayOutputStream bbb = new ByteArrayOutputStream();
		// // ObjectOutputStream out = new ObjectOutputStream(bbb);
		// //
		// // out.writeObject(test); // 将这个对象写入流
		// // out.close(); // 清空并关闭流
		// byte[] t = test.getBytes();
		// System.out.println("---" + t.length);
		// }
		// System.out.println("java序列化时间=" + (System.currentTimeMillis() -
		// start));
		//
		//		
		//
		//		
		//
		// Schema<String> schema = RuntimeSchema.getSchema(String.class);
		//		
		//		
		// start = System.currentTimeMillis();
		// LinkedBuffer buffer = LinkedBuffer.allocate(256);
		// for (int i = 0; i < 1; i++)
		// {
		//			
		//			
		// // protostuff serialize
		// byte[] protostuff = ProtostuffIOUtil.toByteArray(test, schema,
		// buffer);
		// buffer.clear();
		// System.out.println("+++" + protostuff.length);
		// }
		// System.out.println("protostuff序列化时间=" + (System.currentTimeMillis() -
		// start));
	}

}
