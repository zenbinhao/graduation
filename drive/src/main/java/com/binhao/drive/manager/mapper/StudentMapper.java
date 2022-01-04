package com.binhao.drive.manager.mapper;

import com.binhao.drive.common.mapper.MapperCustom;
import com.binhao.drive.manager.po.Student;
import com.binhao.drive.manager.query.StudentQuery;
import com.binhao.drive.manager.vo.StudentVO;
import com.github.pagehelper.dialect.helper.HsqldbDialect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface StudentMapper extends MapperCustom<Student> {

    List<StudentVO> selectList(StudentQuery studentQuery);

    StudentVO selectById(@Param("id") String id);

    StudentVO selectByUserId(@Param("userId") String id);

    Integer updateTwoTable(HashMap<String,Object> hashMap);

    Integer deleteData(@Param("ids") String[] ids);
}
