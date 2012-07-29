package it.gas.altichierock.insert;

import it.gas.altichierock.database.Item;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import net.miginfocom.swing.MigLayout;

public class InsertWindow extends JDialog implements ActionListener, TableModelListener {
	private static final long serialVersionUID = 1L;
	private JComboBox cmbInsert;
	private JTable tblOrder;
	private JButton btnInsert, btnDelete, btnClear, btnOKNew;
	private JTextField txtNumber;
	private JLabel lblSubtotal;
	
	private InsertLogic logic;
	private Container container;
	private InsertTableModel model;
	
	public InsertWindow(Frame w) {
		super(w, true);
		setTitle("Menu creator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initComponents(); //drawing code
		//initListeners(); //listeners
		setSize(500, 300);
		setLocationRelativeTo(w);
		
		logic = new InsertLogic();
		container = new Container();
		
		model = new InsertTableModel(container);
		tblOrder.setModel(model);
		
		//when everything is ready...
		initListeners(); //listeners
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
		lblSubtotal = new JLabel();
		
		lblSubtotal.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubtotal.setText("0.00");
		
		add(cmbInsert, "pushx, growx");
		add(btnInsert, "growx");
		
		add(new JScrollPane(tblOrder), "grow, spany 2");
		add(btnDelete, "growx, pushy, bottom");
		add(btnClear, "growx, pushy, top");
		
		add(new JLabel("Subtotal:"));
		add(lblSubtotal);
		
		add(new JLabel("Numero ordine:"), "split 2");
		add(txtNumber);
		add(btnOKNew, "grow");
	}
	
	private void initListeners() {
		btnInsert.addActionListener(this);
		btnDelete.addActionListener(this);
		btnClear.addActionListener(this);
		btnOKNew.addActionListener(this);
		
		model.addTableModelListener(this);
	}
	
	@Override
	public void setVisible(boolean visible) {
		if (visible == true)
			new MenuReloader().execute();
		super.setVisible(visible);
	}
	
	private class MenuReloader extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			logic.refresh();
			return null;
		}
		
		@Override
		protected void done() {
			cmbInsert.setModel(new InsertComboModel(logic.getMenu()));
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnInsert) {
			Item i = (Item) cmbInsert.getSelectedItem();
			container.addRecord(i);
			model.fireTableDataChanged();
		} else if (arg0.getSource() == btnClear) {
			container.reset();
			model.fireTableDataChanged();
		} else if (arg0.getSource() == btnDelete) {
			int row = tblOrder.getSelectedRow();
			if (row != -1) {
				container.remove(row);
				model.fireTableDataChanged();
			}
		}
	}

	@Override
	public void tableChanged(TableModelEvent arg0) {
		System.out.println("Table changed!");
		lblSubtotal.setText(Float.toString(container.getSubtotal()));
	}

}
