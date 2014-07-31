/**
 * =============================================================================
 * @copyright    Copyright 2010
 * @filename     JavaCmd.java
 * @description  模拟cmd的类
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2010-10-9   梁立平    V001.00		创建文件
 *
 * ============================================================================= 
 */
package com.java.db.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 模拟cmd的类
 * 
 */
public class JavaCmd
{
	public static Process execCmd(String command)
	{ 
		String user_dir = System.getProperty("user.dir");
		// 执行cmd命令的方法
		JavaCmd.changDir(command);
		Runtime run = Runtime.getRuntime();
		Process pro = null;
		try
		{
			pro = run.exec("cmd /c" + command, null, new File(System
					.getProperty("user.dir")));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.setProperty("user.dir", user_dir);	// 还原用户目录
		return pro;
	}

	public static void showResult(Process pro)
	{ 
		// 回显命令执行结果的方法
		InputStream is = pro.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String s = null;
		try
		{
			s = br.readLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		while (s != null)
		{
			try
			{
				s = br.readLine();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}
//		showResult(pro);
	}

	public static void showWindow()
	{ 
		// 显示cmd窗口
		System.out.println("Microsoft Windows XP [版本 5.1.2600]");
		System.out.println("<c> 版权所有 1985-2001 Microsoft 	Corp.");
		String strHome = System.getProperty("user.home");
		System.setProperty("user.dir", strHome);
		String strDir = System.getProperty("user.dir");
		System.out.print(strDir + ">");
	}

	public static void changDir(String str)
	{ 
		// 转换目录的方法
		if (str.indexOf("dir") < 0 && str.endsWith(":"))
		{
			System.setProperty("user.dir", str + "\\");
		}
		else if (str.indexOf("cd") >= 0 && str.indexOf(":") >= 0)
		{
			int i = 3;
			String str1 = str.substring(i);
			System.setProperty("user.dir", str1);
		}
		else if (str.indexOf("cd") >= 0 && str.indexOf(":") < 0)
		{
			String dir = System.getProperty("user.dir");
			String temp = dir.substring(0, 2);
			String tempStr = str.substring(3);
			System.setProperty("user.dir", temp + tempStr);
		}
		else if (str.indexOf("cd /") == 0)
		{
			String dir = System.getProperty("user.dir");
			String dirTmp = dir.substring(0, 3);
			System.setProperty("user.dir", dirTmp);
		}
		else
		{

		}
	}
	
	public static void main(String[] args)
	{
		showWindow();
		Process execCmd = execCmd("dir d:");
		System.out.println(execCmd);
		if(execCmd != null)
		{
			showResult(execCmd);
		}
		
	}
}
