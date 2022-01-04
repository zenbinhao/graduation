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
import com.binhao.drive.manager.service.AccountUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class AccountUserServiceImpl extends ServiceImpl<AccountUserMapper, AccountUser> implements AccountUserService {

    @Resource
    private AccountUserMapper accountUserMapper;

    @Resource
    private AuthenticationService authenticationService;

    @Override
    public AccountUser insertAccount(AccountUserDTO from) {
        //检查
        String password = checkObject(from);

        //type为0 无管理权限
        AccountUser accountUser = this.propertySet(from,0,0,password);

        accountUserMapper.insert(accountUser);

        return accountUser;
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
        String password = this.checkPassword("123456");
        UpdateWrapper<AccountUser> accountUserUpdateWrapper = new UpdateWrapper<>();
        accountUserUpdateWrapper.lambda().set(AccountUser::getUserPassword,password).eq(AccountUser::getId,id);
        accountUserMapper.update(null, accountUserUpdateWrapper);
    }

    @Override
    public void updatePW(String password) {
        String passwordCheck = this.checkPassword(password);
        SessionUser sessionUser = authenticationService.getSessionUser();
        UpdateWrapper<AccountUser> accountUserUpdateWrapper = new UpdateWrapper<>();
        accountUserUpdateWrapper.lambda().set(AccountUser::getUserPassword,passwordCheck).eq(AccountUser::getId,sessionUser.getUserId());
        accountUserMapper.update(null, accountUserUpdateWrapper);

    }

    @Override
    public AccountUser insertTeacher(AccountUserDTO from) {
        //检查
        String password = this.checkObject(from);
        //员工权限 type 2   但本系统不需要教练员 管理
        AccountUser accountUser = this.propertySet(from,0,0,password);

        accountUserMapper.insert(accountUser);

        return accountUser;
    }

    @Override
    public void deletes(String ids) {
        if (StringUtils.isEmpty(ids)) {
            throw new BusinessException("请选择要删除的记录");
        }
        String []id =ids.split(",");
        int row = accountUserMapper.deleteData(id);
        if (row <=0|| ids.length()<=0) {
            throw new BusinessException("批量删除操作失败");
        }
    }

    @Override
    public void updateData(AccountUserDTO formDTO) {
        AccountUser accountUser = new AccountUser();
        BeanUtil.copy(formDTO,accountUser);
        accountUserMapper.updateById(accountUser);
    }

    //密码转化md5并捕获异常
    private String checkPassword(String password){
        try {
            password = ChangeType.EncoderByMd5(password);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return password;
    }

    //冗余检查解决
    private String checkObject(AccountUserDTO from){
        if (!from.getUserAccount().matches(ChangeType.PHONE_CHECK)){
            throw new BusinessException(ErrorCodeEnum.PHONE_FORMAT);
        }
        //判断是否已存在该账号
        AccountUser accountUser1 = accountUserMapper.selectOne(new QueryWrapper<AccountUser>().lambda().eq(AccountUser::getUserAccount, from.getUserAccount()).orderByDesc(AccountUser::getGmtCreate).last("limit 1"));
        if(accountUser1!=null){
            throw new BusinessException(ErrorCodeEnum.LOGIN_EXIST);
        }
        String password= this.checkPassword(from.getUserPassword());
        return password;
    }

    //冗余属性赋值操作
    private AccountUser propertySet(AccountUserDTO from,Integer status,Integer type,String password){
        AccountUser accountUser = new AccountUser();
        BeanUtil.copy(from,accountUser);
        //默认激活
        accountUser.setActiveStatus(status);
        //赋予员工管理员权限
        accountUser.setUserType(type);
        accountUser.setUserPassword(password);
        return accountUser;
    }
}
