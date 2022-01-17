package com.binhao.drive.manager.po;/*
 * @Author: zeng
 * @Data: 2022/1/17 16:30
 * @Description: TODO
 */

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.objects.annotations.Property;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("view_news_delexam")
public class ViewDelExam {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("考试信息id")
    private String examId;

    @ApiModelProperty("考试信息的科目")
    private String examSubject;

}
