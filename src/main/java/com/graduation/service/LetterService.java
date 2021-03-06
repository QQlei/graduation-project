package com.graduation.service;


import com.graduation.domain.Letter;
import com.graduation.domain.User;
import com.graduation.domain.enums.LetterType;
import com.graduation.domain.view.LetterSummary;
import com.graduation.domain.view.LetterView;
import com.graduation.repository.LetterRepository;
import com.graduation.repository.UserRepository;
import com.graduation.utils.DateUtils;
import com.graduation.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JiangL on 2018/3/8.
 */
@Service
public class LetterService{

    @Autowired
    private LetterRepository letterRepository;
    @Resource
    private NoticeService noticeService;
    @Autowired
    private UserRepository userRepository;

    /**
     * 发送私信
     * @param letter
     */
    public void sendLetter(Letter letter){
        if("original".equals(letter.getSendType())){
            letter.setType(LetterType.ORIGINAL);
        }else{
            letter.setType(LetterType.REPLY);
            List<String> userNameList = StringUtil.getAtUser(letter.getContent());
            if(null != userNameList && userNameList.size() > 0){
                User receiveUser = userRepository.findByUserName(userNameList.get(0));
                if(null != receiveUser){
                    letter.setReceiveUserId(receiveUser.getId());
                }
                String content = letter.getContent().substring(0,letter.getContent().indexOf("@"));
                if(StringUtils.isBlank(content)){
                    content = letter.getContent().substring(letter.getContent().indexOf("@")+receiveUser.getUserName().length()+1,letter.getContent().length());
                    letter.setContent(content);
                }
            }
        }
        letter.setCreateTime(DateUtils.getCurrentTime());
        letterRepository.save(letter);
        if(null == letter.getPid()){
            letter.setPid(letter.getId());
            letterRepository.updatePidById(letter.getId(),letter.getId());
        }
        // 添加消息通知
        noticeService.saveNotice(null,"letter",letter.getReceiveUserId(),String.valueOf(letter.getId()));
    }

    /**
     * 私信信息查询
     * @param userId
     * @param pageable
     * @return
     */
    public List<LetterSummary> findLetter(Long userId, Pageable pageable){
        List<LetterView> viewList = letterRepository.findLetterByReceiveUserId(userId,pageable);
        List<LetterSummary> summaryList = new ArrayList<LetterSummary>();
        for(LetterView view : viewList){
            LetterSummary summary = new LetterSummary(view);
            summaryList.add(summary);
        }
        return summaryList;
    }


}
