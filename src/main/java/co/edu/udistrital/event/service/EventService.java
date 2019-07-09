package co.edu.udistrital.event.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import co.edu.udistrital.common.util.ZyosCDNFTP;
import co.edu.udistrital.common.util.ZyosCDNResource;
import co.edu.udistrital.core.util.DateUtil;
import co.edu.udistrital.event.enums.EventReiterativeType;
import co.edu.udistrital.event.enums.EventType;
import co.edu.udistrital.event.model.Event;
import co.edu.udistrital.event.repository.EventRepository;
import co.edu.udistrital.rest.enums.ResponseType;
import co.edu.udistrital.rest.message.model.MessageResponse;
import co.edu.udistrital.rest.message.util.DictionaryDFPlayer;
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
			saveEventTtsFile(event);
			this.eventRepository.save(event);
			return ResponseEntity.ok().body(responseService.successMessage("Guardar mensaje", "Perfecto! ya agregaste tu recordatorio"));
		}
		return ResponseEntity.ok().body(validEvent);
	}

	private void saveEventTtsFile(Event event) {
		try {
			String fileName = event.getMultipartFile().getOriginalFilename();
			ZyosCDNResource resource = ZyosCDNResource.getDefaultCDNResource();
			String ext = fileName.substring(fileName.lastIndexOf('.'), fileName.length());
			resource.setFileName(ZyosCDNFTP.getFileName(ext));
			resource.setInputStream(event.getMultipartFile().getInputStream());
			ZyosCDNFTP.uploadFileResource(resource);
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	private String getDfPlayerDescription(String description) {
		//// @formatter:off
		return description
			.replaceAll("60", "sesenta")
			.replaceAll("59", "cincuenta y nueve")
			.replaceAll("58", "cincuenta y ocho")
			.replaceAll("57", "cincuenta y siete")
			.replaceAll("56", "cincuenta y seis")
			.replaceAll("55", "cincuenta y cinco")
			.replaceAll("54", "cincuenta y cuatro")
			.replaceAll("53", "cincuenta y tres")
			.replaceAll("52", "cincuenta y dos")
			.replaceAll("51", "cincuenta y uno")
			.replaceAll("50", "cincuenta")
			.replaceAll("49", "cuarenta y nueve")
			.replaceAll("48", "cuarenta y ocho")
			.replaceAll("47", "cuarenta y siete")
			.replaceAll("46", "cuarenta y seis")
			.replaceAll("45", "cuarenta y cinco")
			.replaceAll("44", "cuarenta y cuatro")
			.replaceAll("43", "cuarenta y tres")
			.replaceAll("42", "cuarenta y dos")
			.replaceAll("41", "cuarenta y uno")
			.replaceAll("40", "cuarenta")
			.replaceAll("39", "treinta y nueve")
			.replaceAll("38", "treinta y ocho")
			.replaceAll("37", "treinta y siete")
			.replaceAll("36", "treinta y seis")
			.replaceAll("35", "treinta y cinco")
			.replaceAll("34", "treinta y cuatro")
			.replaceAll("33", "treinta y tres")
			.replaceAll("32", "treinta y dos")
			.replaceAll("31", "treinta y uno")
			.replaceAll("30", "treinta")
			.replaceAll("29", "veintinueve")
			.replaceAll("28", "veintiocho")
			.replaceAll("27", "veintisiete")
			.replaceAll("26", "veintiseis")
			.replaceAll("25", "veinticinco")
			.replaceAll("24", "veinticuatro")
			.replaceAll("23", "veintitres")
			.replaceAll("22", "veintidos")
			.replaceAll("21", "veintiuno")
			.replaceAll("20", "veinte")
			.replaceAll("19", "diecinueve")
			.replaceAll("18", "dieciocho")
			.replaceAll("17", "diecisiete")
			.replaceAll("16", "dieciséis")
			.replaceAll("15", "quince")
			.replaceAll("14", "catorce")
			.replaceAll("13", "trece")
			.replaceAll("12", "doce")
			.replaceAll("11", "once")
			.replaceAll("10", "diez")
			.replaceAll("9", "nueve")
			.replaceAll("8", "ocho")
			.replaceAll("7", "siete")
			.replaceAll("6", "seis")
			.replaceAll("5", "cinco")
			.replaceAll("4", "cuatro")
			.replaceAll("3", "tres")
			.replaceAll("2", "dos")
			.replaceAll("1", "un");
		// @formatter:on
	}

	private MessageResponse parseToEventResponse(Event ev) {
		if (ev == null)
			return null;
		MessageResponse response = new MessageResponse(ResponseType.EVENT);
		response.setMt('t');
		response.setHour(DateUtil.getTime(ev.getDate()));
		response.setM(ev.getDescription());
		String messageToPlay = MessageFormat.format("Son las: {0} horas y {1} minutos. Recuerde: {2} ", ev.getDate().get(Calendar.HOUR_OF_DAY),
			ev.getDate().get(Calendar.MINUTE), ev.getDescription());
		response.setDf(DictionaryDFPlayer.getPhrase(getDfPlayerDescription(messageToPlay)));
		return response;
	}


	public MessageResponse getByCurrentHour(String homeUserId, Calendar calendar) {
		List<Event> eventList = eventListByDate(homeUserId, calendar, true);
		return CollectionUtils.isEmpty(eventList) ? new MessageResponse(ResponseType.EVENT) : parseToEventResponse(eventList.get(0));
	}


	public List<Event> eventListByDate(String homeUserId, Calendar date) {
		return eventListByDate(homeUserId, date, false);
	}

	private List<Event> getEventBySpecificDateOrReiterativeEveryDay(Calendar date, List<Event> eventList) {
		List<EventReiterativeType> reiterativeTypeList = new ArrayList<>();
		reiterativeTypeList.add(EventReiterativeType.EVERY_DAY);
		reiterativeTypeList.add(EventReiterativeType.ANNUALY);

		Predicate<Event> specficDate =
			e -> e.getEventType().equals(EventType.SPECIFIC_DATE) && DateUtil.getTime(e.getDate()).equals(DateUtil.getTime(date));

		Predicate<Event> reiterativeEveryDate = e -> e.getEventType().equals(EventType.REITERATIVE)
			&& reiterativeTypeList.contains(e.getEventReiterativeType()) && DateUtil.getTime(e.getDate()).equals(DateUtil.getTime(date));
		return eventList.stream().filter(specficDate.or(reiterativeEveryDate)).collect(Collectors.toList());
	}

	private List<Event> getEventByReiterativeType(Calendar date, List<Event> eventList, boolean returnFirst) {
		List<Event> reiterativeList = eventList.stream().filter(e -> e.getEventType().equals(EventType.REITERATIVE)).collect(Collectors.toList());
		List<Event> eventListResponse = new ArrayList<>(1);
		boolean addToList = false;
		for (Event e : reiterativeList) {
			addToList = false;
			if (e.getEventReiterativeType().equals(EventReiterativeType.HOURLY))
				addToList = validAddHourlyEvent(e, date);
			else if (e.getEventReiterativeType().equals(EventReiterativeType.MONTHLY))
				addToList = validAddMonthlyEvent(e, date);
			else if (e.getEventReiterativeType().equals(EventReiterativeType.SPECIFIC_DAYS))
				addToList = validAddSpecificDaysEvent(e, date);
			else if (e.getEventReiterativeType().equals(EventReiterativeType.WEEKLY))
				addToList = validAddWeeklyEvent(e, date);

			if (addToList) {
				if (returnFirst)
					return Arrays.asList(e);
				else
					eventListResponse.add(e);
			}

		}
		return eventListResponse;
	}

	private List<Event> eventListByDate(String homeUserId, Calendar date, boolean returnFirst) {
		List<Event> eventList = this.eventRepository.findByUserIdAndState(homeUserId, State.ACTIVE);
		if (CollectionUtils.isEmpty(eventList))
			return Collections.emptyList();

		eventList = eventList.stream().sorted(Comparator.comparing(Event::getDate)).collect(Collectors.toList());

		// Eventos que aplican por ser del día actual y la hora actual
		List<Event> eventListResponse = new ArrayList<>(1);
		eventListResponse.addAll(getEventBySpecificDateOrReiterativeEveryDay(date, eventList));
		if (!CollectionUtils.isEmpty(eventListResponse) && returnFirst)
			return Arrays.asList(eventListResponse.get(0));

		eventListResponse.addAll(getEventByReiterativeType(date, eventList, returnFirst));
		return eventListResponse;
	}



	private boolean validAddWeeklyEvent(Event e, Calendar date) {
		if (e == null || date == null)
			return false;
		return DateUtil.getTime(e.getDate()).equals(DateUtil.getTime(date)) && e.getRememberDays().contains(date.get(Calendar.DAY_OF_WEEK));
	}

	private boolean validAddSpecificDaysEvent(Event e, Calendar date) {
		if (e == null || date == null || CollectionUtils.isEmpty(e.getRememberDays()))
			return false;
		return DateUtil.getTime(date).equals(DateUtil.getTime(e.getDate())) && e.getRememberDays().contains(date.get(Calendar.DAY_OF_WEEK));
	}

	private boolean validAddMonthlyEvent(Event e, Calendar date) {
		if (e == null || date == null)
			return false;
		return DateUtil.getTime(e.getDate()).equals(DateUtil.getTime(date))
			&& date.get(Calendar.DAY_OF_MONTH) == e.getDate().get(Calendar.DAY_OF_MONTH);
	}

	private boolean validAddHourlyEvent(Event e, Calendar date) {
		if (e == null || date == null)
			return false;
		return date.get(Calendar.MINUTE) == e.getDate().get(Calendar.MINUTE);
	}



}
