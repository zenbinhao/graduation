package com.binhao.drive.manager.controller;


import com.binhao.drive.common.aop.AopOperation;
import com.binhao.drive.common.bo.SessionUser;
import com.binhao.drive.common.controller.BaseController;
import com.binhao.drive.common.vo.ResultVO;
import com.binhao.drive.manager.dto.LoginFormDTO;
import com.binhao.drive.manager.service.LoginService;
import com.binhao.drive.manager.vo.AccountUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(
    tags = {"登录接口"}
)
@RestController
public class   LoginController extends BaseController {
    @Resource
    private LoginService loginService;

    public LoginController() {
    }

    @PostMapping({"login"})
    @ApiOperation("登录")
    public ResultVO<AccountUserVO> login(@Valid @RequestBody LoginFormDTO form) {
        AccountUserVO userVO = this.loginService.login(form, this.request);
        return this.success(userVO, "登录成功");
    }

    @AopOperation(
            type="注销"
    )
    @ApiOperation("注销")
    @GetMapping({"loginOut"})
    public ResultVO loginOut() {
        this.loginService.loginOut(this.request);
        return this.success("注销成功");
    }

    @AopOperation(
            type="登录状态确认"
    )
    @ApiOperation("登录状态确认")
    @GetMapping({"stateRe"})
    public ResultVO stateRe(){
        SessionUser sessionUser = this.loginService.stateRe(this.request);
        return this.success(sessionUser,"状态查看成功");
    }
//    @ResponseBody
//    @PostMapping({"loginByThirdParty"})
//    @ApiOperation("第三方openId登录")
//    public ResultVO<AccountUserVO> loginByThirdParty(@Valid @RequestBody ThirdPartyLoginFormDTO form) {
//        AccountUserVO userVO = this.loginService.loginByThirdPartyOpenId(form, this.request);
//        return this.success(userVO, "登录成功");
//    }
}