package com.binhao.drive.manager.vo;/*
 * @Author: zeng
 * @Data: 2021/11/10 17:21
 * @Description: TODO
 */

import com.binhao.drive.manager.po.Student;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value="StudentVO", description="学员信息表")
public class StudentVO extends Student {

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("用户账户")
    private String userAccount;

//    @ApiModelProperty("用户密码")
//    private String userPassword;


}
