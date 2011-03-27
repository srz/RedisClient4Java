package com.handinfo.redis4j.impl.classification;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponseMessage;
import com.handinfo.redis4j.api.classification.IServer;
import com.handinfo.redis4j.impl.CommandExecutor;

public class Server extends CommandExecutor implements IServer
{	
	public Server(IConnector connector)
	{
		super(connector);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#bgrewriteaof()
	 */
	public String bgrewriteaof()
	{
		return singleLineReplyForString(RedisCommand.BGREWRITEAOF);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#bgsave()
	 */
	public String bgsave()
	{
		return singleLineReplyForString(RedisCommand.BGSAVE);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#config_get(java.lang.String)
	 */
	public String[] config_get(String parameter)
	{
		return (String[]) multiBulkReply(RedisCommand.CONFIG_GET, false, parameter);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#config_resetstat()
	 */
	public boolean config_resetstat()
	{
		return singleLineReplyForBoolean(RedisCommand.CONFIG_RESETSTAT, RedisResponseMessage.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#config_set(java.lang.String, java.lang.String)
	 */
	public boolean config_set(String parameter, String value)
	{
		return singleLineReplyForBoolean(RedisCommand.CONFIG_SET, RedisResponseMessage.OK, parameter, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#dbsize()
	 */
	public int dbsize()
	{
		return integerReply(RedisCommand.DBSIZE);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#debug_object(java.lang.String)
	 */
	public String[] debug_object(String key)
	{
		return (String[]) multiBulkReply(RedisCommand.DEBUG_OBJECT, false, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#debug_segfault()
	 */
	public String[] debug_segfault()
	{
		return (String[]) multiBulkReply(RedisCommand.DEBUG_SEGFAULT, false);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#flushall()
	 */
	public boolean flushall()
	{
		return singleLineReplyForBoolean(RedisCommand.FLUSHALL, RedisResponseMessage.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#flushdb()
	 */
	public boolean flushdb()
	{
		return singleLineReplyForBoolean(RedisCommand.FLUSHDB, RedisResponseMessage.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#info()
	 */
	public String info()
	{
		return (String)bulkReply(RedisCommand.INFO, false);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#lastsave()
	 */
	public int lastsave()
	{
		return integerReply(RedisCommand.LASTSAVE);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#save()
	 */
	public boolean save()
	{
		return singleLineReplyForBoolean(RedisCommand.SAVE, RedisResponseMessage.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#shutdown()
	 */
	public boolean shutdown()
	{
		return !singleLineReplyForBoolean(RedisCommand.SHUTDOWN, RedisResponseMessage.SHUTDOWNERROR);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#slaveof()
	 */
	public boolean slaveof()
	{
		return singleLineReplyForBoolean(RedisCommand.SLAVEOF, RedisResponseMessage.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#sync()
	 */
	public String[] sync()
	{
		return (String[]) multiBulkReply(RedisCommand.SYNC, false);
	}
}
