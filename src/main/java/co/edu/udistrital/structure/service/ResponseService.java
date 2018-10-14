package co.edu.udistrital.structure.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.udistrital.structure.enums.Severity;
import co.edu.udistrital.structure.model.Response;

@Service
public class ResponseService {

	public ResponseService() {
		// default empty constructor
	}

	public Response warnMessage(String title, String message) {
		return new Response(title, message, Severity.WARN);
	}

	public Response infoMessage(String title, String message) {
		return new Response(title, message, Severity.INFO);
	}

	public Response errorMessage(String title, String message) {
		return new Response(title, message, Severity.ERROR);
	}

	public Response successMessage(String title, String message) {
		return new Response(title, message, Severity.SUCCESS);
	}

	public Response warnResponse(String title, String message, Object entity) {
		return new Response(title, message, Severity.WARN, entity);
	}

	public Response infoResponse(String title, String message, Object entity) {
		return new Response(title, message, Severity.INFO, entity);
	}

	public Response errorResponse(String title, String message, Object entity) {
		return new Response(title, message, Severity.ERROR, entity);
	}

	public Response successResponse(String title, String message, Object entity) {
		return new Response(title, message, Severity.SUCCESS, entity);
	}

	public Response warnResponse(String title, String message, List<?> list) {
		return new Response(title, message, Severity.WARN, list);
	}

	public Response infoResponse(String title, String message, List<?> list) {
		return new Response(title, message, Severity.INFO, list);
	}

	public Response errorResponse(String title, String message, List<?> list) {
		return new Response(title, message, Severity.ERROR, list);
	}

	public Response successResponse(String title, String message, List<?> list) {
		return new Response(title, message, Severity.SUCCESS, list);
	}

	public Response warnResponse(String title, String message) {
		return new Response(title, message, Severity.WARN);
	}

	public Response infoResponse(String title, String message) {
		return new Response(title, message, Severity.INFO);
	}

	public Response errorResponse(String title, String message) {
		return new Response(title, message, Severity.ERROR);
	}

	public Response successResponse(String title, String message) {
		return new Response(title, message, Severity.SUCCESS);
	}

	public Response warnResponse(String title, String message, Response response) {
		response.setTitle(title);
		response.setMessage(message);
		response.setSeverity(Severity.WARN);
		return response;
	}

	public Response infoResponse(String title, String message, Response response) {
		response.setTitle(title);
		response.setMessage(message);
		response.setSeverity(Severity.INFO);
		return response;
	}

	public Response errorResponse(String title, String message, Response response) {
		response.setTitle(title);
		response.setMessage(message);
		response.setSeverity(Severity.ERROR);
		return response;
	}

	public Response successResponse(String title, String message, Response response) {
		response.setTitle(title);
		response.setMessage(message);
		response.setSeverity(Severity.SUCCESS);
		return response;
	}
}
