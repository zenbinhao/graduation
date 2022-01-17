package com.binhao.drive.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2022/1/17 16:44
 * @Description: TODO
 */

import com.binhao.drive.manager.mapper.ViewDelExamMapper;
import com.binhao.drive.manager.mapper.ViewPassExamMapper;
import com.binhao.drive.manager.mapper.ViewPaymentMapper;
import com.binhao.drive.manager.po.ViewDelExam;
import com.binhao.drive.manager.po.ViewPassExam;
import com.binhao.drive.manager.po.ViewPayment;
import com.binhao.drive.manager.service.ViewService;
import com.binhao.drive.manager.vo.view.ViewVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ViewServiceImpl implements ViewService {


    @Resource
    private ViewPassExamMapper viewPassExamMapper;

    @Resource
    private ViewPaymentMapper viewPaymentMapper;

    @Resource
    private ViewDelExamMapper viewDelExamMapper;

    @Override
    public ViewVO selectAllNews() {
        List<ViewPassExam> viewPassExams = viewPassExamMapper.selectList(null);

        List<ViewPayment> viewPayments = viewPaymentMapper.selectList(null);

        List<ViewDelExam> viewDelExams = viewDelExamMapper.selectList(null);

        ViewVO viewVO = new ViewVO(viewPassExams,viewPayments,viewDelExams);

        return viewVO;
    }
}
