package com.graduation.domain.view;
/**
*@InterfaceName: CommentView
*@Description:
*@author JiangL
*/
public interface CommentView {
	Long getUserId();
	String getUserName();
	String getProfilePicture();
	String getContent();
	Long getCreateTime();
	Long getReplyUserId();
}
