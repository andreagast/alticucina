package it.gas.altichierock.order;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import it.gas.altichierock.database.Detail;
import it.gas.altichierock.database.OrderTicket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

public class OrderBox extends JComponent implements ActionListener {
	private static final long serialVersionUID = 1L;
	private OrderTicket order;
	private JButton btnCompleted;
	private JLabel lblId;
	
	private List<OrderBoxListener> listeners;
	
	public OrderBox(OrderTicket order) {
		this.order = order;
		listeners = new ArrayList<OrderBoxListener>();
		initComponents();
		initListeners();
		fillDetail();
	}
	
	private void initComponents() {
		setLayout(new MigLayout("fill, wrap 2", "[][grow]"));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JPanel top = new JPanel(new MigLayout());
		lblId = new JLabel(order.getId().getCreated() + " - " + order.getId().getId(), SwingConstants.CENTER);
		
		top.add(new JLabel("Order ID: "));
		top.add(lblId, "push"); //TODO
		add(top, "north");
		
		//panel detail
		
		btnCompleted = new JButton("Order completed");
		add(btnCompleted, "south");
	}
	
	private void initListeners() {
		btnCompleted.addActionListener(this);
	}
	
	private void fillDetail() {
		List<Detail> l = order.getDetail();
		for (int i = 0; i < l.size(); i++) {
			add(new JLabel("" + l.get(i).getQuantity()));
			add(new JLabel(l.get(i).getItemId().getDescription()));
		}
	}
	
	public int getId() {
		if (order != null)
			return order.getId().getId();
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
	
	public OrderTicket getOrderTicket() {
		return order;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnCompleted) {
			fireOrderCompleted();
		}
	}

}
