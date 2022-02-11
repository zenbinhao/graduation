package com.binhao.drive.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2022/1/14 23:33
 * @Description: TODO
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.binhao.drive.common.bo.SessionUser;
import com.binhao.drive.common.po.BasePO;
import com.binhao.drive.common.server.WebSocketServer;
import com.binhao.drive.common.util.BeanUtil;
import com.binhao.drive.common.util.SendEmailUtil;
import com.binhao.drive.common.vo.BusinessException;
import com.binhao.drive.manager.dto.CourseSubscribeDTO;
import com.binhao.drive.manager.dto.PaymentDTO;
import com.binhao.drive.manager.mapper.ExamSubscribeMapper;
import com.binhao.drive.manager.mapper.PaymentMapper;
import com.binhao.drive.manager.mapper.StudentMapper;
import com.binhao.drive.manager.po.ExamSubscribe;
import com.binhao.drive.manager.po.Payment;
import com.binhao.drive.manager.po.Student;
import com.binhao.drive.manager.service.CourseSubscribeService;
import com.binhao.drive.manager.service.H5StudentService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.Future;

@Service
public class H5StudentServiceImpl implements H5StudentService {

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private CourseSubscribeService courseSubscribeService;

    @Resource
    private SendEmailUtil sendEmailUtil;

    @Resource
    private PaymentMapper paymentMapper;

    @Resource
    private ExamSubscribeMapper examSubscribeMapper;

    @Async(value = "getAsyncExecutor")
    @Override
    public Future<Integer> subscribeCourse(SessionUser sessionUser) {
        //判断考试进度是否进入1  科目一
        Integer integer = studentMapper.selectCount(new QueryWrapper<Student>().lambda().eq(Student::getFkUserId, sessionUser.getUserId()).eq(Student::getPlan,1));
        if (integer<1){
            return new AsyncResult<>(new Integer(2));
        }

        //判断是否有预约已经提交 且未处理
        try {
            courseSubscribeService.selectCountCheck(sessionUser.getUserId());
        } catch (Exception e) {
            return new AsyncResult<>(new Integer(1));
        }

        //传过来userid
        //联表查询到对应师傅的手机号
        String email = studentMapper.selectTeacherPhone(sessionUser.getUserId());

        //先存信息防止在发送邮箱的线程中再次容错
        //存取约课记录
        CourseSubscribeDTO courseSubscribeDTO = new CourseSubscribeDTO();
        courseSubscribeDTO.setFkUserId(sessionUser.getUserId());
        courseSubscribeService.insertData(courseSubscribeDTO);
        //调用发送短信提醒师傅
        String sendContent = "[驾校管理系统]:您的学员"+sessionUser.getUserName()+"已预约了您的课程，请教练员尽快排课\\nhttp://localhost:8080/#/";
        System.out.println("==================开始进入发邮箱的方法=====================");
        sendEmailUtil.sendEmail(email, sendContent);
        System.out.println("==================发邮箱方法已结束=====================");

        return new AsyncResult<>(new Integer(0));
    }

    @Override
    public void insertExamPay(PaymentDTO form, SessionUser sessionUser) {

        //首先判断是否重复预约考试

        //是否缴费信息还未通过
        Integer integer = paymentMapper.selectCount(new QueryWrapper<Payment>().lambda().eq(Payment::getIsCheck, 0).eq(Payment::getFkUserId, sessionUser.getUserId()));
        if(integer>0){
            throw new BusinessException("1060","您的缴费信息还未通过");
        }
        //是否预约信息还未处理
        Integer integer1 = examSubscribeMapper.selectCount(new QueryWrapper<ExamSubscribe>().lambda().eq(ExamSubscribe::getFkUserId, sessionUser.getUserId()).eq(ExamSubscribe::getIsResponse, 0));
        if(integer1>0){
            throw new BusinessException("1061","您的预约考试还在处理中");
        }
        //是否考试还未认定
        Integer integer2 = examSubscribeMapper.selectCount(new QueryWrapper<ExamSubscribe>().lambda().eq(ExamSubscribe::getFkUserId, sessionUser.getUserId()).eq(ExamSubscribe::getIsPass,0));
        if (integer2>0){
            throw new BusinessException("1062","您的考试还未进行认定");
        }
        Student student = studentMapper.selectOne(new QueryWrapper<Student>().lambda().eq(Student::getFkUserId, sessionUser.getUserId()).orderByDesc(BasePO::getGmtCreate).last("limit 1"));

        //信息进行新增
        Payment payment = new Payment();
        BeanUtil.copy(form,payment);
        payment.setContent(student.getPlan());
        payment.setIsCheck(0);
        payment.setFkUserId(sessionUser.getUserId());
        paymentMapper.insert(payment);

        try {
            WebSocketServer.BroadCastInfo("您有一条待处理的约考信息");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
