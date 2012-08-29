package it.gas.altichierock.add;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class AddProductPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField txtDescription, txtPrice;
	
	public AddProductPanel() {
		initComponents();
	}
	
	private void initComponents() {
		setLayout(new MigLayout("wrap 2, ins 0"));
		
		txtDescription = new JTextField(25);		
		txtPrice = new JTextField(new FloatDocument(), "0.0", 25);
		
		add(new JLabel("Description:"));
		add(txtDescription);
		
		add(new JLabel("Price:"));
		add(txtPrice);
	}
	
	public String getDescription() {
		return txtDescription.getText();
	}
	
	public float getPrice() {
		if (txtPrice.getText().compareTo("") == 0)
			return 0;
		return Float.parseFloat(txtPrice.getText());
	}
	
	public void setDescription(String str) {
		txtDescription.setText(str);
	}
	
	public void setPrice(float f) {
		txtPrice.setText(Float.toString(f));
	}
}
