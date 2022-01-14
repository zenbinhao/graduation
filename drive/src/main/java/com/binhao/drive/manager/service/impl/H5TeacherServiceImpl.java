package com.binhao.drive.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2022/1/14 23:56
 * @Description: TODO
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.binhao.drive.common.bo.SessionUser;
import com.binhao.drive.common.service.AuthenticationService;
import com.binhao.drive.common.util.SendEmailUtil;
import com.binhao.drive.manager.dto.CourseSubscribeDTO;
import com.binhao.drive.manager.mapper.TeacherMapper;
import com.binhao.drive.manager.po.Teacher;
import com.binhao.drive.manager.query.MySubQuery;
import com.binhao.drive.manager.service.CourseSubscribeService;
import com.binhao.drive.manager.service.H5TeacherService;
import com.binhao.drive.manager.vo.MySubVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Future;

@Service
public class H5TeacherServiceImpl implements H5TeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private CourseSubscribeService courseSubscribeService;

    @Resource
    private SendEmailUtil sendEmailUtil;
    /**
     * @Author zengbh
     * @Description //TODO 查询到预约自己的学员的预约信息
     * @Date 16:46
     * @Param [query]
     * @return com.github.pagehelper.PageInfo<com.binhao.drive.manager.vo.MySubVO>
     **/
    @Override
    public PageInfo<MySubVO> pageDataMySub(MySubQuery query) {
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        Teacher teacher = teacherMapper.selectOne(new QueryWrapper<Teacher>().lambda().eq(Teacher::getFkUserId, authenticationService.getSessionUser().getUserId()));
        query.setTeacherId(teacher.getId());
        return PageInfo.of(teacherMapper.selectMySub(query));
    }

    /**
     * @Author zengbh
     * @Description //TODO 异步方法 对预约自己的信息进行反馈  修改回应的状态（默认注入）  以及回复的话语记录（传参）
     * @Date 16:46
     * @Param [dto]
     * @return void
     **/
    @Async(value = "getAsyncExecutor")
    @Override
    public Future<Integer> updateResponseStudent(CourseSubscribeDTO dto, SessionUser sessionUser) {
        //先记录  提升 重复发送的 容错
        try {
            courseSubscribeService.updateData(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResult<Integer>(new Integer(1));
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("您的教练员-");
        stringBuilder.append(sessionUser.getUserName());
        stringBuilder.append("-对您的回复:\n");
        stringBuilder.append(dto.getResponseContent());

        //发送处理邮件
        System.out.println("==================开始进入发邮箱的方法=====================");
        sendEmailUtil.sendEmail(dto.getEmail(),stringBuilder.toString());
        System.out.println("==================发邮箱方法已结束=====================");

        return new AsyncResult<Integer>(new Integer(0));
    }
}
