package com.binhao.drive.manager.mapper;

import com.binhao.drive.common.mapper.MapperCustom;
import com.binhao.drive.manager.po.ExamSubscribe;
import com.binhao.drive.manager.query.ExamSubscribeQuery;
import com.binhao.drive.manager.vo.ExamSubscribeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExamSubscribeMapper extends MapperCustom<ExamSubscribe> {

    List<ExamSubscribeVO> selectDataList(ExamSubscribeQuery query);

    ExamSubscribeVO selectDataById(String id);

    List<ExamSubscribeVO> selectExamList(ExamSubscribeQuery query);
}
