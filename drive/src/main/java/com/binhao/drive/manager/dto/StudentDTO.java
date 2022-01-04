package com.binhao.drive.manager.dto;/*
 * @Author: zeng
 * @Data: 2021/11/10 17:42
 * @Description: TODO
 */

import com.binhao.drive.common.dto.BusinessFormDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value="StudentDTO", description="学员信息表")
public class StudentDTO extends BusinessFormDTO {

    @NotBlank(message = "用户姓名不能为空")
    @ApiModelProperty("用户姓名")
    private String userName;

    @NotBlank(message = "用户账户（手机号）不能为空")
    @ApiModelProperty("用户账户")
    private String userAccount;

    @NotBlank(message = "用户密码不能为空")
    @ApiModelProperty("用户密码")
    private String userPassword;
//    业务中自动绑定  外键无需进行操作
//    @NotBlank(message = "外键user_id不能为空")
//    @ApiModelProperty("外键user_id")
//    private String fkUserId;

    //单独做一个业务 新增不传
//    @NotBlank(message = "外键教练员teacher_id不能为空")
    @ApiModelProperty("外键教练员teacher_id")
    private String fkTeacherId;

    @NotBlank(message = "头像附件路径不能为空")
    @ApiModelProperty("照片附件路径")
    private String picture;

    @NotNull(message = "性别不能为空")
    @ApiModelProperty("性别 默认0未确定，1男，2女")
    private Integer sex;

    @NotBlank(message = "身份证不能为空")
    @ApiModelProperty("身份证")
    private String card;

    @NotNull(message = "考试进度不能为空")
    @ApiModelProperty("考试进度（0科目一、1科目二、2科目三、3科目四、4已毕业）")
    private Integer plan;
}
