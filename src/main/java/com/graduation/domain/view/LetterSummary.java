package com.graduation.domain.view;

import com.graduation.utils.DateUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by JiangL on 2018/3/8.
 */
@Data
public class LetterSummary implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long sendUserId;
    private String sendUserName;
    private String profilePicture;
    private String content;
    private String createTime;
    private Long pid;
    private String type;

    public LetterSummary(LetterView view){
        this.id = view.getId();
        this.sendUserId = view.getSendUserId();
        this.sendUserName = view.getSendUserName();
        this.profilePicture = view.getProfilePicture();
        this.content = view.getContent();
        this.createTime = DateUtils.getTimeFormatText(view.getCreateTime());
        this.pid = view.getPid();
        this.type = view.getType();
    }

}
