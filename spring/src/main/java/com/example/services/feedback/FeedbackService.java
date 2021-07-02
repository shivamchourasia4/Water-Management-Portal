package com.example.services.feedback;

import java.util.List;

import com.example.model.FeedbackModel;

public interface FeedbackService {
    String feedbackSave(FeedbackModel data);

    List<FeedbackModel> getFeedback();

    FeedbackModel feedbackByid(String id);

    String feedbackDelete(String id);
}
