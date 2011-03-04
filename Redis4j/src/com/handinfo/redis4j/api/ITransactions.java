package com.handinfo.redis4j.api;

public interface ITransactions
{

	public boolean discard();

	public String[] exec();

	public boolean multi();

	public boolean unwatch();

	public boolean watch();

}