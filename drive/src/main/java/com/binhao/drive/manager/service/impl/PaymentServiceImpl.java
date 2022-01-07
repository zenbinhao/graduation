package com.binhao.drive.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2022/1/5 14:11
 * @Description: TODO
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.binhao.drive.common.util.BeanUtil;
import com.binhao.drive.manager.dto.PaymentDTO;
import com.binhao.drive.manager.mapper.PaymentMapper;
import com.binhao.drive.manager.po.Payment;
import com.binhao.drive.manager.query.PaymentQuery;
import com.binhao.drive.manager.service.PaymentService;
import com.binhao.drive.manager.vo.PaymentVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements PaymentService {

    @Resource
    private PaymentMapper paymentMapper;

    @Override
    public PageInfo<PaymentVO> pageData(PaymentQuery query) {
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        List<PaymentVO> list = paymentMapper.selectDataList(query);
        return PageInfo.of(list);
    }

    @Override
    public void updateData(PaymentDTO formDTO) {
        Payment payment = new Payment();
        BeanUtil.copy(formDTO,payment);
        paymentMapper.updateById(payment);
    }

    @Override
    public PaymentVO selectDataById(String id) {
        return paymentMapper.selectDataById(id);
    }

    @Override
    public void insertData(PaymentDTO formDTO) {
        Payment payment = new Payment();
        BeanUtil.copy(formDTO,payment);
        paymentMapper.insert(payment);
    }
}
