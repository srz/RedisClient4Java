package com.handinfo.redis4j.api;

public interface ITransactions
{

	public boolean discard() throws Exception;

	public String[] exec() throws Exception;

	public boolean multi() throws Exception;

	public boolean unwatch() throws Exception;

	public boolean watch() throws Exception;

}