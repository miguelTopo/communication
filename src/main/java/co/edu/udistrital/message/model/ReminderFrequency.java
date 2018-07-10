package co.edu.udistrital.message.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.udistrital.structure.enums.DataState;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Document(collection = "ReminderFrequency")
public class ReminderFrequency {

	@Id
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String title;

	@Getter
	@Setter
	private Long frequency;

	@Getter
	@Setter
	private DataState dataState;

}
