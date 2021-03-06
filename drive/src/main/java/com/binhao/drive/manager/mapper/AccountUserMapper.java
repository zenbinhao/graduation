package com.binhao.drive.manager.mapper;

import com.binhao.drive.common.mapper.MapperCustom;
import com.binhao.drive.manager.po.AccountUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountUserMapper extends MapperCustom<AccountUser> {

    Integer deleteData(@Param("ids") String[] ids);

}
