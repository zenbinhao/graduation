package com.binhao.drive.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binhao.drive.manager.dto.TeacherDTO;
import com.binhao.drive.manager.dto.TeacherInsertDTO;
import com.binhao.drive.manager.po.Teacher;
import com.binhao.drive.manager.query.TeacherQuery;
import com.binhao.drive.manager.vo.TeacherVO;
import com.github.pagehelper.PageInfo;

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


}
