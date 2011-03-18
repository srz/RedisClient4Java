package com.handinfo.redis4j.api;


public interface IRedis4j
{
	/**
	 * @return 判断网络连接是否建立成功
	 */
	public boolean isConnectSucess();
	
	/**
	 * @return the connection
	 */
	public IConnection getConnection();

	/**
	 * @return the hashes
	 */
	public IHashes getHashes();

	/**
	 * @return the keys
	 */
	public IKeys getKeys();

	/**
	 * @return the lists
	 */
	public ILists getLists();

	/**
	 * @return the server
	 */
	public IServer getServer();

	/**
	 * @return the sets
	 */
	public ISets getSets();

	/**
	 * @return the sortedSets
	 */
	public ISortedSets getSortedSets();

	/**
	 * @return the strings
	 */
	public IStrings getStrings();

	/**
	 * @return the transactions
	 */
	public ITransactions getTransactions();
	
	/**
	 * @return the new IRedis4j
	 */
	public IRedis4j clone();

}