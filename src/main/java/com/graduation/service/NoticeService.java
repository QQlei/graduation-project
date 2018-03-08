package com.graduation.service;

import java.util.List;

import com.graduation.domain.view.CollectSummary;
import org.springframework.data.domain.Pageable;


public interface NoticeService {
	
	public void saveNotice(String collectId, String type, Long userId, String operId);
	
	public List<CollectSummary> getNoticeCollects(String type, Long userId, Pageable pageable);

}
