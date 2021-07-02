package com.example.services.login;

import com.example.model.LoginModel;
import com.example.model.UserModel;

public interface LoginService {
    Boolean checkUser(LoginModel data);

    UserModel getUserModel(String email);
}
