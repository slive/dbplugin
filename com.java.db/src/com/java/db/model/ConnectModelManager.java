/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     ModelFactory.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-26   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class ConnectModelManager implements IList<ConnectModel>,IBase<Object>
{
	private static ConnectModelManager connectFactory = null;
	private List<ConnectModel> connlist;
	private Object parent;
	
	private ConnectModelManager()
	{
		connlist = new LinkedList<ConnectModel>();
		parent = new Object();
	}
	
	public static ConnectModelManager getInstance()
	{
		if(connectFactory == null)
		{
			connectFactory = new ConnectModelManager();
		}
		return connectFactory;
	}
	
	public boolean add(ConnectModel connectModel)
	{
		if(connectModel == null)
		{
			return false;
		}
		if(!isExisted(connectModel))
		{
			connectModel.setParent(this);
			return connlist.add(connectModel);
		}
		return false;
	}
	
	/**
	 * 只要是同名的就默认为同名
	 * @param dbModel
	 * @return
	 */
	public boolean isExisted(ConnectModel connectModel)
	{
		Iterator<ConnectModel> it = connlist.iterator();
		while(it.hasNext())
		{
			ConnectModel next = it.next();
			String connName = next.getConnect().getGeneralInfor().getConnName();
			String connName2 = connectModel.getConnect().getGeneralInfor().getConnName().trim();
			if(connName == connName2 || connName.equals(connName2))
			{
				return true;
			}
		}
		return false;
	}
	
	public void remove(ConnectModel connectModel)
	{
		connlist.remove(connectModel);
	}
	public void removeAll()
	{
		connlist.clear();
	}
	
	public ConnectModel get(int index)
	{
		return connlist.get(index);
	}
	
	/**
	 * 获得所有，当不存在是返回空
	 */
	public ConnectModel[] getChildren()
	{
		int size = connlist.size();
		if(size <= 0)
		{
			return null;
		}
		ConnectModel[] connectModels = new ConnectModel[size];
		return connlist.toArray(connectModels);
	}

	@Override
	public String getName()
	{
		// TODO Auto-generated method stub
		return "数据库管理";
	}

	@Override
	public Object getParent()
	{
		return parent;
	}

	@Override
	public void setParent(Object object)
	{
		parent =object;
	}
}
