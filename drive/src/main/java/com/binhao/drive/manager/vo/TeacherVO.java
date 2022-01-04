package com.binhao.drive.manager.vo;/*
 * @Author: zeng
 * @Data: 2022/1/2 15:50
 * @Description: TODO
 */

import com.binhao.drive.manager.po.Teacher;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value="TeacherVO", description="教练员信息")
public class TeacherVO extends Teacher {

}
