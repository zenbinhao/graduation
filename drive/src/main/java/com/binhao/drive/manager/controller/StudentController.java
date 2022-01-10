package com.binhao.drive.manager.controller;/*
 * @Author: zeng
 * @Data: 2021/11/10 17:58
 * @Description: TODO
 */

import com.binhao.drive.common.aop.AopOperation;
import com.binhao.drive.common.controller.BaseController;
import com.binhao.drive.common.vo.ResultVO;
import com.binhao.drive.manager.dto.ChooseTeacherDTO;
import com.binhao.drive.manager.dto.StudentDTO;
import com.binhao.drive.manager.query.StudentQuery;
import com.binhao.drive.manager.service.StudentService;
import com.binhao.drive.manager.vo.StudentVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(
        tags = {"管理端-学员信息管理"}
)
@RestController
@RequestMapping("/userinfo")
public class StudentController extends BaseController {

    @Resource
    private StudentService studentService;

    @AopOperation(
            type = "分页查询",
            checkPermission = true
    )
    @ApiOperation("分页查询")
    @PostMapping({"/page"})
    public ResultVO<PageInfo<StudentVO>> pageDate(@RequestBody StudentQuery query){
        PageInfo info = studentService.pageData(query);
        return this.success(info,"分页查询成功");
    }

    @AopOperation(
            type = "详情",
            checkPermission = true
    )
    @ApiOperation("通过id查询")
    @GetMapping({"/{id}"})
    public ResultVO<StudentVO> selectById(@PathVariable String id) {

        StudentVO accountUserInfoVO = studentService.selectById(id);
        return this.success(accountUserInfoVO, "查询id为:" + id + "用户成功");
    }


    @AopOperation(
            type = "新增",
            checkPermission = true
    )
    @ApiOperation("新增信息")
    @PostMapping({"/"})
    public ResultVO insertData(@Valid @RequestBody StudentDTO form) {
        studentService.insertData(form);
        return this.success("新增信息成功");
    }

    @AopOperation(
            type = "修改",
            checkPermission = true
    )
    @ApiOperation("修改信息")
    @PutMapping({"/"})
    public ResultVO updateData(@RequestBody StudentDTO form) {
        studentService.updateData(form);
        return this.success("修改信息成功");
    }

    @AopOperation(
            type = "批量删除",
            checkPermission = true
    )
    @ApiOperation("根据ID批量删除")
    @DeleteMapping({"/{ids}"})
    public ResultVO deleteData(@PathVariable("ids") String ids) {
        System.out.println("ids=========================================="+ids);
        studentService.deleteData(ids);
        return this.success("根据ID删除成功");
    }

    @AopOperation(
            type = "详情",
            checkPermission = true
    )
    @ApiOperation("通过fkUserId查询")
    @GetMapping({"/selectByFkUserId/{fkUserId}"})
    public ResultVO selectByFkUserId(@PathVariable("fkUserId") String id){
        StudentVO accountUserInfoVO = studentService.selectByFkUserId(id);
        return this.success(accountUserInfoVO,"查询fkUserId为:" + id + "用户成功");
    }

    @AopOperation(
            type = "绑定教练员",
            checkPermission = true
    )
    @ApiOperation("绑定教练员")
    @PutMapping({"/choose"})
    public ResultVO updateChooseTeacher(@RequestBody ChooseTeacherDTO formDTO) {
        studentService.updateChooseTeacher(formDTO);
        return this.success("成功绑定教练员");
    }

    @AopOperation(
            type = "重选绑定教练员",
            checkPermission = true
    )
    @ApiOperation("重选绑定教练员")
    @PutMapping({"/chooseRe"})
    public ResultVO updateChooseReTeacher(@RequestBody ChooseTeacherDTO formDTO) {
        studentService.updateChooseReTeacher(formDTO);
        return this.success("成功重选教练员");
    }

    @AopOperation(
            type = "重置密码",
            checkPermission = true
    )
    @ApiOperation("重置密码")
    @PutMapping({"/resetPwd/{ids}"})
    public ResultVO resetPwd(@PathVariable String ids){
        studentService.resetPwd(ids);
        return this.success("成功重置密码为123456");
    }



    @AopOperation(
            type = "学员预约课程"
    )
    @ApiOperation("学员预约课程")
    @PostMapping({"/insertCourseSub"})
    public ResultVO insertSubscribeCourse() {
        studentService.subscribeCourse(this.getSessionUser());
        return this.success("学员预约课程成功,并发送邮箱通知其教练员");
    }
}
