package it.gas.altichierock.display;

import it.gas.altichierock.database.entities.Ticket;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class DisplayBox extends JLabel {
	private static final long serialVersionUID = 1L;
	private Ticket ticket;
	
	public DisplayBox(Ticket t) {
		super("" + t.getId(), SwingConstants.CENTER);
		this.ticket = t;
		
		//set the font
		Font f = new Font(getFont().getFamily(), Font.BOLD, 48);
		setFont(f);
	}
	
	public Ticket getOrderTicket() {
		return ticket;
	}
	
}
