package com.binhao.drive.manager.vo;/*
 * @Author: zeng
 * @Data: 2022/1/5 13:18
 * @Description: TODO
 */

import com.binhao.drive.manager.po.Payment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="PaymentVO", description="缴费信息表")
public class PaymentVO extends Payment {

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("用户账户")
    private String userAccount;
}
