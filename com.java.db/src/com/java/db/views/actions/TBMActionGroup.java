/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     TBMActionGroup.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-28   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.views.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TreeViewer;

/**
 *  关于管理多个表格的actiongroup 
 */
public class TBMActionGroup extends AbstractActionGroup
{

	public TBMActionGroup(TreeViewer tv)
	{
		super(tv);
	}

	@Override
	public void fillContextMenu(IMenuManager menu)
	{
		MenuManager mm = (MenuManager) menu;
		mm.add(new CreateTable());
		super.fillContextMenu(menu);
	}
	
	/**
	 * 刷新表s
	 * @see com.java.db.views.actions.AbstractActionGroup#refresh()
	 */
	@Override
	public void refresh()
	{
	}
	
	/**
	 * 新建表
	 */
	class CreateTable extends Action
	{
		public CreateTable()
		{
			setText("新建表");
		}
		
		@Override
		public void run()
		{
			treeViewer.refresh();
		}
	}
}
