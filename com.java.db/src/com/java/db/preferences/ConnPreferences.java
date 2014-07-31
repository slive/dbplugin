
package com.java.db.preferences;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.java.db.DBActivator;
import com.java.db.util.DBSystemConstant;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class ConnPreferences extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage
{

	public ConnPreferences()
	{
		super(GRID);
		setPreferenceStore(DBActivator.getDefault().getPreferenceStore());
		setDescription("管理用户的连接");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors()
	{
		addField(new DirectoryFieldEditor(PreferenceConstants.P_PATH,
				"连接存储路径:", getFieldEditorParent()));
	}
	
	@Override
	protected void performApply()
	{
		super.performApply();
		DBSystemConstant.CONN_PATH = DBActivator.getDefault().
		getPreferenceStore().getString(PreferenceConstants.P_PATH);
	}
	
	@Override
	protected void performDefaults()
	{
		super.performDefaults();
		DBSystemConstant.CONN_PATH = DBActivator.getDefault().
		getPreferenceStore().getDefaultString(PreferenceConstants.P_PATH);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench)
	{
	}

}