package co.edu.udistrital.rest.message.model;

import java.io.Serializable;
import java.util.List;

import co.edu.udistrital.rest.enums.ResponseType;

public class MessageResponse implements Serializable {

	public MessageResponse(ResponseType rt) {
		this.rt = rt;
	}

	private String m;

	private String u;

	private String hour;

	private ResponseType rt;

	private char mt;

	private List<DFPlayerItemResponse> df;

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public String getU() {
		return u;
	}

	public void setU(String u) {
		this.u = u;
	}

	public char getMt() {
		return mt;
	}

	public void setMt(char mt) {
		this.mt = mt;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
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
