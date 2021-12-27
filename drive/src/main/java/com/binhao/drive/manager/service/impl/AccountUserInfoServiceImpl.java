package com.binhao.drive.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2021/11/10 17:44
 * @Description: TODO
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.binhao.drive.common.bo.SessionUser;
import com.binhao.drive.common.service.AuthenticationService;
import com.binhao.drive.common.util.BeanUtil;
import com.binhao.drive.common.vo.BusinessException;
import com.binhao.drive.manager.dto.AccountUserInfoDTO;
import com.binhao.drive.manager.mapper.AccountUserInfoMapper;
import com.binhao.drive.manager.po.AccountUserInfo;
import com.binhao.drive.manager.query.AccountUserInfoQuery;
import com.binhao.drive.manager.service.AccountUserInfoService;
import com.binhao.drive.manager.service.AccountUserService;
import com.binhao.drive.manager.vo.AccountUserInfoVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AccountUserInfoServiceImpl extends ServiceImpl<AccountUserInfoMapper, AccountUserInfo> implements AccountUserInfoService {


    @Resource
    private AccountUserInfoMapper accountUserInfoMapper;

    @Resource
    private AccountUserService accountUserService;

    @Resource
    private AuthenticationService authenticationService;

    @Override
    public PageInfo<AccountUserInfoVO> pageData(AccountUserInfoQuery query) {
        //开启分页
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        List<AccountUserInfoVO> list = accountUserInfoMapper.selectList(query);
        return PageInfo.of(list);
    }

    @Override
    public void updateData(AccountUserInfoDTO formDTO) {
        AccountUserInfo accountUserInfo = new AccountUserInfo();
        BeanUtil.copy(formDTO,accountUserInfo);
        accountUserInfoMapper.updateById(accountUserInfo);
    }

    @Override
    public void deleteData(String id) {
        AccountUserInfoVO accountUserInfoVO = this.selectById(id);
        if (StringUtils.isEmpty(id)) {
            throw new BusinessException("请选择要删除的记录");
        } else {
            accountUserInfoMapper.deleteById(id);
        }
        String fkUserId = accountUserInfoVO.getFkUserId();
        accountUserService.deleteData(fkUserId);
    }

    @Override
    public AccountUserInfoVO selectById(String id) {
        return accountUserInfoMapper.selectById(id);
    }

    @Override
    public void insertData(String id,String phone) {
        AccountUserInfo accountUserInfo = new AccountUserInfo();
        accountUserInfo.setFkUserId(id);
        accountUserInfo.setPhone(phone);
        accountUserInfoMapper.insert(accountUserInfo);
    }

    @Override
    public AccountUserInfoVO selectByUserId() {
        SessionUser sessionUser = authenticationService.getSessionUser();
        AccountUserInfoVO accountUserInfoVO = accountUserInfoMapper.selectByUserId(sessionUser.getUserId());
        return accountUserInfoVO;
    }

    @Override
    public AccountUserInfoVO selectByFkUserId(String fkUserId) {
        AccountUserInfoVO accountUserInfoVO = accountUserInfoMapper.selectByUserId(fkUserId);
        return accountUserInfoVO;
    }
}
