package com.colibrisoft.app_jar;

import com.colibrisoft.app_jar.model.RequestForm;
import com.colibrisoft.app_jar.model.RequestMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@RestController
@RequestMapping
public class RequestService{
    private static final String token = "906702482:AAHjNeLwWupLXfSO3yj7kOEojzj19ubxGbE";

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
        String contentUrl = "username: " + requestForm.getUserName() + "%0A" +
                "phone: " + requestForm.getPhone() + "%0A" +
                "service type: " + requestForm.getServiceType();

        String contentMail = "username: " + requestForm.getUserName() + "\n" +
                "phone: " + requestForm.getPhone() + "\n" +
                "service type: " + requestForm.getServiceType();

        sendToTelegram(contentUrl,"243053893");  //BBro private chat id
        sendToTelegram(contentUrl,"47833754");   //Bosithon private chat id

        simpleMail(new RequestMail("colibrisoftofficial@gmail.com",
                "New request from colibrisoft website",contentMail));
        return ResponseEntity.ok("Message has been sent successfully");
    }

    public static void sendToTelegram(String text,String chatId) {
        new Thread(()->{
            String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
            urlString = String.format(urlString, token, chatId, text);
            try {
                URL url = new URL(urlString);
                URLConnection conn = url.openConnection();
                InputStream is = new BufferedInputStream(conn.getInputStream());
            } catch (IOException e) {
                System.out.println("from: " + chatId);
                e.getCause().getMessage();
            }
            System.out.println("Telegram Thread ended");
        }).start();

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

            System.out.println("Mail Thread ended");
        }).start();
    }
    private String api(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://example.com",String.class);
    }
}
