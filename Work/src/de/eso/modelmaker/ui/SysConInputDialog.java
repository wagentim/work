package de.eso.modelmaker.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.eso.modelmaker.core.SysConstBean;

public class SysConInputDialog {
	
	private Shell shell;
	private Shell parent;
	private static final int SHELL_WIDTH = 500;
	private static final int SHELL_HEIGH = 305;
	
	public static final int OK = 1;
	
	public static final int CANCEL = 0;
	
	private static final String title = "Key-Value Input Dialog";
	
	private SysConstBean value = null;
	
	private int returnValue = CANCEL;
	
	public SysConInputDialog(Shell parent){
	    
		this.parent = parent;
	}
	
	public void open(){
		
		parent.setEnabled(false);
		
		shell = new Shell(parent);
		shell.setSize(SHELL_WIDTH, SHELL_HEIGH);
		shell.setText(title);
		setCenter();
		GridLayout layout = new GridLayout(2, false);
		layout.verticalSpacing = 5;
		shell.setLayout(layout);
		
		GridData grpGridData = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);

		labelKey = new Label(shell, SWT.NONE);
		labelKey.setText("Key");
		txtKey = new Text(shell, SWT.BORDER);
		txtKey.setLayoutData(grpGridData);
		
		labelValue = new Label(shell, SWT.NONE);
		labelValue.setText("Value");
		txtValue = new Text(shell, SWT.BORDER);
		txtValue.setLayoutData(grpGridData);
		
		GridData commenGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		labelComment = new Label(shell, SWT.NONE);
		labelComment.setText("Comment");
		txtComment = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		txtComment.setLayoutData(commenGridData);
		
		Composite buttonPane = new Composite(shell, SWT.NONE);
		layout = new GridLayout(2, false);
		GridData data = new GridData(GridData.END, GridData.CENTER, true, false, 2, 1);
		buttonPane.setLayout(layout);
		buttonPane.setLayoutData(data);
		
		data.horizontalIndent = 5;
		
		data = new GridData(GridData.END, GridData.CENTER, false, false);
		data.widthHint = 90;
		data.heightHint = 25;
		
		btnOk = new Button(buttonPane, SWT.PUSH);
		btnOk.setText("OK");
		btnOk.setLayoutData(data);

		btnCancel = new Button(buttonPane, SWT.PUSH);
		btnCancel.setText("Cancel");
		btnCancel.setLayoutData(data);
		
		assignValues();

		initActions();
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!Display.getCurrent().readAndDispatch()) {
				Display.getCurrent().sleep();
			}
		}
		
		shell.dispose();
		
		parent.setEnabled(true);
	}
	
	public void setValue(SysConstBean value)
	{
	    this.value = value;
	}
	
	private void assignValues()
	{
	    if(value == null)
	    {
	        value = new SysConstBean();
	        value.setKey("");
	        value.setValue("");
	        value.setComment("");
	    }
	    
	    txtKey.setText(value.getKey());
	    txtValue.setText(value.getValue());
	    txtComment.setText(value.getComment());
	}
	
	private void initActions(){
		btnCancel.addListener(SWT.Selection, new Listener(){

			@Override
			public void handleEvent(Event event) {
			    returnValue = CANCEL;
				shell.dispose();
			}
			
		});
		
		btnOk.addListener(SWT.Selection, new Listener(){

			@Override
			public void handleEvent(Event event) {
			    
			    try
			    {
			        Integer v = Integer.valueOf(txtValue.getText());
			        
			        if(v == null)
			        {
			            showErrorMessage();
			            txtValue.setText("");
			            txtValue.setFocus();
			            return;
			        }
			        
			    }catch(Exception e)
			    {
			        showErrorMessage();
                    txtValue.setText("");
                    txtValue.setFocus();
                    return;
			    }
			    
			    
				
				value.setKey(txtKey.getText());
				value.setValue(txtValue.getText());
				value.setComment(txtComment.getText());
				
				returnValue = OK;
				
				shell.dispose();
			}
			
		});
	}
	
	private void showErrorMessage()
	{
	    MsgDialog dialog = new MsgDialog(shell, "Please give the correct values.", 0, null, null);
	    dialog.show();
	}
	
	private void setCenter(){
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		shell.setLocation((int)((dim.getWidth() - SHELL_WIDTH) / 2), 
				(int)((dim.getHeight() - SHELL_HEIGH) / 2));
	}
	
	public SysConstBean getValue()
	{
	    return value;
	}
	
	public int getOperation()
	{
	    return returnValue;
	}
	
	private Label labelKey, labelValue, labelComment;
	private Text txtKey, txtValue, txtComment;
	
	private Button btnOk, btnCancel;
}
