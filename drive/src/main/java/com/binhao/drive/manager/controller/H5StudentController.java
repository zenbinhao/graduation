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
import com.binhao.drive.manager.dto.PaymentDTO;
import com.binhao.drive.manager.service.H5StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.concurrent.Future;

@Api(
        tags = {"H5学员端-业务操作"}
)
@RequestMapping("/h5Student")
@RestController
public class H5StudentController extends BaseController {

    @Resource
    private H5StudentService h5StudentService;

    @SneakyThrows
    @AopOperation(
            type = "学员预约课程"
    )
    @ApiOperation("学员预约课程")
    @PostMapping({"/insertCourseSub"})
    public ResultVO insertSubscribeCourse() {
        Future<Integer> integerFuture = h5StudentService.subscribeCourse(this.getSessionUser());
        if(integerFuture.get()==1){
            throw new BusinessException(ErrorCodeEnum.CHECK_COURSE);
        }else if (integerFuture.get()==2){
            throw new BusinessException(ErrorCodeEnum.NO_TEACHER_ERROR);
        }
        return this.success("学员预约课程成功,并发送邮箱通知其教练员");
    }

//    @AopOperation(
//            type = "学员预约考试"
//    )
//    @ApiOperation("学员预约考试")
//    @PostMapping({"/insertExamSub"})
//    public ResultVO insertSubscribeExam() {
//        int i = studentService.insertExam(this.getSessionUser());
//        String[] out = new String[]{"","科目一","科目二","科目三","科目四"};
//        return this.success("学员预约"+out[i]+"考试成功,并在管理端提醒管理员处理");
//    }

    @AopOperation(
            type = "新增预约考试缴费"
    )
    @ApiOperation("新增预约考试缴费")
    @PostMapping({"/examPay"})
    public ResultVO insertExamPay(@Valid @RequestBody PaymentDTO form) {
        h5StudentService.insertExamPay(form,this.getSessionUser());
        return this.success("新增预约考试缴费信息成功");
    }
}
