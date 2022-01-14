package com.binhao.drive.manager.dto;/*
 * @Author: zeng
 * @Data: 2022/1/13 17:46
 * @Description: TODO
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "DelExamSubDTO",description = "处理考试信息表单")
@Data
public class DelExamSubDTO {

    @ApiModelProperty("表主键id,批量使用,号分割")
    private String ids;

    @ApiModelProperty("对应id邮箱,批量使用,号分割")
    private String emails;

    @ApiModelProperty("处理发送的邮件内容")
    private String content;
}
