package it.gas.altichierock.display;

import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

public class DisplayWindow extends JDialog {
	private static final long serialVersionUID = 1L;
	private DisplayLogic logic;

	public DisplayWindow(Frame f) {
		super(f, true);
		setTitle("Displayer");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initComponents();
		initListeners();
		setSize(500, 500);
		setLocationRelativeTo(f);
		
		logic = new DisplayLogic();
	}

	private void initComponents() {
		setLayout(new MigLayout("fill"));
		
		JLabel lblTop = new JLabel("Now serving:");
		add(lblTop, "north");
	}
	
	private void initListeners() {
		// TODO Auto-generated method stub
		
	}
	
}
