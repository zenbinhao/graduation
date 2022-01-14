package com.binhao.drive.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2022/1/2 15:58
 * @Description: TODO
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.binhao.drive.common.em.ErrorCodeEnum;
import com.binhao.drive.common.util.BeanUtil;
import com.binhao.drive.common.util.ChangeType;
import com.binhao.drive.common.vo.BusinessException;
import com.binhao.drive.manager.dto.AccountUserDTO;
import com.binhao.drive.manager.dto.TeacherDTO;
import com.binhao.drive.manager.dto.TeacherInsertDTO;
import com.binhao.drive.manager.mapper.TeacherMapper;
import com.binhao.drive.manager.po.AccountUser;
import com.binhao.drive.manager.po.Teacher;
import com.binhao.drive.manager.query.TeacherQuery;
import com.binhao.drive.manager.service.AccountUserService;
import com.binhao.drive.manager.service.TeacherService;
import com.binhao.drive.manager.vo.TeacherVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Resource
    private AccountUserService accountUserService;
    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public PageInfo<Teacher> pageData(TeacherQuery query) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.lambda().like(StringUtils.isNotEmpty(query.getName()),Teacher::getName,query.getName()).orderByDesc(Teacher::getGmtCreate);
        //开启分页
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        List<Teacher> list = teacherMapper.selectList(teacherQueryWrapper);
        return PageInfo.of(list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertData(TeacherInsertDTO formDTO) {
        if(StringUtils.isNotEmpty(formDTO.getEmail())){
            if(!formDTO.getEmail().matches(ChangeType.EMAIL_CHECK)){
                throw new BusinessException(ErrorCodeEnum.EMAIL_FORMAT);
            }
        }

        AccountUserDTO accountUserDTO = new AccountUserDTO(formDTO.getName(),formDTO.getPhone(),formDTO.getUserPassword());
        AccountUser accountUser = accountUserService.insertTeacher(accountUserDTO);

        Teacher teacher = new Teacher();
        BeanUtil.copy(formDTO,teacher);
        teacher.setFkUserId(accountUser.getId());
        teacherMapper.insert(teacher);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteData(String ids) {
        if (StringUtils.isEmpty(ids)) {
            throw new BusinessException("请选择要删除的记录");
        }
        String []id =ids.split(",");
        StringBuilder stringBuilder = new StringBuilder();
        //查询一下原来的外键fkuserid
        for (String search:id){
            Teacher teacher = teacherMapper.selectById(search);
            if(teacher!=null){
                stringBuilder.append(teacher.getFkUserId()+",");
            }
        }
        if (stringBuilder!=null){
            //截取   为了去掉最后一个 标识符（，）
            String substring = stringBuilder.substring(0, stringBuilder.length() - 1);
            //调用批量业务
            accountUserService.deletes(substring);
        }
        int row = teacherMapper.deleteData(id);
        if (row <=0|| ids.length()<=0) {
            throw new BusinessException("批量删除操作失败");
        }
    }


    @Override
    public void updateData(TeacherDTO formDTO) {
        //判断一下邮箱格式
        if(StringUtils.isNotEmpty(formDTO.getEmail())){
            if(!formDTO.getEmail().matches(ChangeType.EMAIL_CHECK)){
                throw new BusinessException(ErrorCodeEnum.EMAIL_FORMAT);
            }
        }

        AccountUserDTO accountUserDTO = new AccountUserDTO();
        if(StringUtils.isNotEmpty(formDTO.getName())){
            accountUserDTO.setUserName(formDTO.getName());
        }
        if (StringUtils.isNotEmpty(formDTO.getPhone())){
            accountUserDTO.setUserAccount(formDTO.getPhone());
        }
        if (StringUtils.isNotEmpty(formDTO.getName())||StringUtils.isNotEmpty(formDTO.getPhone())){
            accountUserDTO.setId(formDTO.getFkUserId());
            accountUserService.updateData(accountUserDTO);
        }
        Teacher teacher = new Teacher();
        BeanUtil.copy(formDTO,teacher);
        teacher.setFkUserId(null);
        teacherMapper.updateById(teacher);
    }

    @Override
    public TeacherVO selectById(String id) {
        Teacher teacher = teacherMapper.selectById(id);
        TeacherVO teacherVO = new TeacherVO();
        BeanUtil.copy(teacher,teacherVO);
        return teacherVO;
    }
///**
// * @Author zengbh
// * @Description //TODO 查询到预约自己的学员的预约信息
// * @Date 16:46
// * @Param [query]
// * @return com.github.pagehelper.PageInfo<com.binhao.drive.manager.vo.MySubVO>
// **/
//    @Override
//    public PageInfo<MySubVO> pageDataMySub(MySubQuery query) {
//        PageHelper.startPage(query.getPageNum(),query.getPageSize());
//        Teacher teacher = teacherMapper.selectOne(new QueryWrapper<Teacher>().lambda().eq(Teacher::getFkUserId, authenticationService.getSessionUser().getUserId()));
//        query.setTeacherId(teacher.getId());
//        return PageInfo.of(teacherMapper.selectMySub(query));
//    }
///**
// * @Author zengbh
// * @Description //TODO 异步方法 对预约自己的信息进行反馈  修改回应的状态（默认注入）  以及回复的话语记录（传参）
// * @Date 16:46
// * @Param [dto]
// * @return void
// **/
//    @Async(value = "getAsyncExecutor")
//    @Override
//    public Future<Integer> updateResponseStudent(CourseSubscribeDTO dto, SessionUser sessionUser) {
//        //先记录  提升 重复发送的 容错
//        try {
//            courseSubscribeService.updateData(dto);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new AsyncResult<Integer>(new Integer(1));
//        }
//
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("您的教练员-");
//        stringBuilder.append(sessionUser.getUserName());
//        stringBuilder.append("-对您的回复:\n");
//        stringBuilder.append(dto.getResponseContent());
//
//        //发送处理邮件
//        System.out.println("==================开始进入发邮箱的方法=====================");
//        sendEmailUtil.sendEmail(dto.getEmail(),stringBuilder.toString());
//        System.out.println("==================发邮箱方法已结束=====================");
//
//        return new AsyncResult<Integer>(new Integer(0));
//    }


}
