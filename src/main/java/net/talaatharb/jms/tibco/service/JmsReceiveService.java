package net.talaatharb.jms.tibco.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class JmsReceiveService {

    Logger logger = Logger.getLogger(JmsReceiveService.class.getName());
    @JmsListener(destination = "cep.v1.configuration.ruledesigner")
    public void receive(Object message) {
        logger.info("Received EMS <" + message + ">");
    }


//    @JmsListener(destination = "cep.v1.stream.event.fraudengine")
//    public void receive2(Object message) {
//        logger.info("Received EMS <" + message + ">");
//    }


//    @KafkaListener(topics = "test", groupId = "group_id")
//    public void receiveKafka(String message) {
//        logger.info("Received KAFKA <" + message + ">");
//    }
}
