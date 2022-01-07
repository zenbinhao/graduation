package com.binhao.drive.manager.mapper;

import com.binhao.drive.common.mapper.MapperCustom;
import com.binhao.drive.manager.po.Payment;
import com.binhao.drive.manager.query.PaymentQuery;
import com.binhao.drive.manager.vo.PaymentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentMapper extends MapperCustom<Payment> {
    /**
     * @Author zengbh
     * @Description //TODO 联表查询
     * @Date 13:17
     * @Param
     * @return
     **/

    List<PaymentVO> selectDataList(PaymentQuery paymentQuery);

    PaymentVO selectDataById(String id);
}
