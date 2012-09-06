package it.gas.altichierock.database.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
@NamedQueries({
		@NamedQuery(name = "order.notcomplete", query = "SELECT o FROM Ticket o WHERE o.completed = FALSE"),
		@NamedQuery(name = "order.notserved", query = "SELECT o FROM Ticket o WHERE o.completed = TRUE AND o.served = FALSE"),
		@NamedQuery(name = "order.maxidtoday", query = "SELECT MAX(i.ticketid) FROM Ticket i WHERE i.createDate = CURRENT_DATE") })
public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Date createDate;
	private Time createTime;
	private int ticketid; // generated programmatically
	private boolean completed;
	private boolean served;
	@Column(length = 2000)
	private String note;
	@OneToMany
	@JoinColumn(name = "ticketid")
	@OrderBy("lineNumber")
	private List<TicketContent> content;
	
	public Ticket() {
		content = new ArrayList<TicketContent>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Time getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Time createTime) {
		this.createTime = createTime;
	}

	public int getTicketid() {
		return ticketid;
	}

	public void setTicketid(int ticketid) {
		this.ticketid = ticketid;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean isServed() {
		return served;
	}

	public void setServed(boolean served) {
		this.served = served;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<TicketContent> getContent() {
		return content;
	}

	public void setContent(List<TicketContent> content) {
		this.content = content;
	}

}
