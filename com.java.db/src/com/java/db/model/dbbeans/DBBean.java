/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     DBBean.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-26   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.model.dbbeans;


public class DBBean
{
	private String DBName;			// 数据库名称
	private String characterCode;	// 字符集
	private String sortRule;	    // 排序规则
	
	public DBBean(String dbName)
	{
		DBName = dbName;
		characterCode = "";
		sortRule = "";
	}

	
	public String getCharacterCode()
	{
		return characterCode;
	}

	
	public void setCharacterCode(String characterCode)
	{
		this.characterCode = characterCode;
	}

	
	public String getSortRule()
	{
		return sortRule;
	}

	
	public void setSortRule(String sortRule)
	{
		this.sortRule = sortRule;
	}

	
	public String getDBName()
	{
		return DBName;
	}
	
	
}
