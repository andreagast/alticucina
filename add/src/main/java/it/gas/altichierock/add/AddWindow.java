package it.gas.altichierock.add;

import it.gas.altichierock.database.Constants;
import it.gas.altichierock.database.entities.Product;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.table.TableColumnModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.miginfocom.swing.MigLayout;

public class AddWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory.getLogger(AddWindow.class);
	private JTable tblMenu;
	private JButton btnAdd, btnEdit, btnDelete, btnRefresh;

	private AddLogic logic;

	public AddWindow(Frame w) {
		setTitle("Organizer");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initComponents(); // drawing code
		setSize(400, 300);
		setLocationRelativeTo(w);

		initListeners();

		logic = new AddLogic();
		tblMenu.setModel(new ProductTableModel(logic.getProducts()));
		TableColumnModel tcm = tblMenu.getColumnModel();
		tcm.getColumn(1).setCellRenderer(new DecimalCellRenderer());
	}

	private void initComponents() {
		setLayout(new MigLayout("fill"));

		// draw'n'stuff
		tblMenu = new JTable();
		tblMenu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		btnAdd = new JButton("+");
		btnEdit = new JButton("...");
		btnDelete = new JButton("X");
		btnRefresh = new JButton("><");

		add(new JScrollPane(tblMenu), "push, grow, spany 4");
		add(btnAdd, "wrap, pushy");
		add(btnEdit, "wrap, pushy");
		add(btnDelete, "wrap, pushy");
		add(btnRefresh, "pushy");
	}

	private void initListeners() {
		btnAdd.addActionListener(this);
		btnEdit.addActionListener(this);
		btnDelete.addActionListener(this);
		btnRefresh.addActionListener(this);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				lock(true);
				new Worker(Worker.REFRESH).execute();
			}
		});
	}

	private void lock(boolean b) {
		tblMenu.setEnabled(!b);
		btnAdd.setEnabled(!b);
		btnEdit.setEnabled(!b);
		btnDelete.setEnabled(!b);
		btnRefresh.setEnabled(!b);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdd) {
			add();
		} else if (e.getSource() == btnEdit) {
			edit();
		} else if (e.getSource() == btnDelete) {
			delete();
		} else if (e.getSource() == btnRefresh) {
			lock(true);
			new Worker(Worker.REFRESH).execute();
		}
	}

	private void add() {
		AddProductPanel pnl = new AddProductPanel();
		int res = JOptionPane.showConfirmDialog(this, pnl, Constants.TITLE, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (res == JOptionPane.OK_OPTION) {
			String descr = pnl.getDescription();
			double price = pnl.getPrice();
			if (descr.compareTo("") != 0) {
				Worker w = new Worker(Worker.ADD);
				w.addParam(descr);
				w.addParam(price);
				w.execute();
			}
		}
	}

	private void edit() {
		int row = tblMenu.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Select something first!",
					Constants.TITLE, JOptionPane.WARNING_MESSAGE);
			return;
		}
		int id = (Integer) tblMenu.getModel().getValueAt(row, 2); //hidden id
		Product p = logic.getProduct(id);
		new EditProductDialog(this, logic, p).setVisible(true);
		Worker worker = new Worker(Worker.REFRESH);
		worker.execute();
		log.debug("Edit complete!");
	}

	private void delete() {
		int row = tblMenu.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Select something first!",
					Constants.TITLE, JOptionPane.WARNING_MESSAGE);
			return;
		}
		int id = (Integer) tblMenu.getModel().getValueAt(row, 2); //hidden id
		Worker worker = new Worker(Worker.DELETE);
		worker.addParam(id);
		worker.execute();
	}

	private class Worker extends SwingWorker<Object, Void> {
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
		protected Object doInBackground() throws Exception {
			log.debug("doInBackground");
			switch (status) {
			case REFRESH:
				List<Product> l = logic.getProducts();
				return new ProductTableModel(l);
			case ADD:
				String descr = (String) params.get(0);
				double price = (Double) params.get(1);
				logic.addProduct(descr, price);
				break;
			case EDIT:
				Product e = (Product) params.get(0);
				logic.editProduct(e);
				break;
			case DELETE:
				int id = (Integer) params.get(0);
				logic.removeProduct(id);
				break;
			}
			return null;
		}

		@Override
		protected void done() {
			try {
				if (status == REFRESH) {
					tblMenu.setModel((ProductTableModel) get());
				} else if (status == ADD || status == DELETE){
					lock(true);
					new Worker(REFRESH).execute();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} finally {
				lock(false);
				log.debug("done");
			}
		}

	}

}
