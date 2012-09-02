package it.gas.altichierock.add;

import java.text.DecimalFormat;

import javax.swing.table.DefaultTableCellRenderer;

public class DecimalCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	private DecimalFormat format;	
	
	public DecimalCellRenderer() {
		format = new DecimalFormat("0.00");
	}

	@Override
	protected void setValue(Object arg0) {
		if (arg0 == null)
			setText("");
		setText(format.format(arg0));
	}

	
}
