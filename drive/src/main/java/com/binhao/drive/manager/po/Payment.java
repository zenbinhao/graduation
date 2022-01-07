package com.binhao.drive.manager.po;/*
 * @Author: zeng
 * @Data: 2022/1/5 13:08
 * @Description: TODO
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.binhao.drive.common.po.BusinessPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("drive_payment")
@ApiModel("缴费信息表")
@Data
public class Payment extends BusinessPO {

    @ApiModelProperty("外键fkUserId")
    private String fkUserId;

    @ApiModelProperty("支付方式（0微信、1支付宝、2现金、3其他）")
    private Integer payWay;

    @ApiModelProperty("缴费内容(0报名费、1科目一、2科目二、3科目三、4科目四、5其他)")
    private Integer content;

    @ApiModelProperty("收费认定（0未受理、1已确认、2驳回）")
    private Integer isCheck;

}
