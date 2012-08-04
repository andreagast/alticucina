package it.gas.altichierock.order;

import it.gas.altichierock.database.OrderTicket;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;

import net.miginfocom.swing.MigLayout;

public class OrderWindow extends JDialog implements OrderBoxListener, Runnable {
	private static final long serialVersionUID = 1L;
	private Thread t;
	private OrderLogic logic;
	
	private List<OrderBox> boxes;
	
	public OrderWindow() {
		logic = new OrderLogic();
		boxes = new ArrayList<OrderBox>();
		initComponents();
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
		} else {
			if (t != null)
				t.interrupt();
		}
		super.setVisible(b);
	}

	@Override
	public void orderCompleted(OrderBox ob) {
		this.remove(ob);
		boxes.remove(ob);
	}
	
	@Override
	public void run() {
		try {
			//retrieve the orders not completed
			List<OrderTicket> l = logic.getNonCompletedOrders();
			//remove the one already displayed
			for (int i = 0; i < boxes.size(); i++)
				l.remove(boxes.get(i).getOrderTicket());
			//display the new ones
			for (int i = 0; i < l.size(); i++) {
				OrderBox ob = new OrderBox(l.get(i));
				add(ob);
			}
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			//we're closing, say goodbye!
			System.out.println(e.getMessage());
		}
	}

}
