package it.gas.altichierock.database;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int _id;
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date created;
	private boolean completed;
	private boolean served;
	@OneToMany
	@JoinColumn(name = "orderId")
	private List<Detail> detail;
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
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
	public List<Detail> getDetail() {
		return detail;
	}
	public void setDetail(List<Detail> detail) {
		this.detail = detail;
	}
	
	
}
