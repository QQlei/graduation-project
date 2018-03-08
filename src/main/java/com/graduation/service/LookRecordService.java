package com.graduation.service;

import com.graduation.domain.view.CollectSummary;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 浏览记录service接口
 * Created by JiangL
 */
public interface LookRecordService {

    public void saveLookRecord(Long userId, Long collectId);

    public void deleteLookRecord(Long userId, Long collectId);

    public void deleteLookRecordByUserID(Long userId);

    public List<CollectSummary> getLookRecords(Long userId, Pageable pageable);

}
