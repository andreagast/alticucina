package it.gas.altichierock.display;

import it.gas.altichierock.database.Constants;
import it.gas.altichierock.database.entities.Ticket;

import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.miginfocom.swing.MigLayout;

public class DisplayWindow extends JDialog implements Runnable {
	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory.getLogger(DisplayWindow.class);
	private DisplayLogic logic;
	private Thread t;
	private ArrayList<DisplayBox> tickets;

	public DisplayWindow(Frame f) {
		super(f, true);
		setTitle("Displayer");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initComponents();
		initListeners();
		setSize(500, 500);
		setLocationRelativeTo(f);
		
		logic = new DisplayLogic();
		tickets = new ArrayList<DisplayBox>();
	}

	private void initComponents() {
		setLayout(new MigLayout("fill, wrap 5"));
		
		JPanel pnlTop = new JPanel(new MigLayout("fill"));
		add(pnlTop, "north");
		
		JLabel lblTop = new JLabel("Now serving:");
		lblTop.setFont(new Font(lblTop.getFont().getFamily(), Font.PLAIN, 24));
		pnlTop.add(lblTop);
		
		JLabel lblTwitter = new JLabel(Constants.TWITTER, SwingConstants.RIGHT);
		pnlTop.add(lblTwitter, "grow");
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
	
	private void showTickets(final List<Ticket> l) {
		if (l.size() == 0)
			return; //useless to start a thread for doing nothing
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < l.size(); i++) {
					DisplayBox box = new DisplayBox(l.get(i));
					box.addMouseListener(new MouseLabelClick());
					tickets.add(box);
					add(box, "growx");
					validate();
				}
			}
		});
	}

	@Override
	public void run() {
		try {
			while (! Thread.interrupted()) {
				List<Ticket> l = logic.getCompletedNotServed();
				//remove duplicate
				for (int i = 0; i < tickets.size(); i++)
					l.remove(tickets.get(i).getOrderTicket());
				//add what's left
				showTickets(l);
				//wait
				Thread.sleep(5000);
			}
		} catch (InterruptedException e) {
			log.error("Refresh thread interrupted.", e);
			//time to say goodbye!
		}
	}
	
	private class MouseLabelClick extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			final DisplayBox lbl = (DisplayBox) arg0.getSource();
			log.debug("Click on {}", lbl.getText());
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					logic.markAsDisplayed(lbl.getOrderTicket());
				}
			}).start();
			
			
			tickets.remove(lbl);
			remove(lbl);
			if (tickets.size() == 0)
				repaint();
			else
				validate();
		}
		
	}
	
}
