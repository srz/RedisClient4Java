package com.handinfo.redis4j.impl.protocol;

import java.io.UnsupportedEncodingException;

public class Ping
{
	public static byte[] getProtocol() throws UnsupportedEncodingException
	{
		return "*1\r\n$4\r\nping\r\n".getBytes("UTF-8");
	}
}
