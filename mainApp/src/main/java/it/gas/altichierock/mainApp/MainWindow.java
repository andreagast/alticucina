package it.gas.altichierock.mainApp;

import it.gas.altichierock.add.AddWindow;
import it.gas.altichierock.insert.InsertWindow;
import it.gas.altichierock.order.OrderWindow;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

public class MainWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton btnInsert, btnAdd, btnOrder, btnDisplay;

	public MainWindow() {
		super();
		setTitle(Constants.TITLE);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initComponents(); //drawing code
		pack();
		setLocationRelativeTo(null);
		
		//working code
		btnInsert.addActionListener(this);
		btnAdd.addActionListener(this);
		btnOrder.addActionListener(this);
		btnDisplay.addActionListener(this);
	}
	
	private void initComponents() {
		setLayout(new MigLayout());

		//draw'n'stuff
		btnInsert = new JButton("Make a new order!");
		btnAdd = new JButton("+");
		btnOrder = new JButton("Show orders");
		btnDisplay = new JButton("Display");
		
		JPanel pnlCentered = new JPanel(new MigLayout());
		add(pnlCentered, "push, align center");
		
		JLabel lblTitle = new JLabel(Constants.TITLE);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font(lblTitle.getFont().getFamily(), Font.BOLD, 48));
		
		pnlCentered.add(lblTitle, "growx, wrap");
		pnlCentered.add(btnInsert, "split 2, growx");
		pnlCentered.add(btnAdd, "wrap");
		pnlCentered.add(btnOrder, "split 2, growx, wrap");
		pnlCentered.add(btnDisplay, "growx");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(btnInsert)) {
			//System.out.println("insert");
			new InsertWindow(this).setVisible(true);
		} else if (arg0.getSource().equals(btnAdd)) {
			//System.out.println("add");
			new AddWindow(this).setVisible(true);
		} else if (arg0.getSource().equals(btnOrder)) {
			//System.out.println("order");
			new OrderWindow(this).setVisible(true);
		} else if (arg0.getSource().equals(btnDisplay)) {
			System.out.println("display");
		}
	}
	
}
