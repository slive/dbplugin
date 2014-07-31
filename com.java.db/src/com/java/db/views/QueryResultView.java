/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     QueryResultView.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-31   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.part.ViewPart;

import com.java.db.model.dbbeans.TBDatas;
import com.java.db.views.actions.QueryResultActionGroup;
import com.swtdesigner.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;


public class QueryResultView extends ViewPart
{

	public static final String ID = "com.java.db.views.QueryResultView"; //$NON-NLS-1$
	private Table table;
	private TableViewer tableViewer;
	private Label lbl_tip;
	private ToolBar toolBar;
	private TBDatas tbDatas;
	private Label lbl_page;
	private Label label;
	private Text txt_size;

	public QueryResultView()
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
		container.setLayout(new GridLayout(5, false));
		{
			lbl_tip = new Label(container, SWT.NONE);
			lbl_tip.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			{
				GridData gridData = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
				gridData.widthHint = 138;
				lbl_tip.setLayoutData(gridData);
			}
			lbl_tip.setText("查询结果");
		}
		{
			lbl_page = new Label(container, SWT.NONE);
			lbl_page.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			{
				GridData gridData = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
				gridData.widthHint = 174;
				lbl_page.setLayoutData(gridData);
			}
			lbl_page.setText("");
		}
		{
			label = new Label(container, SWT.NONE);
			label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			label.setText("设置页大小：");
		}
		{
			txt_size = new Text(container, SWT.BORDER);
			GridData gridData = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
			gridData.widthHint = 64;
			txt_size.setLayoutData(gridData);
			txt_size.setText("100");
			txt_size.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent arg0) 
				{
					String textTemp = txt_size.getText();
					if(tbDatas == null)
					{
						txt_size.setText(textTemp);
						return ;
					}
					try
					{
						if(tbDatas.setPageSize(Integer.parseInt(txt_size.getText())))
						{
							if(tbDatas.getNowPage() > tbDatas.getPageCount())
							{
								tableViewer.setInput(tbDatas.getResults(tbDatas.getPageCount()));
								tableViewer.refresh();
							}
							else
							{
								tableViewer.setInput(tbDatas.getResults(tbDatas.getNowPage()));
								tableViewer.refresh();
							}
							
							// 刷新页数
							lbl_page.setText("总页数：" + tbDatas.getPageCount() + " 当前页：" + tbDatas.getNowPage());
						}
					}
					catch (Exception e)
					{
						txt_size.setText(textTemp);
					}
					
				}
			});
		}
		{
			toolBar = new ToolBar(container, SWT.FLAT | SWT.RIGHT);
			toolBar.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		}
		{
			tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
			table = tableViewer.getTable();
			table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1));
			tableViewer.setLabelProvider(new ITableLabelProvider(){

				@Override
				public Image getColumnImage(Object element, int index)
				{
					return null;
				}

				@Override
				public String getColumnText(Object element, int index)
				{
					if(element instanceof String[])
					{
						return ((String[])element)[index];
					}
					return null;
				}

				@Override
				public void addListener(ILabelProviderListener arg0)
				{
					// TODO Auto-generated method stub
					
				}

				@Override
				public void dispose()
				{
					// TODO Auto-generated method stub
					
				}

				@Override
				public boolean isLabelProperty(Object element, String arg1)
				{
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void removeListener(ILabelProviderListener arg0)
				{
					// TODO Auto-generated method stub
					
				}});
			
			tableViewer.setContentProvider(new IStructuredContentProvider(){

				@Override
				public Object[] getElements(Object element)
				{
					if(element instanceof TBDatas)
					{
						return ((TBDatas)element).getResults();
					}
					else if(element instanceof String[][])
					{
						return ((String[][])element);
					}
					else if(element instanceof String[])
					{
						return ((String[])element);
					}
					return null;
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
					
				}});
		}

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	public void updateDatas(TBDatas tbDatas,String tbname)
	{
		lbl_tip.setText(tbname + " --查询结果：");
		setFocus();
		table = tableViewer.getTable();
		final String[] colNames = tbDatas.getColNames();
		if(colNames != null)
		{
			TextCellEditor[] textCellEditors = (TextCellEditor[]) tableViewer.getCellEditors();
			TableColumn[] columns = table.getColumns();
			for (int i = 0; i < columns.length; i++)
			{
				columns[i].dispose();	// 删除栏
				textCellEditors[i].dispose();
			}
			textCellEditors = new TextCellEditor[colNames.length];
			for (int i = 0; i < colNames.length; i++)
			{
				textCellEditors[i] = new TextCellEditor(table);
				TableColumn tableColumn = new TableColumn(table, SWT.NONE);
				tableColumn.setWidth(100);
				tableColumn.setText(colNames[i]);
			}
			tableViewer.setColumnProperties(colNames);
			tableViewer.setCellEditors(textCellEditors);
			tableViewer.setCellModifier(new ICellModifier()
			{
				@Override
				public boolean canModify(Object arg0, String column)
				{
					for (int i = 0; i < colNames.length; i++)
					{
						if(colNames[i].equals(column))
						{
							return true;
						}
					}
					return false;
				}

				@Override
				public Object getValue(Object element, String column)
				{
					for (int i = 0; i < colNames.length; i++)
					{
						if(colNames[i].equals(column))
						{
							return ((String[])element)[i];
						}
					}
					return null;
				}

				@Override
				public void modify(Object arg0, String column, Object arg2)
				{
					// TODO Auto-generated method stub
					
				}});
			tbDatas.setPageSize(Integer.parseInt(txt_size.getText()));
			QueryResultActionGroup qrActionGroup = new QueryResultActionGroup(tableViewer,tbDatas,lbl_page);
			qrActionGroup.fillToolBars(new ToolBarManager(toolBar));
		}
		table.clearAll();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		if(tbDatas.getCount() <= 0)	// 判断为空的情况
		{
			return;
		}
		tableViewer.setInput(tbDatas.getResults(tbDatas.getNowPage()));
		tableViewer.refresh();
		this.tbDatas = tbDatas;
		lbl_page.setText("总页数：" + tbDatas.getPageCount() + " 当前页：" + tbDatas.getNowPage());
		tableViewer.getTable().layout(true);	// 重新布局
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
