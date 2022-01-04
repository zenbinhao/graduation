package com.binhao.drive.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2022/1/2 15:58
 * @Description: TODO
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.binhao.drive.common.util.BeanUtil;
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
        AccountUserDTO accountUserDTO = new AccountUserDTO();
        if(StringUtils.isNotEmpty(formDTO.getName())){
            accountUserDTO.setUserName(formDTO.getName());
        }
        if (StringUtils.isNotEmpty(formDTO.getPhoto())){
            accountUserDTO.setUserAccount(formDTO.getPhone());
        }
        if (StringUtils.isNotEmpty(formDTO.getName())||StringUtils.isNotEmpty(formDTO.getPhoto())){
            accountUserDTO.setId(formDTO.getFkUserId());
            accountUserService.updateData(accountUserDTO);
        }
        Teacher teacher = new Teacher();
        BeanUtil.copy(formDTO,teacher);
        teacherMapper.updateById(teacher);
    }

    @Override
    public TeacherVO selectById(String id) {
        Teacher teacher = teacherMapper.selectById(id);
        TeacherVO teacherVO = new TeacherVO();
        BeanUtil.copy(teacher,teacherVO);
        return teacherVO;
    }
}
