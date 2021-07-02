package com.example.dao.login;

import com.example.model.LoginModel;
import com.example.model.UserModel;

public interface LoginDao {

    Boolean checkUser(LoginModel data);

    UserModel getUserModel(String email);
}
