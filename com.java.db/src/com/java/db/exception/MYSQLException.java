/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     MYSQLException.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-28   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.exception;

import java.sql.SQLException;


public class MYSQLException extends SQLException
{
	private static final long serialVersionUID = 4309102375324044511L;
	
	public MYSQLException()
	{
		super();
	}
	
	public MYSQLException(String msg)
	{
		super(msg);
	}
	
	public void tootipe()
	{
//		MessageBox mb = new MessageBox(activeShell);
//		mb.setText("提示");
//		mb.setMessage("删除失败!");
//		mb.open();
//		getMessage();
	}
}
