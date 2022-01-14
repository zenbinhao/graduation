package com.binhao.drive.manager.po;/*
 * @Author: zeng
 * @Data: 2022/1/5 16:48
 * @Description: TODO
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.binhao.drive.common.po.BusinessPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("预约考试信息表")
@TableName("drive_examsubscribe")
@Data
public class ExamSubscribe extends BusinessPO {

    @ApiModelProperty("外键fkUserId")
    private String fkUserId;

    @ApiModelProperty("考试科目(1科目一、2科目二、3科目三、4科目四)")
    private Integer subject;

    @ApiModelProperty("是否受理(0未受理，1已处理)")
    private Integer isResponse;

    @ApiModelProperty("考试通过认定(0未处理,1通过,2不通过)")
    private Integer isPass;

    @ApiModelProperty("处理内容")
    private String content;
}
