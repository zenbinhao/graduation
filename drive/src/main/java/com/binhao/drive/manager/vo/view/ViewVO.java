package com.binhao.drive.manager.vo.view;/*
 * @Author: zeng
 * @Data: 2022/1/17 15:38
 * @Description: TODO
 */

import com.binhao.drive.manager.po.ViewDelExam;
import com.binhao.drive.manager.po.ViewPassExam;
import com.binhao.drive.manager.po.ViewPayment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="ViewVO", description="消息面板信息")
public class ViewVO {
    @ApiModelProperty("考试是否通过未认定信息")
    private List<ViewPassExam> viewPassExam;
    @ApiModelProperty("缴费未处理信息")
    private List<ViewPayment> viewPayment;
    @ApiModelProperty("考试未处理信息")
    private List<ViewDelExam> viewDelExam;
}
