package com.binhao.drive.manager.service;/*
 * @Author: zeng
 * @Data: 2022/1/14 23:32
 * @Description: TODO
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.binhao.drive.common.bo.SessionUser;
import com.binhao.drive.manager.dto.PaymentDTO;

import java.util.concurrent.Future;

public interface H5StudentService {

    Future<Integer> subscribeCourse(SessionUser sessionUser);

    void insertExamPay(PaymentDTO form, SessionUser sessionUser);
}
