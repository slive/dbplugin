/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     ConnUtil.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-26   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.model.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.java.db.model.ConnectModel;
import com.java.db.model.ConnectModelManager;
import com.java.db.model.connectbeans.ConnectBean;


public class ConnUtil
{
	/**
	 * 读取连接
	 * @param path
	 */
	public static void loadConn(String path)
	{
		try
		{
			File file = new File(path);
			File[] listFiles = file.listFiles();
			for (int i = 0; i < listFiles.length; i++)
			{
				try
				{
					// 过滤
					if(listFiles[i].getName().indexOf(".xml") == -1)
					{
						continue;
					}
					ObjectInputStream ob = new ObjectInputStream(new FileInputStream(listFiles[i]));
					ConnectBean readObject = (ConnectBean)ob.readObject();
					ConnectModel connectModel = new ConnectModel();
					connectModel.setConnect(readObject);
					ConnectModelManager.getInstance().add(connectModel);
					ob.close();
				}
				catch (Exception e)
				{
					// 出错暂时不处理
					e.printStackTrace();
				}
			}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
	}
	
	/**
	 * 保存连接
	 * @param connectModel
	 * @param path
	 * @return
	 */
	public static boolean saveConn(ConnectModel connectModel,String path)
	{
		try
		{
			// xml格式
			String string = path + "/" + connectModel.getName().trim() + ".xml";
			File file = new File(string);
			if(!file.exists())
			{
				file.getParentFile().mkdirs();
			}
			ObjectOutputStream oos = new ObjectOutputStream(
			new FileOutputStream(string));
			oos.writeObject(connectModel.getConnect());
			oos.flush();
			oos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean removeConn(ConnectModel connectModel,String path)
	{
		try
		{
			// xml格式
			String string = path + "/" + connectModel.getName().trim() + ".xml";
			File file = new File(string);
			if(file.exists())
			{
				file.delete();
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
