/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     SQLActionGroup.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-31  Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.editors.actions;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionGroup;

import com.java.db.DBActivator;
import com.java.db.conn.ConnControl;


public class SQLActionGroup extends ActionGroup
{
	private ConnControl connControl;
	private Shell shell;
	private String sql;
	private Text txt;
	private String result;
	
	public SQLActionGroup(ConnControl connControl,Text txt)
	{
		this.connControl = connControl;
		this.txt = txt;
		shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		result = "";
	}
	
	public void setSql(String sql)
	{
		this.sql = sql;
	}
	
	public void fillToolBar(ToolBar toolBar)
	{
		ToolBarManager tb = new ToolBarManager(toolBar);
		tb.add(new RunSql());
		tb.update(true);
	}
	
	
	public String getResult()
	{
		return result;
	}
	
	class RunSql extends Action
	{
		public RunSql()
		{
			setText("运行");
			setToolTipText("运行查询语句");
			setImageDescriptor(DBActivator.getImageDescriptor("icons/run.gif"));
			setHoverImageDescriptor(DBActivator.getImageDescriptor("icons/run.gif"));
		}
		
		@Override
		public void run()
		{
			ResultSet rs = null;
			sql = sql.toLowerCase().trim();	// 先转换大小写，方便比较
			String[] sqls = sql.split(";"); // 以分号分割，循环执行
			boolean isSelect = false;
			for (int i = 0; i < sqls.length; i++)
			{
				String mySql = sqls[i].trim();	// 必须去除空行，否则以下将不方便判断
				if(mySql.isEmpty())
				{
					// 空行或者空字符时，不执行
					continue;
				}
				else if(mySql.startsWith("select"))
				{
					rs = connControl.executeQuery(mySql);
					isSelect = true;
				}
				else if(mySql.startsWith("insert") || mySql.startsWith("update") || mySql.startsWith("delete"))
				{
					if(!connControl.executeUpdate(mySql))
					{
						MessageBox mb = new MessageBox(shell);
						mb.setText("提示");
						mb.setMessage("操作有误!");
						mb.open();
						return;
					}
				}
				else
				{
					if(!connControl.execute(mySql))
					{
						MessageBox mb = new MessageBox(shell);
						mb.setText("提示");
						mb.setMessage("操作有误!");
						mb.open();
						return;
					}
				}
			}
			result = "";
			txt.setText("");
			if(rs == null && isSelect)
			{
				MessageBox mb = new MessageBox(shell);
				mb.setText("提示");
				mb.setMessage("查询有误!");
				mb.open();
				return;
			}
			else
			{
				
				ResultSetMetaData rsData;
				try
				{
					rsData = rs.getMetaData();
					int count = rsData.getColumnCount();
					for (int i = 1; i <= count; i++)
					{
						result += rsData.getColumnName(i) + "\t| ";	// 获取列名
					}
					result += "\r\n";
					int length = result.length();
					for (int i = 0; i < length; i++)
					{
						result += "—";
					}
					result += "\r\n";				
					while(rs.next())
					{
						for (int i = 1; i <= count; i++)
						{
							result += rs.getString(i) + "\t| ";	// 获取列名
						}
						result += "\r\n";
					}
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			txt.setText(result);
		}
	}
}
