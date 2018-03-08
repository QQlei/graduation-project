package com.graduation.web;


import com.graduation.domain.result.Response;
import com.graduation.service.LookRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by JiangL
 */
@RestController
@RequestMapping("/lookRecord")
public class LookRecordController extends BaseController{

    @Autowired
    private LookRecordService lookRecordService;

    /**
     * @author JiangL
     * @date 2018年3月23日
     * @param collectId 收藏ID
     * @return
     */
    @RequestMapping(value="/save/{collectId}")
    public Response saveLookRecord(@PathVariable("collectId") long collectId) {
        lookRecordService.saveLookRecord(this.getUserId(),collectId);
        return result();
    }

    /**
     * @author JiangL
     * @date 2018年3月23日
     * @param collectId 收藏ID
     * @return
     */
    @RequestMapping(value="/delete/{collectId}")
    public Response deleteLookRecord(@PathVariable("collectId") long collectId) {
        lookRecordService.deleteLookRecord(this.getUserId(),collectId);
        return result();
    }

    /**
     * @author JiangL
     * @date 2018年3月23日
     * @return
     */
    @RequestMapping(value="/deleteAll")
    public Response deleteAll() {
        lookRecordService.deleteLookRecordByUserID(this.getUserId());
        return result();
    }
}
