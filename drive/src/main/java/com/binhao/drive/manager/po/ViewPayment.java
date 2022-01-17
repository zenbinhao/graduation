package com.binhao.drive.manager.po;/*
 * @Author: zeng
 * @Data: 2022/1/17 16:30
 * @Description: TODO
 */

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("view_news_payment")
public class ViewPayment {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("缴费信息id")
    private String payId;

    @ApiModelProperty("缴费方式")
    private String payWay;

    @ApiModelProperty("缴费内容")
    private String payContent;

}
