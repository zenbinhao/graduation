package com.binhao.drive.manager.service;/*
 * @Author: zeng
 * @Data: 2021/11/10 17:11
 * @Description: TODO
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.binhao.drive.manager.dto.AccountUserInfoDTO;
import com.binhao.drive.manager.po.AccountUserInfo;
import com.binhao.drive.manager.query.AccountUserInfoQuery;
import com.binhao.drive.manager.vo.AccountUserInfoVO;
import com.github.pagehelper.PageInfo;

public interface AccountUserInfoService extends IService<AccountUserInfo> {

    PageInfo<AccountUserInfoVO> pageData(AccountUserInfoQuery query);

    void updateData(AccountUserInfoDTO formDTO);

    void deleteData(String id);

    AccountUserInfoVO selectById(String id);

    void insertData(String id,String phone);

    AccountUserInfoVO selectByUserId();

    public AccountUserInfoVO selectByFkUserId(String fkUserId);
}
