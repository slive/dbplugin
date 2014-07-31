package com.java.db.intro;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.java.db.views.ConnectManagerView;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) 
	{
		String area = layout.getEditorArea();
		layout.addStandaloneView(ConnectManagerView.ID, true, IPageLayout.LEFT, 0.25f, area);
	}
}
