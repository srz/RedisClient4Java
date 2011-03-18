package com.handinfo.redis4j.impl;

import com.handinfo.redis4j.api.IConnection;
import com.handinfo.redis4j.api.RedisCommandType;
import com.handinfo.redis4j.api.RedisResultInfo;
import com.handinfo.redis4j.impl.transfers.Connector;

public class Connection extends BaseCommand implements IConnection
{
	private Connector connector;
	
	public Connection(Connector connector)
	{
		super(connector);
		this.connector = connector;
	}

	private boolean isHaveConnectToServer()
	{
		return connector == null ? false: true;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IConnection#auth(java.lang.String)
	 */
	public boolean auth(String password)
	{
		return singleLineReplyForBoolean(RedisCommandType.AUTH, RedisResultInfo.OK, password);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IConnection#echo(java.lang.String)
	 */
	public String echo(String message)
	{
		Object result = bulkReply(RedisCommandType.ECHO, false, message);
		if (result != null)
		{
			return (String) result;
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IConnection#ping()
	 */
	public boolean ping()
	{
		return singleLineReplyForBoolean(RedisCommandType.PING, RedisResultInfo.PONG);
	}

	//
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IConnection#quit()
	 */
	public boolean quit()
	{
		connector.disConnect();
		return true;
	}

	/**
	 * 由于使用了连接池,如果公开此函数,并发情况下无法保证连接池中的所有连接都会修改默认操作的数据库
	 * 后续在考虑是否添加此函数
	 * @param dbIndex
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean select(int dbIndex)
	{
		return singleLineReplyForBoolean(RedisCommandType.SELECT, RedisResultInfo.OK, dbIndex);
	}
}
