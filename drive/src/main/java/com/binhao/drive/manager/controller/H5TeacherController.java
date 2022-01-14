package com.binhao.drive.manager.controller;/*
 * @Author: zeng
 * @Data: 2022/1/14 16:26
 * @Description: TODO
 */

import com.binhao.drive.common.aop.AopOperation;
import com.binhao.drive.common.controller.BaseController;
import com.binhao.drive.common.em.ErrorCodeEnum;
import com.binhao.drive.common.vo.BusinessException;
import com.binhao.drive.common.vo.ResultVO;
import com.binhao.drive.manager.dto.CourseSubscribeDTO;
import com.binhao.drive.manager.query.MySubQuery;
import com.binhao.drive.manager.service.H5TeacherService;
import com.binhao.drive.manager.vo.MySubVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.Future;

@Api(
        tags = {"H5教练端-业务操作"}
)
@RequestMapping("/h5Teacher")
@RestController
public class H5TeacherController extends BaseController {

    @Resource
    private H5TeacherService h5TeacherService;

    @AopOperation(
            type = "分页查询自己被预约的信息",
            checkPermission = true
    )
    @ApiOperation("分页查询自己被预约的信息")
    @PostMapping({"/mySub"})
    public ResultVO<PageInfo<MySubVO>> pageDataMySub(@RequestBody MySubQuery query) {
        PageInfo<MySubVO> info = h5TeacherService.pageDataMySub(query);
        return this.success(info,"分页查询自己被预约的信息成功");
    }

    @SneakyThrows
    @AopOperation(
            type = "回复学员的预约",
            checkPermission = true
    )
    @ApiOperation("回复学员的预约")
    @PutMapping({"/updateResponse"})
    public ResultVO updateResponseStudent(@RequestBody CourseSubscribeDTO dto)  {

        Future<Integer> integerFuture = h5TeacherService.updateResponseStudent(dto, this.getSessionUser());
        if (integerFuture.get()==1){
            throw new BusinessException(ErrorCodeEnum.SYS_ERROR);
        }

        return this.success("成功回复学员的预约");
    }
}
