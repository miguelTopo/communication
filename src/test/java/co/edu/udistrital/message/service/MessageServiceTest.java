package co.edu.udistrital.message.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import co.edu.udistrital.message.controller.MessageController;
import co.edu.udistrital.message.model.Message;
import co.edu.udistrital.message.repository.MessageDAOTest;
import co.edu.udistrital.structure.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MessageServiceTest {

	@Autowired private MessageService messageService;

	private static final Logger log = LoggerFactory.getLogger(MessageServiceTest.class);


	@Test
	public void findAll() {
		List<Message> messageList = this.messageService.findAll();
		System.out.println(messageList.size());
	}

}
