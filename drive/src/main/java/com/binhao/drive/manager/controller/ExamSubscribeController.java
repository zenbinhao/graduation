package com.binhao.drive.manager.controller;/*
 * @Author: zeng
 * @Data: 2022/1/5 17:22
 * @Description: TODO
 */

import com.binhao.drive.common.aop.AopOperation;
import com.binhao.drive.common.controller.BaseController;
import com.binhao.drive.common.vo.ResultVO;
import com.binhao.drive.manager.dto.ExamSubscribeDTO;
import com.binhao.drive.manager.query.ExamSubscribeQuery;
import com.binhao.drive.manager.service.ExamSubscribeService;
import com.binhao.drive.manager.vo.ExamSubscribeVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(
        tags = {"管理端-预约考试信息"}
)
@RestController
@RequestMapping("/examSub")
public class ExamSubscribeController extends BaseController {

    @Resource
    private ExamSubscribeService examSubscribeService;

    @AopOperation(
            type = "分页查询",
            checkPermission = true
    )
    @ApiOperation("分页查询")
    @PostMapping({"/page"})
    public ResultVO<PageInfo<ExamSubscribeVO>> pageDate(@RequestBody ExamSubscribeQuery query){
        PageInfo info = examSubscribeService.pageData(query);
        return this.success(info,"分页查询成功");
    }

    @AopOperation(
            type = "详情",
            checkPermission = true
    )
    @ApiOperation("通过id查询")
    @GetMapping({"/{id}"})
    public ResultVO<ExamSubscribeVO> selectById(@PathVariable String id) {

        ExamSubscribeVO examSubscribeVO= examSubscribeService.selectDataById(id);
        return this.success(examSubscribeVO, "查询id为:" + id + "用户成功");
    }


    @AopOperation(
            type = "新增",
            checkPermission = true
    )
    @ApiOperation("新增信息")
    @PostMapping({"/"})
    public ResultVO insertData(@Valid @RequestBody ExamSubscribeDTO form) {
        examSubscribeService.insertData(form);
        return this.success("新增信息成功");
    }

    @AopOperation(
            type = "修改",
            checkPermission = true
    )
    @ApiOperation("修改信息")
    @PutMapping({"/"})
    public ResultVO updateData(@RequestBody ExamSubscribeDTO form) {
        examSubscribeService.updateData(form);
        return this.success("修改信息成功");
    }
}
