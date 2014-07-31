/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     ConnectManagerView.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-25   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.views;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;

import com.java.db.model.ConnectModel;
import com.java.db.model.ConnectModelManager;
import com.java.db.model.DBModel;
import com.java.db.model.TBModelManager;
import com.java.db.model.TableModel;
import com.java.db.model.ViewModel;
import com.java.db.views.actions.ConnActionGroup;
import com.java.db.views.actions.DBActionGroup;
import com.java.db.views.actions.OpenConn;
import com.java.db.views.actions.TBActionGroup;
import com.java.db.views.actions.TBMActionGroup;
import com.java.db.views.actions.VBActionGroup;
import com.java.db.views.providers.ConnectManagerContextProvider;
import com.java.db.views.providers.ConnectManagerLabelProvider;


public class ConnectManagerView extends ViewPart
{

	public static final String ID = "com.java.db.views.ConnectManagerView"; //$NON-NLS-1$
	public static TreeViewer TreeViewer;

	public ConnectManagerView()
	{
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent)
	{
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		{
			TreeViewer = new TreeViewer(container, SWT.BORDER);
			final Tree tree = TreeViewer.getTree();
			tree.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetDefaultSelected(SelectionEvent e)
				{
					Object element = ((IStructuredSelection)TreeViewer.getSelection()).getFirstElement();
					// 过滤上行文菜单
					if(element instanceof ConnectModel)
					{
						// 未打开连接时，双击打开连接
						if(!((ConnectModel)element).getConnControl().isConnected())
						{
							new OpenConn(TreeViewer).run();
						}
					}
				}
			});
			TreeViewer.setLabelProvider(new ConnectManagerLabelProvider());
			TreeViewer.setContentProvider(new ConnectManagerContextProvider());
			ConnectModelManager instance = ConnectModelManager.getInstance();
			if(instance.getChildren() != null)
			{
				TreeViewer.setInput(instance);
			}
		}
		createActions();
		initializeToolBar();
		initializeMenu();
		createContextMenu();	// 配合弹出式菜单一起使用
	}

	
	private void createContextMenu()
	{
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);	// 监听
		menuMgr.addMenuListener(new IMenuListener()
		{
			public void menuAboutToShow(IMenuManager menu)
			{
				Object element = ((IStructuredSelection)TreeViewer.getSelection()).getFirstElement();
				// 过滤上行文菜单
				if(element instanceof ConnectModel)
				{
					ConnActionGroup connAP = new ConnActionGroup(TreeViewer);
					connAP.fillContextMenu(menu);
				}	
				else if(element instanceof DBModel)
				{
					DBActionGroup dbGroup = new DBActionGroup(TreeViewer);
					dbGroup.fillContextMenu(menu);
				}
				else if(element instanceof TBModelManager)
				{
					TBMActionGroup tbmActionGroup = new TBMActionGroup(TreeViewer);
					tbmActionGroup.fillContextMenu(menu);
				}
				else if(element instanceof TableModel)
				{
					TBActionGroup tbActionGroup = new TBActionGroup(TreeViewer);
					tbActionGroup.fillContextMenu(menu);
				}
				else if(element instanceof ViewModel)
				{
					VBActionGroup vbActionGroup = new VBActionGroup(TreeViewer);
					vbActionGroup.fillContextMenu(menu);
				}
			}
		});
		Menu m = menuMgr.createContextMenu(TreeViewer.getControl());
		TreeViewer.getControl().setMenu(m);
		getSite().registerContextMenu(menuMgr, TreeViewer);	// 注册Menu到上下文管理器中
	}
	
	/**
	 * Create the actions.
	 */
	private void createActions()
	{
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar()
	{
		@SuppressWarnings("unused")
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu()
	{
		@SuppressWarnings("unused")
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus()
	{
		// Set the focus
	}
}
