/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     IList.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-28   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.model;

/**
 * 操作数据库时，所需的接口
 * @author llp585470
 * @param <T>
 */
public interface IList <T>
{
	/**
	 * 添加
	 * @param t
	 * @return
	 */
	public boolean add(T t);
	
	/**
	 * 删除
	 * @param t
	 */
	public void remove(T t);
	
	/**
	 * 删除所有
	 */
	public void removeAll();
	
	/**
	 * 获取所需
	 * @param index
	 * @return
	 */
	public T get(int index);
	
	/**
	 * 判断是否存在
	 * @return
	 */
	public boolean isExisted(T t);
	
	/**
	 * 获取子节点
	 */
	public T[] getChildren();
}
