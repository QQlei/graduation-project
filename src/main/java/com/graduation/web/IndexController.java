package com.graduation.web;


import com.graduation.comm.Const;
import com.graduation.comm.aop.LoggerManage;
import com.graduation.domain.Config;
import com.graduation.domain.Favorites;
import com.graduation.domain.User;
import com.graduation.domain.UserIsFollow;
import com.graduation.domain.enums.CollectType;
import com.graduation.domain.enums.FollowStatus;
import com.graduation.domain.enums.IsDelete;
import com.graduation.domain.view.CollectSummary;
import com.graduation.domain.view.IndexCollectorView;
import com.graduation.repository.*;
import com.graduation.service.CollectService;
import com.graduation.service.CollectorService;
import com.graduation.service.LookAroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController{
	
	@Autowired
	private FavoritesRepository favoritesRepository;
	@Autowired
	private ConfigRepository configRepository;
	@Autowired
	private FollowRepository followRepository;
	@Autowired
	private CollectRepository collectRepository;
	@Autowired
	private NoticeRepository noticeRepository;
	@Autowired
	private CollectorService collectorService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private UserRepository userRepository;

	/**
	 * 随便看看  added by JiangL
	 */
	@Autowired
	private LookAroundService lookAroundService;

	@GetMapping(value="/index")
	@LoggerManage(description="首页")
	public String index(Model model){
		IndexCollectorView indexCollectorView = collectorService.getCollectors();
		model.addAttribute("collector",indexCollectorView);
		User user = super.getUser();
		if(null != user){
			model.addAttribute("user",user);
		}
		return "index";
	}

	@GetMapping(value="/")
	@LoggerManage(description="登陆后首页")
	public String home(Model model) {
		long size= collectRepository.countByUserIdAndIsDelete(getUserId(), IsDelete.NO);
		Config config = configRepository.findByUserId(getUserId());
		Favorites favorites = favoritesRepository.findOne(Long.parseLong(config.getDefaultFavorties()));
		List<String> followList = followRepository.findByUserId(getUserId());
		model.addAttribute("config",config);
		model.addAttribute("favorites",favorites);
		model.addAttribute("size",size);
		model.addAttribute("followList",followList);
		model.addAttribute("user",getUser());
		model.addAttribute("newAtMeCount",noticeRepository.countByUserIdAndTypeAndReaded(getUserId(), "at", "unread"));
		model.addAttribute("newCommentMeCount",noticeRepository.countByUserIdAndTypeAndReaded(getUserId(), "comment", "unread"));
		model.addAttribute("newPraiseMeCount",noticeRepository.countPraiseByUserIdAndReaded(getUserId(), "unread"));
		logger.info("collect size="+size+" userID="+getUserId());
		return "home";
	}

	/**
	 * 随便看看 简单模式显示  added by JiangL
	 * @return  /lookAround/simple/ALL
	 */
	@RequestMapping(value="/lookAround/simple/{category}")
	@LoggerManage(description="随便看看页面")
	public String lookAroundSimple(Model model,@RequestParam(value = "page", defaultValue = "0") Integer page,
									 @RequestParam(value = "size", defaultValue = "20") Integer size,
									 @PathVariable("category") String category) {

		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(page, size, sort);
		model.addAttribute("category", category);
		model.addAttribute("type", "lookAround");
		Favorites favorites = new Favorites();
		List<CollectSummary> collects = null;
		List<CollectSummary> fivecollects = lookAroundService.scrollFiveCollect();
		List<UserIsFollow> fiveUsers = lookAroundService.queryFiveUser(this.getUserId());

		collects =lookAroundService.queryCollectExplore(pageable,getUserId(),category);
		User user = super.getUser();
		if(null != user){
			model.addAttribute("user",user);
		}
		model.addAttribute("fiveCollects", fivecollects);
		model.addAttribute("fiveUsers", fiveUsers);
		model.addAttribute("collects", collects);
		model.addAttribute("favorites", favorites);
		model.addAttribute("userId", getUserId());
		model.addAttribute("size", collects.size());
		return "lookAround/simple";
	}

	@GetMapping(value="/login")
	@LoggerManage(description="登陆页面")
	public String login() {

		return "login";
	}

	@GetMapping(value="/register")
	@LoggerManage(description="注册页面")
	public String regist() {
		return "register";
	}
	
	@RequestMapping(value="/tool")
	@LoggerManage(description="工具页面")
	public String tool(Model model) {
	  String path="javascript:(function()%7Bvar%20description;var%20desString=%22%22;var%20metas=document.getElementsByTagName('meta');for(var%20x=0,y=metas.length;x%3Cy;x++)%7Bif(metas%5Bx%5D.name.toLowerCase()==%22description%22)%7Bdescription=metas%5Bx%5D;%7D%7Dif(description)%7BdesString=%22&amp;description=%22+encodeURIComponent(description.content);%7Dvar%20win=window.open(%22"
              	+ Const.BASE_PATH
              	+"collect?from=webtool&url=%22+encodeURIComponent(document.URL)+desString+%22&title=%22+encodeURIComponent(document.title)+%22&charset=%22+document.charset,'_blank');win.focus();%7D)();";
	  model.addAttribute("path",path);
		return "tool";
	}
	
	@RequestMapping(value="/mobile")
	@LoggerManage(description="移动客户端页面")
	public String mobile() {
		return "mobile";
	}
	
	@RequestMapping(value="/import")
	@LoggerManage(description="收藏夹导入页面")
	public String importm() {
		return "favorites/import";
	}
	
	@RequestMapping(value="/newFavorites")
	@LoggerManage(description="新建收藏夹页面")
	public String newFavorites(){
		return "favorites/newfavorites";
	}

	@RequestMapping(value="/feedback")
	@LoggerManage(description="意见反馈页面")
	public String feedback(Model model){
		User user = null;
		user = userRepository.findOne(getUserId());
		model.addAttribute("user", user);
		return "favorites/feedback";
	}

	@GetMapping(value="/collect")
	@LoggerManage(description="收藏页面")
	public String collect(Model model) {
		List<Favorites> favoritesList = favoritesRepository.findByUserId(getUserId());
		Config config = configRepository.findByUserId(getUserId());
		List<String> followList = followRepository.findByUserId(getUserId());
		logger.info("model：" + config.getDefaultModel());
		model.addAttribute("favoritesList",favoritesList);
		model.addAttribute("configObj", config);
		model.addAttribute("followList",followList);
		return "collect";
	}

	@GetMapping(value="/logout")
	@LoggerManage(description="登出")
	public String logout(HttpServletResponse response,Model model) {
		getSession().removeAttribute(Const.LOGIN_SESSION_KEY);
		getSession().removeAttribute(Const.LAST_REFERER);
		Cookie cookie = new Cookie(Const.LOGIN_SESSION_KEY, "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		IndexCollectorView indexCollectorView = collectorService.getCollectors();
		model.addAttribute("collector",indexCollectorView);
		return "index";
	}

	@GetMapping(value="/forgotPassword")
	@LoggerManage(description="忘记密码页面")
	public String forgotPassword() {
		return "user/forgotpassword";
	}

	@GetMapping(value="/newPassword")
	public String newPassword(String email) {
		return "user/newpassword";
	}

	@RequestMapping(value="/uploadHeadPortrait")
	@LoggerManage(description="上传你头像页面")
	public String uploadHeadPortrait(){
		return "user/uploadheadportrait";
	}
	
	@RequestMapping(value="/export")
	@LoggerManage(description="收藏夹导出页面")
	public String export(Model model){
		List<Favorites> favoritesList = favoritesRepository.findByUserId(getUserId());
		model.addAttribute("favoritesList",favoritesList);
		return "favorites/export";
	}

	@RequestMapping(value="/uploadBackground")
	@LoggerManage(description="上传背景页面")
	public String uploadBackground(){
		return "user/uploadbackground";
	}

    /**
     * 首页收藏家个人首页
     * @param model
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value="/collector/{userId}/{favoritesId:[0-9]*}")
    @LoggerManage(description="首页收藏家个人首页")
    public String collectorPageShow(Model model, @PathVariable("userId") Long userId, @PathVariable("favoritesId") Long favoritesId, @RequestParam(value = "page", defaultValue = "0") Integer page,
                                 @RequestParam(value = "size", defaultValue = "15") Integer size){
        User user = userRepository.findOne(userId);
        Long collectCount = 0l;
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        List<CollectSummary> collects = null;
        Integer isFollow = 0;
        if(getUserId().longValue() == userId.longValue()){
            model.addAttribute("myself",IsDelete.YES.toString());
            collectCount = collectRepository.countByUserIdAndIsDelete(userId,IsDelete.NO);
            if(0 == favoritesId){
                collects =collectService.getCollects("myself", userId, pageable,null,null);
            }else{
                collects =collectService.getCollects(String.valueOf(favoritesId), userId, pageable,0l,null);
            }
        }else{
            model.addAttribute("myself",IsDelete.NO.toString());
            collectCount = collectRepository.countByUserIdAndTypeAndIsDelete(userId, CollectType.PUBLIC, IsDelete.NO);
            if(favoritesId == 0){
                collects =collectService.getCollects("others", userId, pageable,null,getUserId());
            }else{
                collects = collectService.getCollects("otherpublic", userId, pageable, favoritesId,getUserId());
            }
            isFollow = followRepository.countByUserIdAndFollowIdAndStatus(getUserId(), userId, FollowStatus.FOLLOW);
        }
        Integer follow = followRepository.countByUserIdAndStatus(userId, FollowStatus.FOLLOW);
        Integer followed = followRepository.countByFollowIdAndStatus(userId, FollowStatus.FOLLOW);
        List<Favorites> favoritesList = favoritesRepository.findByUserId(userId);
        List<String> followUser = followRepository.findFollowUserByUserId(userId);
        List<String> followedUser = followRepository.findFollowedUserByFollowId(userId);
		Config config = configRepository.findByUserId(getUserId());
        if(getUserId()==null||getUserId()==0){
			config = configRepository.findByUserId(userId);
		}
        model.addAttribute("collectCount",collectCount);
        model.addAttribute("follow",follow);
        model.addAttribute("followed",followed);
        model.addAttribute("user",user);
        model.addAttribute("collects", collects);
        model.addAttribute("favoritesList",favoritesList);
        model.addAttribute("followUser",followUser);
        model.addAttribute("followedUser",followedUser);
        model.addAttribute("isFollow",isFollow);
		model.addAttribute("loginUserInfo",getUser());
		model.addAttribute("config",config);
		model.addAttribute("configObj",config);
        return "collector";
    }

}