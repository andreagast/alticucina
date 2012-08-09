package it.gas.altichierock.mainApp;

import it.gas.altichierock.add.AddWindow;
import it.gas.altichierock.database.DatabaseHandler;
import it.gas.altichierock.display.DisplayWindow;
import it.gas.altichierock.insert.InsertWindow;
import it.gas.altichierock.order.OrderWindow;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
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
		initListeners();
		pack();
		setLocationRelativeTo(null);
		
		lock(true);
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
	
	private void initListeners() {
		//working code
		btnInsert.addActionListener(this);
		btnAdd.addActionListener(this);
		btnOrder.addActionListener(this);
		btnDisplay.addActionListener(this);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				//lock the module and start the loading
				lock(true);
				new DBLoader().execute();
			}
		});
	}
	
	private void lock(boolean b) {
		btnInsert.setEnabled(!b);
		btnAdd.setEnabled(!b);
		btnOrder.setEnabled(!b);
		btnDisplay.setEnabled(!b);
	}
	
	private void showMessage(String str) {
		//JOptionPane.showMessageDialog(this, str);
		Object[] options = {"OK", "Configure..."};
		int res = JOptionPane.showOptionDialog(this, str, Constants.TITLE,
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[0]);
		if (res == JOptionPane.NO_OPTION) {
			String ip = JOptionPane.showInputDialog(this, "Insert the new db address:", Constants.TITLE, JOptionPane.QUESTION_MESSAGE);
			if (ip == null) //dialog cancelled
				return;
			Properties p = new Properties();
			p.put("database.ip", ip);
			File f = new File("altichierock.properties");
			try {
				p.store(new FileOutputStream(f), "Use the program!");
			} catch (FileNotFoundException e) {
				e.printStackTrace(); //TODO
			} catch (IOException e) {
				e.printStackTrace(); //TODO
			}
			JOptionPane.showMessageDialog(this, "Done. Next time this program shuld use\nthe new settings.", Constants.TITLE, JOptionPane.INFORMATION_MESSAGE);
		}
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
			//System.out.println("display");
			new DisplayWindow(this).setVisible(true);
		}
	}
	
	//initialize the DB
	private class DBLoader extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			DatabaseHandler.getInstance();
			return null;
		}

		@Override
		protected void done() {
			try {
				get();
				lock(false);
			} catch (ExecutionException e) {
				e.printStackTrace();
				showMessage("Huh oh, there's a problem with the DB.\n" +
						"Check the server and try again.");
				dispose();
			} catch (InterruptedException e) {
				//it should never happen...
				e.printStackTrace();
				dispose();
			}
		}
		
		
		
	}
	
}
