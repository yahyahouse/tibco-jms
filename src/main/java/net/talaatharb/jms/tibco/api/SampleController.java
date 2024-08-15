package net.talaatharb.jms.tibco.api;

import net.talaatharb.jms.tibco.model.CalculatorReq;
import net.talaatharb.jms.tibco.model.QueueRequest;
import net.talaatharb.jms.tibco.service.InvokeAPI;
import org.apache.kafka.common.KafkaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import net.talaatharb.jms.tibco.service.JmsSenderService;

import javax.jms.JMSException;

@RestController
public class SampleController {

	@Autowired
	private JmsSenderService jmsService;


	@Autowired
	private InvokeAPI invokeAPI;

	@PostMapping(path = "/api/message/queue", produces = MediaType.APPLICATION_JSON_VALUE)
	public String sendQueue(@RequestBody QueueRequest message, @RequestParam String queue) {
		try {
			jmsService.send(message,queue);

			return String.format(message.toString());
		} catch (JmsException | JMSException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage());
		}
    }
	@PostMapping(path = "/api/message/topic", produces = MediaType.APPLICATION_JSON_VALUE)
	public String sendTopic(@RequestBody String message) {
		try {
			jmsService.sendTopic(message);
			return String.format("{\"message\":\"%s\"}", message);
		} catch (KafkaException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage());
		}
	}
	@PostMapping(path = "/api/calculator")
	public ResponseEntity<HttpEntity<String>> invoke (@RequestBody CalculatorReq calculatorReq){
		HttpEntity<String> responseEntity = invokeAPI.invokeAPI(calculatorReq);
		return new ResponseEntity<>(responseEntity, HttpStatus.OK);
	}
}
