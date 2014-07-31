
package com.java.db.intro;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import com.java.db.model.ConnectModel;
import com.java.db.model.ConnectModelManager;
import com.java.db.model.util.ConnUtil;
import com.java.db.util.DBSystemConstant;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor
{

	public ApplicationWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer)
	{
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer)
	{
		return new ApplicationActionBarAdvisor(configurer);
	}

	public void preWindowOpen()
	{
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(800, 600));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(true);
		configurer.setTitle("DB");
		
		// 加载连接文件
		ConnUtil.loadConn(DBSystemConstant.CONN_PATH);
	}
	
	@Override
	public boolean preWindowShellClose()
	{
		// 关闭前保存
		ConnectModel[] connectModels = ConnectModelManager.getInstance().getChildren();
		if(connectModels != null)
		{
			for (int i = 0; i < connectModels.length; i++)
			{
				ConnUtil.saveConn(connectModels[i],DBSystemConstant.CONN_PATH);
			}
		}
		return super.preWindowShellClose();
	}
}
