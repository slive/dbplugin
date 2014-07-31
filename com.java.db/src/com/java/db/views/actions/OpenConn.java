package com.java.db.views.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.java.db.model.ConnectModel;

/**
 * 打开数据库连接
 */
public class OpenConn extends Action
{
	private TreeViewer treeViewer;
	private ConnectModel selection;
	private Shell activeShell;

	public OpenConn(TreeViewer tv)
	{
		treeViewer = tv;
		selection = (ConnectModel) ((IStructuredSelection)treeViewer.getSelection()).getFirstElement();
		activeShell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
		
		setText("打开连接");
		if(selection.getConnControl().isConnected())
		{
			setEnabled(false);	// 连上后，禁用
		}
		else
		{
			setEnabled(true);
		}
	}
	
	@Override
	public void run()
	{
		if(selection.getConnControl().openConn())
		{
			// 刷新
			treeViewer.refresh();
			treeViewer.expandToLevel(2);	// 展开到二级菜单
			setEnabled(false);
		}
		else
		{
			MessageBox mb = new MessageBox(activeShell);
			mb.setText("提示");
			mb.setMessage("连接失败!");
			mb.open();
			setEnabled(true);
		}
	}
}