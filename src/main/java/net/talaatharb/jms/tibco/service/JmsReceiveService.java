package net.talaatharb.jms.tibco.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class JmsReceiveService {

    Logger logger = Logger.getLogger(JmsReceiveService.class.getName());
    @JmsListener(destination = "queue.test.receive")
    public void receive(String message) {
        logger.info("Received <" + message + ">");
    }
}
