package it.gas.altichierock.display;

import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

public class DisplayWindow extends JDialog {
	private static final long serialVersionUID = 1L;

	public DisplayWindow(Frame f) {
		super(f, true);
		setTitle("Displayer");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initComponents();
		initListeners();
		setSize(500, 500);
		setLocationRelativeTo(f);
	}

	private void initListeners() {
		// TODO Auto-generated method stub
		
	}

	private void initComponents() {
		// TODO Auto-generated method stub
		
	}

	
}
