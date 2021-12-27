package com.binhao.drive.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binhao.drive.common.bo.SessionUser;
import com.binhao.drive.manager.dto.LoginFormDTO;
import com.binhao.drive.manager.po.AccountUser;
import com.binhao.drive.manager.vo.AccountUserVO;

import javax.servlet.http.HttpServletRequest;

public interface LoginService extends IService<AccountUser> {

    AccountUserVO login(LoginFormDTO dto, HttpServletRequest httpServletRequest);

    void loginOut(HttpServletRequest request);

    SessionUser stateRe(HttpServletRequest request);
}
