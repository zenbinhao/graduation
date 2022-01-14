package com.binhao.drive.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binhao.drive.common.bo.SessionUser;
import com.binhao.drive.manager.dto.CourseSubscribeDTO;
import com.binhao.drive.manager.dto.TeacherDTO;
import com.binhao.drive.manager.dto.TeacherInsertDTO;
import com.binhao.drive.manager.po.Teacher;
import com.binhao.drive.manager.query.MySubQuery;
import com.binhao.drive.manager.query.TeacherQuery;
import com.binhao.drive.manager.vo.MySubVO;
import com.binhao.drive.manager.vo.TeacherVO;
import com.github.pagehelper.PageInfo;

import java.util.concurrent.Future;

public interface TeacherService extends IService<Teacher>{
    /**
     * @Author zengbh
     * @Description //TODO 分页查询
     * @Date 15:46 
     * @Param [query]
     * @return com.github.pagehelper.PageInfo<com.binhao.drive.manager.vo.AccountUserInfoVO>
     **/
    PageInfo<Teacher> pageData(TeacherQuery query);
    /**
     * @Author zengbh
     * @Description //TODO 增加
     * @Date 15:47
     * @Param [formDTO]
     * @return void
     **/
    void insertData(TeacherInsertDTO formDTO);
    /**
     * @Author zengbh
     * @Description //TODO 批量删除
     * @Date 15:46 
     * @Param [id]
     * @return void
     **/
    void deleteData(String ids);

    /**
     * @Author zengbh
     * @Description //TODO 修改
     * @Date 15:48
     * @Param [formDTO]
     * @return void
     **/
    void updateData(TeacherDTO formDTO);

    /**
     * @Author zengbh
     * @Description //TODO 详情（根据表的主键id单查）
     * @Date 15:46
     * @Param [id]
     * @return com.binhao.drive.manager.vo.TeacherVO
     **/
    TeacherVO selectById(String id);


//    /**
//     * @Author zengbh
//     * @Description //TODO 查看自己的学员预约信息
//     * @Date 16:40
//     * @Param
//     * @return
//     **/
//
//    PageInfo<MySubVO> pageDataMySub(MySubQuery query);
//
//    /**
//     * @Author zengbh
//     * @Description //TODO 回应学员预约信息
//     * @Date 16:43
//     * @Param
//     * @return
//     **/
//    Future<Integer> updateResponseStudent(CourseSubscribeDTO dto, SessionUser sessionUser);
//

}
