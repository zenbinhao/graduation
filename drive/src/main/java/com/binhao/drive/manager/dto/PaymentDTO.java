package com.binhao.drive.manager.dto;/*
 * @Author: zeng
 * @Data: 2022/1/5 14:03
 * @Description: TODO
 */

import com.binhao.drive.common.dto.BusinessFormDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "PaymentDTO",description = "缴费信息表单")
@Data
public class PaymentDTO extends BusinessFormDTO {

    @ApiModelProperty("外键fkUserId")
    private String fkUserId;

    @ApiModelProperty("支付方式（0微信、1支付宝、2现金、3其他）")
    private Integer payWay;

    @ApiModelProperty("缴费内容(0报名费、1科目一、2科目二、3科目三、4科目四、5其他)")
    private Integer content;

    @ApiModelProperty("收费认定（0未受理、1已确认、2驳回）")
    private Integer isCheck;
}
