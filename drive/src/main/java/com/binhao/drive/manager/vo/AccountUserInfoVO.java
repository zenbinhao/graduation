package com.binhao.drive.manager.vo;/*
 * @Author: zeng
 * @Data: 2021/11/10 17:21
 * @Description: TODO
 */

import com.binhao.drive.manager.po.AccountUserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("会员信息表")
@Data
public class AccountUserInfoVO extends AccountUserInfo {

    @ApiModelProperty("用户姓名")
    private String userName;
    @ApiModelProperty("用户账号(手机号)")
    private String userAccount;
}
