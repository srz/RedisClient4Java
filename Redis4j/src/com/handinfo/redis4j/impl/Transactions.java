package com.handinfo.redis4j.impl;

import com.handinfo.redis4j.api.ITransactions;
import com.handinfo.redis4j.api.RedisCommandType;
import com.handinfo.redis4j.api.RedisResultInfo;
import com.handinfo.redis4j.impl.transfers.Connector;

public class Transactions extends BaseCommand implements ITransactions
{

	public Transactions(Connector connector)
	{
		super(connector);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ITransactions#discard()
	 */
	public boolean discard() throws Exception
	{
		return singleLineReplyForBoolean(RedisCommandType.DISCARD, RedisResultInfo.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ITransactions#exec()
	 */
	public String[] exec() throws Exception
	{
		return (String[]) multiBulkReply(RedisCommandType.EXEC, false);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ITransactions#multi()
	 */
	public boolean multi() throws Exception
	{
		return singleLineReplyForBoolean(RedisCommandType.MULTI, RedisResultInfo.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ITransactions#unwatch()
	 */
	public boolean unwatch() throws Exception
	{
		return singleLineReplyForBoolean(RedisCommandType.UNWATCH, RedisResultInfo.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ITransactions#watch()
	 */
	public boolean watch() throws Exception
	{
		return singleLineReplyForBoolean(RedisCommandType.WATCH, RedisResultInfo.OK);
	}
}
