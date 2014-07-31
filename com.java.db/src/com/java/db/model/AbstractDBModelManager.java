/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     TBModel.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-28   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.model;

import java.util.ArrayList;
import java.util.List;


/**
 * 表模型，可以存放多个表，view，存储过程及函数结构
 * @param T 代表需要管理的数据模型
 */
public abstract class AbstractDBModelManager<T> implements IList<T>,IBase<DBModel>
{
	private DBModel parent;			// 父节点
	protected List<T> list;
	public AbstractDBModelManager()
	{
		list = new ArrayList<T>();
	}
	
	@Override
	public DBModel getParent()
	{
		return parent;
	}
	
	@Override
	public void setParent(DBModel parent)
	{
		this.parent = parent;
	}
	
	@Override
	public void remove(T t)
	{
		list.remove(t);
	}
	@Override
	public void removeAll()
	{
		list.clear();
	}

	@Override
	public T get(int index)
	{
		return list.get(index);
	}
	
}
