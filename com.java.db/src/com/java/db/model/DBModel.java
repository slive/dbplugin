/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     DBModel.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-26   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.model;

import com.java.db.model.dbbeans.DBBean;


/**
 * 数据库模型，建立在数据库bean之上，提供管理数据库方法<br>
 * 处于
 * @see ConnectModel
 *
 */
public class DBModel implements IBase<ConnectModel>
{
	private DBBean db;
	private ConnectModel parent;
	private TBModelManager tbManger;		// 对应管理表格模型，固定的
	private ViewModelManager viewManger; 	// 对应视图模型，固定的
	private PreModelManager preModelManager;// 存储过程
	private FunModelManager funModelManager;// 函数
	
	public DBModel(String dbName)
	{
		db = new DBBean(dbName.trim());
		tbManger = new TBModelManager();
		tbManger.setParent(this);	// 设置父节点
		viewManger = new ViewModelManager();
		viewManger.setParent(this);
		preModelManager = new PreModelManager();
		preModelManager.setParent(this);
		funModelManager = new FunModelManager();
		funModelManager.setParent(this);
	}
	
	public DBBean getDb()
	{
		return db;
	}
	
	public TBModelManager getTbManger()
	{
		return tbManger;
	}
	
	public ViewModelManager getViewManger()
	{
		return viewManger;
	}
	
	public PreModelManager getPreModelManager()
	{
		return preModelManager;
	}
	
	public FunModelManager getFunModelManager()
	{
		return funModelManager;
	}
	
	public ConnectModel getParent()
	{
		return parent;
	}
	
	public void setParent(ConnectModel parent)
	{
		this.parent = parent;
	}
	
	/**
	 * 返回子节点
	 * @return
	 */
	public Object[] getChildren()
	{
		// 各个管理节点
		return new Object[]{tbManger,viewManger,preModelManager,funModelManager};
	}
	
	@Override
	public String getName()
	{
		return db.getDBName();
	}
}
