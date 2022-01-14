package com.binhao.drive.manager.dto;/*
 * @Author: zeng
 * @Data: 2022/1/5 17:17
 * @Description: TODO
 */

import com.binhao.drive.common.dto.BusinessFormDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "ExamSubscribeDTO",description = "预约考试信息表单")
@Data
public class ExamSubscribeDTO extends BusinessFormDTO {

    @ApiModelProperty("外键fkUserId")
    private String fkUserId;

    @ApiModelProperty("考试科目(1科目一、2科目二、3科目三、4科目四)")
    private Integer subject;

    @ApiModelProperty("是否受理(0未受理，1已处理)")
    private Integer isResponse;

    @ApiModelProperty("考试通过认定(0未处理,1通过,2不通过)")
    private Integer isPass;

    @ApiModelProperty("处理内容")
    private String content;

}
