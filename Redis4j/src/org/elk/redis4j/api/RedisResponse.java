package org.elk.redis4j.api;

import java.util.List;

public class RedisResponse
{
	private RedisResponseType type;
	private String textValue;
	private byte[] bulkValue;
	private List<RedisResponse> multiBulkValue;
	
	/**
	 * @param type
	 * @param result
	 */
	public RedisResponse(RedisResponseType type)
	{
		super();
		this.type = type;
		this.textValue = null;
		this.bulkValue = null;
		this.multiBulkValue = null;
	}
	
	/**
	 * @return the type
	 */
	public RedisResponseType getType()
	{
		return type;
	}

	/**
	 * @return the textValue
	 */
	public String getTextValue()
	{
		return textValue;
	}

	/**
	 * @param textValue the textValue to set
	 */
	public void setTextValue(String textValue)
	{
		this.textValue = textValue;
	}

	/**
	 * @return the bulkValue
	 */
	public byte[] getBulkValue()
	{
		return bulkValue;
	}

	/**
	 * @param bulkValue the bulkValue to set
	 */
	public void setBulkValue(byte[] bulkValue)
	{
		this.bulkValue = bulkValue;
	}

	/**
	 * @return the multiBulkValue
	 */
	public List<RedisResponse> getMultiBulkValue()
	{
		return multiBulkValue;
	}

	/**
	 * @param multiBulkValue the multiBulkValue to set
	 */
	public void setMultiBulkValue(List<RedisResponse> multiBulkValue)
	{
		this.multiBulkValue = multiBulkValue;
	}

}
