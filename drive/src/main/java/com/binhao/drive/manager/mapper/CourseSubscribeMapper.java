package com.binhao.drive.manager.mapper;

import com.binhao.drive.common.mapper.MapperCustom;
import com.binhao.drive.manager.po.CourseSubscribe;
import com.binhao.drive.manager.query.CourseSubscribeQuery;
import com.binhao.drive.manager.vo.CourseSubscribeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseSubscribeMapper extends MapperCustom<CourseSubscribe> {

    List<CourseSubscribeVO> selectDataList(CourseSubscribeQuery paymentQuery);

    CourseSubscribeVO selectDataById(String id);
}
