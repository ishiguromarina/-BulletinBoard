package beans;

import java.io.Serializable;

public class Branch implements Serializable {
	private static final long serialVersionUID = 1l;

	private int id;
	private String name;

	// getter, setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
