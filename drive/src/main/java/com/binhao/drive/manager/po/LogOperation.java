package com.binhao.drive.manager.po;

import com.binhao.drive.common.po.BasePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("操作记录")
public class LogOperation extends BasePO {

    @ApiModelProperty(" 操作人id")
    private String fkUserId;
    @ApiModelProperty(" 操作类型操作类型(新增、修改、删除、查询等，详情见常量)")
    private String operationType;
    @ApiModelProperty(" 请求ip")
    private String requestIp;
    @ApiModelProperty(" 请求参数")
    private String requestParams;
    @ApiModelProperty(" 请求方法")
    private String requestMethod;

}
