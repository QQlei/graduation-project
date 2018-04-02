package com.graduation.service;


import com.graduation.domain.LookRecord;
import com.graduation.domain.Praise;
import com.graduation.domain.view.CollectSummary;
import com.graduation.domain.view.CollectView;
import com.graduation.repository.CommentRepository;
import com.graduation.repository.LookRecordRepository;
import com.graduation.repository.PraiseRepository;
import com.graduation.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by JiangL on 2018/3/12.
 */
@Service("lookRecordService")
public class LookRecordService{

    @Autowired
    private LookRecordRepository lookRecordRepository;

    @Autowired
    private PraiseRepository praiseRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CollectService collectService;

    public void saveLookRecord(Long userId,Long collectId) {
        if(userId != null && userId > 0 && collectId != null) {
            Integer count = lookRecordRepository.countByUserIdAndCollectId(userId, collectId);
            if (count > 0) {
                lookRecordRepository.updatelastModifyTime(userId, collectId, DateUtils.getCurrentTime());
            } else {
                LookRecord lookRecord = new LookRecord();
                lookRecord.setUserId(userId);
                lookRecord.setCollectId(collectId);
                lookRecord.setCreateTime(DateUtils.getCurrentTime());
                lookRecord.setLastModifyTime(DateUtils.getCurrentTime());
                lookRecordRepository.save(lookRecord);
            }
        }

    }

    public void deleteLookRecord(Long userId, Long collectId) {
        lookRecordRepository.deleteByUserIdAndCollectId(userId,collectId);
    }

    public void deleteLookRecordByUserID(Long userId) {
        lookRecordRepository.deleteByUserId(userId);
    }

    public List<CollectSummary> getLookRecords(Long userId, Pageable pageable) {
        Page<CollectView> views = null;

        views = lookRecordRepository.findViewByUserId(userId, pageable);

        return convertCollect(views,userId);
    }

    /**
     * @author JiangL
     * @date 2018年3月12日
     * @return
     */
    private List<CollectSummary> convertCollect(Page<CollectView> views,Long userId) {
        List<CollectSummary> summarys=new ArrayList<>();
        for (CollectView view : views) {
            CollectSummary summary=new CollectSummary(view);
            summary.setPraiseCount(praiseRepository.countByCollectId(view.getId()));
            summary.setCommentCount(commentRepository.countByCollectId(view.getId()));
            Praise praise=praiseRepository.findByUserIdAndCollectId(userId, view.getId());
            if(praise!=null){
                summary.setPraise(true);
            }else{
                summary.setPraise(false);
            }
            summary.setCollectTime(DateUtils.getTimeFormatText(view.getCreateTime()));
            summarys.add(summary);
        }
        return summarys;
    }


}
