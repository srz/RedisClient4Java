package com.handinfo.redis4j.api.exception;

public class Test
{

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		int a = 1;
		if(a == 1)
		throw new NullBatchException("错误");
		
		System.out.println("sss");
		//throw new Exception("ee");
//		try
//		{
//			throw new Exception("ee");
//		}
//		catch(Exception ex)
//		{
//			
//		}
	}

}
