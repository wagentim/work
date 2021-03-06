package cn.wagentim.work.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.work.TicketEntity;
import cn.wagentim.work.config.IConstants;

public class TicketContentViewComposite extends Composite
{
	private Text txtKpmNum, txtShortText, txtSupplier, txtEngineerStatus,
			txtDescription, txtAnalysis, txtSupplierStatus, txtFrequency, txtFFD, txtEProj, txtFunction;
	public static final GridLayout GRP_LAYOUT = new GridLayout(1, false);
	private static final GridData longInfoGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
	
	private static final int GRID_SHORT = 8;
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
		desc.setLayout(GRP_LAYOUT);
		desc.setLayoutData(longInfoGridData);
		txtDescription = new Text(desc, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		txtDescription.setLayoutData(new GridData(GridData.FILL_BOTH));
		txtDescription.setEditable(false);

		// Analysis Block
		final Group analysis = new Group(longInfo, SWT.NONE);
		analysis.setText("Analysis");
		analysis.setLayout(GRP_LAYOUT);
		analysis.setLayoutData(longInfoGridData);
		txtAnalysis = new Text(analysis, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		txtAnalysis.setLayoutData(new GridData(GridData.FILL_BOTH));
		txtAnalysis.setEditable(false);

		// Supplier Status
		final Group supplierStatus = new Group(longInfo, SWT.NONE);
		supplierStatus.setText("Supplier Status");
		supplierStatus.setLayout(GRP_LAYOUT);
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
		grpKPM.setText(IConstants.STRING_KPM_ID);
		grpKPM.setLayout(GRP_LAYOUT);
		txtKpmNum = new Text(grpKPM, SWT.SINGLE);
		txtKpmNum.setEditable(false);
		GridData gdKPM = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdKPM.widthHint = 40;
		txtKpmNum.setLayoutData(gdKPM);

		// Short Text
		final Group grpShortText = new Group(shortInfo, SWT.NONE);
		grpShortText.setText(IConstants.STRING_SHORT_TEXT);
		grpShortText.setLayout(GRP_LAYOUT);
		txtShortText = new Text(grpShortText, SWT.SINGLE);
		txtShortText.setEditable(false);
		GridData gdShortText = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdShortText.widthHint = 280;
		txtShortText.setLayoutData(gdShortText);
		txtShortText.setForeground(txtShortText.getDisplay().getSystemColor(SWT.COLOR_DARK_GREEN));

		// supplier
		final Group grpSupplier = new Group(shortInfo, SWT.NONE);
		grpSupplier.setText("Supplier");
		grpSupplier.setLayout(GRP_LAYOUT);
		txtSupplier = new Text(grpSupplier, SWT.SINGLE);
		txtSupplier.setEditable(false);
		GridData gdSupplier = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdSupplier.widthHint = 80;
		txtSupplier.setLayoutData(gdSupplier);

		// Problem Status
//		final Group grpProStat = new Group(shortInfo, SWT.NONE);
//		grpProStat.setText("Pro Stat");
//		grpProStat.setLayout(grpLayout);
//		txtProblemStatus = new Text(grpProStat, SWT.SINGLE);
//		txtProblemStatus.setEditable(false);
//		GridData gdProblemStatus = new GridData(SWT.FILL, SWT.FILL, true, true);
//		gdProblemStatus.widthHint = 30;
//		txtProblemStatus.setLayoutData(gdProblemStatus);
		
		// Engineer Status
		final Group grpEngStat = new Group(shortInfo, SWT.NONE);
		grpEngStat.setText("Eng Stat");
		grpEngStat.setLayout(GRP_LAYOUT);
		txtEngineerStatus = new Text(grpEngStat, SWT.SINGLE);
		txtEngineerStatus.setEditable(false);
		GridData gdEngineerStatus = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdEngineerStatus.widthHint = 30;
		txtEngineerStatus.setLayoutData(gdEngineerStatus);
		
		final Group grpFrequency = new Group(shortInfo, SWT.NONE);
		grpFrequency.setText("Frequency");
		grpFrequency.setLayout(GRP_LAYOUT);
		txtFrequency = new Text(grpFrequency, SWT.SINGLE);
		txtFrequency.setEditable(false);
		GridData gdFrequencyStatus = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdFrequencyStatus.widthHint = 60;
		txtFrequency.setLayoutData(gdFrequencyStatus);
		
		final Group grpFFD = new Group(shortInfo, SWT.NONE);
		grpFFD.setText("FFD");
		grpFFD.setLayout(GRP_LAYOUT);
		txtFFD = new Text(grpFFD, SWT.SINGLE);
		txtFFD.setEditable(false);
		GridData gdProblemSolver = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdProblemSolver.widthHint = 60;
		txtFFD.setLayoutData(gdProblemSolver);
		
		final Group grpEProj = new Group(shortInfo, SWT.NONE);
		grpEProj.setText("E-Proj");
		grpEProj.setLayout(GRP_LAYOUT);
		txtEProj = new Text(grpEProj, SWT.SINGLE);
		txtEProj.setEditable(false);
		GridData gdEProj = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdEProj.widthHint = 60;
		txtEProj.setLayoutData(gdEProj);
		
		final Group grpFunction = new Group(shortInfo, SWT.NONE);
		grpFunction.setText("Func");
		grpFunction.setLayout(GRP_LAYOUT);
		txtFunction = new Text(grpFunction, SWT.SINGLE);
		txtFunction.setEditable(false);
		GridData gdFunc = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdFunc.widthHint = 130;
		txtFunction.setLayoutData(gdFunc);
	}

	public void setSelectedTicket(TicketEntity selectedTicket)
	{
		if( null == selectedTicket )
		{
			txtKpmNum.setText(StringConstants.EMPTY_STRING);
			txtShortText.setText(StringConstants.EMPTY_STRING);
			txtSupplier.setText(StringConstants.EMPTY_STRING);
//			txtProblemStatus.setText(StringConstants.EMPTY_STRING);
			txtEngineerStatus.setText(StringConstants.EMPTY_STRING);
			txtDescription.setText(StringConstants.EMPTY_STRING);
			txtAnalysis.setText(StringConstants.EMPTY_STRING);
			txtSupplierStatus.setText(StringConstants.EMPTY_STRING);
			txtFrequency.setText(StringConstants.EMPTY_STRING);
			txtFFD.setText(StringConstants.EMPTY_STRING);
			txtEProj.setText(StringConstants.EMPTY_STRING);
			txtFunction.setText(StringConstants.EMPTY_STRING);
		}
		else
		{
			txtKpmNum.setText(String.valueOf(selectedTicket.getKPMID()));
			txtShortText.setText(selectedTicket.getShortText());
			txtSupplier.setText(selectedTicket.getSupplier());
//			txtProblemStatus.setText("0");
			txtEngineerStatus.setText(String.valueOf(selectedTicket.getEnginerringStatus()));
			txtDescription.setText(selectedTicket.getProblemDescription());
			txtAnalysis.setText(selectedTicket.getAnalysis());
			txtSupplierStatus.setText(selectedTicket.getSupplierInfo() + "\n" + selectedTicket.getSupplierResponse());
			txtFrequency.setText(selectedTicket.getFaultFrequency());
			txtFFD.setText(selectedTicket.getImplementationDate());
			txtEProj.setText(selectedTicket.getEproject());
			txtFunction.setText(selectedTicket.getFunctionality());
		}
	}

}
