package co.edu.udistrital.event.service;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import co.edu.udistrital.event.enums.EventReiterativeType;
import co.edu.udistrital.event.enums.EventType;
import co.edu.udistrital.event.model.Event;
import co.edu.udistrital.event.repository.EventRepository;
import co.edu.udistrital.structure.enums.State;
import co.edu.udistrital.structure.model.Response;
import co.edu.udistrital.structure.service.ResponseService;

@Service
public class EventService {

	private final EventRepository eventRepository;
	private final ResponseService responseService;

	@Autowired
	public EventService(@Lazy EventRepository eventRepository, @Lazy ResponseService responseService) {
		this.eventRepository = eventRepository;
		this.responseService = responseService;
	}

	private Response validEventDateAndDescription(Event event) {
		if (event == null)
			return responseService.warnMessage("Guardar recordatorio", "Los datos enviados no se lograron leer");
		if (event.getDate() == null)
			return responseService.warnMessage("Guardar recordatorio", "Agregue una fecha y hora para el recordatorio");
		if (StringUtils.isEmpty(event.getDescription()))
			return responseService.warnMessage("Guardar recordatorio", "Agregue una descripción al recordatorio");
		return responseService.successMessage("Guardar recordatorio", "OK");
	}

	private Response validEventSpecificDays(Event event) {
		if (event == null)
			return responseService.warnMessage("Guardar recordatorio", "Los datos enviados no se lograron leer");
		Response response = validEventDateAndDescription(event);
		if (!response.isBooleanResponse())
			return response;
		if (CollectionUtils.isEmpty(event.getRememberDays()))
			return responseService.warnMessage("Guardar recordatorio", "Debe seleccionar por lo menos un día al recordatorio");
		return responseService.successMessage("Guardar recordatorio", "OK");
	}

	private Response validEventWeekly(Event event) {
		if (event == null)
			return responseService.warnMessage("Guardar recordatorio", "Los datos enviados no se lograron leer");
		Response response = validEventDateAndDescription(event);
		if (!response.isBooleanResponse())
			return response;
		if (CollectionUtils.isEmpty(event.getRememberDays()) || event.getRememberDays().size() > 1)
			return responseService.warnMessage("Guardar recordatorio", "Debe seleccionar un día de recordatorio");
		return responseService.successMessage("Guardar recordatorio", "OK");
	}

	private Response validEventReiterativeType(Event event) {
		if (event == null)
			return responseService.warnMessage("Guardar recordatorio", "Los datos enviados no se lograron leer");
		if (event.getEventReiterativeType() == null)
			return responseService.warnMessage("Guardar recordatorio", "Seleccione una opción de repetición para el recordatorio");
		if (event.getEventReiterativeType().equals(EventReiterativeType.SPECIFIC_DAYS))
			return validEventSpecificDays(event);
		else if (event.getEventReiterativeType().equals(EventReiterativeType.WEEKLY))
			return validEventWeekly(event);
		return validEventDateAndDescription(event);
	}

	private Response validEvent(Event event) {
		if (event == null)
			return responseService.warnMessage("Guardar recordatorio", "Los datos enviados no se lograron leer");
		if (event.getEventType() == null)
			return responseService.warnMessage("Guardar recordatorio", "Seleccione un tipo de recordatorio");

		// Es un evento para una fecha específica
		if (event.getEventType().equals(EventType.SPECIFIC_DATE))
			return validEventDateAndDescription(event);
		// Es un evento reiterativo
		else
			return validEventReiterativeType(event);
	}


	public ResponseEntity<Response> save(Event event) {
		if (event == null)
			return ResponseEntity.ok()
				.body(responseService.warnMessage("Guardar recordatorio", "Ocurrió un error, intente nuevamente o consulte al administrador"));
		Response validEvent = validEvent(event);
		if (validEvent.isBooleanResponse()) {
			event.setActive(true);
			event.setState(State.ACTIVE);
			this.eventRepository.save(event);
			return ResponseEntity.ok().body(responseService.successMessage("Guardar mensaje", "Perfecto! ya agregaste tu recordatorio"));
		}
		return ResponseEntity.ok().body(validEvent);
	}

	public ResponseEntity<Response> findByUserIdAndState(String userId) {
		List<Event> eventList = this.eventRepository.findByUserIdAndState(userId, State.ACTIVE);
		boolean emptyList = CollectionUtils.isEmpty(eventList);
		return ResponseEntity.ok().body(responseService.successResponse("Consultar eventos",
			emptyList ? "No se encontraron registros" : MessageFormat.format("Se encontraron {0} registros", eventList.size()), eventList));
	}

	public ResponseEntity<Response> stateUpdate(Event event) {
		if (event == null)
			return ResponseEntity.ok()
				.body(responseService.warnMessage("Recordatorio", "Ocurrió un error, intente nuevamente o consulte al administrador"));
		Optional<Event> optional = this.eventRepository.findById(event.getId());
		if (optional.isPresent()) {
			Event e = optional.get();
			e.setActive(event.isActive());
			this.eventRepository.save(e);
			return ResponseEntity.ok()
				.body(responseService.successMessage("Recordatorio", e.isActive() ? "El recordatorio se activó" : "El recordatorio se inactivó"));
		} else {
			return ResponseEntity.ok().body(responseService.warnMessage("Recordatorio", "No se encontró un evento con el Id enviado"));
		}
	}

}
