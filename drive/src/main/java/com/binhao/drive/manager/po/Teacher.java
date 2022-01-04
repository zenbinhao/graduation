package com.binhao.drive.manager.po;/*
 * @Author: zeng
 * @Data: 2022/1/2 13:03
 * @Description: TODO
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.binhao.drive.common.po.BusinessPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@TableName("drive_teacher")
@ApiModel(value = "Teacher",description = "教练员信息表")
public class Teacher extends BusinessPO{

    private static final Long serialVersionUID = 1L;

    @ApiModelProperty("教练员姓名")
    private String name;

    @ApiModelProperty("教练员照片路径")
    private String photo;

    @ApiModelProperty("教练员手机号")
    private String phone;

    @ApiModelProperty("总学员数量")
    private Integer studentNumber;

    @ApiModelProperty("性别默认0男,1女")
    private Integer sex;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("外键userid")
    private String fkUserId;

    @ApiModelProperty("教练员简介")
    private String introduction;
}
