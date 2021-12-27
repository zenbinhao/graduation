package com.binhao.drive.manager.controller;/*
 * @Author: zeng
 * @Data: 2021/11/10 17:58
 * @Description: TODO
 */

import com.binhao.drive.common.aop.AopOperation;
import com.binhao.drive.common.controller.BaseController;
import com.binhao.drive.common.vo.ResultVO;
import com.binhao.drive.manager.dto.AccountUserInfoDTO;
import com.binhao.drive.manager.query.AccountUserInfoQuery;
import com.binhao.drive.manager.service.AccountUserInfoService;
import com.binhao.drive.manager.vo.AccountUserInfoVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(
        tags = {"管理端-会员管理"}
)
@RestController
@RequestMapping("/userinfo")
public class AccountUserInfoController extends BaseController {

    @Resource
    private AccountUserInfoService accountUserInfoService;

    @AopOperation(
            type = "分页查询",
            checkPermission = true
    )
    @ApiOperation("分页查询")
    @PostMapping({"/page"})
    public ResultVO<PageInfo<AccountUserInfoVO>> pageDate(@RequestBody AccountUserInfoQuery query){
        PageInfo info = accountUserInfoService.pageData(query);
        return this.success(info,"分页查询成功");
    }

    @AopOperation(
            type = "详情",
            checkPermission = true
    )
    @ApiOperation("通过id查询")
    @GetMapping({"/{id}"})
    public ResultVO<AccountUserInfoVO> selectById(@PathVariable String id) {

        AccountUserInfoVO accountUserInfoVO = accountUserInfoService.selectById(id);
        return this.success(accountUserInfoVO, "查询id为:" + id + "用户成功");
    }


//    @AopOperation(
//            type = "新增",
//            checkPermission = true
//    )
//    @ApiOperation("新增信息")
//    @PostMapping({"/"})
//    public ResultVO insertData(@Valid @RequestBody MealDTO form) {
//        mealService.insertData(form);
//        return this.success("新增信息成功");
//    }

    @AopOperation(
            type = "修改",
            checkPermission = true
    )
    @ApiOperation("修改信息")
    @PutMapping({"/"})
    public ResultVO updateData(@Valid @RequestBody AccountUserInfoDTO form) {
        accountUserInfoService.updateData(form);
        return this.success("修改信息成功");
    }

    @AopOperation(
            type = "单删",
            checkPermission = true
    )
    @ApiOperation("根据ID删除")
    @DeleteMapping({"/{id}"})
    public ResultVO deleteData(@PathVariable("id") String id) {
        accountUserInfoService.deleteData(id);
        return this.success("根据ID删除成功");
    }

    @AopOperation(
            type = "详情",
            checkPermission = true
    )
    @ApiOperation("通过fkUserId查询")
    @GetMapping({"/selectByFkUserId/{fkUserId}"})
    public ResultVO selectByFkUserId(@PathVariable("fkUserId") String id){
        AccountUserInfoVO accountUserInfoVO = accountUserInfoService.selectByFkUserId(id);
        return this.success(accountUserInfoVO,"查询fkUserId为:" + id + "用户成功");
    }


}
