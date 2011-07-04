package org.elk.redis4j.api;


/**
 * 向List集合插入元素时使用此对象标识插入的位置
 */
public enum ListPosition
{
	
	/**
	 * 在指定元素之前插入
	 */
	AFTER, 
	/**
	 * 在指定元素之后插入
	 */
	BEFORE;
}
