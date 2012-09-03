package it.gas.altichierock.order;

import it.gas.altichierock.database.entities.Ticket;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.miginfocom.swing.MigLayout;

public class OrderWindow extends JFrame implements OrderBoxListener, Runnable {
	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory.getLogger(OrderWindow.class);
	private Thread t;
	private OrderLogic logic;

	private List<OrderBox> boxes;

	public OrderWindow(Frame f) {
		// super(f, true);
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
		setLayout(new MigLayout("wrap 5, fill, ins 0"));
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
		logic.markAsCompleted(ob.getTicket());
		// if there's no subcomponent, validate does nothing,
		// leaving the last box (just removed) draw on the screen
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
			while (!Thread.interrupted()) {
				//max 10 boxes on the screen
				if (boxes.size() < 10) {
					// retrieve the orders not completed
					List<Ticket> l = logic.getNonCompletedOrders();
					// remove the one already displayed
					for (int i = 0; i < boxes.size(); i++)
						l.remove(boxes.get(i).getTicket());
					// display the new ones
					addOrder(l);
				}
				Thread.sleep(5000);
			}
		} catch (InterruptedException e) {
			// we're closing, say goodbye!
			log.warn("Refresh thread interrupted.", e);
		}
		log.debug("Refresh thread stopped.");
	}

	private void addOrder(final List<Ticket> l) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int maxToAdd = 10 - boxes.size(); //no more than 10 boxes!
				if (l.size() < maxToAdd)
					maxToAdd = l.size();
				for (int i = 0; i < maxToAdd; i++) {
					OrderBox ob = makeOrderBox(l.get(i));
					boxes.add(ob); // ADD IT TO THE LIST!
					add(ob, "grow, height 50%:75%:100%");
				}
				validate();
			}
		});
	}

	private OrderBox makeOrderBox(Ticket t) {
		// just wrap the order in a box and add the listener
		OrderBox ob = new OrderBox(t);
		ob.addListener(this);
		return ob;
	}

}
