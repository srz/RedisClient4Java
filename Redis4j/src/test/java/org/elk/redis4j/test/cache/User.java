package org.elk.redis4j.test.cache;

import java.io.Serializable;

public class User implements Serializable
{
	int id;

	String name;

	long timestamp;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public long getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(long timestamp)
	{
		this.timestamp = timestamp;
	}

	/**
	 * @param id
	 * @param name
	 * @param timestamp
	 */
	public User(int id, String name, long timestamp)
	{
		super();
		this.id = id;
		this.name = name;
		this.timestamp = timestamp;
	}
	
	public User()
	{
	}
}
