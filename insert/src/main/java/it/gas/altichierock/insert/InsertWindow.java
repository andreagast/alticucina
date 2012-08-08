package it.gas.altichierock.insert;

import it.gas.altichierock.database.Detail;
import it.gas.altichierock.database.Item;
import it.gas.altichierock.database.OrderTicket;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
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
	private JButton btnInsert, btnDelete, btnClear, btnMake;
	//private JTextField txtNumber;
	private JLabel lblSubtotal;
	
	private JTextArea txtNote;
	
	private InsertLogic logic;
	private Container container;
	private InsertTableModel model;
	
	public InsertWindow(Frame w) {
		super(w, true);
		setTitle("Create-a-menu!");
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
		btnMake = new JButton("Make it!");
		lblSubtotal = new JLabel();
		txtNote = new JTextArea();
		
		lblSubtotal.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubtotal.setText("0.00");
		
		txtNote.setLineWrap(true);
		txtNote.setWrapStyleWord(true);
		
		add(cmbInsert, "pushx, growx");
		add(btnInsert, "growx");
		
		add(new JScrollPane(tblOrder), "grow, spany 2");
		add(btnDelete, "growx, pushy, bottom");
		add(btnClear, "growx, pushy, top");
		
		add(new JLabel("Subtotal:"));
		add(lblSubtotal);
		
		add(new JScrollPane(txtNote), "grow");
		add(btnMake, "grow");
	}
	
	private void initListeners() {
		btnInsert.addActionListener(this);
		btnDelete.addActionListener(this);
		btnClear.addActionListener(this);
		btnMake.addActionListener(this);
		
		model.addTableModelListener(this);
	}
	
	private void lock(boolean b) {
		cmbInsert.setEnabled(!b);
		tblOrder.setEnabled(!b);
		btnInsert.setEnabled(!b);
		btnDelete.setEnabled(!b);
		btnClear.setEnabled(!b);
		btnMake.setEnabled(!b);
		lblSubtotal.setEnabled(!b);
		txtNote.setEnabled(!b);
	}
	
	private void reset() {
		//order table
		container.reset();
		model.fireTableDataChanged();
		//notes
		txtNote.setText("");
	}
	
	private void showMessage(String str, int mode) {
		JOptionPane.showMessageDialog(this, str, "Altichierock", mode);
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
	
	private class OrderSaver extends SwingWorker<Integer, Void> {

		@Override
		protected Integer doInBackground() throws Exception {
			OrderTicket o = new OrderTicket();
			//o.getId().setCreated(Calendar.getInstance().getTime());
			o.getId().setCreated(new Date(System.currentTimeMillis()));
			o.setNote(txtNote.getText());
			List<Detail> l = new ArrayList<Detail>();
			for (int i = 0; i < container.getSize(); i++) {
				Detail d = new Detail();
				d.setItemId(container.getItem(i));
				d.setQuantity(container.getQuantity(i));
				l.add(d);
			}
			o.setDetail(l);
			
			logic.save(o);
			
			System.out.println("ID: " + o.getId().getId());
			return o.getId().getId();
		}
		
		@Override
		protected void done() {
			try {
				int id = get();
				showMessage("Order ID: " + id, JOptionPane.INFORMATION_MESSAGE);
				reset();
			} catch (Exception e) { 
				e.printStackTrace();
				showMessage(e.getMessage(), JOptionPane.ERROR_MESSAGE);
			}
			lock(false);
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
		} else if (arg0.getSource() == btnMake) {
			lock(true);
			new OrderSaver().execute();
		}
	}

	@Override
	public void tableChanged(TableModelEvent arg0) {
		System.out.println("Table changed!");
		lblSubtotal.setText(Float.toString(container.getSubtotal()));
	}

}
