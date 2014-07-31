/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     ConnectManagerContextProvider.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-26  Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.views.providers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.java.db.model.ConnectModel;
import com.java.db.model.ConnectModelManager;
import com.java.db.model.DBModel;
import com.java.db.model.FunModel;
import com.java.db.model.FunModelManager;
import com.java.db.model.PreModel;
import com.java.db.model.PreModelManager;
import com.java.db.model.TBModelManager;
import com.java.db.model.ViewModelManager;


public class ConnectManagerContextProvider implements ITreeContentProvider
{

	@Override
	public Object[] getChildren(Object element)
	{
		return getAllElements(element);
	}

	/**
	 * 获取元素
	 * @param element
	 * @return
	 */
	private Object[] getAllElements(Object element)
	{
		if(element instanceof ConnectModelManager)
		{
			ConnectModelManager cm = (ConnectModelManager) element;
			return cm.getChildren();
		}
		else if(element instanceof ConnectModel)
		{
			ConnectModel connectModel = (ConnectModel)element;
			return connectModel.getChildren();
		}
		else if(element instanceof DBModel)
		{
			return ((DBModel)element).getChildren();
		}
		else if(element instanceof TBModelManager)
		{
			return ((TBModelManager)element).getChildren();
		}
		else if(element instanceof ViewModelManager)
		{
			return ((ViewModelManager)element).getChildren();
		}
		else if(element instanceof PreModelManager)
		{
			return ((PreModelManager)element).getChildren();
		}
		else if(element instanceof FunModelManager)
		{
			return ((FunModelManager)element).getChildren();
		}
		return null;
	}

	@Override
	public Object getParent(Object element)
	{
		if(element instanceof ConnectModel)
		{
			ConnectModel connectModel = (ConnectModel)element;
			return connectModel.getParent();
		}
		else if(element instanceof DBModel)
		{
			return ((DBModel)element).getParent();
		}
		else if(element instanceof TBModelManager)
		{
			return ((TBModelManager)element).getParent();
		}
		else if(element instanceof ViewModelManager)
		{
			return ((ViewModelManager)element).getParent();
		}
		else if(element instanceof PreModelManager)
		{
			return ((PreModelManager)element).getParent();
		}
		else if(element instanceof FunModelManager)
		{
			return ((FunModelManager)element).getParent();
		}
		else if(element instanceof PreModel)
		{
			return ((PreModel)element).getParent();
		}
		else if(element instanceof FunModel)
		{
			return ((FunModel)element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element)
	{
		// 为空时，不存在子节点
		return getAllElements(element) == null ? false:true;
	}

	@Override
	public Object[] getElements(Object element)
	{
		return getAllElements(element);
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2)
	{
		// TODO Auto-generated method stub
		
	}

}
