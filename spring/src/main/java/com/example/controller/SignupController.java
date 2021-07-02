package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.JWT.GenerateToken;
import com.example.model.UserModel;
import com.example.services.signup.SignupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class SignupController {

    @Autowired
    private SignupService signupservice;

    private boolean validate(String format, String entry) {
        Pattern pattern = Pattern.compile(format);

        Matcher matcher = pattern.matcher(entry);

        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    @PostMapping("/signup")
    public ResponseEntity<List<Map<String, String>>> save(@RequestBody UserModel user) {

        List<Map<String, String>> lst = new ArrayList<Map<String, String>>();

        Map<String, String> resp = new HashMap<>();

        String emailFormat = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        ;

        String mobileFormat = "[6-9]{1}[0-9]{9}";

        try {

            if (!validate(emailFormat, user.getEmail())) {
                resp.put("message", "false");
                resp.put("error", "Not a Valid Email!");
                lst.add(resp);
                return new ResponseEntity<>(lst, HttpStatus.BAD_REQUEST);
            }
            if (!validate(mobileFormat, user.getMobileNumber())) {
                resp.put("message", "false");
                resp.put("error", "Not a Valid Mobile Number!");
                lst.add(resp);
                return new ResponseEntity<>(lst, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            resp.put("message", "false");
            lst.add(resp);
            return new ResponseEntity<>(lst, HttpStatus.BAD_REQUEST);
        }

        try {
            String status = String.valueOf(signupservice.saveUser(user));
            resp.put("message", status);
            if (status == "true") {
                lst.add(resp);
                lst.add(GenerateToken.generateJWTToken(user));
            } else {
                resp.put("error", "User With That Email Already Exists!");
                lst.add(resp);
            }
            return new ResponseEntity<>(lst, status == "true" ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            resp.put("message", "false");
            lst.add(resp);
            return new ResponseEntity<>(lst, HttpStatus.BAD_REQUEST);
        }

    }

}
