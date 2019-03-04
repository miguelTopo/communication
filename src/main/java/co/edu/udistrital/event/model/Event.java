package co.edu.udistrital.event.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import co.edu.udistrital.event.enums.EventReiterativeType;
import co.edu.udistrital.event.enums.EventType;
import co.edu.udistrital.structure.enums.State;
import co.edu.udistrital.structure.model.User;

@Document(collection = "Event")
public class Event implements Serializable {

	@Id private String id;

	private String userId;

	@JsonFormat(shape = JsonFormat.Shape.NUMBER) private Calendar date;
	private EventType eventType;

	private boolean active;

	private String description;

	private State state;

	private EventReiterativeType eventReiterativeType;

	private List<Integer> rememberDays;

	@Transient private User user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EventReiterativeType getEventReiterativeType() {
		return eventReiterativeType;
	}

	public void setEventReiterativeType(EventReiterativeType eventReiterativeType) {
		this.eventReiterativeType = eventReiterativeType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Integer> getRememberDays() {
		return rememberDays;
	}

	public void setRememberDays(List<Integer> rememberDays) {
		this.rememberDays = rememberDays;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


}
