package co.edu.udistrital.rest.event.model;

import co.edu.udistrital.rest.enums.ResponseType;

public class EventResponse {

	public EventResponse() {
		this.rt = ResponseType.EVENT;
	}

	private String date;

	private String desc;

	private ResponseType rt;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public ResponseType getRt() {
		return rt;
	}

	public void setRt(ResponseType rt) {
		this.rt = rt;
	}

}
