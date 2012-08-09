package it.gas.altichierock.order;

import it.gas.altichierock.database.OrderTicket;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.miginfocom.swing.MigLayout;

public class OrderWindow extends JDialog implements OrderBoxListener, Runnable {
	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory.getLogger(OrderWindow.class);
	private Thread t;
	private OrderLogic logic;
	
	private List<OrderBox> boxes;
	
	public OrderWindow(Frame f) {
		super(f, true);
		setTitle("Show(er)");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		logic = new OrderLogic();
		boxes = new ArrayList<OrderBox>();
		initComponents();
		initListeners();
		setSize(500, 500);
		setLocationRelativeTo(f);
	}

	private void initComponents() {
		setLayout(new MigLayout("wrap 5, fill"));
	}
	
	private void initListeners() {
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				stopThread();
				super.windowClosing(arg0);
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				startThread();
				super.windowOpened(arg0);
			}
			
		});
	}

	@Override
	public void orderCompleted(OrderBox ob) {
		remove(ob);
		boxes.remove(ob);
		logic.markAsCompleted(ob.getOrderTicket());
		//if there's no subcomponent, validate does nothing,
		//leaving the last box (just removed) draw on the screen
		if (boxes.size() == 0)
			this.repaint();
		else
			validate();
	}
	
	private void startThread() {
		if (t != null)
			stopThread();
		t = new Thread(this);
		t.start();
	}
	
	private void stopThread() {
		if (t != null)
			t.interrupt();
	}
	
	@Override
	public void run() {
		try {
			while (! Thread.interrupted()) {
				//retrieve the orders not completed
				List<OrderTicket> l = logic.getNonCompletedOrders();
				//remove the one already displayed
				for (int i = 0; i < boxes.size(); i++)
					l.remove(boxes.get(i).getOrderTicket());
				//display the new ones
				addOrder(l);
				Thread.sleep(5000);
			}
		} catch (InterruptedException e) {
			//we're closing, say goodbye!
			log.error("Refresh thread interrupted.", e);
		}
		System.out.println("Interrupted.");
	}
	
	private void addOrder(final List<OrderTicket> l) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				for (int i = 0; i < l.size(); i++) {
					OrderBox ob = makeOrderBox(l.get(i));
					boxes.add(ob); //ADD IT TO THE LIST!
					add(ob, "grow");
				}
				validate();
				
			}
		});
	}

	private OrderBox makeOrderBox(OrderTicket t) {
		//just wrap the order in a box and add the listener
		OrderBox ob = new OrderBox(t);
		ob.addListener(this);
		return ob;
	}

}
