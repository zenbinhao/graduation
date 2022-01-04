package com.binhao.drive.manager.query;/*
 * @Author: zeng
 * @Data: 2021/11/10 17:24
 * @Description: TODO
 */

import com.binhao.drive.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="AccountUserInfoQuery", description="会员信息表")
@Data
public class StudentQuery extends PageQuery {

    @ApiModelProperty("学员姓名")
    private String userName;

    @ApiModelProperty("学员账号（手机号）")
    private String userAccount;
}
