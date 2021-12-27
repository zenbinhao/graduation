//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.binhao.drive.common.controller;


import com.binhao.drive.common.bo.SessionUser;
import com.binhao.drive.common.em.ErrorCodeEnum;
import com.binhao.drive.common.service.AuthenticationService;
import com.binhao.drive.common.vo.ResultVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Resource
    protected HttpServletRequest request;

    @Resource
    private AuthenticationService authenticationService;

    public BaseController() {
    }

    protected ResultVO success(String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setMessage(msg);
        resultVO.setCode(ErrorCodeEnum.SUCCESS.getCode());
        return resultVO;
    }

    protected ResultVO success(Object data, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setMessage(msg);
        resultVO.setData(data);
        resultVO.setCode(ErrorCodeEnum.SUCCESS.getCode());
        return resultVO;
    }
    protected SessionUser getSessionUser() {
        return this.authenticationService.getSessionUser();
    }

    protected String redirect(String path) {
        return "redirect:" + path;
    }}
