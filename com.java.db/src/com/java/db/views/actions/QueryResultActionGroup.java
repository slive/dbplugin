/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     QueryResultActionGroup.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-11-2   Slive      V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.views.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.actions.ActionGroup;

import com.java.db.DBActivator;
import com.java.db.model.dbbeans.TBDatas;


public class QueryResultActionGroup extends ActionGroup
{
	private TableViewer tableViewer;
	private TBDatas tbDatas;
	private Label lbl_page;
	
	public QueryResultActionGroup(TableViewer tb,TBDatas tDatas,Label lbl_page)
	{
		this.tableViewer = tb;
		this.tbDatas = tDatas;
		this.lbl_page = lbl_page;
	}
	
	@Override
	public void fillContextMenu(IMenuManager menu)
	{
		menu.add(new DownPage());
		super.fillContextMenu(menu);
	}
	
	public void fillToolBars(ToolBarManager toolBarManager)
	{
		toolBarManager.add(createContributionItem(new DownPage()));
		toolBarManager.add(createContributionItem(new UpPage()));
		toolBarManager.update(true);
	}
	
	class DownPage extends Action
	{
		public DownPage()
		{
			setText("下一页");
			setImageDescriptor(DBActivator.getImageDescriptor("icons/arrowdown.gif"));
			setHoverImageDescriptor(DBActivator.getImageDescriptor("icons/arrowdown.gif"));
		}
		
		@Override
		public void run()
		{
			tableViewer.setInput(tbDatas.getResults(tbDatas.getNowPage() + 1));
			lbl_page.setText("总页数：" + tbDatas.getPageCount() + " 当前页：" + tbDatas.getNowPage());
			tableViewer.refresh();
		}
	}
	
	class UpPage extends Action
	{
		public UpPage()
		{
			setText("上一页");
			setImageDescriptor(DBActivator.getImageDescriptor("icons/arrowup.gif"));
			setHoverImageDescriptor(DBActivator.getImageDescriptor("icons/arrowup.gif"));
		}
		
		@Override
		public void run()
		{
			tableViewer.setInput(tbDatas.getResults(tbDatas.getNowPage() - 1));
			lbl_page.setText("总页数：" + tbDatas.getPageCount() + " 当前页：" + tbDatas.getNowPage());
			tableViewer.refresh();
		}
	}
	
	// 将Action包装成ActionContributionItem类的方法。实际上Action加入到
	// ToolBarManager或MenuManager里时，也做了ActionContributionItem的包装，
	// 大家看它ToolBarManager的add(IAction)的源代码即知
	private IContributionItem createContributionItem(IAction action)
	{
		ActionContributionItem aci = new ActionContributionItem(action);
		aci.setMode(ActionContributionItem.MODE_FORCE_TEXT);// 显示图像+文字
		return aci;
	}
}
