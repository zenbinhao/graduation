package com.binhao.drive.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binhao.drive.manager.dto.CourseSubscribeDTO;
import com.binhao.drive.manager.po.CourseSubscribe;
import com.binhao.drive.manager.query.CourseSubscribeQuery;
import com.binhao.drive.manager.vo.CourseSubscribeVO;
import com.github.pagehelper.PageInfo;

public interface CourseSubscribeService extends IService<CourseSubscribe> {

    PageInfo<CourseSubscribeVO> pageData(CourseSubscribeQuery query);

    //修改状态
    void updateData(CourseSubscribeDTO formDTO);

    CourseSubscribeVO selectDataById(String id);

    void insertData(CourseSubscribeDTO formDTO);

    void selectCountCheck(String id);
}
