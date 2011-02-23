package com.handinfo.redis4j.test;

import com.handinfo.redis4j.impl.Redis4jClient;

public class TestKeys
{

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		Redis4jClient client = new Redis4jClient("127.0.0.1", 6379);
		if (client.connect())
		{
			//**********************************************************************************************************************************************************\\
			client.set("WillBeRemovedKey_1", "123");
			client.set("WillBeRemovedKey_2", "456");
			client.set("WillBeRemovedKey_3", "789");
			int numberOfDel = client.del("ErrorKey");
			System.out.println("Server back numberOfDel(del error key) = " + numberOfDel);

			numberOfDel = client.del("WillBeRemovedKey_2");
			System.out.println("Server back numberOfDel(del WillBeRemovedKey_2) = " + numberOfDel);

			numberOfDel = client.del("WillBeRemovedKey_1", "WillBeRemovedKey_2", "WillBeRemovedKey_3");
			System.out.println("Server back numberOfDel(del three key) = " + numberOfDel);
			//**********************************************************************************************************************************************************//

			
			//**********************************************************************************************************************************************************\\
			String[] keys = client.keys("*");

			for (int i = 0; i < keys.length; i++)
			{
				System.out.println("Server back keys = " + i + "、" + keys[i]);
			}
			//**********************************************************************************************************************************************************//
			
			
			
			//**********************************************************************************************************************************************************\\
			client.set("WillBeRenamedKey_1", "i am WillBeRenamedKey_1");
			System.out.println("Server back WillBeRenamedKey_1 = " + client.get("WillBeRenamedKey_1"));
			System.out.println("Server back WillBeRenamedKey_3 = " + client.get("WillBeRenamedKey_3"));
			client.rename("WillBeRenamedKey_1", "WillBeRenamedKey_3");
			System.out.println("rename WillBeRenamedKey_1 to WillBeRenamedKey_3");
			System.out.println("Server back WillBeRenamedKey_1 = " + client.get("WillBeRenamedKey_1"));
			System.out.println("Server back WillBeRenamedKey_3 = " + client.get("WillBeRenamedKey_3"));
			//**********************************************************************************************************************************************************//

			client.quit();
		}
	}

}
