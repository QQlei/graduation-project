package com.graduation.service;


import com.graduation.domain.Notice;
import com.graduation.domain.User;
import com.graduation.domain.view.CollectSummary;
import com.graduation.domain.view.CollectView;
import com.graduation.domain.view.CommentView;
import com.graduation.repository.CommentRepository;
import com.graduation.repository.NoticeRepository;
import com.graduation.repository.PraiseRepository;
import com.graduation.repository.UserRepository;
import com.graduation.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("noticeService")
public class NoticeService{
	
	@Autowired
	private NoticeRepository noticeRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PraiseRepository praiseRepository;
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 保存消息通知
	 * @param collectId
	 * @param type
	 * @param userId
	 * @param operId
	 */
	public void saveNotice(String collectId,String type,Long userId,String operId){
		Notice notice = new Notice();
		if(StringUtils.isNotBlank(collectId)){
			notice.setCollectId(collectId);
		}
		notice.setReaded("unread");
		notice.setType(type);
		if(StringUtils.isNotBlank(operId)){
			notice.setOperId(operId);
		}
		notice.setUserId(userId);
		notice.setCreateTime(DateUtils.getCurrentTime());
		noticeRepository.save(notice);
	}

	/**
	 * 展示消息通知
	 * @param type
	 * @param userId
	 * @param pageable
	 */
	public List<CollectSummary> getNoticeCollects(String type, Long userId, Pageable pageable) {
		Page<CollectView> views = noticeRepository.findViewByUserIdAndType(userId, type, pageable);
		return convertCollect(views, type);
	}

	private List<CollectSummary> convertCollect(Page<CollectView> views, String type) {
		List<CollectSummary> summarys=new ArrayList<>();
		for (CollectView view : views) {
			CollectSummary summary=new CollectSummary(view);
			if("at".equals(type)){
				summary.setCollectTime(DateUtils.getTimeFormatText(view.getLastModifyTime())+" at了你");
			}else if("comment".equals(type)){
				CommentView comment = commentRepository.findReplyUser(Long.valueOf(view.getOperId()));
				if(comment == null){
					continue;
				}
				summary.setUserId(comment.getUserId());
				summary.setUserName(comment.getUserName());
				summary.setProfilePicture(comment.getProfilePicture());
				if(comment.getReplyUserId() != null && comment.getReplyUserId() != 0L){
					User replyUser = userRepository.findOne(comment.getReplyUserId());
				    summary.setRemark("回复@"+replyUser.getUserName()+": "+comment.getContent());
				}else{
					summary.setRemark(comment.getContent());
				}
				summary.setCollectTime(DateUtils.getTimeFormatText(comment.getCreateTime()));
			}else if("praise".equals(type)){
				CommentView comment = praiseRepository.findPraiseUser(Long.valueOf(view.getOperId()));
				if(comment == null){
					continue;
				}
				summary.setUserId(comment.getUserId());
				summary.setUserName(comment.getUserName());
				summary.setProfilePicture(comment.getProfilePicture());
				summary.setCollectTime(DateUtils.getTimeFormatText(comment.getCreateTime())+" 赞了你的收藏");
			}		
			summarys.add(summary);
		}
		return summarys;
	}
}
