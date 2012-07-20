package it.gas.altichierock.add;

import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

public class AddWindow extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTable tblMenu;
	private JCheckBox chkEnabled;
	private JTextField txtName, txtPrice;
	private JButton btnAdd, btnRefresh;

	public AddWindow(Frame w) {
		super(w, true);
		setTitle("Menu organizer");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initComponents(); //drawing code
		setSize(400, 300);
		setLocationRelativeTo(w);
	}
	
	private void initComponents() {
		setLayout(new MigLayout("fill, wrap 1"));
		
		//draw'n'stuff
		tblMenu = new JTable();
		chkEnabled = new JCheckBox("Enabled");
		btnRefresh = new JButton("Refresh");
		txtName = new JTextField();
		txtPrice = new JTextField();
		btnAdd = new JButton("Add!");
		
		JPanel pnlAdd = new JPanel(new MigLayout("fill"));
		pnlAdd.setBorder(BorderFactory.createTitledBorder("Add an entry"));
		pnlAdd.add(new JLabel("Name:"));
		pnlAdd.add(txtName, "wrap, push, grow, span 2");
		pnlAdd.add(new JLabel("Price:"));
		pnlAdd.add(txtPrice, "push, grow");
		pnlAdd.add(btnAdd);
		
		add(new JScrollPane(tblMenu), "push, grow");
		add(chkEnabled, "split 2, grow");
		add(btnRefresh);
		add(pnlAdd, "grow");
	}
	
}
