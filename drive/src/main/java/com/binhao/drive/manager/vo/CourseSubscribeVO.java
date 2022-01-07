package com.binhao.drive.manager.vo;/*
 * @Author: zeng
 * @Data: 2022/1/5 16:31
 * @Description: TODO
 */

import com.binhao.drive.manager.po.CourseSubscribe;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="CourseSubscribeVO", description="约课信息表")
public class CourseSubscribeVO extends CourseSubscribe {

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("用户账户")
    private String userAccount;

}
