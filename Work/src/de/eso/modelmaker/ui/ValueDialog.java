package de.eso.modelmaker.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.Constants;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import de.eso.modelmaker.core.SysConstBean;

public class ValueDialog
{

    private final Shell shell;

    private Shell parent;
    
    private static Color BACKGROUND_ROW;

    private static final int SHELL_WIDTH = 600;
    private static final int SHELL_HEIGH = 400;
    
    public static final int OK = 1;
    
    public static final int CANCEL = 0;
    
    private int returnValue = CANCEL;

    private static final Color COLOR_WHITE = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
    
    private boolean changed = false;
    
    private List<SysConstBean> sysCons = null;

    public ValueDialog(Shell parent)
    {
        this.parent = parent;
        shell = new Shell(parent, SWT.RESIZE | SWT.BORDER | SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        BACKGROUND_ROW = new Color(shell.getDisplay(), 230, 235, 249);
    }

    public void setValue(List<SysConstBean> constants)
    {
        this.sysCons = constants;
    }
    
    public List<SysConstBean> getValue()
    {
        return sysCons;
    }
    
    public void open()
    {
        parent.setEnabled(false);
        
        shell.setSize(SHELL_WIDTH, SHELL_HEIGH);

        shell.setText("Add Values");
        shell.setBackground(COLOR_WHITE);

        GridLayout layout = new GridLayout(2, false);
        layout.verticalSpacing = 5;
        shell.setLayout(layout);

        table = new Table(shell, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION | SWT.HIDE_SELECTION);

        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

        table.setHeaderVisible(true);

        final TableColumn image = new TableColumn(table, SWT.LEFT);
        image.setWidth(0);
        image.setResizable(false);

        final TableColumn tcName = new TableColumn(table, SWT.LEFT);
        tcName.setText("Key");
        tcName.setWidth(150);

        final TableColumn tcValue = new TableColumn(table, SWT.LEFT);
        tcValue.setText("Value");
        tcValue.setWidth(150);

        final TableColumn tcComment = new TableColumn(table, SWT.LEFT);
        tcComment.setText("Comments");
        tcComment.setWidth(200);

        editor = new TableEditor(table);
        editor.horizontalAlignment = SWT.LEFT;
        editor.grabHorizontal = true;

        Composite buttonPane = new Composite(shell, SWT.NONE);
        layout = new GridLayout(4, false);
        GridData data = new GridData(GridData.END, GridData.CENTER, true, false, 2, 1);
        buttonPane.setLayout(layout);
        buttonPane.setLayoutData(data);
        buttonPane.setBackground(COLOR_WHITE);

        data.horizontalIndent = 5;

        data = new GridData(GridData.END, GridData.CENTER, false, false);
        data.widthHint = 90;
        data.heightHint = 25;

        btnAdd = new Button(buttonPane, SWT.PUSH);
        btnAdd.setText("Add");
        btnAdd.setLayoutData(data);
        btnAdd.setBackground(COLOR_WHITE);
        btnAdd.addListener(SWT.Selection, new Listener()
        {

            @Override
            public void handleEvent(Event arg0)
            {
                SysConInputDialog inputDialog = new SysConInputDialog(shell);
                
                inputDialog.open();

                if ( inputDialog.getOperation() == SysConInputDialog.OK )
                {
                    SysConstBean value = inputDialog.getValue();

                    generateRow(value);
                    
                    if(sysCons == null)
                    {
                        sysCons = new ArrayList<SysConstBean>();
                    }
                    
                    sysCons.add(value);
                    
                    changed = true;
                }
            }
        });
        
        btnModify = new Button(buttonPane, SWT.PUSH);
        btnModify.setText("Modify");
        btnModify.setLayoutData(data);
        btnModify.setBackground(COLOR_WHITE);
        btnModify.addListener(SWT.Selection, new Listener()
        {

            @Override
            public void handleEvent(Event arg0)
            {
                modifyAction();
            }
        });
        
        btnModify.setEnabled(false);

        btnRemove = new Button(buttonPane, SWT.PUSH);
        btnRemove.setText("Delete");
        btnRemove.setLayoutData(data);
        btnRemove.setBackground(COLOR_WHITE);
        btnRemove.addListener(SWT.Selection, new Listener()
        {

            @Override
            public void handleEvent(Event arg0)
            {
                deleteSelectedRow();
                changed = true;
            }
        });
        
        btnRemove.setEnabled(false);

        btnOk = new Button(buttonPane, SWT.PUSH);
        btnOk.setText("OK");
        btnOk.setLayoutData(data);
        btnOk.setBackground(COLOR_WHITE);
        btnOk.addListener(SWT.Selection, new Listener()
        {

            @Override
            public void handleEvent(Event arg0)
            {
                TableItem[] items = table.getItems();
                
                if(items != null && items.length > 0)
                {
                    if(sysCons == null)
                    {
                        sysCons = new ArrayList<SysConstBean>();
                    }else
                    {
                        sysCons.clear();
                    }
                    
                    for(TableItem item : items)
                    {
                        SysConstBean bean = new SysConstBean();
                        bean.setKey(item.getText(1));
                        bean.setValue(item.getText(2));
                        bean.setComment(item.getText(3));
                        sysCons.add(bean);
                    }
                }
                
                returnValue = OK;
                shell.dispose();
            }
        });
        
        setCenter();
        
        initActions();
        
        assignValues();

        shell.open();
        while ( !shell.isDisposed() )
        {
            if ( !Display.getCurrent().readAndDispatch() )
            {
                Display.getCurrent().sleep();
            }
        }

        shell.dispose();

        parent.setEnabled(true);
    }

    private void deleteSelectedRow()
    {
        int row = getSelectedRow();
        
        if(row < 0)
        {
            return;
        }
        
        table.remove(row);
        
        refreshTable();
        
        sysCons.remove(row);
    }
    
    private void refreshTable()
    {
        TableItem[] items = table.getItems();
        
        for(int i = 0; i < items.length; i++)
        {
            items[i].setBackground(COLOR_WHITE);
            
            if( (i % 2) == 0 )
            {
                items[i].setBackground(BACKGROUND_ROW);
            }
        }
    }
    
    private int getSelectedRow()
    {
        return table.getSelectionIndex();
    }

    private void setCenter()
    {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        shell.setLocation((int) ( ( dim.getWidth() - SHELL_WIDTH ) / 2 ), (int) ( ( dim.getHeight() - SHELL_HEIGH ) / 2 ));
    }
    
    private void modifyAction()
    {
        int row = -1;
        
        if( (row = getSelectedRow() ) < 0)
        {
            return;
        }
        
        SysConstBean value = sysCons.get(row);
        
        SysConstBean bean = (SysConstBean)(value.clone());
        
        SysConInputDialog dialog = new SysConInputDialog(shell);

        dialog.setValue(bean);
        
        dialog.open();
        
        if(dialog.getOperation() == SysConInputDialog.OK)
        {
            value = bean;
            
            TableItem item = table.getItem(row);
            item.setText(1, bean.getKey());
            item.setText(2, bean.getValue());
            item.setText(3, bean.getComment());
            changed = true;
        }
    }
    
    private void initActions()
    {
        table.addMouseListener(new MouseListener()
        {
            
            @Override
            public void mouseUp(MouseEvent paramMouseEvent)
            {
            }
            
            @Override
            public void mouseDown(MouseEvent paramMouseEvent)
            {
            }
            
            @Override
            public void mouseDoubleClick(MouseEvent paramMouseEvent)
            {
                modifyAction();
            }
        });
    }
    
    private void assignValues()
    {
        if(sysCons != null && sysCons.size() > 0)
        {
            for(SysConstBean s : sysCons)
            {
                generateRow(s);
            }
        }
    }
    
    private void generateRow(SysConstBean bean)
    {
        TableItem tItem = new TableItem(table, SWT.None);

        tItem.setText(new String[] { "", bean.getKey(), bean.getValue(), bean.getComment() });

        if ( ( table.getItemCount() % 2 ) != 0 )
        {
            tItem.setBackground(BACKGROUND_ROW);
        }
    }
    
    public int getOperation()
    {
        return returnValue;
    }
    
    public boolean hasChanged()
    {
        return changed;
    }

    private Table table;

    private Button btnOk, btnAdd, btnRemove, btnModify;

    private TableEditor editor;

}
