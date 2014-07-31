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
public class PreModelManager extends AbstractDBModelManager<PreModel>
{

	@Override
	public boolean add(PreModel PreModel)
	{
		if(PreModel == null)
		{
			return false;
		}
		if(!isExisted(PreModel))
		{
			PreModel.setParent(this);	// 添加父类
			return list.add(PreModel);	// 相同名称的应该排除
		}
		else
		{
			return false;
		}
	}
	@Override
	public PreModel[] getChildren()
	{
		int size = list.size();
		if(size <= 0)
		{
			return null;
		}
		PreModel[] tableBeans = new PreModel[size];
		return list.toArray(tableBeans);
	}
	@Override
	public boolean isExisted(PreModel t)
	{
		Iterator<PreModel> it = list.iterator();
		while(it.hasNext())
		{
			PreModel next = it.next();
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
		return "存储过程";
	}
}
