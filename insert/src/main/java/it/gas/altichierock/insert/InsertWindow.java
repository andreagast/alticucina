package it.gas.altichierock.insert;

import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

public class InsertWindow extends JDialog {
	private static final long serialVersionUID = 1L;
	private JComboBox cmbInsert;
	private JTable tblOrder;
	private JButton btnInsert, btnDelete, btnClear, btnOKNew;
	private JTextField txtNumber;
	
	public InsertWindow(Frame w) {
		super(w, true);
		setTitle("Menu creator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initComponents(); //drawing code
		setSize(500, 300);
		setLocationRelativeTo(w);
	}

	private void initComponents() {
		setLayout(new MigLayout("fill, wrap 2"));
		
		//draw'n'stuff
		cmbInsert = new JComboBox();
		tblOrder = new JTable();
		btnInsert = new JButton("+");
		btnDelete = new JButton("Delete");
		btnClear = new JButton("Clear");
		btnOKNew = new JButton("OK"); //change to "new" when the order is saved
		txtNumber = new JTextField();
		
		add(cmbInsert, "pushx, growx");
		add(btnInsert, "growx");
		add(new JScrollPane(tblOrder), "grow, spany 2");
		add(btnDelete, "growx, pushy, bottom");
		add(btnClear, "growx, pushy, top");
		add(new JLabel("Numero ordine:"), "split 2");
		add(txtNumber);
		add(btnOKNew, "grow");
	}

}
