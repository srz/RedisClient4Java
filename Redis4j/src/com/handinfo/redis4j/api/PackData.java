package com.handinfo.redis4j.api;

public class PackData<T>
{
	private Class<T> dataType;
	private Object original;
	/**
	 * @return the dataType
	 */
	public Class<T> getDataType()
	{
		return dataType;
	}
	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(Class<T> dataType)
	{
		this.dataType = dataType;
	}
	/**
	 * @return the original
	 */
	public Object getOriginal()
	{
		return original;
	}
	/**
	 * @param original the original to set
	 */
	public void setOriginal(Object original)
	{
		this.original = original;
	}
}
