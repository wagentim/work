package cn.wagentim.work.test;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.eso.modelmaker.ui.CommentsEditor;

public class TestGUI
{
	private Shell shell;
	
	public TestGUI()
	{
		final Display display = Display.getDefault();
		
//		shell = new Shell();
//		shell.setLayout(new GridLayout(1, false));
//		shell.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
//		
//		new TicketContentViewComposite(shell, SWT.NONE);
//		
//		shell.setSize(800, 600);
//		shell.open();
		CommentsEditor editor = new CommentsEditor();
		shell = editor.getShell();
		
		editor.open();

		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		display.dispose();
	}
	
	public static void main(String[] args)
	{
		new TestGUI();
	}
}
