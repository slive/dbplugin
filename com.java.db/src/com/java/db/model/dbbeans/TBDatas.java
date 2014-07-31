/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     DBFiled.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-31   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.model.dbbeans;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 存放查询表或者视图对应的数据
 */
public class TBDatas
{
	private Map<String, ArrayList<String>> result;
	private int pageSize;
	private int count;
	private int nowPage;
	
	/**
	 * @description
	 * @param count 查询结果大小，结果应该控制在大于0
	 */
	public TBDatas()
	{
		result = new LinkedHashMap<String, ArrayList<String>>();
		pageSize = -1;
		nowPage = 1;
	}
	
	/**
	 * 设置每一页page的大小，不能大于总数目
	 * @param pageSize
	 */
	public boolean setPageSize(int pageSize)
	{
		if(pageSize == getPageSize())
		{
			return false;
		}
		else if(pageSize >= getCount() || pageSize <= 0)
		{
			this.pageSize = getCount();
			return true;
		}
		else
		{
			this.pageSize = pageSize;
			return true;
		}
		
	}
	
	/**
	 * 获取每一页page的大小
	 * @return
	 */
	public int getPageSize()
	{
		return pageSize;
	}
	
	/**
	 * 获取总页数
	 * @return
	 */
	public int getPageCount()
	{
		if(pageSize == -1)
		{
			return 1;
		}
		else
		{
			return getCount()/pageSize + ((getCount()%pageSize) > 0 ? 1 : 0);
		}
	}
	
	public void add(String cloName,ArrayList<String> str)
	{
		result.put(cloName, str);
	}
	
	public void clear()
	{
		result.clear();
	}
	
	public String[] getColNames()
	{
		int size = result.keySet().size();
		if(size <= 0)
		{
			return null;
		}
		String[] str = new String[size];
		return result.keySet().toArray(str);
	}
	
	private String getColName(int index)
	{
		String[] str = getColNames();
		if(str == null)
		{
			return null;
		}
		else
		{
			try
			{
				return str[index];
			}
			catch (Exception e)
			{
				return null;
			}
		}
	}
	
	/**
	 * 获取每一栏的返回值
	 * @param index	栏索引
	 * @return
	 */
	private String[] getResultCol(int index)
	{
		String colName = getColName(index);
		if(colName == null)
		{
			return null;
		}
		ArrayList<String> arrayList = result.get(colName);
		if(arrayList.size() <= 0)
		{
			return null;
		}
		String[] str = new String[arrayList.size()];
		return arrayList.toArray(str);
	}
	
	/**
	 * 获取每一栏的返回值
	 * @param index	栏索引
	 * @return
	 */
	private String[] getResultCol(int index,int page)
	{
		String colName = getColName(index);
		if(colName == null)
		{
			return null;
		}
		List<String> arrayList = null;
		ArrayList<String> al = result.get(colName);
		if(pageSize <= 0)
		{
			arrayList = al;
		}
		else
		{
			int from = (page - 1)* pageSize;
			int to = (page* pageSize);
			if(al.size() < to)		// 判断是否溢出
			{
				to = al.size();
			}
			arrayList = al.subList(from, to);
		}
		if(arrayList.size() <= 0)
		{
			return null;
		}
		String[] str = new String[arrayList.size()];
		return arrayList.toArray(str);
	}
	
	/**
	 * 获取所有的结果
	 * @return
	 */
	public String[][] getResults()
	{
		int size = result.size();
		if(size <= 0)
		{
			return null;
		}
		String[][] str = new String[getResultCol(0).length][size];
		for (int i = 0; i < size; i++)
		{
			String[] resultCol = getResultCol(i);
			for (int j = 0; j < resultCol.length; j++)
			{
				str[j][i] = resultCol[j];
			}
		}
		return str;
	}

	
	public void setCount(int count)
	{
		this.count = count;
	}
	
	/**
	 * 获取数目
	 * @return
	 */
	public int getCount()
	{
		return count;
	}
	/**
	 * 根据页数获取结果
	 * @param page 如果page <= 0 都返回第一页
	 * @return
	 */
	public String[][] getResults(int page)
	{
		nowPage = 1;
		if(page >= 1 && page <= getPageCount())
		{
			nowPage = page;
		}
		else if(page > getPageCount())
		{
			nowPage = getPageCount();
		}
		
		int size = result.size();
		if(size <= 0)
		{
			return null;
		}
		int length = getResultCol(0,nowPage).length;	// 最后一页的长度
		String[][] str = new String[length][size];
		for (int i = 0; i < size; i++)
		{
			String[] resultCol = getResultCol(i,nowPage);
			for (int j = 0; j < resultCol.length; j++)
			{
				str[j][i] = resultCol[j];
			}
		}
		return str;
	}
	
	/**
	 * 获取当前页
	 * @return
	 */
	public int getNowPage()
	{
		return nowPage;
	}
}
