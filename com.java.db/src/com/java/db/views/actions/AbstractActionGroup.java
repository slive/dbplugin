/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     AbstractActionGroup.java
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
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionGroup;

import com.java.db.DBActivator;

/**
 * 针对view中创建的抽象ActionGroup
 */
public abstract class AbstractActionGroup extends ActionGroup
{
	protected TreeViewer treeViewer;
	protected Shell activeShell;
	protected Object selectElement;
	
	public AbstractActionGroup(TreeViewer tv)
	{
		treeViewer = tv;
		activeShell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
		selectElement = ((IStructuredSelection)treeViewer.getSelection()).getFirstElement();
	}

	/**
	 * 已经包括了添加菜单到treeViewer中
	 * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
	 */
	@Override
	public void fillContextMenu(IMenuManager menu)
	{
		menu.add(new Separator());		// 分割
		menu.add(new Refresh());		// 添加刷新
		super.fillContextMenu(menu);
	}
	
	/**
	 * 刷新连接
	 */
	class Refresh extends Action
	{
		public Refresh()
		{
			setText("刷新");
			
			// 设置图片
			ImageDescriptor imageDescriptor = DBActivator.getImageDescriptor("icons/refresh.gif");
			setImageDescriptor(imageDescriptor);
			setHoverImageDescriptor(imageDescriptor);
		}
		
		@Override
		public void run()
		{
			refresh();
		}
	}
	
	/**
	 * 公用刷新action
	 */
	public abstract void refresh();
}
