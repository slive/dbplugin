/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     ConnActionGroup.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-26   Slive      V001.00		创建文件
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
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.java.db.actions.OpenSQLQueryEditor;
import com.java.db.editors.SQLEditor;
import com.java.db.editors.SQLInput;
import com.java.db.model.ConnectModel;
import com.java.db.model.ConnectModelManager;
import com.java.db.model.util.ConnUtil;
import com.java.db.util.DBSystemConstant;

/**
 * 管理连接的ActionGroup，selection 属于 @ConnectModel类
 */
public class ConnActionGroup extends AbstractActionGroup
{
	private ConnectModel selection;

	public ConnActionGroup(TreeViewer tv)
	{
		super(tv);
		selection = (ConnectModel) ((IStructuredSelection)treeViewer.getSelection()).getFirstElement();
	}
	
	
	/**
	 * 添加上下文菜单
	 * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
	 */
	@Override
	public void fillContextMenu(IMenuManager menu)
	{
		MenuManager mm = (MenuManager) menu;
		mm.add(new OpenConn(treeViewer));			// 打开连接
		mm.add(new CloseConn());		// 关闭连接
		mm.add(new Separator());
		mm.add(new DelConn());			// 删除连接
		mm.add(new Separator());
		mm.add(new AddDB(selection));	// 新建数据库
		mm.add(new Separator());
		mm.add(new PropertyConn());		// 连接属性
		mm.add(new OpenSQLQueryEditor(
				selection.getConnControl())); // 打开编辑界面
		super.fillContextMenu(menu);
	}
	
	/**
	 * 关闭连接
	 */
	class CloseConn extends Action
	{
		public CloseConn()
		{
			setText("关闭连接");
			if(selection.getConnControl().isConnected())
			{
				setEnabled(true);	// 已经连上后，才可以关闭
			}
			else
			{
				setEnabled(false);
			}
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void run()
		{
			selection.getConnControl().closeConn();
			treeViewer.refresh();
			
			// 关闭相关的查询窗口
			IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IEditorPart[] parts = activePage.getEditors();
			for (int i = 0; i < parts.length; i++)
			{
				IEditorPart editorPart = parts[i];
				
				// 过滤重复打开
				if(editorPart instanceof SQLEditor)
				{
					SQLInput editorInput = (SQLInput) editorPart.getEditorInput();
					if(editorInput.getConnControl().equals(selection.getConnControl()))	// 同一个链接，不需要打开
					{
						// 关闭
						activePage.closeEditor(editorPart, false);
						return ;
					}
				}
			}
		}
	}
	
	/**
	 * 删除连接
	 */
	class DelConn extends Action
	{
		public DelConn()
		{
			setText("删除连接");
		}
		
		@Override
		public void run()
		{
			if(MessageDialog.openConfirm(activeShell, "确认删除", "请确定是否删除该连接？"))
			{
				// 先关闭
				selection.getConnControl().closeConn();
				// 如果涉及到文件存储，还得删除文件
				ConnectModelManager.getInstance().remove(selection);
				ConnUtil.removeConn(selection, DBSystemConstant.CONN_PATH);
				if(ConnectModelManager.getInstance().getChildren() == null)	// 防止显示为空情况出现
				{
					treeViewer.setInput(null);
				}
				treeViewer.refresh();
			}
		}
	}
	
	/**
	 * 连接属性
	 */
	class PropertyConn extends Action
	{
		public PropertyConn()
		{
			setText("连接属性");
		}
		
		@Override
		public void run()
		{
			MessageBox mb = new MessageBox(activeShell);
			mb.setText("提示");
			mb.setMessage("连接属性");
			mb.open();
			return;
		}
	}

	@Override
	public void refresh()
	{
		selection.getConnControl().refreshConn();
		treeViewer.refresh();
	}
}
