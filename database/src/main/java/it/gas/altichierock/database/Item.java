package it.gas.altichierock.database;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;

@Entity
@NamedQueries({
	@NamedQuery(name = "item.ordered", query = "SELECT i FROM Item i ORDER BY i.id"),
	@NamedQuery(name = "item.search", query = "SELECT i FROM Item i WHERE i.id = :id")
})
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false, length = 255)
	private String description;
	@Column(nullable = false)
	private float price;
	private boolean deprecated;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public boolean isDeprecated() {
		return deprecated;
	}
	public void setDeprecated(boolean deprecated) {
		this.deprecated = deprecated;
	}
	
	@Override
	public String toString() {
		return getDescription();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
