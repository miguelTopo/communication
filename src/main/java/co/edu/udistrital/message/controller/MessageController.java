package co.edu.udistrital.message.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messageRequest")
public class MessageController {
	
	public String sendMessage(){
		return "";
	}
	
}
