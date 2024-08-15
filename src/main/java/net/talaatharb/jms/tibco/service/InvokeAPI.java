package net.talaatharb.jms.tibco.service;

import net.talaatharb.jms.tibco.model.CalculatorReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.mail.SimpleMailMessage;

import java.util.HashMap;
import java.util.Map;

@Service
public class InvokeAPI {
    @Autowired
    private JavaMailSender emailSender;

    RestTemplate restTemplate = new RestTemplate();

    public HttpEntity<String> invokeAPI(CalculatorReq req) {
        String url = "http://localhost:8109/Channels/HTTPChannel/Calculator";
        Map<String,Object> map = new HashMap<>();
        map.put("operation", req.getOperation());
        map.put("number1", req.getNumber1());
        map.put("number2", req.getNumber2());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        return restTemplate.exchange(builder.toUriString(), HttpMethod.POST, new HttpEntity<>(headers), String.class);
    }
    public void sendResetPasswordEmail(String email, String resetPasswordUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset Password");
        message.setText("yahya");
        emailSender.send(message);
    }
}

