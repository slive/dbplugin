/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     ViewModel.java
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
 * view 模型，管理数据库所有的view
 */
public class ViewModelManager  extends AbstractDBModelManager<ViewModel>
{
	public ViewModelManager()
	{
		super();
	}
	
	@Override
	public String getName()
	{
		return "视图";		// 国际化时，需要做修改
	}
	
	@Override
	public boolean add(ViewModel tbModel)
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
	public ViewModel[] getChildren()
	{
		int size = list.size();
		if(size <= 0)
		{
			return null;
		}
		ViewModel[] tableBeans = new ViewModel[size];
		return list.toArray(tableBeans);
	}
	@Override
	public boolean isExisted(ViewModel t)
	{
		Iterator<ViewModel> it = list.iterator();
		while(it.hasNext())
		{
			ViewModel next = it.next();
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
