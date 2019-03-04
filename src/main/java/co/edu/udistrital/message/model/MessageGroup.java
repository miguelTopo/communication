package co.edu.udistrital.message.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import co.edu.udistrital.structure.enums.State;
import co.edu.udistrital.structure.model.User;

public class MessageGroup {

	@Id @GeneratedValue(generator = "increment") @GenericGenerator(name = "increment", strategy = "increment") private String id;
	private String name;
	private Date creationDate;
	private State dataState;
	private List<String> adminIdList;
	private List<User> adminList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public State getDataState() {
		return dataState;
	}

	public void setDataState(State dataState) {
		this.dataState = dataState;
	}

	public List<String> getAdminIdList() {
		return adminIdList;
	}

	public void setAdminIdList(List<String> adminIdList) {
		this.adminIdList = adminIdList;
	}

	public List<User> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<User> adminList) {
		this.adminList = adminList;
	}

}
