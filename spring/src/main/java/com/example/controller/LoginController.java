package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.JWT.GenerateToken;
import com.example.model.LoginModel;
import com.example.services.login.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<List<Map<String, String>>> save(@RequestBody LoginModel user) {

        List<Map<String, String>> lst = new ArrayList<Map<String, String>>();

        Map<String, String> resp = new HashMap<>();

        String status = String.valueOf(loginService.checkUser(user));

        resp.put("message", status);
        lst.add(resp);
        if (status == "true") {
            lst.add(GenerateToken.generateJWTToken(loginService.getUserModel(user.getEmail())));
        }

        return new ResponseEntity<>(lst, status == "true" ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
