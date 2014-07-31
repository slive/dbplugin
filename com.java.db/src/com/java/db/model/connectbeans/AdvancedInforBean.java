/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     AdvancedInforBean.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-26   Slive      V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.model.connectbeans;

import java.io.Serializable;

/*********************
 * Advanced Information 设置保存路径 编码 保持连接间隔 (秒) 使用 MySQL 字符集 使用压缩 自动连接 使用高级连接
 **********************/
public class AdvancedInforBean implements Serializable
{
	/**
	 * general VersionID
	 */
	private static final long serialVersionUID = 7176219491142823310L;

	/*********************
	 * Advanced Information
	 * 设置保存路径 编码 保持连接间隔 (秒) 使用 MySQL 字符集 使用压缩 自动连接 使用高级连接
	 **********************/
	private String savePath;		// 保存路径
	private String characterCode;	// 字符编码
	private boolean isUseMySQLCode; // 使用SQL编码，与字符编码相关
	private int keepConnTime;		// 保存连接间隔（秒）
	private boolean isCompress;		// 是否压缩
	private boolean isAutoConn;		// 是否自动连接
	private boolean isUserAdvancedConn; // 是否使用高级连接
	
	/**
	 * @description 构造函数，初始化
	 */
	public AdvancedInforBean()
	{
		savePath = "C:/MySQL";
		characterCode = "65001 (UTF-8)";
		isUseMySQLCode = false;
		keepConnTime = -1;
		isCompress = false;
		isAutoConn = false;
		isUserAdvancedConn = false;
	}

	
	public String getSavePath()
	{
		return savePath;
	}

	
	public void setSavePath(String savePath)
	{
		this.savePath = savePath;
	}

	
	public String getCharacterCode()
	{
		return characterCode;
	}

	
	public void setCharacterCode(String characterCode)
	{
		this.characterCode = characterCode;
	}

	
	public boolean isUseMySQLCode()
	{
		return isUseMySQLCode;
	}

	
	public void setUseMySQLCode(boolean isUseMySQLCode)
	{
		this.isUseMySQLCode = isUseMySQLCode;
	}

	
	public int getKeepConnTime()
	{
		return keepConnTime;
	}
	
	public void setKeepConnTime(int keepConnTime)
	{
		this.keepConnTime = keepConnTime;
	}

	public boolean isCompress()
	{
		return isCompress;
	}
	
	public void setCompress(boolean isCompress)
	{
		this.isCompress = isCompress;
	}

	
	public boolean isAutoConn()
	{
		return isAutoConn;
	}

	
	public void setAutoConn(boolean isAutoConn)
	{
		this.isAutoConn = isAutoConn;
	}
	
	public boolean isUserAdvancedConn()
	{
		return isUserAdvancedConn;
	}
	
	public void setUserAdvancedConn(boolean isUserAdvancedConn)
	{
		this.isUserAdvancedConn = isUserAdvancedConn;
	}
}
