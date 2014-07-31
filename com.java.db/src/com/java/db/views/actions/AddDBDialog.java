/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     AddDBDialog.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-26   Slive      V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.views.actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.java.db.model.ConnectModel;
import com.java.db.model.DBModel;
import com.java.db.views.ConnectManagerView;


public class AddDBDialog extends Dialog
{

	protected Object result;
	protected Shell shell;
	private Text text;
	private Button button;
	private Button button_1;
	private ConnectModel connectModel;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AddDBDialog(Shell parent, int style, ConnectModel connectModel)
	{
		super(parent, style);
		setText("添加数据库");
		this.connectModel = connectModel;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open()
	{
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents()
	{
		shell = new Shell(getParent(), getStyle());
		shell.setSize(311, 123);
		shell.setText(getText());
		{
			Label label = new Label(shell, SWT.NONE);
			label.setBounds(10, 10, 72, 12);
			label.setText("数据库名称");
		}
		{
			text = new Text(shell, SWT.BORDER);
			text.setBounds(110, 7, 179, 18);
		}
		{
			button = new Button(shell, SWT.NONE);
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e)
				{
					if(connectModel.getConnControl().execute("CREATE DATABASE " + text.getText().trim()))
					{
						connectModel.add(new DBModel(text.getText()));
					}
					else
					{
						MessageBox mb = new MessageBox(shell);
						mb.setText("提示");
						mb.setMessage("该数据库已存在!");
						mb.open();
						return ;
					}
					ConnectManagerView.TreeViewer.refresh();
					shell.close();
				}
			});
			button.setBounds(110, 63, 72, 22);
			button.setText("确定");
		}
		{
			button_1 = new Button(shell, SWT.NONE);
			button_1.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e)
				{
					shell.close();
				}
			});
			button_1.setBounds(201, 63, 72, 22);
			button_1.setText("取消");
		}
	}

}
