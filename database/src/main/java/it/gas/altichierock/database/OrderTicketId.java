package it.gas.altichierock.database;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderTicketId implements Serializable {
	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	private int id;
	//@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date created;
}
