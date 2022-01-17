package com.binhao.drive.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2022/1/5 14:11
 * @Description: TODO
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.binhao.drive.common.bo.SessionUser;
import com.binhao.drive.common.po.BasePO;
import com.binhao.drive.common.util.BeanUtil;
import com.binhao.drive.common.vo.BusinessException;
import com.binhao.drive.manager.dto.PaymentDTO;
import com.binhao.drive.manager.mapper.ExamSubscribeMapper;
import com.binhao.drive.manager.mapper.PaymentMapper;
import com.binhao.drive.manager.mapper.StudentMapper;
import com.binhao.drive.manager.po.ExamSubscribe;
import com.binhao.drive.manager.po.Payment;
import com.binhao.drive.manager.po.Student;
import com.binhao.drive.manager.query.PaymentQuery;
import com.binhao.drive.manager.service.ExamSubscribeService;
import com.binhao.drive.manager.service.PaymentService;
import com.binhao.drive.manager.service.StudentService;
import com.binhao.drive.manager.vo.PaymentVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements PaymentService {

    @Resource
    private PaymentMapper paymentMapper;

    @Resource
    private ExamSubscribeService examSubscribeService;

    @Override
    public PageInfo<PaymentVO> pageData(PaymentQuery query) {
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        List<PaymentVO> list = paymentMapper.selectDataList(query);
        return PageInfo.of(list);
    }

    @Override
    public List<PaymentVO> selectIsCheckList(PaymentQuery query) {
        List<PaymentVO> list = paymentMapper.selectDataList(query);
        return list;
    }

    @Override
    public void updateData(PaymentDTO formDTO) {
        Payment payment = new Payment();
        BeanUtil.copy(formDTO,payment);
        paymentMapper.updateById(payment);
    }

    @Override
    public PaymentVO selectDataById(String id) {
        return paymentMapper.selectDataById(id);
    }

    @Override
    public void insertData(PaymentDTO formDTO) {
        Payment payment = new Payment();
        BeanUtil.copy(formDTO,payment);
        paymentMapper.insert(payment);
    }

//    @Override
//    public void insertExamPay(PaymentDTO form, SessionUser sessionUser) {
//
//        //首先判断是否重复预约考试
//
//        //是否缴费信息还未通过
//        Integer integer = paymentMapper.selectCount(new QueryWrapper<Payment>().lambda().eq(Payment::getIsCheck, 0).eq(Payment::getFkUserId, form.getFkUserId()));
//        if(integer>0){
//            throw new BusinessException("1060","您的缴费信息还未通过");
//        }
//        //是否预约信息还未处理
//        Integer integer1 = examSubscribeMapper.selectCount(new QueryWrapper<ExamSubscribe>().lambda().eq(ExamSubscribe::getFkUserId, form.getFkUserId()).eq(ExamSubscribe::getIsResponse, 0));
//        if(integer1>0){
//            throw new BusinessException("1061","您的预约考试还在处理中");
//        }
//        //是否考试还未认定
//        Integer integer2 = examSubscribeMapper.selectCount(new QueryWrapper<ExamSubscribe>().lambda().eq(ExamSubscribe::getFkUserId,form.getFkUserId()).eq(ExamSubscribe::getIsPass,0));
//        if (integer2>0){
//            throw new BusinessException("1062","您的考试还未进行认定");
//        }
//        Student student = studentMapper.selectOne(new QueryWrapper<Student>().lambda().eq(Student::getFkUserId, form.getFkUserId()).orderByDesc(BasePO::getGmtCreate).last("limit 1"));
//
//        //信息进行新增
//        Payment payment = new Payment();
//        BeanUtil.copy(form,payment);
//        payment.setContent(student.getPlan());
//        payment.setIsCheck(0);
//        payment.setFkUserId(sessionUser.getUserId());
//        paymentMapper.insert(payment);
//    }

    @Override
    public void updateExamPayState(String id,String fkUserId) {
        //首先修改为已缴费通过
        paymentMapper.update(null,new UpdateWrapper<Payment>().lambda().set(Payment::getIsCheck,1).eq(BasePO::getId,id));
        //生成预约考试的信息
        examSubscribeService.insertExam(fkUserId);
    }
}
