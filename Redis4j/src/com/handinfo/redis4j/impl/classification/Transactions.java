package com.handinfo.redis4j.impl.classification;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponseMessage;
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
		return singleLineReplyForBoolean(RedisCommand.DISCARD, RedisResponseMessage.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ITransactions#exec()
	 */
	public String[] exec()
	{
		return (String[]) multiBulkReply(RedisCommand.EXEC, false);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ITransactions#multi()
	 */
	public boolean multi()
	{
		return singleLineReplyForBoolean(RedisCommand.MULTI, RedisResponseMessage.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ITransactions#unwatch()
	 */
	public boolean unwatch()
	{
		return singleLineReplyForBoolean(RedisCommand.UNWATCH, RedisResponseMessage.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ITransactions#watch()
	 */
	public boolean watch()
	{
		return singleLineReplyForBoolean(RedisCommand.WATCH, RedisResponseMessage.OK);
	}
}
