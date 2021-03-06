package com.binhao.drive.manager.controller;/*
 * @Author: zeng
 * @Data: 2022/1/5 14:40
 * @Description: TODO
 */

import com.binhao.drive.common.aop.AopOperation;
import com.binhao.drive.common.controller.BaseController;
import com.binhao.drive.common.vo.ResultVO;
import com.binhao.drive.manager.dto.PaymentDTO;
import com.binhao.drive.manager.query.PaymentQuery;
import com.binhao.drive.manager.service.PaymentService;
import com.binhao.drive.manager.vo.PaymentVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(
        tags = {"管理端-缴费信息"}
)
@RestController
@RequestMapping("/payment")
public class PaymentController extends BaseController {

    @Resource
    private PaymentService paymentService;

    @AopOperation(
            type = "分页查询",
            checkPermission = true,
            checkEmployee = true
    )
    @ApiOperation("分页查询")
    @PostMapping({"/page"})
    public ResultVO<PageInfo<PaymentVO>> pageDate(@RequestBody PaymentQuery query){
        PageInfo info = paymentService.pageData(query);
        return this.success(info,"分页查询成功");
    }

    @AopOperation(
            type = "查询所有需要确认的缴费信息",
            checkPermission = true,
            checkEmployee = true

    )
    @ApiOperation("查询所有需要确认的缴费信息")
    @PostMapping({"/isCheckList"})
    public ResultVO<List<PaymentVO>> selectIsCheckList(@RequestBody PaymentQuery query){
        List<PaymentVO> info = paymentService.selectIsCheckList(query);
        return this.success(info,"查询所有需要确认的缴费信息成功");
    }

    @AopOperation(
            type = "详情",
            checkPermission = true,
            checkEmployee = true

    )
    @ApiOperation("通过id查询")
    @GetMapping({"/{id}"})
    public ResultVO<PaymentVO> selectById(@PathVariable String id) {

        PaymentVO paymentVO = paymentService.selectDataById(id);
        return this.success(paymentVO, "查询id为:" + id + "用户成功");
    }


    @AopOperation(
            type = "新增",
            checkPermission = true,
            checkEmployee = true

    )
    @ApiOperation("新增信息")
    @PostMapping({"/"})
    public ResultVO insertData(@Valid @RequestBody PaymentDTO form) {
        paymentService.insertData(form);
        return this.success("新增信息成功");
    }


    @AopOperation(
            type = "考试缴费已确认",
            checkPermission = true,
            checkEmployee = true

    )
    @ApiOperation("考试缴费已确认")
    @PutMapping({"/examPayState/{id}/{fkUserId}"})
    public ResultVO updateExamPayState(@PathVariable String id,@PathVariable String fkUserId) {
        paymentService.updateExamPayState(id,fkUserId);
        return this.success("考试缴费已确认");
    }


    @AopOperation(
            type = "修改",
            checkPermission = true,
            checkEmployee = true

    )
    @ApiOperation("修改信息")
    @PutMapping({"/"})
    public ResultVO updateData(@RequestBody PaymentDTO form) {
        paymentService.updateData(form);
        return this.success("修改信息成功");
    }

}
