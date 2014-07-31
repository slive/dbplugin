/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     SQLInput.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-28   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.java.db.conn.ConnControl;

/**
 * 查询输入
 */
public class SQLInput implements IEditorInput
{
	private ConnControl connControl;
	@Override
	public boolean exists()
	{
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor()
	{
		return null;
	}

	@Override
	public String getName()
	{
		return connControl.getConnModel().getName() + "--数据库查询";
	}

	@Override
	public IPersistableElement getPersistable()
	{
		return null;
	}

	@Override
	public String getToolTipText()
	{
		return "数据库查询";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public ConnControl getConnControl()
	{
		return connControl;
	}
	
	public void setConnControl(ConnControl connControl)
	{
		this.connControl = connControl;
	}
}
