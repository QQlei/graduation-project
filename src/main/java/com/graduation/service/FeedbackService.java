package com.graduation.service;

import com.graduation.domain.Feedback;
import com.graduation.repository.FeedbackRepository;
import com.graduation.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by JiangL on 2018/3/23.
 */
@Service("feedbackService")
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public void saveFeeddback(Feedback feedback, Long userId) {
        feedback.setUserId(userId == null || userId == 0L ? null : userId);
        feedback.setCreateTime(DateUtils.getCurrentTime());
        feedback.setLastModifyTime(DateUtils.getCurrentTime());
        feedbackRepository.save(feedback);
    }
}
