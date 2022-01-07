package com.binhao.drive.manager.dto;/*
 * @Author: zeng
 * @Data: 2022/1/5 16:35
 * @Description: TODO
 */

import com.binhao.drive.common.dto.BusinessFormDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "CourseSubscribeDTO",description = "预约信息表单")
@Data
public class CourseSubscribeDTO extends BusinessFormDTO {

    @ApiModelProperty("外键fkUserId")
    private String fkUserId;

    @ApiModelProperty("教练员自定义回应内容")
    private String responseContent;

    @ApiModelProperty("教练员是否回应(0未受理，1已回应)")
    private Integer isResponse;
}
