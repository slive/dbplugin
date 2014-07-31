/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     Conn.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-27   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.conn;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.java.db.model.ConnectModel;
import com.java.db.model.DBModel;
import com.java.db.model.FunModel;
import com.java.db.model.FunModelManager;
import com.java.db.model.PreModel;
import com.java.db.model.PreModelManager;
import com.java.db.model.TBModelManager;
import com.java.db.model.TableModel;
import com.java.db.model.ViewModel;
import com.java.db.model.ViewModelManager;
import com.java.db.model.connectbeans.GeneralInforBean;

/**
 * 连接数据，操作数据库等
 */
public class ConnControl implements Serializable
{
	/**
	 * 默认ID
	 */
	private static final long serialVersionUID = -1978392618854565537L;
	/**
	 * 数据库属性模型，属性——控制
	 */
	private ConnectModel connModel;
	private boolean isConnected; 	// 连接状态
	private Connection conn;
	private Statement statememt;			// 数据集合

	public ConnControl(ConnectModel conn)
	{
		this.connModel = conn;
	}
	
	/**
	 * 获取数据库属性模型
	 */
	public ConnectModel getConnModel()
	{
		return connModel;
	}
	
	/**
	 * 获取连接
	 */
	public Connection getConn()
	{
		return conn;
	}
	
	/**
	 * 连接状态，当为已连接上时，对外不再允许修改连接属性
	 * @return
	 */
	public boolean isConnected()
	{
		return isConnected;
	}
	
	/**
	 * 获取Statement
	 * @return
	 */
	public Statement getStatememt()
	{
		return statememt;
	}
	
	/**
	 * 启动连接
	 * @return
	 */
	public boolean openConn()
	{
		if(isConnected)
		{
			return false;	// 已经启动，不需要重新启动
		}
		else
		{
			//先关闭
			closeConn();
			try
			{
				Class.forName("com.mysql.jdbc.Driver");	// 1. 注册驱动
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return false;
			}
			// Mysql 的驱动
			GeneralInforBean generalInfor = connModel.getConnect().getGeneralInfor();
			try
			{
				conn = DriverManager.getConnection("jdbc:mysql://" 
						+ generalInfor.getHostName() + ":" + generalInfor.getPort(),
						generalInfor.getUserName(),generalInfor.getPwd());
				statememt = conn.createStatement();
				refreshConn();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				closeConn();	// 关闭，防止泄露
				return false;
			}	
			isConnected = true;
			return true;
		}
	}
	
	/**
	 * 刷新连接
	 */
	public void refreshConn()
	{
		String[][] strings = getShowInfor("show databases", null);
		if(strings != null)
		{
			connModel.removeAll();
			for (int i = 0; i < strings.length; i++)
			{
				DBModel dbModel = new DBModel(strings[i][0]);
				connModel.add(dbModel);
				refreshDB(dbModel);
			}
		}
	}
	/**
	 * 刷新数据库
	 */
	public void refreshDB(DBModel dbModel)
	{
		refreshTB(dbModel.getTbManger());	// 刷新表格
		refreshVB(dbModel.getViewManger());	// 刷新视图等等；
		refreshPre(dbModel.getPreModelManager());	// 刷新存储过程
		refreshFun(dbModel.getFunModelManager());	// 刷新函数
	}
	
	/**
	 * 刷新表格
	 */
	public void refreshTB(TBModelManager tbManager)
	{
		String[][] strings = getShowInfor("show full tables from " 
				+ tbManager.getParent().getName(), tbManager.getParent());
		if(strings != null)
		{
			tbManager.removeAll();
			for (int j = 0; j < strings.length; j++)
			{
				// 过滤view
				if(!strings[j][1].equalsIgnoreCase("view"))
				{
					tbManager.add(new TableModel(strings[j][0]));
				}
			}
		}
	}
	
	/**
	 * 刷新视图
	 */
	public void refreshVB(ViewModelManager vbManager)
	{
		String[][] strings = getShowInfor("show full tables from " + 
				vbManager.getParent().getName(), vbManager.getParent());
		if(strings != null)
		{
			vbManager.removeAll();
			for (int j = 0; j < strings.length; j++)
			{
				// 过滤table
				if(strings[j][1].equalsIgnoreCase("view"))
				{
					vbManager.add(new ViewModel(strings[j][0]));
				}
			}
		}
	}
	
