package it.gas.altichierock.add;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.miginfocom.swing.MigLayout;

public class AddWindow extends JDialog implements ActionListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory.getLogger(AddWindow.class);
	private JTable tblMenu;
	private JCheckBox chkEnabled;
	private JTextField txtName, txtPrice;
	private JButton btnAdd, btnRefresh;
	
	private MenuHolder menu;
	private int selected;

	public AddWindow(Frame w) {
		super(w, true);
		setTitle("Organizer");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initComponents(); //drawing code
		setSize(400, 300);
		setLocationRelativeTo(w);
		
		initListeners();
		
		menu = new MenuHolder();
		tblMenu.setModel(menu.getModel());
	}
	
	private void initComponents() {
		setLayout(new MigLayout("fill, wrap 1"));
		
		//draw'n'stuff
		tblMenu = new JTable(new AddTableModel());
		tblMenu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
	
	private void initListeners() {
		btnRefresh.addActionListener(this);
		btnAdd.addActionListener(this);
		chkEnabled.addActionListener(this);
		tblMenu.getSelectionModel().addListSelectionListener(this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				lock(true);
				new Reloader().execute();
			}
		});
	}
	
	private void lock(boolean b) {
		tblMenu.setEnabled(!b);
		chkEnabled.setEnabled(!b);
		txtName.setEnabled(!b);
		txtPrice.setEnabled(!b);
		btnAdd.setEnabled(!b);
		btnRefresh.setEnabled(!b);
	}
	
	private void addNewItem() {
		String descr = txtName.getText();
		String price = txtPrice.getText();
		if (descr.compareTo("") == 0 || price.compareTo("") == 0) {
			JOptionPane.showMessageDialog(this, "Inserisci qualcosa!");
			return;
		}
		try {
			float fPrice = Float.parseFloat(price);
			menu.addItem(descr, fPrice);
			lock(true);
			new Reloader().execute();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Usa il punto come separatore delle decine.");
			return;
		}
		txtName.setText("");
		txtPrice.setText("");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRefresh) {
			lock(true);
			new Reloader().execute();
		} else if (e.getSource() == btnAdd) {
			addNewItem();
		} else if (e.getSource() == chkEnabled) {
			int id = (Integer) tblMenu.getModel().getValueAt(selected, 0);
			lock(true);
			menu.changeEnabled(id, chkEnabled.isSelected());
			new Reloader().execute();
		}
	}
	
	private class Reloader extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			log.debug("doInBackground");
			menu.refresh();
			return null;
		}

		@Override
		protected void done() {
			lock(false);
			log.debug("done");
		}
		
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if (arg0.getValueIsAdjusting())
			return;
		selected = tblMenu.getSelectedRow();
		log.debug("Changed selection! {}", selected);
		if (selected == -1) {
			chkEnabled.setEnabled(false);
			chkEnabled.setSelected(false);
			return;
		}
		//TODO:non funziona. trova l'id della riga selezionata e poi
		//fatti passare l'oggetto con quell'id.
		boolean b = (Boolean) tblMenu.getModel().getValueAt(selected, 3);
		chkEnabled.setEnabled(true);
		chkEnabled.setSelected(b);
	}

}
