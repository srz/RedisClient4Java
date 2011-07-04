package org.elk.redis4j.impl.cache;

public class Node
{

	private String nodeName;

	Node(String name)
	{
		this.nodeName = name;
	}

	public String getName()
	{
		return this.nodeName;
	}

	public String toString()
	{
		return this.nodeName;
	}

	public boolean equals(Object obj)
	{
		if (obj instanceof Node)
		{
			return ((Node) obj).nodeName.equals(this.nodeName);
		}

		return false;
	}

	public int hashCode()
	{
		return this.nodeName.hashCode();
	}
}