	/**
	 * 刷新存储过程
	 */
	public void refreshPre(PreModelManager preManager)
	{
		String[][] strings = getShowInfor("show procedure status", preManager.getParent());
		if(strings != null)
		{
			preManager.removeAll();
			for (int j = 0; j < strings.length; j++)
			{
				// 过滤数据库，只有属于所在数据库时，才添加
				if(preManager.getParent().getName().equals(strings[j][0]))
				{
					String name = strings[j][1];
					preManager.add(new PreModel(name));
				}
			}
		}
	}
	
	/**
	 * 刷新函数
	 */
	public void refreshFun(FunModelManager funManager)
	{
		String[][] strings = getShowInfor("show function status", funManager.getParent());
		if(strings != null)
		{
			funManager.removeAll();
			for (int j = 0; j < strings.length; j++)
			{
				// 过滤数据库，只有属于所在数据库时，才添加
				if(funManager.getParent().getName().equals(strings[j][0]))
				{
					String name = strings[j][1];
					funManager.add(new FunModel(name));
				}
			}
		}
	}
	
	/**
	 * 执行sql语句
	 * @param sql
	 * @return
	 */
	public boolean execute(String sql)
	{
		if(statememt == null)
		{
			return false;
		}
		else
		{
			try
			{
				// 如果第一个结果为 ResultSet 对象，则返回 true；如果其为更新计数或者不存在任何结果，则返回 false 
				statememt.execute(sql);
				return true;
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				return false;
			}
		}
	}
	
	/**
	 * 返回查询结果（常用）
	 * @param sql
	 * @return 为null时代表查询失败
	 */
	public ResultSet executeQuery(String sql)
	{
		if(statememt == null)
		{
			return null;
		}
		else
		{
			try
			{
				return statememt.executeQuery(sql);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			return null;
		}
	}
	
	/**
	 * 更新语句，如insert update del等
	 * @param sql
	 * @return 
	 */
	public boolean executeUpdate(String sql) 
	{
		if(statememt == null)
		{
			return false;
		}
		else
		{
			try
			{
				// INSERT、UPDATE 或 DELETE 语句的行计数；或者 0，表示不返回任何内容的 SQL 语句
				return (statememt.executeUpdate(sql) >= 0) ?true:false;
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * 返回show 命令查询信息
	 * @param dbModel 数据库模型，未空时代表不切换数据库
	 * @return
	 */
	private String[][] getShowInfor(String string, DBModel dbModel)
	{
		if(statememt == null)
		{
			return null;
		}
		else
		{
			try
			{
				ArrayList<String[]> al = new ArrayList<String[]>();
				if(dbModel != null)
				{
					execute("use " + dbModel.getName());
				}
				ResultSet rs = statememt.executeQuery(string);
				while(rs.next())
				{
					try
					{
						int columnCount = rs.getMetaData().getColumnCount();
						String[] strings = new String[columnCount];
						for (int i = 0; i < strings.length; i++)
						{
							strings[i] = rs.getString(i + 1);
						}
						al.add(strings);
					}
					catch (SQLException e)
					{
						e.printStackTrace();
					}
				}
				if(al.size() <= 0)
				{
					return null;
				}
				String[][] dataBases = new String[al.size()][2];
				return al.toArray(dataBases);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				return null;
			}
		}
	}
	
	/**
	 * 关闭连接
	 */
	public void closeConn()
	{
		try
		{
			connModel.removeAll();
			if(statememt != null)
			{
				statememt.close();
			}
		}
		catch (SQLException e)
		{
		}
		try
		{
			if(conn != null)
			{
				conn.close();
			}
		}
		catch (SQLException e)
		{
		}
		isConnected = false;
		statememt = null;
		conn = null;
	}
	
	/**
	 * 测试连接
	 * @param connectModel
	 * @return
	 */
	public static boolean TestConn(ConnectModel connectModel)
	{
		System.out.println(connectModel);
		if(connectModel == null)
		{
			return false;
		}
		// 1. 注册驱动，是否每次都需要注册？
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		try
		{
			GeneralInforBean generalInfor = connectModel.getConnect().getGeneralInfor();
			Connection conn = DriverManager.getConnection("jdbc:mysql://" 
					+ generalInfor.getHostName() + ":" + generalInfor.getPort(),
					generalInfor.getUserName(),generalInfor.getPwd());
			if(!conn.isClosed())
			{
				conn.close();	// 关闭
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}	
		return true;
	}
}
