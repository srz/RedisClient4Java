package org.elk.redis4j.manager.widget;

public class NavigationData
{
	private String title;
	private String classFullName;
	private String methodName;
	/**
	 * @param title
	 * @param classFullName
	 * @param methodName
	 */
	public NavigationData(String title, String classFullName, String methodName)
	{
		super();
		this.title = title;
		this.classFullName = classFullName;
		this.methodName = methodName;
	}
	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}
	/**
	 * @return the classPath
	 */
	public String getClassFullName()
	{
		return classFullName;
	}
	
	public String getClassName()
	{
		String[] str = classFullName.split(".");
		return str[str.length-1];
	}
	
	public Class getClassType()
	{
		String[] str = classFullName.split(".");
		Class c= null;
		try
		{
			c = Class.forName("");
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
	/**
	 * @return the methodName
	 */
	public String getMethodName()
	{
		return methodName;
	}
}
