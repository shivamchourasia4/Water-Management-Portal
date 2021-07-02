package com.example.dao.login;

import java.util.List;

import javax.persistence.EntityManager;

import com.example.model.LoginModel;
import com.example.model.UserModel;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDaoImpl implements LoginDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Boolean checkUser(LoginModel data) {

        // System.out.println(data.getPassword());

        Session currentSession = entityManager.unwrap(Session.class);

        String hql = "FROM UserModel U WHERE U.email = :req_email";
        Query<UserModel> query = currentSession.createQuery(hql, UserModel.class);
        query.setParameter("req_email", data.getEmail());
        List<UserModel> list = query.getResultList();

        if (!list.isEmpty()) {

            String raw = list.get(0).getPassword();
            // System.out.println(list.get(0).getPassword() + " " + data.getPassword());

            BCryptPasswordEncoder b = new BCryptPasswordEncoder();
            if (b.matches(data.getPassword(), raw)) {
                return true;
            }

            return false;
        }

        return false;

    }

    public UserModel getUserModel(String email) {
        Session currentSession = entityManager.unwrap(Session.class);
        String hql = "FROM UserModel U WHERE U.email = :req_email";
        Query<UserModel> query = currentSession.createQuery(hql, UserModel.class);
        query.setParameter("req_email", email);
        List<UserModel> list = query.getResultList();

        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);

    }

}
