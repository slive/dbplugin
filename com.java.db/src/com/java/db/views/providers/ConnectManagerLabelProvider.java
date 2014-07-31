/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     ConnectMangerLabelProvider.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-26   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.views.providers;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.java.db.DBActivator;
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


/**
 * 连接管理treeLabelProvider
 */
public class ConnectManagerLabelProvider implements ILabelProvider
{
	private Image imageConn = DBActivator.getImageDescriptor("icons/connect.gif").createImage();
	private Image imageDB = DBActivator.getImageDescriptor("icons/database.gif").createImage();
	private Image imageTBManager = DBActivator.getImageDescriptor("icons/tables.gif").createImage();
	private Image imageTable = DBActivator.getImageDescriptor("icons/table.gif").createImage();
	private Image imageViewManger = DBActivator.getImageDescriptor("icons/views.gif").createImage();
	private Image imageView = DBActivator.getImageDescriptor("icons/view.gif").createImage();
	private Image imagePreManager = DBActivator.getImageDescriptor("icons/procedures.gif").createImage();
	private Image imagePre = DBActivator.getImageDescriptor("icons/procedure.gif").createImage();
	private Image imageFunManger = DBActivator.getImageDescriptor("icons/functions.gif").createImage();
	private Image imageFun = DBActivator.getImageDescriptor("icons/function.gif").createImage();
	
	
	@Override
	public Image getImage(Object element)
	{
	    if(element instanceof ConnectModel)
		{
			return imageConn;	// 返回连接图片
		}
		else if(element instanceof DBModel)
		{
			return imageDB;		// 返回数据库图片
		}
		else if(element instanceof TBModelManager)
		{
			return imageTBManager;
		}
		else if(element instanceof ViewModelManager)
		{
			return imageViewManger;
		}
		else if(element instanceof PreModelManager)
		{
			return imagePreManager;
		}
		else if(element instanceof FunModelManager)
		{
			return imageFunManger;
		}
		else if(element instanceof TableModel)
		{
			return imageTable;
		}
		else if(element instanceof ViewModel)
		{
			return imageView;
		}
		else if(element instanceof PreModel)
		{
			return imagePre;
		}
		else if(element instanceof FunModel)
		{
			return imageFun;
		}
		return null;
	}

	@Override
	public String getText(Object element)
	{
		if(element instanceof ConnectModel)
		{
			ConnectModel connectModel = (ConnectModel)element;
			return connectModel.getName();
		}
		else if(element instanceof DBModel)
		{
			DBModel dModel = (DBModel) element;
			return dModel.getName();
		}
		else if(element instanceof TBModelManager)
		{
			return ((TBModelManager)element).getName();
		}
		else if(element instanceof ViewModelManager)
		{
			return ((ViewModelManager)element).getName();
		}
		else if(element instanceof PreModelManager)
		{
			return ((PreModelManager)element).getName();
		}
		else if(element instanceof FunModelManager)
		{
			return ((FunModelManager)element).getName();
		}
		else if(element instanceof TableModel)
		{
			return ((TableModel)element).getName();
		}
		else if(element instanceof ViewModel)
		{
			return ((ViewModel)element).getName();
		}
		else if(element instanceof PreModel)
		{
			return ((PreModel)element).getName();
		}
		else if(element instanceof FunModel)
		{
			return ((FunModel)element).getName();
		}
		return null;
	}

	@Override
	public void addListener(ILabelProviderListener arg0)
	{
		
	}

	@Override
	public void dispose()
	{
		imageConn.dispose();
		imageDB.dispose();
		imageTable.dispose();
		imageView.dispose();
		imagePre.dispose();
		imageFun.dispose();
		imageTBManager.dispose();
		imageViewManger.dispose();
		imagePreManager.dispose();
		imageFunManger.dispose();
	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0)
	{
		// TODO Auto-generated method stub
		
	}

}
