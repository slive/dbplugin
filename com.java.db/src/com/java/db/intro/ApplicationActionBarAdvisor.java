
package com.java.db.intro;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import com.java.db.actions.OpenConnectView;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor
{

	private IWorkbenchAction introAction;
	private OpenConnectView openConnectView;
	
	// window actions
	private IContributionItem wOpenPerspectives;	// 打开透视图
	private IContributionItem wShowViews;			// 打开view列表
	private IWorkbenchAction wPreferences;			// 打开首选项

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer)
	{
		super(configurer);
	}

	protected void makeActions(IWorkbenchWindow window)
	{
		introAction = ActionFactory.INTRO.create(window);
		register(introAction);
		
		openConnectView = new OpenConnectView();
		
		// 初始化&Window菜单中的actions
		wOpenPerspectives = ContributionItemFactory.PERSPECTIVES_SHORTLIST.create(window);
		wShowViews = ContributionItemFactory.VIEWS_SHORTLIST.create(window);
		wPreferences = ActionFactory.PREFERENCES.create(window);
		wPreferences.setText("首选项");
		register(wPreferences);
	}

	protected void fillMenuBar(IMenuManager menuBar)
	{
		// 文件菜单
		MenuManager fileMenu = new MenuManager("&文件",IWorkbenchActionConstants.M_FILE);
		menuBar.add(fileMenu);
		
		// 工具菜单
		MenuManager toolMenu = new MenuManager("&工具");
		menuBar.add(toolMenu);
		toolMenu.add(openConnectView);
		
		// windowMenu
		MenuManager windowMenu = new MenuManager("&窗口",IWorkbenchActionConstants.M_WINDOW);
		menuBar.add(windowMenu);
		MenuManager perspectiveMenu = new MenuManager("打开 &透视图");
		windowMenu.add(perspectiveMenu);
		perspectiveMenu.add(wOpenPerspectives);
		MenuManager showViewMenu = new MenuManager("显示 &View");
		windowMenu.add(showViewMenu);
		showViewMenu.add(wShowViews);
		windowMenu.add(wPreferences);
		
		// 帮助菜单
		MenuManager helpMenu = new MenuManager("&帮助");
		menuBar.add(helpMenu);

		// Help
		helpMenu.add(introAction);
	}

}
