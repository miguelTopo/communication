package co.edu.udistrital.message.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.udistrital.structure.enums.DataState;
import co.edu.udistrital.structure.model.User;

@Document(collection = "MessageGroup")
public class MessageGroup {

	@Id
	private String id;

	private String name;

	private Date creationDate;

	private DataState dataState;

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

	public DataState getDataState() {
		return dataState;
	}

	public void setDataState(DataState dataState) {
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
