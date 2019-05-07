package co.edu.udistrital.event.controller;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.core.util.DateUtil;
import co.edu.udistrital.event.model.Event;
import co.edu.udistrital.event.service.EventService;
import co.edu.udistrital.rest.event.model.EventResponse;
import co.edu.udistrital.structure.model.Response;
import co.edu.udistrital.structure.service.ResponseService;

@RestController
@RequestMapping(path = "/event")
public class EventController {

	private final ResponseService responseService;
	private final EventService eventService;

	@Autowired
	public EventController(ResponseService responseService, EventService eventService) {
		this.responseService = responseService;
		this.eventService = eventService;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/save")
	public ResponseEntity<Response> save(@RequestBody Event event) {
		if (event == null)
			return ResponseEntity.ok()
				.body(responseService.warnMessage("Registrar usuario", "Ocurrió un error, intente nuevamente o consulte al administrador"));
		return eventService.save(event);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/list")
	public ResponseEntity<Response> list(@RequestParam String userId) {
		if (StringUtils.isEmpty(userId))
			return ResponseEntity.ok()
				.body(responseService.warnMessage("Consultar eventos", "Ocurrió un error, intente nuevamente o consulte al administrador"));
		return eventService.findByUserIdAndState(userId);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/stateUpdate")
	public ResponseEntity<Response> stateUpdate(@RequestBody Event event) {
		if (event == null)
			return ResponseEntity.ok()
				.body(responseService.warnMessage("Registrar usuario", "Ocurrió un error, intente nuevamente o consulte al administrador"));
		return this.eventService.stateUpdate(event);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/eventListByDate")
	public List<Event> eventListByDate(@RequestParam("homeUserId") String homeUserId, @RequestParam("date") Calendar date) {
		if (StringUtils.isEmpty(homeUserId) || date == null)
			return Collections.emptyList();
		return this.eventService.eventListByDate(homeUserId, date);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/byHour")
	public EventResponse getByCurrentHour(@RequestParam("homeUserId") String homeUserId) {
		if (StringUtils.isEmpty(homeUserId))
			return null;
		return this.eventService.getByCurrentHour(homeUserId, DateUtil.getCurrentCalendar());
	}


}
