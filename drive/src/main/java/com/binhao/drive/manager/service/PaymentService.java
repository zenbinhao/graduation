package com.binhao.drive.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binhao.drive.common.bo.SessionUser;
import com.binhao.drive.manager.dto.PaymentDTO;
import com.binhao.drive.manager.po.Payment;
import com.binhao.drive.manager.query.PaymentQuery;
import com.binhao.drive.manager.vo.PaymentVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PaymentService extends IService<Payment> {

    PageInfo<PaymentVO> pageData(PaymentQuery query);

    List<PaymentVO> selectIsCheckList(PaymentQuery query);

    //修改状态
    void updateData(PaymentDTO formDTO);

    PaymentVO selectDataById(String id);

    void insertData(PaymentDTO formDTO);

//    void insertExamPay(PaymentDTO form, SessionUser sessionUser);

    void updateExamPayState(String id);
}
