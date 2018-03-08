package com.graduation.repository;

import com.graduation.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by JiangL on 2018/2/23.
 */
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
