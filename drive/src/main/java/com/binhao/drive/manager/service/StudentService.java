package com.binhao.drive.manager.service;/*
 * @Author: zeng
 * @Data: 2021/11/10 17:11
 * @Description: TODO
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.binhao.drive.manager.dto.ChooseTeacherDTO;
import com.binhao.drive.manager.dto.StudentDTO;
import com.binhao.drive.manager.po.Student;
import com.binhao.drive.manager.query.StudentQuery;
import com.binhao.drive.manager.vo.StudentVO;
import com.github.pagehelper.PageInfo;

public interface StudentService extends IService<Student> {

    PageInfo<StudentVO> pageData(StudentQuery query);

    void updateData(StudentDTO formDTO);

    void deleteData(String ids);

    StudentVO selectById(String id);

    void insertData(StudentDTO studentDTO);

    StudentVO selectByUserId();

    public StudentVO selectByFkUserId(String fkUserId);

    void updateChooseTeacher(ChooseTeacherDTO formDTO);

    void updateChooseReTeacher(ChooseTeacherDTO formDTO);

    void resetPwd(String ids);
}
