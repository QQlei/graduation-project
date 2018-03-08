package com.graduation.service;

import com.graduation.domain.Feedback;

/**
 * Created by JiangL on 2018/2/23.
 */
public interface FeedbackService {

    public void saveFeeddback(Feedback feedback, Long userId);
}
