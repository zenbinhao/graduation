package com.binhao.drive.manager.controller;/*
 * @Author: zeng
 * @Data: 2022/1/5 16:43
 * @Description: TODO
 */

import com.binhao.drive.common.aop.AopOperation;
import com.binhao.drive.common.controller.BaseController;
import com.binhao.drive.common.vo.ResultVO;
import com.binhao.drive.manager.dto.CourseSubscribeDTO;
import com.binhao.drive.manager.query.CourseSubscribeQuery;
import com.binhao.drive.manager.service.CourseSubscribeService;
import com.binhao.drive.manager.vo.CourseSubscribeVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(
        tags = "管理端-约课信息"
)
@RestController
@RequestMapping("/courseSub")
public class CourseSubscribeController extends BaseController {

    @Resource
    private CourseSubscribeService courseSubscribeService;

    @AopOperation(
            type = "分页查询",
            checkPermission = true
    )
    @ApiOperation("分页查询")
    @PostMapping({"/page"})
    public ResultVO<PageInfo<CourseSubscribeVO>> pageDate(@RequestBody CourseSubscribeQuery query){
        PageInfo info = courseSubscribeService.pageData(query);
        return this.success(info,"分页查询成功");
    }

    @AopOperation(
            type = "详情",
            checkPermission = true
    )
    @ApiOperation("通过id查询")
    @GetMapping({"/{id}"})
    public ResultVO<CourseSubscribeVO> selectById(@PathVariable String id) {

        CourseSubscribeVO courseSubscribeVO = courseSubscribeService.selectDataById(id);
        return this.success(courseSubscribeVO, "查询id为:" + id + "用户成功");
    }


    @AopOperation(
            type = "新增",
            checkPermission = true
    )
    @ApiOperation("新增信息")
    @PostMapping({"/"})
    public ResultVO insertData(@Valid @RequestBody CourseSubscribeDTO form) {
        courseSubscribeService.insertData(form);
        return this.success("新增信息成功");
    }

    @AopOperation(
            type = "修改",
            checkPermission = true
    )
    @ApiOperation("修改信息")
    @PutMapping({"/"})
    public ResultVO updateData(@RequestBody CourseSubscribeDTO form) {
        courseSubscribeService.updateData(form);
        return this.success("修改信息成功");
    }
}
