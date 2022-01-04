package com.binhao.drive.manager.controller;/*
 * @Author: zeng
 * @Data: 2021/10/17 20:45
 * @Description: TODO
 */

import com.binhao.drive.common.aop.AopOperation;
import com.binhao.drive.common.controller.BaseController;
import com.binhao.drive.common.vo.ResultVO;
import com.binhao.drive.manager.dto.AccountUserDTO;
import com.binhao.drive.manager.service.AccountUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(
        tags = {"管理端-学员管理"}
)
@RestController
@RequestMapping("/user")
public class AccountUserController extends BaseController {

    @Resource
    AccountUserService accountUserService;

    @AopOperation(type = "添加用户",
    checkPermission = true)
    @ApiOperation("添加用户接口")
    @PostMapping("/")
    public ResultVO insertData(@Valid @RequestBody AccountUserDTO form) {
        accountUserService.insertAccount(form);
        return this.success("新增信息成功");
    }

    @AopOperation(
            type = "重置密码",
            checkPermission = true,
            checkEmployee = true
    )
    @ApiOperation("重置密码 通过fkUserId")
    @PutMapping({"/{id}"})
    public ResultVO reset(@PathVariable("id") String id) {
        accountUserService.reset(id);
        return this.success("密码重置成功");
    }
}
