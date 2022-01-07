package com.binhao.drive.manager.query;/*
 * @Author: zeng
 * @Data: 2022/1/5 13:18
 * @Description: TODO
 */

import com.binhao.drive.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "PaymentQuery",description = "缴费信息查询条件")
public class PaymentQuery extends PageQuery {

    @ApiModelProperty("学员姓名")
    private String userName;

    @ApiModelProperty("学员账号（手机号）")
    private String userAccount;
}
