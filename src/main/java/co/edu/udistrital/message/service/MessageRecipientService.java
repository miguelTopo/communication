package co.edu.udistrital.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udistrital.message.model.MessageRecipient;
import co.edu.udistrital.message.repository.MessageRecipientDAO;
import co.edu.udistrital.message.repository.MessageRecipientRepository;

@Service
public class MessageRecipientService {
	private MessageRecipientDAO messageRecipientDAO;

	private MessageRecipientDAO getMessageRecipientDAO() {
		if (this.messageRecipientDAO == null)
			this.messageRecipientDAO = new MessageRecipientDAO();
		return this.messageRecipientDAO;
	}

	@Transactional
	public boolean save(MessageRecipient messageRecipient) {
		if (messageRecipient == null)
			return false;
		getMessageRecipientDAO().getSession().save(messageRecipient);
		return true;
	}

	public List<MessageRecipient> findMessageTreeTalk(String messageId) {
		return null;
		// return messageRecipientRepository.loadMessageTreeTalk(messageId);
	}

}
