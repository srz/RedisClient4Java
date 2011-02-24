package com.handinfo.redis4j.api;

import java.io.Serializable;

public class DataWrapper<T> implements Serializable
{
	/**
	 * @param original
	 */
	public DataWrapper(T original)
	{
		this.original = original;
	}
	
	/**
	 */
	public DataWrapper()
	{
	}
	
	private T original;

	/**
	 * @return the original
	 */
	public T getOriginal()
	{
		return original;
	}
	/**
	 * @param original the original to set
	 */
	public void setOriginal(T original)
	{
		this.original = original;
	}
}
