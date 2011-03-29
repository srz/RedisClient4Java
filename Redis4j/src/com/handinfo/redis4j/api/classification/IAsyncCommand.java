package com.handinfo.redis4j.api.classification;

/**
 * 异步命令
 */
public interface IAsyncCommand
{
	public interface Notify
	{
		public void onNotify(String result);
	}
	
	public void monitor(Notify notify);
}
