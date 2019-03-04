package co.edu.udistrital.core.util;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JsonUtil {

	private JsonUtil() {}

	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	public static <T> List<T> asList(String jsonArray, Class<?> classInstance) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			TypeFactory typeFactory = objectMapper.getTypeFactory();
			return objectMapper.readValue(jsonArray, typeFactory.constructCollectionType(List.class, classInstance));
		} catch (JsonParseException e) {
			logger.error("JsonParseException Error class JsonUtil method asObject ", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException Error class JsonUtil method asObject ", e);
		} catch (IOException e) {
			logger.error("IOException Error class JsonUtil method asObject ", e);
		}
		return Collections.emptyList();
	}

	public static Object asObject(String json, Class<?> classInstance) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(json, classInstance);
		} catch (JsonParseException e) {
			logger.error("JsonParseException Error class JsonUtil method asObject ", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException Error class JsonUtil method asObject ", e);
		} catch (IOException e) {
			logger.error("IOException Error class JsonUtil method asObject ", e);
		}
		return new Object();
	}

	public static String asString(Object obj) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(obj).toString();
		} catch (JsonProcessingException e) {
			logger.error("JsonProcessingException Error class JsonUtil method asString ", e);
		}
		return "";
	}

}
