/**
 * Controller编写规则：
 *   1、所有路径均按节划分，每一节均为 单个 名词
 *   2、路径结尾以反斜杠结束
 *   3、符合Restful接口规范
 *        GET：请求资源，不修改服务器数据
 *        POST：向服务器新增资源或修改资源
 *        DELETE：删除服务器资源
 *   4、返回结果均以Resp对象返回，框架统一转换为json
 *   5、代码中不得出现汉字，返回信息统一规范到HttpRespCode中
 *      若Resp中没有所需要的文字，请在ConstantsMsg中添加文字
 *   6、所有函数请在头部标明作者，以便代码回溯
 *   7、使用 @Resource 注解自动装配 Service
 *
 *   @author Jack Chen
 */
package com.ecnu.trivia.web.room.controller;

import com.ecnu.trivia.common.component.web.HttpRespCode;
import com.ecnu.trivia.common.util.ObjectUtils;
import com.ecnu.trivia.web.rbac.domain.User;
import com.ecnu.trivia.web.rbac.utils.UserUtils;
import com.ecnu.trivia.web.room.domain.vo.RoomVO;
import com.ecnu.trivia.web.room.service.RoomService;
import com.ecnu.trivia.web.utils.Resp;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/room", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomController {
    @Resource
    protected RoomService roomService;

    /**
    * @Description: 获取房间列表
    * @Author: Handsome Zhao
    * @Date: 16:29 2017/12/7
    */
    @RequestMapping(value = "/list/", method = RequestMethod.GET)
    public Resp getRoomList() {
        List<RoomVO> list = roomService.getRoomList();
        return new Resp(HttpRespCode.SUCCESS,list);
    }

    /**
     * @Description: 根据id号获取特定的房间
     * @Author: Handsome Zhao
     * @Date: 16:29 2017/12/7
     */
    @RequestMapping(value = "/detail/", method = RequestMethod.GET)
    public Resp getRoomList(Integer roomId) {
        RoomVO roomVO = roomService.getRoomById(roomId);
        return new Resp(HttpRespCode.SUCCESS,roomVO);
    }

    @RequestMapping(value = "/type/", method = RequestMethod.GET)
    public Resp editPlayerRoom(@RequestParam("isEnter") Integer isEnter,
                               @RequestParam("roomId") Integer roomId) {
        if(ObjectUtils.isNullOrEmpty(isEnter)
                ||ObjectUtils.isNullOrEmpty(roomId)){
            return new Resp(HttpRespCode.PARAM_ERROR);
        }
        User user = UserUtils.fetchUser();
        if(ObjectUtils.isNullOrEmpty(user)){
            return new Resp(HttpRespCode.USER_NOT_LOGIN);
        }
        return roomService.editPlayerRoom(roomId,user.getId(),isEnter);
    }

}