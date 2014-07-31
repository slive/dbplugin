/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     DBSystemConstant.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-31   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.util;

import com.java.db.DBActivator;
import com.java.db.preferences.PreferenceConstants;


public class DBSystemConstant
{
	public static String CONN_PATH = null;
	static
	{
		CONN_PATH = DBActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH);
	}
}
