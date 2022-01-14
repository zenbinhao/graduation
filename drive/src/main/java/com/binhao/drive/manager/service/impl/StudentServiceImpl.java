package com.binhao.drive.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2021/11/10 17:44
 * @Description: TODO
 */

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.binhao.drive.common.bo.SessionUser;
import com.binhao.drive.common.em.ErrorCodeEnum;
import com.binhao.drive.common.po.BasePO;
import com.binhao.drive.common.service.AuthenticationService;
import com.binhao.drive.common.util.BeanUtil;
import com.binhao.drive.common.util.ChangeType;
import com.binhao.drive.common.vo.BusinessException;
import com.binhao.drive.manager.dto.*;
import com.binhao.drive.manager.mapper.*;
import com.binhao.drive.manager.po.*;
import com.binhao.drive.manager.query.StudentQuery;
import com.binhao.drive.manager.service.*;
import com.binhao.drive.manager.vo.StudentVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;

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

    @Resource
    private PaymentMapper paymentMapper;

    @Resource
    private TeacherMapper teacherMapper;


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
        //校验账号 手机格式 以及 判重
        if(StringUtils.isNotEmpty(formDTO.getUserAccount())){
            if (!formDTO.getUserAccount().matches(ChangeType.PHONE_CHECK)){
                throw new BusinessException(ErrorCodeEnum.PHONE_FORMAT);
            }
            AccountUser accountUser = accountUserMapper.selectOne(new QueryWrapper<AccountUser>().lambda().eq(AccountUser::getUserAccount, formDTO.getUserAccount()).orderByDesc(AccountUser::getGmtCreate).last("limit 1"));
            if(accountUser!=null){
                throw new BusinessException(ErrorCodeEnum.LOGIN_EXIST);
            }
        }
        //校验 邮箱格式
        if(StringUtils.isNotEmpty(formDTO.getEmail())){
            if(!formDTO.getEmail().matches(ChangeType.EMAIL_CHECK)){
                throw new BusinessException(ErrorCodeEnum.EMAIL_FORMAT);
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

        //判断一下邮箱格式
        if(StringUtils.isNotEmpty(studentDTO.getEmail())){
            if(!studentDTO.getEmail().matches(ChangeType.EMAIL_CHECK)){
                throw new BusinessException(ErrorCodeEnum.EMAIL_FORMAT);
            }
        }

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

    @Override
    public void updateChooseTeacher(ChooseTeacherDTO formDTO) {
        //首先判断报名费是否通过确认已缴费
        QueryWrapper<Payment> paymentQueryWrapper = new QueryWrapper<>();
        paymentQueryWrapper.lambda().eq(Payment::getContent,0).eq(Payment::getFkUserId,formDTO.getFkUserId()).eq(Payment::getIsCheck,1);
        Integer integer = paymentMapper.selectCount(paymentQueryWrapper);
        if (integer<1){
            throw new BusinessException("5110","报名费未确认已收缴，无法绑定教练员");
        }

        //选择后教练员人数加1
        teacherMapper.update(null,new UpdateWrapper<Teacher>().lambda().setSql("student_number=student_number+1").eq(BasePO::getId,formDTO.getFkTeacherId()));

        //最后进行教练员绑定  考试进度并进入科目一
        Student student = new Student();
        student.setVersion(formDTO.getVersion());
        student.setId(formDTO.getId());
        student.setFkTeacherId(formDTO.getFkTeacherId());
        student.setPlan(1);
        studentMapper.updateById(student);
    }

    @Override
    public void updateChooseReTeacher(ChooseTeacherDTO formDTO) {

        //原教练人数减1
        teacherMapper.update(null,new UpdateWrapper<Teacher>().lambda().setSql("student_number=student_number-1").eq(BasePO::getId,formDTO.getOldFkTeacherId()));

        //新教练人数加1
        teacherMapper.update(null,new UpdateWrapper<Teacher>().lambda().setSql("student_number=student_number+1").eq(BasePO::getId,formDTO.getFkTeacherId()));

        //进行教练员绑定
        Student student = new Student();
        student.setVersion(formDTO.getVersion());
        student.setId(formDTO.getId());
        student.setFkTeacherId(formDTO.getFkTeacherId());
        studentMapper.updateById(student);
    }

    @Override
    public void resetPwd(String ids) {
        if (StringUtils.isEmpty(ids)) {
            throw new BusinessException("请选择要重置的对象");
        }
        String[] id = ids.split(",");
        String password = this.checkPassword("123456");
        for(String i:id){
//            System.out.println("=================================");
//            System.out.println(i);
//            System.out.println("=================================");
            studentMapper.resetPwd(i,password);
        }
    }

//    @Transactional(rollbackFor = Exception.class)
//    @Async(value = "getAsyncExecutor")
//    @Override
//    public Future<Integer> subscribeCourse(SessionUser sessionUser) {
//        //判断考试进度是否进入1  科目一
//        Integer integer = studentMapper.selectCount(new QueryWrapper<Student>().lambda().eq(Student::getFkUserId, sessionUser.getUserId()).eq(Student::getPlan,1));
//        if (integer<1){
//            return new AsyncResult<>(new Integer(2));
//        }
//
//        //判断是否有预约已经提交 且未处理
//        try {
//            courseSubscribeService.selectCountCheck(sessionUser.getUserId());
//        } catch (Exception e) {
//            return new AsyncResult<>(new Integer(1));
//        }
//
//        //传过来userid
//        //联表查询到对应师傅的手机号
//        String email = studentMapper.selectTeacherPhone(sessionUser.getUserId());
//
//        //先存信息防止在发送邮箱的线程中再次容错
//        //存取约课记录
//        CourseSubscribeDTO courseSubscribeDTO = new CourseSubscribeDTO();
//        courseSubscribeDTO.setFkUserId(sessionUser.getUserId());
//        courseSubscribeService.insertData(courseSubscribeDTO);
//        //调用发送短信提醒师傅
//        String sendContent = "[驾校管理系统]:您的学员"+sessionUser.getUserName()+"已预约了您的课程，请教练员尽快排课\\nhttp://localhost:8080/#/";
//        System.out.println("==================开始进入发邮箱的方法=====================");
//        sendEmailUtil.sendEmail(email, sendContent);
//        System.out.println("==================发邮箱方法已结束=====================");
//
//        return new AsyncResult<>(new Integer(0));
//    }

//    @Override
//    public int insertExam(String id) {
//        //判断是否重复操作   本人是否存在未处理的信息
//        Integer integer = examSubscribeMapper.selectCount(new QueryWrapper<ExamSubscribe>().lambda().eq(ExamSubscribe::getIsResponse, 0).eq(ExamSubscribe::getFkUserId, id));
//        if(integer>0){
//            throw new BusinessException(ErrorCodeEnum.EXAM_EXIST_ERROR);
//        }
//
//        //先查询到自己的考试进度
//        ExamSubscribeDTO examSubscribeDTO = new ExamSubscribeDTO();
//        Student student = studentMapper.selectOne(new QueryWrapper<Student>().lambda().eq(Student::getFkUserId, id).orderByDesc(BasePO::getGmtCreate).last("limit 1"));
//        examSubscribeDTO.setFkUserId(id);
//        examSubscribeDTO.setSubject(student.getPlan());
//        examSubscribeDTO.setIsPass(0);
//        examSubscribeDTO.setIsResponse(0);
//        //进行对应考试进度的  约考信息生成
//        examSubscribeService.insertData(examSubscribeDTO);
//
//        return student.getPlan();
//    }

//    @Override
//    public void checkPass(String fkUserId) {
//        //考试信息认定通过后  先修改成通过状态为1
//        examSubscribeService.checkPass(fkUserId,1);
//
//        //考试进度进入 下一阶段
//        studentMapper.update(null,new UpdateWrapper<Student>().lambda().setSql("plan = plan+1").eq(Student::getFkTeacherId,fkUserId));
//    }


}
