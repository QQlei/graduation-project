package com.graduation.schedule;


import com.graduation.cache.CacheService;
import com.graduation.comm.Const;
import com.graduation.comm.aop.LoggerManage;
import com.graduation.domain.Collect;
import com.graduation.domain.UrlLibrary;
import com.graduation.domain.enums.IsDelete;
import com.graduation.repository.CollectRepository;
import com.graduation.repository.FavoritesRepository;
import com.graduation.repository.UrlLibraryRepository;
import com.graduation.utils.DateUtils;
import com.graduation.utils.HtmlUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private CollectRepository collectRespository;
	@Autowired
	private FavoritesRepository favoritesRespository;
	@Autowired
	private UrlLibraryRepository urlLibraryRepository;
	@Autowired
	private CacheService cacheService;

	@Autowired
//	private RedisService redisService;

	/**
	 * 回收站定时
	 */
	@Scheduled(cron="22 2 2 * * ?")
	@LoggerManage(description="回收站定时")
    public void autoRecovery() {
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.DAY_OF_YEAR,-30);
		Long date = ca.getTime().getTime();
		List<Long> favoritesId = favoritesRespository.findIdByName("未读列表");
		List<Collect> collectList = collectRespository.findByCreateTimeLessThanAndIsDeleteAndFavoritesIdIn(date, IsDelete.NO,favoritesId);
		for(Collect collect : collectList){
			try {
				logger.info("文章id:" + collect.getId());
				collectRespository.modifyIsDeleteById(IsDelete.YES, DateUtils.getCurrentTime(), collect.getId());
				favoritesRespository.reduceCountById(collect.getFavoritesId(), DateUtils.getCurrentTime());
			} catch (Exception e) {
				logger.error("回收站定时任务异常：",e);
			}
		}
    }

	@Scheduled(cron="11 1 1 * * ?")
	@LoggerManage(description="获取图片logoUrl定时")
    public void getImageLogoUrl(){
		List<UrlLibrary> urlLibraryList = urlLibraryRepository.findByCountLessThanAndLogoUrl(10, Const.BASE_PATH+"img/logo.jpg");
		logger.info("集合长度：" + urlLibraryList.size());
		for(UrlLibrary urlLibrary : urlLibraryList){
			try {
				String logoUrl = HtmlUtil.getImge(urlLibrary.getUrl());
				// 刷新缓存
				boolean result = cacheService.refreshOne(urlLibrary.getUrl(),logoUrl);
				if(result){
					collectRespository.updateLogoUrlByUrl(logoUrl,DateUtils.getCurrentTime(),urlLibrary.getUrl());
					urlLibraryRepository.updateLogoUrlById(urlLibrary.getId(),logoUrl);
				}else{
					urlLibraryRepository.increaseCountById(urlLibrary.getId());
				}
			}catch (Exception e){
				logger.error("获取图片异常：",e);
			}
		}
	}

}
