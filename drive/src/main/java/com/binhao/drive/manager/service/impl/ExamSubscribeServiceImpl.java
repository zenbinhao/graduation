package com.binhao.drive.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2022/1/5 17:18
 * @Description: TODO
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.binhao.drive.common.em.ErrorCodeEnum;
import com.binhao.drive.common.po.BasePO;
import com.binhao.drive.common.server.WebSocketServer;
import com.binhao.drive.common.util.BeanUtil;
import com.binhao.drive.common.util.SendEmailUtil;
import com.binhao.drive.common.vo.BusinessException;
import com.binhao.drive.manager.dto.DelExamSubDTO;
import com.binhao.drive.manager.dto.ExamSubscribeDTO;
import com.binhao.drive.manager.mapper.ExamSubscribeMapper;
import com.binhao.drive.manager.mapper.StudentMapper;
import com.binhao.drive.manager.po.ExamSubscribe;
import com.binhao.drive.manager.po.Student;
import com.binhao.drive.manager.query.ExamSubscribeQuery;
import com.binhao.drive.manager.service.ExamSubscribeService;
import com.binhao.drive.manager.vo.ExamSubscribeVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

@Service
public class ExamSubscribeServiceImpl extends ServiceImpl<ExamSubscribeMapper, ExamSubscribe> implements ExamSubscribeService {

    @Resource
    private ExamSubscribeMapper examSubscribeMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private SendEmailUtil sendEmailUtil;

    @Override
    public PageInfo<ExamSubscribeVO> pageData(ExamSubscribeQuery query) {
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        List<ExamSubscribeVO> list = examSubscribeMapper.selectDataList(query);
        return PageInfo.of(list);
    }

    @Override
    public List<ExamSubscribeVO> selectIsResponseList(ExamSubscribeQuery query) {
        return examSubscribeMapper.selectDataList(query);
    }

    @Override
    public PageInfo<ExamSubscribeVO> selectExamList(ExamSubscribeQuery query) {
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        List<ExamSubscribeVO> list = examSubscribeMapper.selectExamList(query);
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

    @Async("getAsyncExecutor")
    @Override
    public void updateDel(String id,String content,String email,CountDownLatch countDownLatch) {


        //????????????
        //??????????????????
        examSubscribeMapper.update(null,new UpdateWrapper<ExamSubscribe>().lambda().set(ExamSubscribe::getIsResponse,1).set(ExamSubscribe::getContent,content).eq(BasePO::getId,id));

        //??????????????????
        sendEmailUtil.sendEmail(email,content);

        //??????
        countDownLatch.countDown();
    }

    @Override
    public void checkPass(String fkUserId,Integer isPass) {

        //??????????????????????????????  ?????????????????????
        int integer = examSubscribeMapper.selectCount(new QueryWrapper<ExamSubscribe>().lambda().eq(ExamSubscribe::getFkUserId, fkUserId).eq(ExamSubscribe::getIsResponse, 0));
        if(integer>0){

            throw new BusinessException("????????????????????????  ????????????????????????");
        }

        //???????????????
        examSubscribeMapper.update(null,new UpdateWrapper<ExamSubscribe>().lambda().set(ExamSubscribe::getIsPass,isPass).eq(ExamSubscribe::getFkUserId,fkUserId));
    }

    /**
     * @Author zengbh
     * @Description //TODO ???????????? ??????????????????
     * @Date 18:21 
     * @Param [id]
     * @return int
     **/

    @Override
    public int insertExam(String id) {
        //????????????????????????   ????????????????????????????????????
        Integer integer = examSubscribeMapper.selectCount(new QueryWrapper<ExamSubscribe>().lambda().eq(ExamSubscribe::getIsResponse, 0).eq(ExamSubscribe::getFkUserId, id));
        if(integer>0){
            throw new BusinessException(ErrorCodeEnum.EXAM_EXIST_ERROR);
        }

        //?????????????????????????????????
        ExamSubscribeDTO examSubscribeDTO = new ExamSubscribeDTO();
        Student student = studentMapper.selectOne(new QueryWrapper<Student>().lambda().eq(Student::getFkUserId, id).orderByDesc(BasePO::getGmtCreate).last("limit 1"));
        examSubscribeDTO.setFkUserId(id);
        examSubscribeDTO.setSubject(student.getPlan());
        examSubscribeDTO.setIsPass(0);
        examSubscribeDTO.setIsResponse(0);
        //???????????????????????????  ??????????????????
        this.insertData(examSubscribeDTO);

        return student.getPlan();
    }


    @Override
    public void checkPass(String fkUserId) {
        //???????????????????????????  ???????????????????????????1
        this.checkPass(fkUserId,1);

        //?????????????????? ????????????
        studentMapper.update(null,new UpdateWrapper<Student>().lambda().setSql("plan = plan+1").eq(Student::getFkUserId,fkUserId));
    }

}
