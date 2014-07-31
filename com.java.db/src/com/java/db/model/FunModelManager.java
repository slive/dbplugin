/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     FunAndPreModelManager.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-11-1   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.model;

import java.util.Iterator;

/**
 * 函数模型的管理
 */
public class FunModelManager extends AbstractDBModelManager<FunModel>
{

	@Override
	public boolean add(FunModel funModel)
	{
		if(funModel == null)
		{
			return false;
		}
		if(!isExisted(funModel))
		{
			funModel.setParent(this);	// 添加父类
			return list.add(funModel);	// 相同名称的应该排除
		}
		else
		{
			return false;
		}
	}
	@Override
	public FunModel[] getChildren()
	{
		int size = list.size();
		if(size <= 0)
		{
			return null;
		}
		FunModel[] tableBeans = new FunModel[size];
		return list.toArray(tableBeans);
	}
	@Override
	public boolean isExisted(FunModel t)
	{
		Iterator<FunModel> it = list.iterator();
		while(it.hasNext())
		{
			FunModel next = it.next();
			String name = next.getName();
			String name2 = t.getName();
			if(name == name2 || name.equals(name2))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public String getName()
	{
		return "函数";
	}
}
