package com.binhao.drive.common.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SessionUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("认证id")
    private String authToken;
    @ApiModelProperty("用户账号")
    private String userAccount;
    @ApiModelProperty("用户姓名")
    private String userName;
    @ApiModelProperty("用户类型0普通用户，1超级管理员，2员工管理员")
    private Integer userType;
}
