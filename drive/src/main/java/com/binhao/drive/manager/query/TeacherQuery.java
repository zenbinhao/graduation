package com.binhao.drive.manager.query;/*
 * @Author: zeng
 * @Data: 2022/1/2 15:52
 * @Description: TODO
 */

import com.binhao.drive.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="TeacherQuery", description="教练员信息查询条件")
@Data
public class TeacherQuery extends PageQuery{

    @ApiModelProperty("教练员姓名")
    private String name;
}
