package com.binhao.drive.manager.controller;/*
 * @Author: zeng
 * @Data: 2022/1/17 15:36
 * @Description: TODO
 */

import com.binhao.drive.common.aop.AopOperation;
import com.binhao.drive.common.controller.BaseController;
import com.binhao.drive.common.vo.ResultVO;
import com.binhao.drive.manager.po.Teacher;
import com.binhao.drive.manager.query.TeacherQuery;
import com.binhao.drive.manager.service.ViewService;
import com.binhao.drive.manager.vo.view.ViewVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(
        tags = {"管理端-消息板块"}
)
@RestController
@RequestMapping("/view")
public class ViewController extends BaseController {

    @Resource
    private ViewService viewService;

    @AopOperation(
            type = "消息查询",
            checkPermission = true,
            checkEmployee = true

    )
    @ApiOperation("分页查询")
    @PostMapping({"/"})
    public ResultVO<ViewVO> pageDate(){
        ViewVO viewVO = viewService.selectAllNews();
        return this.success(viewVO,"分页查询成功");
    }

}
