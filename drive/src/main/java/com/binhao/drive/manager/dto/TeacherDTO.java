package com.binhao.drive.manager.dto;/*
 * @Author: zeng
 * @Data: 2022/1/2 15:54
 * @Description: TODO
 */

import com.binhao.drive.common.dto.BusinessFormDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value="TeacherDTO", description="教练员信息更新表单")
public class TeacherDTO extends BusinessFormDTO {

    @NotBlank(message = "教练员姓名不能为空")
    @ApiModelProperty("教练员姓名")
    private String name;

    @NotBlank(message = "教练员简介不能为空")
    @ApiModelProperty("教练员简介")
    private String introduction;

    @NotBlank(message = "教练员照片路径不能为空")
    @ApiModelProperty("教练员照片路径")
    private String photo;

    @NotBlank(message = "教练员手机号不能为空")
    @ApiModelProperty("教练员手机号")
    private String phone;

    @NotNull(message = "性别不能为空")
    @ApiModelProperty("性别默认0男,1女")
    private Integer sex;

    @NotNull(message = "年龄不能为空")
    @ApiModelProperty("年龄")
    private Integer age;

    @NotBlank(message = "外键userid不能为空")
    @ApiModelProperty("外键userid")
    private String fkUserId;

    @NotBlank(message = "邮箱不能为空")
    @ApiModelProperty("邮箱")
    private String email;
}
