package com.binhao.drive.manager.dto;/*
 * @Author: zeng
 * @Data: 2021/11/10 17:42
 * @Description: TODO
 */

import com.binhao.drive.common.dto.BusinessFormDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="AccountUserInfoDTO", description="会员信息表")
public class AccountUserInfoDTO extends BusinessFormDTO {

//    @ApiModelProperty("用户id")
//    private String fkUserId;
    @ApiModelProperty("性别 默认0未确定，1男，2女")
    private Integer sex;
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("其他联系方式")
    private String contact;
    @ApiModelProperty("年龄")
    private Integer age;
    @ApiModelProperty("身高")
    private String height;
    @ApiModelProperty("体重")
    private String weight;
    @ApiModelProperty("微信")
    private String wechat;
    @ApiModelProperty("头像附件路径")
    private String headPath;
//    @ApiModelProperty("关联账户id")
//    private String openId;
}
