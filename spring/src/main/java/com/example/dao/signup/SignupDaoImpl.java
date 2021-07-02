package com.example.dao.signup;

import java.util.List;

import javax.persistence.EntityManager;

import com.example.model.UserModel;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SignupDaoImpl implements SignupDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Boolean saveUser(UserModel user) {

        // System.out.println(user.getEmail() + " " + user.getPassword());

        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.save(user);

        return true;
    }

    public Boolean isUserPresent(String email) {
        Session currentSession = entityManager.unwrap(Session.class);

        String hql = "FROM UserModel U WHERE U.email = :req_email";
        Query<UserModel> query = currentSession.createQuery(hql, UserModel.class);
        query.setParameter("req_email", email);
        List<UserModel> list = query.getResultList();

        if (!list.isEmpty()) {
            return true;
        }

        return false;

    }

}
