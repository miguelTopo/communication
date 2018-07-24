package co.edu.udistrital.structure.model;

import java.util.List;

import co.edu.udistrital.structure.enums.Severity;

public class Response {

	private String title;
	private String message;
	private Severity severity;
	private List<?> list;
	private Object entity;
	private boolean existInDB;

	public Response() {}

	public Response(String title, String message, Severity severity) {
		this.title = title;
		this.message = message;
		this.severity = severity;
	}

	public Response(String title, String message, Severity severity, Object entity) {
		this.title = title;
		this.message = message;
		this.severity = severity;
		this.entity = entity;
	}

	public Response(String title, String message, Severity severity, List<?> list) {
		this.title = title;
		this.message = message;
		this.severity = severity;
		this.list = list;
	}

	public Response(String title, String message, Severity severity, boolean existInDB) {
		this.title = title;
		this.message = message;
		this.severity = severity;
		this.existInDB = existInDB;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public boolean isExistInDB() {
		return existInDB;
	}

	public void setExistInDB(boolean existInDB) {
		this.existInDB = existInDB;
	}

}
