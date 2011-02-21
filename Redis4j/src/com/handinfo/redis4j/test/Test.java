package com.handinfo.redis4j.test;

import java.io.IOException;

import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.impl.Redis4jClient;

public class Test
{

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		IRedis4j client = new Redis4jClient("192.2.8.164", 6379);
		if (client.connect())
		{
//			if (!client.ping())
//			{
//				client.quit();
//				return;
//			}
			// System.out.println("服务器返回=" + client.echo("test srz"));

			User user = new User();
			user.setId(123);
			user.setName("srz");
			user.setTimestamp(System.currentTimeMillis());
			
			//client.set("testKey", user);

			try
			{
				User u1 = (User) client.get("testKey", user.getClass());
				System.out.println("服务器返回=" + u1.getName());
			}
			catch (InstantiationException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			client.quit();
		}

		// User user = new User();
		// user.setId(123);
		// user.setName("srz");
		// user.setTimestamp(System.currentTimeMillis());
		// System.out.println("服务器返回=" + user.getTimestamp());
		//		
		// Schema<User> schema =
		// RuntimeSchema.getSchema(user.getClass().getName(), true);
		// LinkedBuffer buffer = LinkedBuffer.allocate(256);
		//		
		// // protostuff serialize
		// byte[] protostuff = ProtostuffIOUtil.toByteArray(user,
		// schema, buffer);
		// buffer.clear();
		// //
		// // // protostuff deserialize
		// User f = new User();
		// ProtostuffIOUtil.mergeFrom(protostuff, f, schema);
		// System.out.println("服务器返回=" +f.getTimestamp());

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
