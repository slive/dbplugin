/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     ConnectDialog.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-26   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.views;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.java.db.conn.ConnControl;
import com.java.db.model.ConnectModel;
import com.java.db.model.ConnectModelManager;
import com.java.db.model.connectbeans.GeneralInforBean;
import com.java.db.model.util.ConnUtil;
import com.java.db.util.DBSystemConstant;


public class NewConnectDialog extends Dialog
{

	protected Object result;
	protected Shell shell;
	private TreeViewer treeViewer;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public NewConnectDialog(Shell parent, int style,TreeViewer tv)
	{
		super(parent, style);
		setText("建立连接");
		treeViewer = tv;
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
		shell.setSize(520, 342);
		shell.setText(getText());
		{
			CTabFolder tabFolder = new CTabFolder(shell, SWT.BORDER);
			tabFolder.setSimple(false);
			tabFolder.setBounds(10, 10, 429, 254);
			tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
			{
				CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
				tabItem.setText("正常设置");
				{
					Group group = new Group(tabFolder, SWT.NONE);
					tabItem.setControl(group);
					{
						Label label = new Label(group, SWT.NONE);
						label.setBounds(29, 20, 54, 21);
						label.setText("名称");
					}
					{
						text = new Text(group, SWT.BORDER);
						text.setBounds(85, 17, 166, 22);
						text.setText("mysql");
					}
					{
						Label label = new Label(group, SWT.NONE);
						label.setBounds(29, 61, 54, 21);
						label.setText("IP");
					}
					{
						text_1 = new Text(group, SWT.BORDER);
						text_1.setBounds(85, 58, 166, 22);
						text_1.setText("127.0.0.1");
					}
					{
						Label label = new Label(group, SWT.NONE);
						label.setBounds(29, 102, 54, 22);
						label.setText("端口");
					}
					{
						text_2 = new Text(group, SWT.BORDER);
						text_2.setBounds(85, 99, 166, 22);
						text_2.setText("3306");
					}
					{
						Label label = new Label(group, SWT.NONE);
						label.setBounds(29, 144, 54, 21);
						label.setText("用户名");
					}
					{
						text_3 = new Text(group, SWT.BORDER);
						text_3.setBounds(85, 140, 166, 22);
						text_3.setText("root");
					}
					{
						Label label = new Label(group, SWT.NONE);
						label.setBounds(29, 185, 54, 19);
						label.setText("密码");
					}
					{
						text_4 = new Text(group, SWT.BORDER | SWT.PASSWORD);
						text_4.setBounds(85, 181, 166, 22);
						text_4.setText("");
					}
				}
			}
		}
		{
			Button button = new Button(shell, SWT.NONE);
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) 
				{
					try
					{
						if(ConnControl.TestConn(getConnectModel()))
						{
							MessageBox mb = new MessageBox(shell);
							mb.setText("提示");
							mb.setMessage("测试成功!");
							mb.open();
							return;
						}
					}
					catch (Exception e1)
					{
						e1.printStackTrace();
					}
					MessageBox mb = new MessageBox(shell);
					mb.setText("提示");
					mb.setMessage("测试失败!");
					mb.open();
				}
			});
			button.setBounds(10, 270, 96, 30);
			button.setText("测试连接");
		}
		{
			Button button = new Button(shell, SWT.NONE);
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) 
				{
					ConnectModel connectModel = getConnectModel();
					ConnectModelManager instance = ConnectModelManager.getInstance();
					if(!instance.add(connectModel))
					{
						MessageBox mb = new MessageBox(shell);
						mb.setText("提示");
						mb.setMessage("该连接已存在!");
						mb.open();
						return ;
					}
					Object input = treeViewer.getInput();
					ConnUtil.saveConn(connectModel, DBSystemConstant.CONN_PATH);
					if(input == null)
					{
						if(instance.getChildren() != null)
						{
							treeViewer.setInput(instance);
							treeViewer.refresh();
						}
					}
					else
					{
						treeViewer.refresh();
					}
					shell.close();
				}
			});
			button.setBounds(267, 270, 72, 30);
			button.setText("确定");
		}
		{
			Button button = new Button(shell, SWT.NONE);
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e)
				{
					shell.close();
				}
			});
			button.setBounds(352, 270, 72, 30);
			button.setText("取消");
		}

	}
	
	private ConnectModel getConnectModel()
	{
		try
		{
			ConnectModel connectModel = new ConnectModel();
			GeneralInforBean generalInfor = connectModel.getConnect().getGeneralInfor();
			generalInfor.setConnName(text.getText().trim());
			generalInfor.setHostName(text_1.getText().trim());
			generalInfor.setPort(Integer.parseInt(text_2.getText().trim()));
			generalInfor.setUserName(text_3.getText().trim());
			generalInfor.setPwd(text_4.getText().trim());
			return connectModel;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
