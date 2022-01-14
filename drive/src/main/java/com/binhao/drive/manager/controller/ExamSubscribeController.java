package com.binhao.drive.manager.controller;/*
 * @Author: zeng
 * @Data: 2022/1/5 17:22
 * @Description: TODO
 */

import com.binhao.drive.common.aop.AopOperation;
import com.binhao.drive.common.controller.BaseController;
import com.binhao.drive.common.vo.ResultVO;
import com.binhao.drive.manager.dto.DelExamSubDTO;
import com.binhao.drive.manager.dto.ExamSubscribeDTO;
import com.binhao.drive.manager.query.ExamSubscribeQuery;
import com.binhao.drive.manager.service.ExamSubscribeService;
import com.binhao.drive.manager.service.StudentService;
import com.binhao.drive.manager.vo.ExamSubscribeVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
            checkPermission = true,
            checkEmployee = true

    )
    @ApiOperation("分页查询")
    @PostMapping({"/page"})
    public ResultVO<PageInfo<ExamSubscribeVO>> pageDate(@RequestBody ExamSubscribeQuery query){
        PageInfo info = examSubscribeService.pageData(query);
        return this.success(info,"分页查询成功");
    }


    @AopOperation(
            type = "查询所有未处理的预约考试信息",
            checkPermission = true,
            checkEmployee = true

    )
    @ApiOperation("查询所有未处理的预约考试信息")
    @PostMapping({"/isResponseList"})
    public ResultVO<List<ExamSubscribeVO>> selectIsResponseList(@RequestBody ExamSubscribeQuery query){
        List<ExamSubscribeVO> info = examSubscribeService.selectIsResponseList(query);
        return this.success(info,"分页查询成功");
    }

    @AopOperation(
            type = "详情",
            checkPermission = true,
            checkEmployee = true

    )
    @ApiOperation("通过id查询")
    @GetMapping({"/{id}"})
    public ResultVO<ExamSubscribeVO> selectById(@PathVariable String id) {

        ExamSubscribeVO examSubscribeVO= examSubscribeService.selectDataById(id);
        return this.success(examSubscribeVO, "查询id为:" + id + "用户成功");
    }


    @AopOperation(
            type = "新增"
    )
    @ApiOperation("新增信息")
    @PostMapping({"/"})
    public ResultVO insertData(@Valid @RequestBody ExamSubscribeDTO form) {
        examSubscribeService.insertData(form);
        return this.success("新增信息成功");
    }

    @AopOperation(
            type = "修改",
            checkPermission = true,
            checkEmployee = true
    )
    @ApiOperation("修改信息")
    @PutMapping({"/"})
    public ResultVO updateData(@RequestBody ExamSubscribeDTO form) {
        examSubscribeService.updateData(form);
        return this.success("修改信息成功");
    }


    @AopOperation(
            type = "处理信息",
            checkPermission = true,
            checkEmployee = true
    )
    @ApiOperation("处理信息")
    @PutMapping({"/del"})
    public ResultVO updateDel(@RequestBody DelExamSubDTO form) {
        String[] ids= form.getIds().split(",");
        String[] emails = form.getEmails().split(",");
        CountDownLatch countDownLatch = new CountDownLatch(ids.length);
        for (int i=0 ; i<ids.length; i++)
            examSubscribeService.updateDel(ids[i],form.getContent(),emails[i],countDownLatch);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(ids.length+"个线程都发送邮件完毕----------------------程序结束");
        }

        return this.success("信息处理完成，邮件发送完成");
    }

    @AopOperation(
            type = "通过认定",
            checkPermission = true,
            checkEmployee = true
    )
    @ApiOperation("通过认定")
    @PutMapping({"/pass/{fkUserId}"})
    public ResultVO updateCheckPass(@PathVariable String fkUserId) {
        examSubscribeService.checkPass(fkUserId);
        return this.success("通过认定成功,考试进度进入下一阶段");
    }

    @AopOperation(
            type = "不通过认定",
            checkPermission = true,
            checkEmployee = true
    )
    @ApiOperation("不通过认定")
    @PutMapping({"/noPass/{fkUserId}"})
    public ResultVO updateCheckNoPass(@PathVariable String fkUserId) {
        //不通过状态改为2
        examSubscribeService.checkPass(fkUserId,2);
        return this.success("不通过认定成功");
    }

}
