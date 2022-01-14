package com.binhao.drive.manager.query;/*
 * @Author: zeng
 * @Data: 2022/1/11 16:32
 * @Description: TODO
 */

import com.binhao.drive.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "MySubQuery",description = "本教练员的预约信息查询条件")
public class MySubQuery extends PageQuery {

//    @NotBlank(message = "教练员id不能为空")
    @ApiModelProperty("教练员id")
    private String teacherId;

    @ApiModelProperty("是否回应 0未处理 1已回应")
    private String isResponse;

}
