package it.gas.altichierock.insert.print;

import it.gas.altichierock.database.entities.Ticket;
import it.gas.altichierock.database.entities.TicketContent;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.DecimalFormat;

public class TicketPage implements Printable {
	private Ticket ticket;
	private DecimalFormat format;

	public TicketPage(Ticket t) {
		this.ticket = t;
		this.format = new DecimalFormat("0.00");
	}

	@Override
	public int print(Graphics g, PageFormat pf, int pageIndex)
			throws PrinterException {
		Font font = new Font("Serif", Font.PLAIN, 10);
		FontMetrics metrics = g.getFontMetrics(font);
		int lineHeight = metrics.getHeight();
		int linesPerPage = (int) (pf.getImageableHeight() / lineHeight);
		int lines = 2 + ticket.getContent().size();
		int pages = lines / linesPerPage;
		if (lines % linesPerPage != 0)
			pages++;
		if (pageIndex > pages - 1)
			return NO_SUCH_PAGE;

		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		int start = pageIndex * linesPerPage;
		int y = 0;
		for (int i = 0; i <= linesPerPage; i++) {
			//System.out.println("Ciclo! " + pageIndex + ":" + i);
			y += lineHeight;
			if (pageIndex == 0 && i == 0) {
				g.drawString("Ticket n. " + ticket.getTicketid(), 0, y);
			} else if (pageIndex == 0 && i == 1) {
				g.drawString("Date: " + ticket.getCreateDate() + " - Time: "
						+ ticket.getCreateTime(), 0, y);
			} else {
				try {
					TicketContent tc = ticket.getContent().get(start + i - 2);
					String qty = tc.getQuantity() == 0 ? "" : Integer
							.toString(tc.getQuantity());
					String descr = tc.getDescription();
					//System.out.println(descr);
					String price = tc.getPrice() == 0 ? "" : format.format(tc
							.getPrice());
					g.drawString(qty + "\t" + descr + "\t" + price, 0, y);
				} catch (IndexOutOfBoundsException e) {
					g.drawString("", 0, y);
				}
			}
		}
		return PAGE_EXISTS;

	}

}
