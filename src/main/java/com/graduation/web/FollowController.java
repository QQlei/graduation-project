package com.graduation.web;

import com.graduation.comm.aop.LoggerManage;
import com.graduation.domain.Follow;
import com.graduation.domain.enums.FollowStatus;
import com.graduation.domain.result.ExceptionMsg;
import com.graduation.domain.result.Response;
import com.graduation.repository.FollowRepository;
import com.graduation.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/follow")
public class FollowController extends BaseController{
	
	@Autowired
	private FollowRepository followRepository;
	
	/**
	 * 关注&取消关注
	 * @return
	 */
	@RequestMapping("/changeFollowStatus")
	@LoggerManage(description="关注&取消关注")
	public Response changeFollowStatus(String status, Long userId){
		try {
			FollowStatus followStatus = FollowStatus.FOLLOW;
			if(!"follow".equals(status)){
				followStatus = FollowStatus.UNFOLLOW;
			}
			Follow follow = followRepository.findByUserIdAndFollowId(getUserId(), userId);
			if(null != follow){
				followRepository.updateStatusById(followStatus, DateUtils.getCurrentTime(), follow.getId());
			}else{
				follow = new Follow();
				follow.setFollowId(userId);
				follow.setUserId(getUserId());
				follow.setStatus(followStatus);
				follow.setCreateTime(DateUtils.getCurrentTime());
				follow.setLastModifyTime(DateUtils.getCurrentTime());
				followRepository.save(follow);
			}
		} catch (Exception e) {
			logger.error("关注&取消关注异常：",e);
			return result(ExceptionMsg.FAILED);
		}
		return result();
	}

}
