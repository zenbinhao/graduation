package com.binhao.drive.manager.vo;/*
 * @Author: zeng
 * @Data: 2022/1/5 16:58
 * @Description: TODO
 */

import com.binhao.drive.manager.po.ExamSubscribe;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="ExamSubscribeVO", description="预约考试信息表")
public class ExamSubscribeVO extends ExamSubscribe {

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("用户账户")
    private String userAccount;
}
