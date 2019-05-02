package co.edu.udistrital.rest.message.model;

import java.io.Serializable;

public class MessageRest implements Serializable {


	private String m;

	private String u;

	private String f;

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

}
