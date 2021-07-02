package com.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import com.example.model.MailModel;
import com.example.services.mail.SendMailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin
public class MailController {

    @Autowired
    private SendMailService mailService;

    @PostMapping(value = "/admin/sendmail")
    public ResponseEntity<?> post(@RequestBody MailModel mail) {

        Map<String, String> resp = new HashMap<>();
        try {
            mailService.sendEmail(mail.getTo(), mail.getBody(), mail.getTopic());
        } catch (MessagingException e) {
            resp.put("message", "Error Occured!");
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {

            resp.put("message", "Error Request");
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            resp.put("message", "Bad Request2");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        resp.put("message", "Email Sent Successfully!");

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}
