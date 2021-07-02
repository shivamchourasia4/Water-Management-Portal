package com.example.dao.feedback;

import java.util.List;

import javax.persistence.EntityManager;

import com.example.model.FeedbackModel;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FeedbackDaoImpl implements FeedbackDao {

    @Autowired
    private EntityManager entitymanager;

    @Override
    public String feedbackSave(FeedbackModel data) {

        Session currentSession = entitymanager.unwrap(Session.class);
        currentSession.save(data);

        return "Feedback Added";
    }

    @Override
    public List<FeedbackModel> getFeedback() {
        Session currentSession = entitymanager.unwrap(Session.class);
        String hql = "FROM FeedbackModel";
        Query<FeedbackModel> query = currentSession.createQuery(hql, FeedbackModel.class);
        List<FeedbackModel> list = query.getResultList();

        return list;
    }

    @Override
    public FeedbackModel feedbackByid(String id) {

        Session currentSession = entitymanager.unwrap(Session.class);
        FeedbackModel feedbackModel = currentSession.get(FeedbackModel.class, id);
        return feedbackModel;
    }

    @Override
    public String feedbackDelete(String id) {
        try {
            Session currentSession = entitymanager.unwrap(Session.class);
            FeedbackModel feedbackModel = currentSession.get(FeedbackModel.class, id);
            currentSession.delete(feedbackModel);
            return "Feedback Deleted";
        } catch (Exception e) {
            return "Requested Feedback Does Not Exist!";
        }
    }

}

// System.out.println(id);
// Session currentSession = entitymanager.unwrap(Session.class);
// String hql = "DELETE FROM feedback_table WHERE feedback_id = :f_id";
// Query<FeedbackModel> query = currentSession.createQuery(hql,
// FeedbackModel.class);
// query.setParameter("f_id", id);
// query.executeUpdate();
// return "Feedback Deleted";