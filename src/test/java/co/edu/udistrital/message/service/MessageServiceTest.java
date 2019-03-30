package co.edu.udistrital.message.service;

import org.junit.Test;

import co.edu.udistrital.message.model.Message;
import co.edu.udistrital.structure.model.User;


public class MessageServiceTest {
	
	
	@Test
	public void sendTextMessage() {
		Message message = new Message();
		User receiver = new User();
		receiver.setId("222222");
		message.setReceiverUser(receiver);
		User sender = new User();
		sender.setId("111111");
		message.setSenderUser(sender);
		message.setMessageBody("Este es un primewr mensaje que vamos a enviar");
		
	}

}
