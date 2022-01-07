package com.binhao.drive.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2022/1/5 17:18
 * @Description: TODO
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.binhao.drive.common.util.BeanUtil;
import com.binhao.drive.manager.dto.ExamSubscribeDTO;
import com.binhao.drive.manager.mapper.ExamSubscribeMapper;
import com.binhao.drive.manager.po.ExamSubscribe;
import com.binhao.drive.manager.query.ExamSubscribeQuery;
import com.binhao.drive.manager.service.ExamSubscribeService;
import com.binhao.drive.manager.vo.ExamSubscribeVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ExamSubscribeServiceImpl extends ServiceImpl<ExamSubscribeMapper, ExamSubscribe> implements ExamSubscribeService {

    @Resource
    private ExamSubscribeMapper examSubscribeMapper;

    @Override
    public PageInfo<ExamSubscribeVO> pageData(ExamSubscribeQuery query) {
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        List<ExamSubscribeVO> list = examSubscribeMapper.selectDataList(query);
        return PageInfo.of(list);
    }

    @Override
    public void updateData(ExamSubscribeDTO formDTO) {
        ExamSubscribe examSubscribe = new ExamSubscribe();
        BeanUtil.copy(formDTO,examSubscribe);
        examSubscribeMapper.updateById(examSubscribe);
    }

    @Override
    public ExamSubscribeVO selectDataById(String id) {
        return examSubscribeMapper.selectDataById(id);
    }

    @Override
    public void insertData(ExamSubscribeDTO formDTO) {
        ExamSubscribe examSubscribe = new ExamSubscribe();
        BeanUtil.copy(formDTO,examSubscribe);
        examSubscribeMapper.insert(examSubscribe);
    }
}
