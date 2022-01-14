package com.binhao.drive.manager.controller;/*
 * @Author: zeng
 * @Data: 2022/1/4 15:11
 * @Description: TODO
 */

import com.binhao.drive.common.aop.AopOperation;
import com.binhao.drive.common.bo.SessionUser;
import com.binhao.drive.common.controller.BaseController;
import com.binhao.drive.common.em.ErrorCodeEnum;
import com.binhao.drive.common.vo.BusinessException;
import com.binhao.drive.common.vo.ResultVO;
import com.binhao.drive.manager.dto.CourseSubscribeDTO;
import com.binhao.drive.manager.dto.TeacherDTO;
import com.binhao.drive.manager.dto.TeacherInsertDTO;
import com.binhao.drive.manager.po.Teacher;
import com.binhao.drive.manager.query.MySubQuery;
import com.binhao.drive.manager.query.TeacherQuery;
import com.binhao.drive.manager.service.TeacherService;
import com.binhao.drive.manager.vo.MySubVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Api(
        tags = "管理端-教练员信息管理"
)
@RequestMapping("/teacher")
@RestController
public class TeacherController extends BaseController {

    @Resource
    private TeacherService teacherService;

    @AopOperation(
            type = "分页查询",
            checkPermission = true,
            checkEmployee = true

    )
    @ApiOperation("分页查询")
    @PostMapping({"/page"})
    public ResultVO<PageInfo<Teacher>> pageDate(@RequestBody TeacherQuery query){
        PageInfo info = teacherService.pageData(query);
        return this.success(info,"分页查询成功");
    }

    @AopOperation(
            type = "详情",
            checkPermission = true,
            checkEmployee = true

    )
    @ApiOperation("通过id查询")
    @GetMapping({"/{id}"})
    public ResultVO<Teacher> selectById(@PathVariable String id) {

        Teacher teacher = teacherService.selectById(id);
        return this.success(teacher, "查询id为:" + id + "用户成功");
    }


    @AopOperation(
            type = "新增",
            checkPermission = true,
            checkEmployee = true

    )
    @ApiOperation("新增信息")
    @PostMapping({"/"})
    public ResultVO insertData(@Valid @RequestBody TeacherInsertDTO form) {
        teacherService.insertData(form);
        return this.success("新增信息成功");
    }

    @AopOperation(
            type = "修改",
            checkPermission = true,
            checkEmployee = true
    )
    @ApiOperation("修改信息")
    @PutMapping({"/"})
    public ResultVO updateData(@RequestBody TeacherDTO form) {
        teacherService.updateData(form);
        return this.success("修改信息成功");
    }

    @AopOperation(
            type = "批量删除",
            checkPermission = true,
            checkEmployee = true
    )
    @ApiOperation("批量删除ID删除逗号隔开")
    @DeleteMapping({"/{ids}"})
    public ResultVO deleteData(@PathVariable("ids") String ids) {
        teacherService.deleteData(ids);
        return this.success("批量删除成功");
    }




}
