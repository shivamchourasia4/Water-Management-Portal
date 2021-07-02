package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.model.FeedbackModel;
import com.example.services.feedback.FeedbackService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/addFeedback")
    public ResponseEntity<Map<String, String>> save(@RequestBody FeedbackModel feedbackModel) {
        Map<String, String> resp = new HashMap<>();
        try {

            String status = feedbackService.feedbackSave(feedbackModel);

            resp.put("message", status);
            if (status != "Feedback Added") {
                return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(resp, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            resp.put("message", "Bad Request");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/admin/feedback")
    public List<FeedbackModel> get() {
        return feedbackService.getFeedback();
    }

    @GetMapping("/admin/feedback/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) {

        FeedbackModel feedbackModel = feedbackService.feedbackByid(id);

        if (feedbackModel == null) {
            Map<String, String> resp = new HashMap<>();
            resp.put("message", "Requested Info Not Found");
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(feedbackModel, HttpStatus.OK);
    }

}
