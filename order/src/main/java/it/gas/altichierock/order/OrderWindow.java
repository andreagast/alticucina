package it.gas.altichierock.order;

import it.gas.altichierock.database.OrderTicket;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

public class OrderWindow extends JDialog implements OrderBoxListener, Runnable {
	private static final long serialVersionUID = 1L;
	private Thread t;
	private OrderLogic logic;
	
	private List<OrderBox> boxes;
	
	public OrderWindow(Frame f) {
		super(f, true);
		setTitle("Menu creator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		logic = new OrderLogic();
		boxes = new ArrayList<OrderBox>();
		initComponents();
		setSize(500, 500);
		setLocationRelativeTo(f);
	}

	private void initComponents() {
		setLayout(new MigLayout("wrap 5, fill"));
	}
	
	@Override
	public void setVisible(boolean b) {
		if (b) {
			if (t != null) t.interrupt();
			t = new Thread(this);
			t.start();
			System.out.println("visible true");
		} else {
			if (t != null)
				t.interrupt();
			System.out.println("visible false");
		}
		super.setVisible(b);
	}

	@Override
	public void orderCompleted(OrderBox ob) {
		remove(ob);
		boxes.remove(ob);
		logic.markAsCompleted(ob.getOrderTicket());
		validate();
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
			System.out.println(e.getMessage());
		}
	}
	
	private void addOrder(final List<OrderTicket> l) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				for (int i = 0; i < l.size(); i++) {
					add(makeOrderBox(l.get(i)));
				}
				validate();
				
			}
		});
	}
	
	private OrderBox makeOrderBox(OrderTicket t) {
		OrderBox ob = new OrderBox(t);
		ob.addListener(this);
		return ob;
	}

}
