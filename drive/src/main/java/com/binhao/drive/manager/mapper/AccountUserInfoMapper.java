package com.binhao.drive.manager.mapper;

import com.binhao.drive.common.mapper.MapperCustom;
import com.binhao.drive.manager.po.AccountUserInfo;
import com.binhao.drive.manager.query.AccountUserInfoQuery;
import com.binhao.drive.manager.vo.AccountUserInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountUserInfoMapper extends MapperCustom<AccountUserInfo> {

    List<AccountUserInfoVO> selectList(AccountUserInfoQuery accountUserInfoQuery);

    AccountUserInfoVO selectById(@Param("id") String id);

    AccountUserInfoVO selectByUserId(@Param("userId") String id);
}
