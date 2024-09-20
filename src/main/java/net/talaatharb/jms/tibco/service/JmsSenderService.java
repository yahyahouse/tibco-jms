package net.talaatharb.jms.tibco.service;

import javax.jms.*;

import lombok.extern.slf4j.Slf4j;
import net.talaatharb.jms.tibco.model.QueueRequest;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

@Service
@Slf4j
public class JmsSenderService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

//    @Value("${ems.queue}")
//    private String queue;

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

//    Session session;
//    MessageProducer msgProducer = null;
//    Destination destination = session.createQueue(queue);

    public JmsSenderService() throws JMSException {
        // TODO document why this constructor is empty
    }

//    public void send(final QueueRequest request,String queue) throws JMSException {
//        JSONObject jsonObject = new JSONObject(request);
//        String msg = XML.toString(jsonObject);
//        log.info("Sending <{}> to <{}>", msg, queue);
//        log.info("Sending {} to {}", jsonObject, queue);
//
////        Message jmsMsg = session.createTextMessage();
////        jmsMsg.setStringProperty("_ns_", "www.tibco.com/be/ontology/Events/Definitions/EventDefinition");
////        jmsMsg.setStringProperty("_nm_", "EventDefinition");
//        MessageCreator jmsMsg = session -> session.createTextMessage(jsonObject.toString());
//        MessageCreator msgXML = session -> session.createTextMessage(msg.toString());
//        jmsTemplate.send(queue, new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                return msgXML.createMessage(session);
//            }
//        });
//
////        msgProducer.send(destination,jmsMsg);
//    }

    public void send(final Map<String, Object> msg1,String queue) {
//        jmsTemplate.send(queue, new MessageCreator() {
////            JSONObject jsonObject = new JSONObject(msg1);
////        String msg = XML.toString(jsonObject);
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                TextMessage message = session.createTextMessage();
//                message.setText(msg1.toString());
//                message.
//                return message;
//            }});
        jmsTemplate.convertAndSend(queue, msg1);
//        jmsTemplate.convertAndSend(msg1);
    }

    public void sendTopic(final String msg) {
        kafkaTemplate.send(topic,"1", msg);
    }
}