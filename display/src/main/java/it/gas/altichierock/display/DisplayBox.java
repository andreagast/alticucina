package it.gas.altichierock.display;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import it.gas.altichierock.database.OrderTicket;

public class DisplayBox extends JLabel {
	private static final long serialVersionUID = 1L;
	private OrderTicket ticket;
	
	public DisplayBox(OrderTicket t) {
		super("" + t.getId().getId(), SwingConstants.CENTER);
		this.ticket = t;
		
		//set the font
		Font f = new Font(getFont().getFamily(), Font.BOLD, 48);
		setFont(f);
	}
	
	public OrderTicket getOrderTicket() {
		return ticket;
	}
	
}
