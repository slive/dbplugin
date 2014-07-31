/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     GeneralInforBean.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-26   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.model.connectbeans;

import java.io.Serializable;


public class GeneralInforBean implements Serializable
{
	/**
	 * general VersionID
	 */
	private static final long serialVersionUID = 1L;
	/*********************
	 * General Information 
	 * 服务器类型 连接名 主机名或 IP 地址 端口 用户名 连接状态 是否保存用户密码
	 ********************/
	private String DBType;			// 服务器类型
	private String connName;		// 连接名
	private String hostName;		// 主机名或 IP 地址
	private String userName;		// 用户名
	private String pwd;				// 用户密码
	private boolean isSavePwd;		// 是否保存用户密码
	private int port;				// 端口
	
	/**
	 * @description 构造函数，初始化各个数据
	 */
	public GeneralInforBean()
	{
		// 初始
		DBType = "";
		connName = "";
		hostName = "";
		userName = "";
		port = -1;
	}
	
	public String getDBType()
	{
		return DBType;
	}
	
	public void setDBType(String type)
	{
		DBType = type;
	}
	
	public String getConnName()
	{
		return connName;
	}
	
	public void setConnName(String connName)
	{
		this.connName = connName;
	}
	
	public String getHostName()
	{
		return hostName;
	}
	
	public void setHostName(String hostName)
	{
		this.hostName = hostName;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public int getPort()
	{
		return port;
	}
	
	public void setPort(int port)
	{
		this.port = port;
	}
	
	public boolean isSavePwd()
	{
		return isSavePwd;
	}
	
	public void setSavePwd(boolean isSavePwd)
	{
		this.isSavePwd = isSavePwd;
	}

	
	public String getPwd()
	{
		return pwd;
	}
	
	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}
	
}
