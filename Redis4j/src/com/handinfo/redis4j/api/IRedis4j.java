package com.handinfo.redis4j.api;


public interface IRedis4j
{

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

}