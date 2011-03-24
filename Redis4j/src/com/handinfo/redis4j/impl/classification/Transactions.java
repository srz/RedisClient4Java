package com.handinfo.redis4j.impl.classification;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.RedisCommandType;
import com.handinfo.redis4j.api.RedisResultInfo;
import com.handinfo.redis4j.api.classification.ITransactions;
import com.handinfo.redis4j.impl.CommandExecutor;

public class Transactions extends CommandExecutor implements ITransactions
{

	public Transactions(IConnector connector)
	{
		super(connector);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ITransactions#discard()
	 */
	public boolean discard()
	{
		return singleLineReplyForBoolean(RedisCommandType.DISCARD, RedisResultInfo.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ITransactions#exec()
	 */
	public String[] exec()
	{
		return (String[]) multiBulkReply(RedisCommandType.EXEC, false);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ITransactions#multi()
	 */
	public boolean multi()
	{
		return singleLineReplyForBoolean(RedisCommandType.MULTI, RedisResultInfo.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ITransactions#unwatch()
	 */
	public boolean unwatch()
	{
		return singleLineReplyForBoolean(RedisCommandType.UNWATCH, RedisResultInfo.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ITransactions#watch()
	 */
	public boolean watch()
	{
		return singleLineReplyForBoolean(RedisCommandType.WATCH, RedisResultInfo.OK);
	}
}
