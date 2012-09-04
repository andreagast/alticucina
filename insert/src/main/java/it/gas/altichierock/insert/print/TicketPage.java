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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicketPage implements Printable {
	private static final int HEADER_ROWS = 3;
	private static final int FOOTER_ROWS = 2;
	private DecimalFormat format;
	private Logger log = LoggerFactory.getLogger(TicketPage.class);
	
	private Ticket ticket;
	private double totalPrice;

	public TicketPage(Ticket t) {
		if (t == null)
			throw new NullPointerException();
		this.ticket = t;
		totalPrice = 0;
		for (int i = 0; i < t.getContent().size(); i++) {
			TicketContent tc = t.getContent().get(i);
			double temp = tc.getQuantity() * tc.getPrice();
			totalPrice += temp;
		}
		this.format = new DecimalFormat("0.00");
	}

	@Override
	public int print(Graphics g, PageFormat pf, int pageIndex)
			throws PrinterException {
		Font font = new Font("Serif", Font.PLAIN, 10);
		FontMetrics metrics = g.getFontMetrics(font);
		int lineHeight = metrics.getHeight();

		int linesPerPage = (int) (pf.getImageableHeight() / lineHeight);
		log.debug("linesPerPage: " + linesPerPage);
		int totalLines = HEADER_ROWS + ticket.getContent().size() + FOOTER_ROWS;
		int totalPages = totalLines / linesPerPage;
		if (totalLines % linesPerPage != 0)
			totalPages++;
		log.debug("totalPages: " + totalPages);
		if (pageIndex > totalPages - 1)
			return NO_SUCH_PAGE;

		// calculate lines to draw in this page
		int thisLines = 0;
		thisLines = totalLines - (linesPerPage * pageIndex);
		if (thisLines > linesPerPage)
			thisLines = linesPerPage;

		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		
		int start = pageIndex * linesPerPage;
		int y = 0;
		for (int i = 0; i <= thisLines - 1; i++) {
			log.debug(pageIndex + ":" + i);
			// log.debug("Ciclo! " + pageIndex + ":" + i);
			y += lineHeight;
			// draw header & footer
			if (pageIndex == 0 && i == 0) {
				g.drawString("Ticket n. " + ticket.getTicketid(), 0, y);
			} else if (pageIndex == 0 && i == 1) {
				g.drawString("Date: " + ticket.getCreateDate() + " - Time: "
						+ ticket.getCreateTime(), 0, y);
			} else if (pageIndex == 0 && i == 2) {
				g.drawString("", 0, y);
			} else if (pageIndex == totalPages - 1 && i == thisLines - 2) {
				g.drawString("--------", 0, y);
			} else if (pageIndex == totalPages - 1 && i == thisLines - 1) {
				g.drawString("TOTAL: € " + format.format(totalPrice), 0, y);
			} else {
				// draw content
				try {
					// remember to skip header rows
					TicketContent tc = ticket.getContent().get(
							start + i - HEADER_ROWS);
					String qty = tc.getQuantity() == 0 ? "" : Integer
							.toString(tc.getQuantity());
					String descr = tc.getDescription();
					String price = tc.getPrice() == 0 ? "" : format.format(tc
							.getPrice());
					g.drawString(qty + "\t" + descr + "\t" + price, 0, y);
				} catch (IndexOutOfBoundsException e) {
					log.warn("This should not happen.", e);
				}
			}
		}
		return PAGE_EXISTS;

	}

}
