package com.binhao.drive.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binhao.drive.common.bo.SessionUser;
import com.binhao.drive.manager.dto.CourseSubscribeDTO;
import com.binhao.drive.manager.query.MySubQuery;
import com.binhao.drive.manager.vo.MySubVO;
import com.github.pagehelper.PageInfo;

import java.util.concurrent.Future;

public interface H5TeacherService {

    PageInfo<MySubVO> pageDataMySub(MySubQuery query);

    Future<Integer> updateResponseStudent(CourseSubscribeDTO dto, SessionUser sessionUser);
}
