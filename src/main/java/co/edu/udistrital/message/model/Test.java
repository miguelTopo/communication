package co.edu.udistrital.message.model;


import javax.persistence.Entity;
import javax.persistence.Table;

import co.edu.udistrital.common.model.CoreEntity;

@Entity
@Table(name = "Test")
public class Test extends CoreEntity {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
