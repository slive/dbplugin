
package com.java.db.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.SHELLEXECUTEINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main
{

	private Shell sShell = null;
	// @jve:decl-index=0:visual-constraint="10,10"
	private Composite composite = null;
	private Button button = null;

	/**
	 * * This method initializes composite *
	 */
	private void createComposite()
	{
		sShell.setLayout(new GridLayout(1, false));

		composite = new Composite(sShell, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
	}

	/**
	 * * @param args
	 */
	public static void main(String[] args)
	{
		Display display = Display.getDefault();
		Main thisClass = new Main();
		thisClass.createSShell();
		thisClass.sShell.open();
		while (!thisClass.sShell.isDisposed())
		{
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	/**
	 * * This method initializes sShell
	 */
	private void createSShell()
	{
		sShell = new Shell();
		sShell.setText("Shell");
		createComposite();
		sShell.setSize(new Point(434, 270));
		button = new Button(sShell, SWT.NONE);
		{
			GridData gridData = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
			gridData.widthHint = 102;
			button.setLayoutData(gridData);
		}
		button.setText("启动");
		button
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
				{

					@Override
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e)
					{
						try
						{
							startCMD();
//							startNotePad();
						}
						catch (Exception e1)
						{
							e1.printStackTrace();
						}
					}
				});
	}

	private void executeProg(String fileName) throws Exception
	{
		int hHeap = OS.GetProcessHeap();
		TCHAR buffer = new TCHAR(0, fileName, true);
		int byteCount = buffer.length() * TCHAR.sizeof;
		int lpFile = OS.HeapAlloc(hHeap, OS.HEAP_ZERO_MEMORY, byteCount);
		OS.MoveMemory(lpFile, buffer, byteCount);
		SHELLEXECUTEINFO info = new SHELLEXECUTEINFO();
		info.cbSize = SHELLEXECUTEINFO.sizeof;
		info.lpFile = lpFile;
		// 隐藏启动
		info.nShow = OS.SW_HIDE;
		boolean result = OS.ShellExecuteEx(info);
		if (lpFile != 0)
			OS.HeapFree(hHeap, 0, lpFile);
		if (result == false)
			throw new Exception("启动失败!");
	}

	protected void startNotePad() throws Exception
	{
		// "notepad.exe"为待启动的程序名
		executeProg("notepad.exe");
		// 等待NotePad.exe启动并且初始化完毕，需要根据实际情况调整sleep的时间
		Thread.sleep(100);
		// "Notepad"为被嵌套程序窗口的ClassName(Win32级别)，可以使用Spy++等工具查看
		int notepadHwnd = OS.FindWindow(new TCHAR(0, "Notepad", true), null);
		// &~WS_BORDER去掉内嵌程序边框，这样看起来更像一个内嵌的程序。如果需要显示边框，则将这两行代码删除
//		int oldStyle = OS.GetWindowLong(notepadHwnd, OS.GWL_STYLE);
//		OS.SetWindowLong(notepadHwnd, OS.GWL_STYLE, oldStyle & ~OS.WS_BORDER);
		// composite为承载被启动程序的控件
		OS.SetParent(notepadHwnd, composite.handle);
		// 窗口最大化
		OS.SendMessage(notepadHwnd, OS.WM_SYSCOMMAND, OS.SC_MAXIMIZE, 0);
	}

	protected void startCMD() throws Exception
	{
		// "notepad.exe"为待启动的程序名
		executeProg("cmd.exe");
		// 等待NotePad.exe启动并且初始化完毕，需要根据实际情况调整sleep的时间
		Thread.sleep(100);
		// "Notepad"为被嵌套程序窗口的ClassName(Win32级别)，可以使用Spy++等工具查看
		int notepadHwnd = OS.FindWindow(
				new TCHAR(0, "ConsoleWindowClass", true), null);
		// &~WS_BORDER去掉内嵌程序边框，这样看起来更像一个内嵌的程序。如果需要显示边框，则将这两行代码删除
		int oldStyle = OS.GetWindowLong(notepadHwnd, OS.GWL_STYLE);
		OS.SetWindowLong(notepadHwnd, OS.GWL_STYLE, oldStyle & ~OS.WS_BORDER);
		// composite为承载被启动程序的控件
		OS.SetParent(notepadHwnd, composite.handle);
		// 窗口最大化
		OS.SendMessage(notepadHwnd, OS.WM_SYSCOMMAND, OS.SC_MAXIMIZE, 0);
	}
}
