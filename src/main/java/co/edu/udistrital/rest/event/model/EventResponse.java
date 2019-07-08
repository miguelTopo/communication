package co.edu.udistrital.rest.event.model;

import java.io.Serializable;
import java.util.List;

import co.edu.udistrital.rest.enums.ResponseType;
import co.edu.udistrital.rest.message.model.DFPlayerItemResponse;

public class EventResponse implements Serializable {

	public EventResponse() {
		this.rt = ResponseType.EVENT;
	}

	private String date;

	private String desc;

	private ResponseType rt;

	private List<DFPlayerItemResponse> df;

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

	public List<DFPlayerItemResponse> getDf() {
		return df;
	}

	public void setDf(List<DFPlayerItemResponse> df) {
		this.df = df;
	}

}
