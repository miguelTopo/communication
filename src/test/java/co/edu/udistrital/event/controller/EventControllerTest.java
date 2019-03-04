package co.edu.udistrital.event.controller;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.udistrital.event.enums.EventType;
import co.edu.udistrital.event.model.Event;

public class EventControllerTest {

	@Test
	public void serializeEvent() {
		try {
			Event e = new Event();
			e.setDate(Calendar.getInstance());
			e.setDescription("yfu");
			e.setEventType(EventType.SPECIFIC_DATE);
			
			System.out.println(e.getDate().getTimeInMillis()); 
			

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(e);
			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
    

}
