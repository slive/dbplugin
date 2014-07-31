/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     DBActionGroup.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-27   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.views.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.MessageBox;

import com.java.db.actions.OpenSQLQueryEditor;
import com.java.db.conn.ConnControl;
import com.java.db.model.DBModel;

/**
 * 操作数据库的actiongroup
 */
public class DBActionGroup extends AbstractActionGroup
{
	private DBModel selection;
	public DBActionGroup(TreeViewer tv)
	{
		super(tv);
		selection = (DBModel) ((IStructuredSelection)treeViewer.getSelection()).getFirstElement();
	}
	
	/**
	 * 添加上行文菜单
	 * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
	 */
	public void fillContextMenu(IMenuManager menu)
	{
		MenuManager mm = (MenuManager) menu;
		mm.add(new AddDB(selection.getParent()));			// 新建数据库
		mm.add(new DelDB());								// 删除数据库
		mm.add(new Separator());
		mm.add(new PropertyDB());							// 连接属性
		mm.add(new OpenSQLQueryEditor(
				selection.getParent().getConnControl()));	// 打开编辑界面
		super.fillContextMenu(menu);
	}
	
	/**
	 * 删除数据库
	 */
	class DelDB extends Action
	{
		public DelDB()
		{
			setText("删除数据库");
		}
		
		@Override
		public void run()
		{
			ConnControl connControl = selection.getParent().getConnControl();
			if(MessageDialog.openConfirm(activeShell, "确认删除", "请确定是否删除该数据库？"))
			{
				if(connControl.execute("DROP DATABASE " +  selection.getDb().getDBName()))
				{
					selection.getParent().remove(selection);	// 删除数据库
					treeViewer.refresh(); 					// 刷新treeviewer
				}
				else
				{
					MessageBox mb = new MessageBox(activeShell);
					mb.setText("提示");
					mb.setMessage("删除失败!");
					mb.open();
				}
			}
		}
	}
	
	/**
	 * 数据库属性
	 */
	class PropertyDB extends Action
	{
		public PropertyDB()
		{
			setText("数据库属性");
		}
		
		@Override
		public void run()
		{
			MessageBox mb = new MessageBox(activeShell);
			mb.setText("提示");
			mb.setMessage("数据库属性!");
			mb.open();
			return;
		}
	}
	
	/**
	 * 刷新数据库内的信息
	 */
	@Override
	public void refresh()
	{
		selection.getParent().getConnControl().refreshDB(selection);
		treeViewer.refresh();
	}
}
