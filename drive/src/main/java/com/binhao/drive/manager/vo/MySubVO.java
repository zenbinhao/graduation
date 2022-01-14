package com.binhao.drive.manager.vo;/*
 * @Author: zeng
 * @Data: 2022/1/11 16:28
 * @Description: TODO
 */

import com.binhao.drive.manager.po.CourseSubscribe;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="MySubVO", description="预约本教练员的信息表")
public class MySubVO extends CourseSubscribe {

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("用户账户")
    private String userAccount;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("考试进度（0科目一、1科目二、2科目三、3科目四、4已毕业）")
    private Integer plan;
}
