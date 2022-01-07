package com.binhao.drive.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binhao.drive.manager.dto.ExamSubscribeDTO;
import com.binhao.drive.manager.po.ExamSubscribe;
import com.binhao.drive.manager.query.ExamSubscribeQuery;
import com.binhao.drive.manager.vo.ExamSubscribeVO;
import com.github.pagehelper.PageInfo;

public interface ExamSubscribeService extends IService<ExamSubscribe> {

    PageInfo<ExamSubscribeVO> pageData(ExamSubscribeQuery query);

    //修改状态
    void updateData(ExamSubscribeDTO formDTO);

    ExamSubscribeVO selectDataById(String id);

    void insertData(ExamSubscribeDTO formDTO);
}
