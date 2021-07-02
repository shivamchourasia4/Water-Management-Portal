package com.example.dao.signup;

import com.example.model.UserModel;

public interface SignupDao {

    Boolean saveUser(UserModel user);

    Boolean isUserPresent(String email);
}
