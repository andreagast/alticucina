package it.gas.altichierock.order;

import it.gas.altichierock.database.entities.Ticket;
import it.gas.altichierock.database.entities.TicketContent;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class OrderBox extends JComponent implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Ticket order;
	private JButton btnCompleted;
	private JLabel lblId;
	
	private List<OrderBoxListener> listeners;
	
	public OrderBox(Ticket order) {
		this.order = order;
		listeners = new ArrayList<OrderBoxListener>();
		initComponents();
		initListeners();
		fillDetail();
	}
	
	private void initComponents() {
		setLayout(new MigLayout("fill, wrap"));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//NORTH
		
		JPanel top = new JPanel(new MigLayout());
		lblId = new JLabel(order.getCreateDate() + " - " +
				order.getId());
		Font f = lblId.getFont();
		f = new Font(f.getFamily(), Font.BOLD, f.getSize());
		lblId.setFont(f);
		
		top.add(new JLabel("Order ID: "));
		top.add(lblId, "push, grow");
		add(top, "north");
		
		//SOUTH
		
		JPanel bottom = new JPanel(new MigLayout());
		add(bottom, "south");
		
		JLabel lblNote = new JLabel(order.getNote());
		f = lblNote.getFont();
		f = new Font(f.getFamily(), Font.ITALIC, f.getSize());
		lblNote.setFont(f);
		bottom.add(lblNote, "push");
		
		btnCompleted = new JButton("Order completed");
		bottom.add(btnCompleted, "south");
	}
	
	private void initListeners() {
		btnCompleted.addActionListener(this);
	}
	
	private void fillDetail() {
		List<TicketContent> l = order.getContent();
		for (int i = 0; i < l.size(); i++) {
			TicketContent tc = l.get(i);
			String quantity = "";
			if (tc.getQuantity() != 0)
				quantity = Integer.toString(tc.getQuantity());
			add(new JLabel(quantity), "split 2");
			add(new JLabel(tc.getDescription()), "growx");
		}
		add(Box.createGlue(), "push, grow");
	}
	
	public int getId() {
		if (order != null)
			return order.getId();
		return -1;
	}
	
	public void addListener(OrderBoxListener obl) {
		listeners.add(obl);
	}
	
	public void removeListener(OrderBoxListener obl) {
		listeners.remove(obl);
	}
	
	private void fireOrderCompleted() {
		for (int i = 0; i < listeners.size(); i++)
			listeners.get(i).orderCompleted(this);
	}
	
	public Ticket getTicket() {
		return order;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnCompleted) {
			fireOrderCompleted();
		}
	}

}
