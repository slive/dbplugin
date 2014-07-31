/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     SQLEditor.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-28   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.java.db.conn.ConnControl;
import com.java.db.editors.actions.SQLActionGroup;
import com.java.db.model.ConnectModel;
import com.java.db.model.DBModel;
import com.swtdesigner.SWTResourceManager;


public class SQLEditor extends EditorPart
{

	public static final String ID = "com.java.db.editors.SQLEditor"; //$NON-NLS-1$
	private StyledText styleTxt;
	private ConnControl connControl;
	private ConnectModel connModel;
	private Text txt;
	private ToolBar toolBar;
	private SQLActionGroup sqGroup;
	private Label label_1;

	public SQLEditor()
	{
	}

	/**
	 * Create contents of the editor part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent)
	{
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(3, false));
		{
			toolBar = new ToolBar(container, SWT.FLAT | SWT.RIGHT);
			toolBar.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		}
		{
			Label label = new Label(container, SWT.NONE);
			label.setText("选择数据库");
		}
		final Combo combo = new Combo(container, SWT.NONE);
		{
			GridData gridData = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
			gridData.widthHint = 120;
			combo.setLayoutData(gridData);
		}
		combo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				String text2 = combo.getText();
				if(text2 != null)
				{
					connControl.execute("use " + text2);
				}
			}
		});
		{
			styleTxt = new StyledText(container, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
			styleTxt.setFont(SWTResourceManager.getFont("Times New Roman", 11, SWT.NORMAL));
			styleTxt.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent arg0) 
				{
					sqGroup.setSql(styleTxt.getText());
				}
			});
			styleTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 2));
		}
		{
			label_1 = new Label(container, SWT.NONE);
			label_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			label_1.setText("* 查询结果：");
		}
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		{
			txt = new Text(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
			txt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		}
		
		sqGroup = new SQLActionGroup(connModel.getConnControl(),txt);
		sqGroup.fillToolBar(toolBar);
		
		{   // 添加数据库
			DBModel[] children = connModel.getChildren();
			if(children != null)
			{
				for (int i = 0; i < children.length; i++)
				{
					combo.add(children[i].getName());
				}
				
				try
				{
					combo.setVisibleItemCount(combo.getItemCount());
					combo.select(combo.getItemCount() - 2);
					connControl.execute("use " + combo.getText().trim()); // 选用数据库
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void setFocus()
	{
		// Set the focus
	}

	@Override
	public void doSave(IProgressMonitor monitor)
	{
		// Do the Save operation
	}

	@Override
	public void doSaveAs()
	{
		// Do the Save As operation
	}

	/**
	 * 需要手动初始化
	 * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException
	{
		// 初始化
		if(input instanceof SQLInput)
		{
			connControl = ((SQLInput)input).getConnControl();
			connModel = connControl.getConnModel();
			
			// 两者都不可缺少
			setSite(site);	
			setInput(input);
			setTitle(input.getName());
		}
	}

	@Override
	public boolean isDirty()
	{
		return false;
	}

	@Override
	public boolean isSaveAsAllowed()
	{
		return false;
	}
}
