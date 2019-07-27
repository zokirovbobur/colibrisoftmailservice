package com.colibrisoft.app_jar;

import com.colibrisoft.app_jar.model.RequestForm;
import com.colibrisoft.app_jar.model.RequestMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping
public class RequestService {
    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping
    public boolean isActive(){
        return true;
    }

    @PostMapping("sendMail")
    public ResponseEntity<Object> emailAPI(@RequestBody RequestMail requestMail){
        simpleMail(requestMail);
        return ResponseEntity.ok("Message has been sent successfully");
    }

    @PostMapping("userRequest")
    public ResponseEntity<Object> userRequest(@RequestBody RequestForm requestForm){
        String content = "username: " + requestForm.getUserName() + "\n" +
                "phone: " + requestForm.getPhone() + "\n" +
                "service type: " + requestForm.getServiceType();
        simpleMail(new RequestMail("colibrisoftofficial@gmail.com",
                "New request from colibrisoft website",content));
        return ResponseEntity.ok("Message has been sent successfully");
    }

    private void simpleMail(RequestMail requestMail){
        String email = requestMail.getEmailAddress();
        String title = requestMail.getTitle();
        String content = requestMail.getContent();

        new Thread(()->{
            SimpleMailMessage msg = new SimpleMailMessage();

            msg.setTo("zokirovbobur93@gmail.com",email);
            msg.setSubject(title);
            msg.setText(content);
            javaMailSender.send(msg);
        }).start();
    }
    private String api(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://example.com",String.class);
    }
}
