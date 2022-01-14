package com.binhao.drive.manager.mapper;

import com.binhao.drive.common.mapper.MapperCustom;
import com.binhao.drive.manager.po.Teacher;
import com.binhao.drive.manager.query.MySubQuery;
import com.binhao.drive.manager.vo.MySubVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeacherMapper extends MapperCustom<Teacher> {

    Integer deleteData(@Param("ids") String[] ids);

    List<MySubVO> selectMySub(MySubQuery query);
}
