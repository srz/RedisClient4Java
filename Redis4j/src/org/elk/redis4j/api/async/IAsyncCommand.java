package org.elk.redis4j.api.async;

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
