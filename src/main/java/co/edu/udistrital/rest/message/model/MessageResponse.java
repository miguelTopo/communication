package co.edu.udistrital.rest.message.model;

import java.io.Serializable;

import co.edu.udistrital.rest.enums.ResponseType;

public class MessageResponse implements Serializable {

	public MessageResponse() {
		this.rt = ResponseType.MSG;
	}

	private String m;

	private String u;

	private String f;

	private String hour;

	private ResponseType rt;

	private char mt;


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

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
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

}
