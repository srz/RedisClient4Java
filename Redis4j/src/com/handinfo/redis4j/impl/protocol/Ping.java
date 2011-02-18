package com.handinfo.redis4j.impl.protocol;

import java.io.UnsupportedEncodingException;

public class Ping
{
	public static byte[] getProtocol() throws UnsupportedEncodingException
	{
		//return "*1\r\n$4\r\nping\r\n".getBytes("UTF-8");
		//return "*2\r\n$4\r\necho\r\n$9\r\ntest srz\r\n".getBytes("UTF-8");
		return "*2\r\n$4\r\nECHO\r\n$5\r\nmykey\r\n".getBytes();
		//return "*3\r\n$3\r\nSET\r\n$5\r\nmykey\r\n$7\r\nmyvalue\r\n".getBytes();
	}
}
