package com.handinfo.redis4j.impl;

import com.handinfo.redis4j.api.IServer;
import com.handinfo.redis4j.api.RedisCommandType;
import com.handinfo.redis4j.api.RedisResultInfo;
import com.handinfo.redis4j.impl.transfers.Connector;

public class Server extends BaseCommand implements IServer
{	
	public Server(Connector connector)
	{
		super(connector);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#bgrewriteaof()
	 */
	public String bgrewriteaof() throws Exception
	{
		return singleLineReplyForString(RedisCommandType.BGREWRITEAOF);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#bgsave()
	 */
	public String bgsave() throws Exception
	{
		return singleLineReplyForString(RedisCommandType.BGSAVE);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#config_get(java.lang.String)
	 */
	public String[] config_get(String parameter) throws Exception
	{
		return (String[]) multiBulkReply(RedisCommandType.CONFIG, false, RedisCommandType.CONFIG_GET, parameter);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#config_resetstat()
	 */
	public boolean config_resetstat() throws Exception
	{
		return singleLineReplyForBoolean(RedisCommandType.CONFIG, RedisResultInfo.OK, RedisCommandType.CONFIG_RESETSTAT);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#config_set(java.lang.String, java.lang.String)
	 */
	public boolean config_set(String parameter, String value) throws Exception
	{
		return singleLineReplyForBoolean(RedisCommandType.CONFIG, RedisResultInfo.OK, RedisCommandType.CONFIG_SET, parameter, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#dbsize()
	 */
	public int dbsize() throws Exception
	{
		return integerReply(RedisCommandType.DBSIZE);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#debug_object(java.lang.String)
	 */
	public String[] debug_object(String key) throws Exception
	{
		return (String[]) multiBulkReply(RedisCommandType.DEBUG, false, RedisCommandType.DEBUG_OBJECT, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#debug_segfault()
	 */
	public String[] debug_segfault() throws Exception
	{
		return (String[]) multiBulkReply(RedisCommandType.DEBUG, false, RedisCommandType.DEBUG_SEGFAULT);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#flushall()
	 */
	public boolean flushall() throws Exception
	{
		return singleLineReplyForBoolean(RedisCommandType.FLUSHALL, RedisResultInfo.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#flushdb()
	 */
	public boolean flushdb() throws Exception
	{
		return singleLineReplyForBoolean(RedisCommandType.FLUSHDB, RedisResultInfo.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#info()
	 */
	public String info() throws Exception
	{
		return (String)bulkReply(RedisCommandType.INFO, false);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#lastsave()
	 */
	public int lastsave() throws Exception
	{
		return integerReply(RedisCommandType.LASTSAVE);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#monitor()
	 */
	public void monitor() throws Exception
	{
		asyncBulkReply(RedisCommandType.MONITOR, false);
	}
	
	public String getMonitorResult() throws Exception
	{
		return asyncGetBulkReplyResult();
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#save()
	 */
	public boolean save() throws Exception
	{
		return singleLineReplyForBoolean(RedisCommandType.SAVE, RedisResultInfo.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#shutdown()
	 */
	public boolean shutdown() throws Exception
	{
		return !singleLineReplyForBoolean(RedisCommandType.SHUTDOWN, RedisResultInfo.SHUTDOWNERROR);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#slaveof()
	 */
	public boolean slaveof() throws Exception
	{
		return singleLineReplyForBoolean(RedisCommandType.SLAVEOF, RedisResultInfo.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IServer#sync()
	 */
	public String[] sync() throws Exception
	{
		return (String[]) multiBulkReply(RedisCommandType.SYNC, false);
	}
}
