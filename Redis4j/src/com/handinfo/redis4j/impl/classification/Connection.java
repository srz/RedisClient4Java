package com.handinfo.redis4j.impl.classification;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponseMessage;
import com.handinfo.redis4j.api.classification.IConnection;
import com.handinfo.redis4j.impl.CommandExecutor;

public class Connection extends CommandExecutor implements IConnection
{
	private IConnector connector;
	
	public Connection(IConnector connector)
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
	public boolean auth(String password)//
	{
		return singleLineReplyForBoolean(RedisCommand.AUTH, RedisResponseMessage.OK, password);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IConnection#echo(java.lang.String)
	 */
	public String echo(String message)
	{
		Object result = bulkReply(RedisCommand.ECHO, false, message);
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
		return singleLineReplyForBoolean(RedisCommand.PING, RedisResponseMessage.PING);
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
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private boolean select(int dbIndex)
	{
		return singleLineReplyForBoolean(RedisCommand.SELECT, RedisResponseMessage.OK, dbIndex);
	}
}
