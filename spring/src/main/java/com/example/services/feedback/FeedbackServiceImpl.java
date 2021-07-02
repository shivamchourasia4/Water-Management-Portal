package com.example.services.feedback;

import java.util.List;

import com.example.dao.feedback.FeedbackDao;
import com.example.model.FeedbackModel;
import com.example.model.WaterModel;
import com.example.services.water.WaterServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private WaterServiceImpl waterServiceImpl;

    @Autowired
    private FeedbackDao feedbackDao;

    @Override
    @Transactional
    public String feedbackSave(FeedbackModel data) {
        try {

            if (data.getWaterId() == null) {
                return "Invalid WaterID, Cannot Save Feedback!";
            }

            WaterModel waterModel = waterServiceImpl.getWaterInfoById(data.getWaterId());

            waterModel.getFeedback().add(data);

            waterServiceImpl.WaterInfoUpdate(waterModel);
        } catch (Exception e) {
            return "Bad Request";
        }

        return feedbackDao.feedbackSave(data);

    }

    @Override
    @Transactional
    public List<FeedbackModel> getFeedback() {

        return feedbackDao.getFeedback();
    }

    @Override
    @Transactional
    public FeedbackModel feedbackByid(String id) {

        return feedbackDao.feedbackByid(id);
    }

    @Override
    @Transactional
    public String feedbackDelete(String id) {
        return feedbackDao.feedbackDelete(id);
    }

}
