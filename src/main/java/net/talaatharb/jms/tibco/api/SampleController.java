package net.talaatharb.jms.tibco.api;

import net.talaatharb.jms.tibco.model.CalculatorReq;
import net.talaatharb.jms.tibco.service.InvokeAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import net.talaatharb.jms.tibco.service.JmsSenderService;

@RestController
public class SampleController {

	@Autowired
	private JmsSenderService jmsService;

	@Autowired
	private InvokeAPI invokeAPI;

	@PostMapping(path = "/api/message", produces = MediaType.APPLICATION_JSON_VALUE)
	public String send(@RequestBody String message) {
		try {
			jmsService.send(message);
			return String.format("{\"message\":\"%s\"}", message);
		} catch (JmsException e) {
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
