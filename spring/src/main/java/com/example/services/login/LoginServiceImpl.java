package com.example.services.login;

import com.example.dao.login.LoginDao;
import com.example.model.LoginModel;
import com.example.model.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginDao loginDao;

    @Override
    @Transactional
    public Boolean checkUser(LoginModel data) {

        return loginDao.checkUser(data);

    }

    @Override
    @Transactional
    public UserModel getUserModel(String email) {
        return loginDao.getUserModel(email);
    }

}
