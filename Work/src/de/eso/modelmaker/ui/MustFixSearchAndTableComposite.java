package de.eso.modelmaker.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

public class MustFixSearchAndTableComposite extends DefaultSearchAndTableComposite
{

	public MustFixSearchAndTableComposite(Composite parent, int style)
	{
		super(parent, style);
	}

	protected String[] getSearchItems()
	{
		return new String[]{"KPM Number", "Market", "Short Text", "Supplier", "Comments", "Priority", "Next Step"};
	}

	protected void initialTableColumns()
	{
		final TableColumn tcNumber = new TableColumn(table, SWT.CENTER);
		tcNumber.setText("Number");
		tcNumber.setWidth(100);
		tcNumber.setResizable(true);

		final TableColumn tcMarket = new TableColumn(table, SWT.LEFT);
		tcMarket.setText("Market");
		tcMarket.setWidth(150);

		final TableColumn tcShortText = new TableColumn(table, SWT.LEFT);
		tcShortText.setText("Short Text");
		tcShortText.setWidth(250);


		final TableColumn tcSupplier = new TableColumn(table, SWT.LEFT);
		tcSupplier.setText("Supplier");
		tcSupplier.setWidth(100);
		
		final TableColumn tcPriority = new TableColumn(table, SWT.LEFT);
		tcPriority.setText("Priority");
		tcPriority.setWidth(100);
		
		final TableColumn tcNext = new TableColumn(table, SWT.LEFT);
		tcNext.setText("Next Step");
		tcNext.setWidth(100);
	}
}
