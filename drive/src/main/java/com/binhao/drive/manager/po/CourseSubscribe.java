package com.binhao.drive.manager.po;/*
 * @Author: zeng
 * @Data: 2022/1/5 14:51
 * @Description: TODO
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.binhao.drive.common.po.BusinessPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("预约课程信息表")
@TableName("drive_coursesubscribe")
@Data
public class CourseSubscribe extends BusinessPO {

    @ApiModelProperty("外键fkUserId")
    private String fkUserId;

    @ApiModelProperty("教练员自定义回应内容")
    private String responseContent;

    @ApiModelProperty("教练员是否回应(0未受理，1已回应)")
    private Integer isResponse;

}
