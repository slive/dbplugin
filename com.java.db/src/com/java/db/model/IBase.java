/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     IBase.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-28   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.model;


public interface IBase<T>
{
	/**
	 * 获取名称
	 */
	public String getName();
	
	/**
	 * 获取的父节点
	 * @return
	 */
	public T getParent();
	
	/**
	 * 设置父类
	 * @param object
	 */
	public void setParent(T object);
}
