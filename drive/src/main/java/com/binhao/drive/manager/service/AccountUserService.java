package com.binhao.drive.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binhao.drive.manager.dto.AccountUserDTO;
import com.binhao.drive.manager.po.AccountUser;

public interface AccountUserService extends IService<AccountUser> {
    //管理员添加
    Integer insertAccount(AccountUserDTO from);

    void deleteData(String id);

    void reset(String id);

    void updatePW(String password);

    AccountUser insertTeacher(AccountUserDTO from);

    void deletes(String ids);

    void updateData(AccountUserDTO formDTO);
}
