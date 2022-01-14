package com.binhao.drive.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2022/1/5 16:39
 * @Description: TODO
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.binhao.drive.common.em.ErrorCodeEnum;
import com.binhao.drive.common.util.BeanUtil;
import com.binhao.drive.common.vo.BusinessException;
import com.binhao.drive.manager.dto.CourseSubscribeDTO;
import com.binhao.drive.manager.mapper.CourseSubscribeMapper;
import com.binhao.drive.manager.po.CourseSubscribe;
import com.binhao.drive.manager.query.CourseSubscribeQuery;
import com.binhao.drive.manager.service.CourseSubscribeService;
import com.binhao.drive.manager.vo.CourseSubscribeVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseSubscribeServiceImpl extends ServiceImpl<CourseSubscribeMapper, CourseSubscribe> implements CourseSubscribeService {

    @Resource
    private CourseSubscribeMapper courseSubscribeMapper;

    @Override
    public PageInfo<CourseSubscribeVO> pageData(CourseSubscribeQuery query) {
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        List<CourseSubscribeVO> list = courseSubscribeMapper.selectDataList(query);
        return PageInfo.of(list);
    }

    @Override
    public void updateData(CourseSubscribeDTO formDTO) {
        CourseSubscribe courseSubscribe = new CourseSubscribe();
        BeanUtil.copy(formDTO,courseSubscribe);
        courseSubscribe.setIsResponse(1);
        courseSubscribeMapper.updateById(courseSubscribe);
    }

    @Override
    public CourseSubscribeVO selectDataById(String id) {
        return courseSubscribeMapper.selectDataById(id);
    }

    @Override
    public void insertData(CourseSubscribeDTO formDTO) {
        CourseSubscribe courseSubscribe = new CourseSubscribe();
        BeanUtil.copy(formDTO,courseSubscribe);
        courseSubscribeMapper.insert(courseSubscribe);
    }

    @Override
    public void selectCountCheck(String id) {
        //找到这个人 以及未处理的  预约信息
        int check = courseSubscribeMapper.selectCount(new QueryWrapper<CourseSubscribe>().lambda().eq(CourseSubscribe::getIsResponse, 0).eq(CourseSubscribe::getFkUserId, id));
        if (check>0){
            throw new BusinessException(ErrorCodeEnum.CHECK_COURSE);
        }
    }
}
