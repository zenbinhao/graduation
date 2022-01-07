package com.binhao.drive.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2022/1/5 16:39
 * @Description: TODO
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.binhao.drive.common.util.BeanUtil;
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
}
