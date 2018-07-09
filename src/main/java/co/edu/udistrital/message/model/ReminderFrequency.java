package co.edu.udistrital.message.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.udistrital.structure.enums.DataState;
import lombok.Data;

@Data
@Document(collection = "ReminderFrequency")
public class ReminderFrequency {

	@Id
	private String id;

	private String title;

	private Long frequency;

	private DataState dataState;

}
