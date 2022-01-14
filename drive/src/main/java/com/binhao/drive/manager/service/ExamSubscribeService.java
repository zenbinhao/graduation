package com.binhao.drive.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binhao.drive.manager.dto.DelExamSubDTO;
import com.binhao.drive.manager.dto.ExamSubscribeDTO;
import com.binhao.drive.manager.po.ExamSubscribe;
import com.binhao.drive.manager.query.ExamSubscribeQuery;
import com.binhao.drive.manager.vo.ExamSubscribeVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public interface ExamSubscribeService extends IService<ExamSubscribe> {

    PageInfo<ExamSubscribeVO> pageData(ExamSubscribeQuery query);

    List<ExamSubscribeVO> selectIsResponseList(ExamSubscribeQuery query);
    //修改状态
    void updateData(ExamSubscribeDTO formDTO);

    ExamSubscribeVO selectDataById(String id);

    void insertData(ExamSubscribeDTO formDTO);

    void updateDel(String id, String content, String email, CountDownLatch countDownLatch);

    void checkPass(String fkUserId, Integer isPass);

    int insertExam(String id);

    void checkPass(String fkUserId);
}
