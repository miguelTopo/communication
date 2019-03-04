package co.edu.udistrital.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.event.model.Event;
import co.edu.udistrital.event.service.EventService;
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
	public ResponseEntity<Response> stateUpdate(@RequestBody Event event){
		if (event == null)
			return ResponseEntity.ok()
				.body(responseService.warnMessage("Registrar usuario", "Ocurrió un error, intente nuevamente o consulte al administrador"));
		return this.eventService.stateUpdate(event);
	}
	
}
