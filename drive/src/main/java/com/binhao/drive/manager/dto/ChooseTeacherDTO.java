package com.binhao.drive.manager.dto;/*
 * @Author: zeng
 * @Data: 2022/1/8 15:59
 * @Description: TODO
 */

import com.binhao.drive.common.dto.BusinessFormDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value="ChooseTeacherDTO", description="选择教练员的表单")
public class ChooseTeacherDTO extends BusinessFormDTO {

    //    业务中自动绑定  外键无需进行操作
    @NotBlank(message = "外键user_id不能为空")
    @ApiModelProperty("外键user_id")
    private String fkUserId;

    //单独做一个业务 新增不传
    @NotBlank(message = "外键教练员teacher_id不能为空")
    @ApiModelProperty("外键教练员teacher_id")
    private String fkTeacherId;

    @ApiModelProperty("原需修改外键教练员teacher_id")
    private String oldFkTeacherId;
}
