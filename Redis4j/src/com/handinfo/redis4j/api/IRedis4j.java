package com.handinfo.redis4j.api;

import java.util.ArrayList;

import com.handinfo.redis4j.api.classification.IConnection;
import com.handinfo.redis4j.api.classification.IHashes;
import com.handinfo.redis4j.api.classification.IKeys;
import com.handinfo.redis4j.api.classification.ILists;
import com.handinfo.redis4j.api.classification.IServer;
import com.handinfo.redis4j.api.classification.ISets;
import com.handinfo.redis4j.api.classification.ISortedSets;
import com.handinfo.redis4j.api.classification.IStrings;
import com.handinfo.redis4j.api.classification.ITransactions;


public interface IRedis4j
{
	public boolean getIsConnected();
	
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
	
	public IRedis4jAsync getAsyncClient();
	
	public ArrayList<String> batch(Batch batchCommand);

}