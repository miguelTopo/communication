package co.edu.udistrital.message.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.udistrital.structure.enums.State;

@Document(collection = "ReminderFrequency")
public class ReminderFrequency {

	@Id private String id;
	private String title;
	private Long frequency;
	private State state;

	public Long getFrequency() {
		return frequency;
	}

	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}

	public State getState() {
		return state;
	}

	public void setState(State dataState) {
		this.state = dataState;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



}
