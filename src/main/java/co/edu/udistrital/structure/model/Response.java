package co.edu.udistrital.structure.model;

import co.edu.udistrital.structure.enums.ResponseSeverity;

public class Response {

	private String message;

	private ResponseSeverity responseSeverity;

	private String response;

	public Response() {
	}

	public Response(ResponseSeverity responseSeverity, String message) {
		this.responseSeverity = responseSeverity;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResponseSeverity getResponseSeverity() {
		return responseSeverity;
	}

	public void setResponseSeverity(ResponseSeverity responseSeverity) {
		this.responseSeverity = responseSeverity;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Response successResponse(Object o) {
		this.setResponseSeverity(ResponseSeverity.SUCCESS);
		return this;
	}

	public Response infoResponse(Object o) {
		this.setResponseSeverity(ResponseSeverity.INFO);
		return this;
	}

	public Response warnResponse(Object o) {
		this.setResponseSeverity(ResponseSeverity.WARN);
		return this;
	}

	public Response errorResponse(Object o) {
		this.setResponseSeverity(ResponseSeverity.ERROR);
		return this;
	}

}
