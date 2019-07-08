package co.edu.udistrital.rest.message.model;

public class DFPlayerItemResponse {

	public DFPlayerItemResponse(int d, int f) {
		this.d = d;
		this.f = f;
	}

	private int d;

	private int f;

	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}
	
}
