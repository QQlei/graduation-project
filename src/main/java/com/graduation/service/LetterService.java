package com.graduation.service;

import com.graduation.domain.Letter;
import com.graduation.domain.view.LetterSummary;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by JiangL on 2018/3/8.
 */
public interface LetterService {

    public void sendLetter(Letter letter);

    public List<LetterSummary> findLetter(Long userId, Pageable pageable);
}
