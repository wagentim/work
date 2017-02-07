package de.eso.modelmaker.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.work.Ticket;

public class TicketContentViewComposite extends Composite
{
	private Text txtKpmNum, txtShortText, txtSupplier, txtProblemStatus, txtEngineerStatus,
			txtDescription, txtAnalysis, txtSupplierStatus;
	private static final GridLayout grpLayout = new GridLayout(1, false);
	private static final GridData longInfoGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
	
	private static final int GRID_SHORT = 5;
	private static final int GRID_LONG = 3;
	
	private Composite shortInfo, longInfo;

	public TicketContentViewComposite(Composite parent, int style)
	{
		super(parent, style);
		initial();
	}

	private void initial()
	{
		initialLayout();
		initialShortInfoParts();
		initialLongInfoParts();
	}

	private void initialLongInfoParts()
	{
		// Description Block
		final Group desc = new Group(longInfo, SWT.NONE);
		desc.setText("Description");
		desc.setLayout(grpLayout);
		desc.setLayoutData(longInfoGridData);
		txtDescription = new Text(desc, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		txtDescription.setLayoutData(new GridData(GridData.FILL_BOTH));
		txtDescription.setEditable(false);

		// Analysis Block
		final Group analysis = new Group(longInfo, SWT.NONE);
		analysis.setText("Analysis");
		analysis.setLayout(grpLayout);
		analysis.setLayoutData(longInfoGridData);
		txtAnalysis = new Text(analysis, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		txtAnalysis.setLayoutData(new GridData(GridData.FILL_BOTH));
		txtAnalysis.setEditable(false);

		// Supplier Status
		final Group supplierStatus = new Group(longInfo, SWT.NONE);
		supplierStatus.setText("Supplier Status");
		supplierStatus.setLayout(grpLayout);
		supplierStatus.setLayoutData(longInfoGridData);
		txtSupplierStatus = new Text(supplierStatus, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		txtSupplierStatus.setLayoutData(new GridData(GridData.FILL_BOTH));
		txtSupplierStatus.setEditable(false);

	}

	private void initialLayout()
	{
		final GridLayout layout = new GridLayout(1, false);
		this.setLayout(layout);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		shortInfo = new Composite(this, SWT.NONE);
		shortInfo.setLayout(new GridLayout(GRID_SHORT, false));
		shortInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
		longInfo = new Composite(this, SWT.NONE);
		longInfo.setLayout(new GridLayout(GRID_LONG, true));
		longInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	private void initialShortInfoParts()
	{
		// KPM Number
		final Group grpKPM = new Group(shortInfo, SWT.NONE);
		grpKPM.setText("KPM ID");
		grpKPM.setLayout(grpLayout);
		txtKpmNum = new Text(grpKPM, SWT.SINGLE);
		txtKpmNum.setEditable(false);
		GridData gdKPM = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdKPM.widthHint = 40;
		txtKpmNum.setLayoutData(gdKPM);

		// Short Text
		final Group grpShortText = new Group(shortInfo, SWT.NONE);
		grpShortText.setText("Short Text");
		grpShortText.setLayout(grpLayout);
		txtShortText = new Text(grpShortText, SWT.SINGLE);
		txtShortText.setEditable(false);
		GridData gdShortText = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdShortText.widthHint = 280;
		txtShortText.setLayoutData(gdShortText);
		txtShortText.setForeground(txtShortText.getDisplay().getSystemColor(SWT.COLOR_DARK_GREEN));

		// supplier
		final Group grpSupplier = new Group(shortInfo, SWT.NONE);
		grpSupplier.setText("Supplier");
		grpSupplier.setLayout(grpLayout);
		txtSupplier = new Text(grpSupplier, SWT.SINGLE);
		txtSupplier.setEditable(false);
		GridData gdSupplier = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdSupplier.widthHint = 80;
		txtSupplier.setLayoutData(gdSupplier);

		// Problem Status
		final Group grpProStat = new Group(shortInfo, SWT.NONE);
		grpProStat.setText("Pro Stat");
		grpProStat.setLayout(grpLayout);
		txtProblemStatus = new Text(grpProStat, SWT.SINGLE);
		txtProblemStatus.setEditable(false);
		GridData gdProblemStatus = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdProblemStatus.widthHint = 30;
		txtProblemStatus.setLayoutData(gdProblemStatus);
		
		// Engineer Status
		final Group grpEngStat = new Group(shortInfo, SWT.NONE);
		grpEngStat.setText("Eng Stat");
		grpEngStat.setLayout(grpLayout);
		txtEngineerStatus = new Text(grpEngStat, SWT.SINGLE);
		txtEngineerStatus.setEditable(false);
		GridData gdEngineerStatus = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdEngineerStatus.widthHint = 30;
		txtEngineerStatus.setLayoutData(gdEngineerStatus);
	}

	public void setSelectedTicket(Ticket selectedTicket)
	{
		if( null == selectedTicket )
		{
			txtKpmNum.setText(StringConstants.EMPTY_STRING);
			txtShortText.setText(StringConstants.EMPTY_STRING);
			txtSupplier.setText(StringConstants.EMPTY_STRING);
			txtProblemStatus.setText(StringConstants.EMPTY_STRING);
			txtEngineerStatus.setText(StringConstants.EMPTY_STRING);
			txtDescription.setText(StringConstants.EMPTY_STRING);
			txtAnalysis.setText(StringConstants.EMPTY_STRING);
			txtSupplierStatus.setText(StringConstants.EMPTY_STRING);
		}
		else
		{
			txtKpmNum.setText(String.valueOf(selectedTicket.getNumber()));
			txtShortText.setText(selectedTicket.getShortText());
			txtSupplier.setText(selectedTicket.getSupplier());
			txtProblemStatus.setText("0");
			txtEngineerStatus.setText(String.valueOf(selectedTicket.getEnginerringStatus()));
			txtDescription.setText(selectedTicket.getProblemDescription());
			txtAnalysis.setText(selectedTicket.getAnalysis());
			txtSupplierStatus.setText(selectedTicket.getSupplierInfo() + "\n" + selectedTicket.getSupplierResponse());
		}
	}

}
