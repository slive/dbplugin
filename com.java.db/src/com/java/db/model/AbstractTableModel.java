/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     Table.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-28  Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.model;

import com.java.db.model.dbbeans.TBDatas;

/**
 * 包括了View,Table的抽象模型
 *
 */
public class AbstractTableModel<T> implements IBase<T>
{
	private String name;	// 名字是可修改的
	private T parent;
	private TBDatas datas;
	
	public AbstractTableModel(String name)
	{
		this.name = name;
		datas = new TBDatas();
	}
	
	public TBDatas getDatas()
	{
		return datas;
	}
	
	/**
	 * 名字不能为空，并且不能是非法值
	 * @param name
	 */
	public void setName(String name)
	{
		if(name == null || name.isEmpty())
		{
			return;
		}
		this.name = name;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public T getParent()
	{
		return parent;
	}

	@Override
	public void setParent(T object)
	{
		parent = object;
	}

}
