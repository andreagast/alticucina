package it.gas.altichierock.add;

import java.awt.Font;
import java.awt.Frame;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import it.gas.altichierock.database.Constants;
import it.gas.altichierock.database.entities.Component;
import it.gas.altichierock.database.entities.Product;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.table.TableColumnModel;

import net.miginfocom.swing.MigLayout;

public class EditProductDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton btnEdit, btnAdd, btnRemove;
	private JTable tblComponents;

	private AddLogic logic;
	private Product product;

	public EditProductDialog(Frame f, AddLogic logic, Product p) {
		super(f, true);
		this.logic = logic;
		this.product = p;

		initComponents();
		initListeners();
		setSize(300, 400);
		setLocationRelativeTo(f);

		// INIT
		tblComponents
				.setModel(new ComponentTableModel(product.getComponents()));
		TableColumnModel tcm = tblComponents.getColumnModel();
		tcm.getColumn(1).setCellRenderer(new DecimalCellRenderer());
	}

	private void initComponents() {
		setLayout(new MigLayout("fill"));

		JPanel top = new JPanel(new MigLayout("fill"));
		JLabel lbl = new JLabel(product.getDescription());
		lbl.setFont(new Font(lbl.getFont().getFamily(), Font.BOLD, 24));
		top.add(lbl, "grow, push");
		btnEdit = new JButton("...");
		top.add(btnEdit);
		add(top, "north");

		tblComponents = new JTable();
		tblComponents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(new JScrollPane(tblComponents), "grow, push, spany 2");
		btnAdd = new JButton("+");
		add(btnAdd, "wrap, pushy");
		btnRemove = new JButton("X");
		add(btnRemove, "pushy");
	}

	private void initListeners() {
		btnEdit.addActionListener(this);
		btnAdd.addActionListener(this);
		btnRemove.addActionListener(this);
	}

	private void lock(boolean b) {
		tblComponents.setEnabled(!b);
		btnEdit.setEnabled(!b);
		btnAdd.setEnabled(!b);
		btnRemove.setEnabled(!b);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEdit) {
			edit();
		} else if (e.getSource() == btnAdd) {
			add();
		} else if (e.getSource() == btnRemove) {
			remove();
		}
	}

	private void edit() { // product
		AddProductPanel pnl = new AddProductPanel();
		pnl.setDescription(product.getDescription());
		pnl.setPrice(product.getBasePrice());
		int res = JOptionPane.showConfirmDialog(this, pnl, Constants.TITLE,
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (res == JOptionPane.CANCEL_OPTION
				|| pnl.getDescription().compareTo("") == 0)
			return;
		product.setDescription(pnl.getDescription());
		product.setBasePrice(pnl.getPrice());
		lock(true);
		Worker w = new Worker(Worker.EDIT);
		w.addParam(product);
		w.execute();
		dispose(); // close everything and go back to main window
	}

	private void add() { // component
		AddProductPanel pnl = new AddProductPanel();
		int res = JOptionPane.showConfirmDialog(this, pnl, Constants.TITLE,
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (res == JOptionPane.CANCEL_OPTION
				|| pnl.getDescription().compareTo("") == 0)
			return;
		String descr = pnl.getDescription();
		double price = pnl.getPrice();
		lock(true);
		Worker w = new Worker(Worker.ADD);
		w.addParam(descr);
		w.addParam(price);
		w.execute();
	}

	private void remove() { // component
		int row = tblComponents.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Select something first!",
					Constants.TITLE, JOptionPane.WARNING_MESSAGE);
			return;
		}
		int id = (Integer) tblComponents.getModel().getValueAt(row, 2); // id
		Worker w = new Worker(Worker.DELETE);
		w.addParam(id);
		w.execute();
	}

	private class Worker extends SwingWorker<Void, Void> {
		public static final int REFRESH = 0;
		public static final int ADD = 1;
		public static final int EDIT = 2;
		public static final int DELETE = 3;

		public final int status;
		public final ArrayList<Object> params;

		public Worker(int status) {
			if (status < 0 || status > 3)
				throw new IllegalArgumentException();
			this.status = status;
			params = new ArrayList<Object>();
		}

		public void addParam(Object o) {
			params.add(o);
		}

		@Override
		protected Void doInBackground() throws Exception {
			switch (status) {
			case REFRESH:
				logic.refreshProduct(product);
			case ADD:
				String descr = (String) params.get(0);
				double price = (Double) params.get(1);
				logic.addComponent(product, descr, price);
				break;
			case EDIT:
				Product e = (Product) params.get(0);
				logic.editProduct(e);
				break;
			case DELETE:
				int id = (Integer) params.get(0);
				logic.removeComponent(id);
				break;
			}
			return null;
		}

		@Override
		protected void done() {
			if (status == REFRESH) {
				List<Component> l = product.getComponents();
				ComponentTableModel tm = new ComponentTableModel(l);
				tblComponents.setModel(tm);
			}
			if (status == ADD || status == DELETE) {
				Worker w = new Worker(REFRESH);
				w.execute();
			}
			lock(false);
		}

	}

}
