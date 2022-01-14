package com.binhao.drive.manager.query;/*
 * @Author: zeng
 * @Data: 2022/1/5 16:59
 * @Description: TODO
 */

import com.binhao.drive.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "ExamSubscribeQuery",description = "预约考试信息查询条件")
public class ExamSubscribeQuery extends PageQuery {

    @ApiModelProperty("学员姓名")
    private String userName;

    @ApiModelProperty("学员账号（手机号）")
    private String userAccount;

    @NotNull(message = "考试科目不能为空")
    @ApiModelProperty("考试科目(1科目一、2科目二、3科目三、4科目四)")
    private Integer subject;

}
