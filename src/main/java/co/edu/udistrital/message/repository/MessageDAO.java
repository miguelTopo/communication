package co.edu.udistrital.message.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

import co.edu.udistrital.core.jdbc.postgres.HibernateUtil;
import co.edu.udistrital.message.model.Message;
import co.edu.udistrital.message.model.Test;

public class MessageDAO extends HibernateUtil {

	public List<Message> findAll() {
		return getSession().createQuery("from Message", Message.class).getResultList();
	}	

	public Message findParentMessage(String senderUserId, String receiverUserId) {
		if (StringUtils.isEmpty(senderUserId) || StringUtils.isEmpty(receiverUserId))
			return null;
		return (Message) getSession()
			.createNativeQuery(
				"SELECT m.id, m.senderUserId, m.parentMessageId FROM Message m, MessageRecipient mr WHERE mr.messageId = m.id AND m.parentMessageId IS NULL AND ( mr.recipientUserId IN(:idRequestSenderUserId) OR  m.senderUserId IN(:idRequestSenderUserId))")
			.setParameterList("idRequestSenderUserId", Arrays.asList(senderUserId, receiverUserId)).setMaxResults(1).getResultStream().findFirst()
			.orElse(new Message());
	}



}
