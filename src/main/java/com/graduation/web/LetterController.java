package com.graduation.web;

import com.graduation.comm.aop.LoggerManage;
import com.graduation.domain.Letter;
import com.graduation.domain.result.ExceptionMsg;
import com.graduation.domain.result.Response;
import com.graduation.domain.view.LetterSummary;
import com.graduation.service.LetterService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by JiangL on 2018/3/7.
 * 私信Controller
 */
@RestController
@RequestMapping("/letter")
public class LetterController extends BaseController {

    @Resource
    private LetterService letterService;

    /**
     * 发送私信
     * @param letter
     * @return
     */
    @RequestMapping("/sendLetter")
    @LoggerManage(description = "发送私信")
    public Response sendLetter(Letter letter){
        try {
            letter.setSendUserId(getUserId());
            letterService.sendLetter(letter);
        }catch (Exception e){
            logger.error("发送私信异常：",e);
            return result(ExceptionMsg.FAILED);
        }
        return result();
    }

    /**
     * 私信列表获取
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/getLetterList")
    @LoggerManage(description = "获取私信列表")
    public List<LetterSummary> getLetterList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                             @RequestParam(value = "size", defaultValue = "15") Integer size){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        return letterService.findLetter(getUserId(),pageable);
    }
}
