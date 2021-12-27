package com.binhao.drive.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.binhao.drive.common.bo.SessionUser;
import com.binhao.drive.common.em.ErrorCodeEnum;
import com.binhao.drive.common.service.AuthenticationService;
import com.binhao.drive.common.util.BeanUtil;
import com.binhao.drive.common.util.ChangeType;
import com.binhao.drive.common.vo.BusinessException;
import com.binhao.drive.manager.dto.AccountUserDTO;
import com.binhao.drive.manager.mapper.AccountUserMapper;
import com.binhao.drive.manager.po.AccountUser;
import com.binhao.drive.manager.service.AccountUserInfoService;
import com.binhao.drive.manager.service.AccountUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccountUserServiceImpl extends ServiceImpl<AccountUserMapper, AccountUser> implements AccountUserService {

    @Resource
    private AccountUserMapper accountUserMapper;

    @Resource
    private AccountUserInfoService accountUserInfoService;

    @Resource
    private AuthenticationService authenticationService;

    @Override
    public Integer insertAccount(AccountUserDTO from) {

        if (!from.getUserAccount().matches(ChangeType.PHONE_CHECK)){
            throw new BusinessException(ErrorCodeEnum.PHONE_FORMAT);
        }
        //判断是否已存在该账号
        AccountUser accountUser1 = accountUserMapper.selectOne(new QueryWrapper<AccountUser>().lambda().eq(AccountUser::getUserAccount, from.getUserAccount()).orderByDesc(AccountUser::getGmtCreate).last("limit 1"));
        if(accountUser1!=null){
            throw new BusinessException(ErrorCodeEnum.LOGIN_EXIST);
        }

        String password= null;
        try {
            password = ChangeType.EncoderByMd5(from.getUserPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        AccountUser accountUser = new AccountUser();
        BeanUtil.copy(from,accountUser);
        //默认激活
        accountUser.setActiveStatus(0);
        //默认无管理员权限
        accountUser.setUserType(0);
        accountUser.setUserPassword(password);
        accountUserMapper.insert(accountUser);

        //成功添加用户后，往扩展信息表中添加一条记录  形成一对一的关系
        accountUserInfoService.insertData(accountUser.getId(),accountUser.getUserAccount());

        return 0;
    }

    @Override
    public void deleteData(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new BusinessException("请选择要删除的记录");
        } else {
            accountUserMapper.deleteById(id);
        }
    }

    @Override
    public void reset(String id) {
        String password = "123456";
        try {
            password = ChangeType.EncoderByMd5(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UpdateWrapper<AccountUser> accountUserUpdateWrapper = new UpdateWrapper<>();
        accountUserUpdateWrapper.lambda().set(AccountUser::getUserPassword,password).eq(AccountUser::getId,id);
        accountUserMapper.update(null, accountUserUpdateWrapper);
    }

    @Override
    public void updatePW(String password) {
        try {
            password = ChangeType.EncoderByMd5(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SessionUser sessionUser = authenticationService.getSessionUser();
        UpdateWrapper<AccountUser> accountUserUpdateWrapper = new UpdateWrapper<>();
        accountUserUpdateWrapper.lambda().set(AccountUser::getUserPassword,password).eq(AccountUser::getId,sessionUser.getUserId());
        accountUserMapper.update(null, accountUserUpdateWrapper);

    }

    @Override
    public AccountUser insertEmployee(AccountUserDTO from) {
        if (!from.getUserAccount().matches(ChangeType.PHONE_CHECK)){
            throw new BusinessException(ErrorCodeEnum.PHONE_FORMAT);
        }
        //判断是否已存在该账号
        AccountUser accountUser1 = accountUserMapper.selectOne(new QueryWrapper<AccountUser>().lambda().eq(AccountUser::getUserAccount, from.getUserAccount()).orderByDesc(AccountUser::getGmtCreate).last("limit 1"));
        if(accountUser1!=null){
            throw new BusinessException(ErrorCodeEnum.LOGIN_EXIST);
        }

        String password= null;
        try {
            password = ChangeType.EncoderByMd5(from.getUserPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        AccountUser accountUser = new AccountUser();
        BeanUtil.copy(from,accountUser);
        //默认激活
        accountUser.setActiveStatus(0);
        //赋予员工管理员权限
        accountUser.setUserType(2);
        accountUser.setUserPassword(password);
        accountUserMapper.insert(accountUser);

        //  员工省略步骤
        //  成功添加用户后，往扩展信息表中添加一条记录  形成一对一的关系
//        accountUserInfoService.insertData(accountUser.getId());

        return accountUser;
    }
}
