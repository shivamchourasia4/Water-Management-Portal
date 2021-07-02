package com.example.services.signup;

import com.example.dao.signup.SignupDao;
import com.example.model.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupServiceImpl implements SignupService {

    @Autowired
    private SignupDao signupDao;

    @Transactional
    @Override
    public Boolean saveUser(UserModel user) {

        if (signupDao.isUserPresent(user.getEmail())) {
            return false;
        }

        // System.out.println(user.getPassword());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        signupDao.saveUser(user);
        return true;
    }

}
