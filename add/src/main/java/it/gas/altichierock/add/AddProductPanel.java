package it.gas.altichierock.add;

import java.text.DecimalFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class AddProductPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	//private JTextField txtDescription, txtPrice;
	private JTextField txtDescription;
	private JFormattedTextField txtPrice;
	
	public AddProductPanel() {
		initComponents();
	}
	
	private void initComponents() {
		setLayout(new MigLayout("wrap 2, ins 0"));
		
		txtDescription = new JTextField(25);		
		DecimalFormat format = new DecimalFormat("0.00");
		txtPrice = new JFormattedTextField(format);
		txtPrice.setColumns(25);
		txtPrice.setValue(0);
		
		add(new JLabel("Description:"));
		add(txtDescription);
		
		add(new JLabel("Price:"));
		add(txtPrice);
	}
	
	public String getDescription() {
		return txtDescription.getText();
	}
	
	public double getPrice() {
		//TODO: find a better way
		Object value = txtPrice.getValue();
		Double d;
		if (value instanceof Long) {
			d = Double.valueOf(Long.toString((Long) value));
		} else { //Double
			d = (Double) value;
		}
		return d;
	}
	
	public void setDescription(String str) {
		txtDescription.setText(str);
	}
	
	public void setPrice(double f) {
		txtPrice.setValue(f);
	}
}
