package com.binhao.drive.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2021/11/10 17:44
 * @Description: TODO
 */

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.binhao.drive.common.bo.SessionUser;
import com.binhao.drive.common.em.ErrorCodeEnum;
import com.binhao.drive.common.service.AuthenticationService;
import com.binhao.drive.common.util.BeanUtil;
import com.binhao.drive.common.util.ChangeType;
import com.binhao.drive.common.vo.BusinessException;
import com.binhao.drive.manager.dto.AccountUserDTO;
import com.binhao.drive.manager.dto.PaymentDTO;
import com.binhao.drive.manager.dto.StudentDTO;
import com.binhao.drive.manager.mapper.AccountUserMapper;
import com.binhao.drive.manager.mapper.StudentMapper;
import com.binhao.drive.manager.po.AccountUser;
import com.binhao.drive.manager.po.Payment;
import com.binhao.drive.manager.po.Student;
import com.binhao.drive.manager.po.Teacher;
import com.binhao.drive.manager.query.StudentQuery;
import com.binhao.drive.manager.service.PaymentService;
import com.binhao.drive.manager.service.StudentService;
import com.binhao.drive.manager.service.AccountUserService;
import com.binhao.drive.manager.vo.StudentVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {


    @Resource
    private StudentMapper studentMapper;

    @Resource
    private AccountUserMapper accountUserMapper;

    @Resource
    private AccountUserService accountUserService;

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private PaymentService paymentService;

    @Override
    public PageInfo<StudentVO> pageData(StudentQuery query) {
        //开启分页
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        List<StudentVO> list = studentMapper.selectList(query);
        return PageInfo.of(list);
    }

    //密码转化md5并捕获异常
    private String checkPassword(String password){
        try {
            password = ChangeType.EncoderByMd5(password);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return password;
    }

    private void checkObject(StudentDTO formDTO){
        if(StringUtils.isNotEmpty(formDTO.getUserAccount())){
            if (!formDTO.getUserAccount().matches(ChangeType.PHONE_CHECK)){
                throw new BusinessException(ErrorCodeEnum.PHONE_FORMAT);
            }
            AccountUser accountUser = accountUserMapper.selectOne(new QueryWrapper<AccountUser>().lambda().eq(AccountUser::getUserAccount, formDTO.getUserAccount()).orderByDesc(AccountUser::getGmtCreate).last("limit 1"));
            if(accountUser!=null){
                throw new BusinessException(ErrorCodeEnum.LOGIN_EXIST);
            }
        }
    }
    /**
     * @Author zengbh
     * @Description //TODO 联表修改 hashmap传参方式
     * @Date 23:10 
     * @Param [formDTO]
     * @return void
     **/
    @Override
    public void updateData(StudentDTO formDTO) {
        this.checkObject(formDTO);
        HashMap<String, Object> stringObjectHashMap = this.objectToMap(formDTO);
        SessionUser sessionUser = authenticationService.getSessionUser();
        if(StringUtils.isNotEmpty(formDTO.getUserPassword())){
            stringObjectHashMap.put("userPassword",this.checkPassword((String) stringObjectHashMap.get("userPassword")));
        }
        stringObjectHashMap.put("updateUserId",sessionUser.getUserId());
        stringObjectHashMap.put("updateUserName",sessionUser.getUserName());

        Integer integer = studentMapper.updateTwoTable(stringObjectHashMap);
        if(integer<1){
            throw new BusinessException("记录已被更新");
        }
    }

    private HashMap<String,Object> objectToMap(Object object){
        return JSONObject.parseObject(JSONObject.toJSONString(object),HashMap.class);
    }

    @Override
    public void deleteData(String ids) {
        if (StringUtils.isEmpty(ids)) {
            throw new BusinessException("请选择要删除的记录");
        }
        String[] id = ids.split(",");
        int row = studentMapper.deleteData(id);
        if (row <= 0 || ids.length() <= 0) {
            throw new BusinessException("批量删除操作失败");
        }
    }
//    @Override
//    public void deleteData(String ids) {
//        StudentVO accountUserInfoVO = this.selectById(id);
//        if (StringUtils.isEmpty(id)) {
//            throw new BusinessException("请选择要删除的记录");
//        } else {
//            studentMapper.deleteById(id);
//        }
//        String fkUserId = accountUserInfoVO.getFkUserId();
//        accountUserService.deleteData(fkUserId);
//    }

    @Override
    public StudentVO selectById(String id) {
        return studentMapper.selectById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertData(StudentDTO studentDTO) {
        // 首先新增一个账号  并拿到该账号的id
        AccountUserDTO accountUserDTO = new AccountUserDTO();
        BeanUtil.copy(studentDTO,accountUserDTO);
        AccountUser accountUser = accountUserService.insertAccount(accountUserDTO);

        // 新增学员信息
        Student student = new Student();
        BeanUtil.copy(studentDTO,student);
        student.setFkUserId(accountUser.getId());
        student.setPlan(0);
        studentMapper.insert(student);

        // 新增需确认的报名缴费信息
        PaymentDTO paymentDTO = new PaymentDTO(accountUser.getId(),new Integer(2),new Integer(0),new Integer(0));
        paymentService.insertData(paymentDTO);
    }

    @Override
    public StudentVO selectByUserId() {
        SessionUser sessionUser = authenticationService.getSessionUser();
        StudentVO accountUserInfoVO = studentMapper.selectByUserId(sessionUser.getUserId());
        return accountUserInfoVO;
    }

    @Override
    public StudentVO selectByFkUserId(String fkUserId) {
        StudentVO accountUserInfoVO = studentMapper.selectByUserId(fkUserId);
        return accountUserInfoVO;
    }
}
