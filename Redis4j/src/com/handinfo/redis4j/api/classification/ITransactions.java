package com.handinfo.redis4j.api.classification;

public interface ITransactions
{

	public boolean discard();

	public String[] exec();

	public boolean multi();

	public boolean unwatch();

	public boolean watch();

}