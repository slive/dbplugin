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

import java.util.Iterator;


/**
 * 表模型，可以存放多个表结构
 */
public class TBModelManager extends AbstractDBModelManager<TableModel>
{
	public TBModelManager()
	{
		super();
	}

	@Override
	public String getName()
	{
		return "表";		// 国际化时，需要做修改
	}
	
	@Override
	public boolean add(TableModel tbModel)
	{
		if(tbModel == null)
		{
			return false;
		}
		if(!isExisted(tbModel))
		{
			tbModel.setParent(this);	// 添加父类
			return list.add(tbModel);	// 相同名称的应该排除
		}
		else
		{
			return false;
		}
	}
	@Override
	public TableModel[] getChildren()
	{
		int size = list.size();
		if(size <= 0)
		{
			return null;
		}
		TableModel[] tableBeans = new TableModel[size];
		return list.toArray(tableBeans);
	}
	@Override
	public boolean isExisted(TableModel t)
	{
		Iterator<TableModel> it = list.iterator();
		while(it.hasNext())
		{
			TableModel next = it.next();
			String name = next.getName();
			String name2 = t.getName();
			if(name == name2 || name.equals(name2))
			{
				return true;
			}
		}
		return false;
	}
	
}
