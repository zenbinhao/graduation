package com.binhao.drive.manager.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.binhao.drive.common.dto.BusinessFormDTO;
import com.binhao.drive.common.po.BasePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("drive_student")
@ApiModel("学员信息表")
@Data
public class Student extends BusinessFormDTO {

    @ApiModelProperty("外键user_id")
    private String fkUserId;

    @ApiModelProperty("外键教练员teacher_id")
    private String fkTeacherId;

    @ApiModelProperty("照片附件路径")
    private String picture;

    @ApiModelProperty("性别 默认0未确定，1男，2女")
    private Integer sex;

    @ApiModelProperty("学员身份证")
    private String card;

    @ApiModelProperty("考试进度（0科目一、1科目二、2科目三、3科目四、4已毕业）")
    private Integer plan;

}
