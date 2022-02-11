<template>
  <div class="student-box">




    <!--头部按钮操作-->
    <div class="operation">
      <div>
        <el-button type="primary" @click="isAddShow=true">录入信息</el-button>
        <el-button type="danger" @click="deleteTableData()">批量删除</el-button>
        <el-button type="info" @click="resetPwd()">重置密码</el-button>
      </div>
      <div class="operation-search">
        <el-button type="success" @click="getList()">显示所有</el-button>
        <el-input v-model="search.name" placeholder="学员姓名回车搜索" @keydown.enter.native="searchList()"></el-input>
        <el-input v-model="search.phone" placeholder="学员手机号回车搜索" @keydown.enter.native="searchList()"></el-input>
      </div>
    </div>







    <!--表格信息-->
    <el-table
      :data="tableData"
      @selection-change="getIds"
      style="width: 100%">
      <el-table-column
        type="selection"
        width="30">
      </el-table-column>

      <!-- 下拉详情 -->
      <el-table-column
      type="expand">
        <template slot-scope="scope">
         <el-form label-position="right" inline class="demo-table-expand" label-width="90px">
            <el-form-item :label="obj[1]+':'" v-for="(obj,index) in tableHead" :key="index">
              <img :src="scope.row[obj[0]]" v-if="index==4" class="listImg">
              <span v-else-if="index==1">
                {{scope.row[obj[0]]==0?"男":"女" }}
              </span>
              <span v-else-if="index==5">
                {{planTableShow[scope.row[obj[0]]]}}
              </span>
              <span v-else-if="index==7||index==9">
                {{scope.row[obj[0]] | formatDate}}
              </span>
              <span v-else-if="index==10">
                {{scope.row[obj[0]]!=null && scope.row[obj[0]]!=''?scope.row[obj[0]]:'暂无'}}
              </span>
              <span v-else>
                {{scope.row[obj[0]]}}
              </span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
     <el-table-column
        v-for="(obj,index) in tableHead" :key="index"
        :label="obj[1]"
        :width="obj[2]"
        >
         <template slot-scope="scope">
          <img :src="scope.row[obj[0]]" v-if="index==4" class="listImg">
          <span v-else-if="index==1">
            {{scope.row[obj[0]]==0?"男":"女" }}
          </span>
          <span v-else-if="index==5">
            {{planTableShow[scope.row[obj[0]]]}}
          </span>
          <span v-else-if="index==7||index==9">
            {{scope.row[obj[0]] | formatDate}}
          </span>
          <span v-else-if="index==10">
            {{scope.row[obj[0]]!=null && scope.row[obj[0]]!=''?scope.row[obj[0]]:'暂无'}}
          </span>
          <span v-else>
            {{scope.row[obj[0]]}}
          </span>
        </template>
      </el-table-column>
     <el-table-column
        label="操作"
        width="160">
      <template slot-scope="scope">
<!--        <el-button
          size="mini"
          type="success"
          @click="payAdd(scope.$index, scope.row)">缴费</el-button> -->
        <el-button
          size="mini"
          @click="updateStudent(scope.$index, scope.row)">修改</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="chooseTeacher(scope.$index, scope.row,0)"
          v-if="scope.row.teacherName==null || scope.row.teacherName==''"
          >择师</el-button>
          <el-button
            size="mini"
            type="warning"
            @click="chooseTeacher(scope.$index, scope.row,1)"
            v-else
            >重选</el-button>
      </template>
      </el-table-column>
    </el-table>






    <!-- 表格底部 分页工具 -->
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[6, 10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
    <p>&nbsp;</p>





    <!-- 新增功能的抽屉 -->
    <el-drawer
      title="学员信息录入"
      :visible.sync="isAddShow"
      direction="ltr"
      size="40%"
      >
      <el-form class="addStudentform" :rules="rules" ref="addStudentForm" :model="add" label-width="110px">
        <el-form-item prop="userName" label="学员姓名 :">
          <el-input placeholder="请填写学员姓名" v-model="add.userName">
          </el-input>
        </el-form-item>
        <el-form-item prop="userAccount" label="手机号(账号) :">
          <el-input placeholder="请填写学员手机号" v-model="add.userAccount">
          </el-input>
        </el-form-item>
        <el-form-item prop="userPassword" label="学员密码 :">
          <el-input placeholder="请填写学员密码" v-model="add.userPassword" show-password>
          </el-input>
        </el-form-item>
        <el-form-item label="性别 :" prop="sex">
            <el-radio-group v-model="add.sex">
              <el-radio :label="0">男</el-radio>
              <el-radio :label="1">女</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item prop="card" label="身份证 :">
          <el-input placeholder="请填身份证" v-model="add.card">
          </el-input>
        </el-form-item>
        <el-form-item prop="email" label="邮箱 :">
          <el-input placeholder="请填邮箱" v-model="add.email">
          </el-input>
        </el-form-item>
<!--        <el-form-item label="考试进度:" prop="plan">
          <el-radio-group v-model="add.plan">
            <el-radio-button :label="0">报名</el-radio-button>
            <el-radio-button :label="1">科目一</el-radio-button>
            <el-radio-button :label="3">科目二</el-radio-button>
            <el-radio-button :label="2">科目三</el-radio-button>
            <el-radio-button :label="4">科目四</el-radio-button>
          </el-radio-group>
        </el-form-item> -->
      <el-form-item label="照片 :" prop="picture">
          <el-upload
            class="avatar-uploader"
            action="drive/file/upload"
            :headers="headers"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            name="pictures">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <div class="pictureDisable">
            <input v-model="add.picture" type="text"/>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addSubmitStudent('addStudentForm')">提交</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>







    <!-- 修改功能的抽屉 -->
        <el-drawer
          title="学员信息修改"
          :visible.sync="isAltShow"
          direction="rtl"
          size="40%"
          >
          <el-form class="addStudentform" :rules="rulesUpdate" ref="altStudentForm" :model="alt" label-width="110px">
            <el-form-item prop="userName" label="学员姓名 :">
              <el-input placeholder="请填写学员姓名" v-model="alt.userName">
              </el-input>
            </el-form-item>
            <el-form-item prop="userAccount" label="手机号(账号) :">
              <el-input placeholder="请填写学员手机号" v-model="alt.userAccount">
              </el-input>
            </el-form-item>
            <el-form-item label="性别 :" prop="sex">
                <el-radio-group v-model="alt.sex">
                  <el-radio :label="0">男</el-radio>
                  <el-radio :label="1">女</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item prop="card" label="身份证 :">
              <el-input placeholder="请填身份证" v-model="alt.card">
              </el-input>
            </el-form-item>
            <el-form-item prop="email" label="邮箱 :">
              <el-input placeholder="请填邮箱" v-model="alt.email">
              </el-input>
            </el-form-item>
    <!--        <el-form-item label="考试进度:" prop="plan">
              <el-radio-group v-model="alt.plan">
                <el-radio-button :label="0">报名</el-radio-button>
                <el-radio-button :label="1">科目一</el-radio-button>
                <el-radio-button :label="3">科目二</el-radio-button>
                <el-radio-button :label="2">科目三</el-radio-button>
                <el-radio-button :label="4">科目四</el-radio-button>
              </el-radio-group>
            </el-form-item> -->
          <el-form-item label="照片 :" prop="picture">
              <el-upload
                class="avatar-uploader"
                action="drive/file/upload"
                :headers="headers"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                name="pictures">
                <img v-if="imageUrl" :src="imageUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
              <div class="pictureDisable">
                <input v-model="alt.picture" type="text"/>
              </div>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="altSubmitStudent('altStudentForm')">提交</el-button>
            </el-form-item>
          </el-form>
        </el-drawer>





        <!--选择师傅的抽屉-->
        <el-drawer
          title="选择教练员"
          :visible.sync="isChooseShow"
          direction="ttb"
          size="60%"
          >
            <ul class="infinite-list" v-infinite-scroll="load" style="overflow:auto">
              <li v-for="i in foreachUse" class="infinite-list-item">
                <div class="teacherIntroduce" v-for="index in 4" @click="chooseTeacherCheck(teacherData[(i-1)*4+index-1].id)">
                    <el-image
                      style="width: 100px;height: 100px;"
                      v-show="((i-1)*4+index)<=teacherTotal"
                      :src="((i-1)*4+index)>teacherTotal?'':teacherData[(i-1)*4+index-1].photo"
                      id="anyel"
                      @click.stop
                      :preview-src-list="((i-1)*4+index)>teacherTotal?[]:srcList" lazy>
                    </el-image>
                  <p v-show="((i-1)*4+index)<=teacherTotal">{{ ((i-1)*4+index)>teacherTotal?'':teacherData[(i-1)*4+index-1].name}}</p>
                  <p v-show="((i-1)*4+index)<=teacherTotal">{{ ((i-1)*4+index)>teacherTotal?'':teacherData[(i-1)*4+index-1].introduction}}</p>
                </div>
              </li>
              <li v-show="isOver" class="infinite-list-item">已经没有更多了~~</li>
            </ul>
        </el-drawer>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: "",
    //数据存放
    data() {
      var validateEmail = (rule, value, callback) => {
        let emailReg = /^[0-9a-z]+\w*@([0-9a-z]+\.)+[0-9a-z]+$/
        if (!emailReg.test(value)) {
          return callback(new Error('邮箱格式不正确'))
        }
        callback()
      }

      var validatePhone = (rule, value, callback) => {
        let phoneReg = /^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0-9]))\d{8}$/
        if (!phoneReg.test(value)) {
          return callback(new Error('手机号格式不正确'))
        }
        callback()
      }
      var validateCard = (rule, value, callback) => {
        let cardReg = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/
        if (!cardReg.test(value)) {
          return callback(new Error('18位身份证格式不正确'))
        }
        callback()
      }
      var validatePwd = (rule, value, callback) => {
        if(value.length<6 || value.length>12){
          return callback(new Error('密码需大于6位小于12位'))
        }
        callback()
      }
      var validateName = (rule, value, callback) => {
        let nameReg = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/
        if (!nameReg.test(value)) {
          return callback(new Error('姓名格式不正确'))
        }
        callback()
      }
      return {
        src:"http://localhost:8088/drive/upload/b9145826-fd8c-4d55-a192-2095272bb4f0.jpg;",
        srcList: ["http://localhost:8088/drive/upload/b9145826-fd8c-4d55-a192-2095272bb4f0.jpg"],
        isOver: false,
        search: {
          name: null,
          phone: null
        },
        tableData: [],
        action: {
          selectList: "drive/userinfo/page",
          addStudent: "drive/userinfo/",
          deleteStudent: "/drive/userinfo/{ids}",
          altStudent: "drive/userinfo/",
          selectTeacherList: "drive/teacher/page",
          chooseTeacher:"drive/userinfo/choose",
          chooseReTeacher:"drive/userinfo/chooseRe",
          resetPwd:"drive/userinfo/resetPwd/{ids}"
        },
        planTableShow:["报名阶段","科目一","科目二","科目三","科目四","已毕业"],
        tableHead:[
          ["userName","姓名",90],
          ["sex","性别",50],
          ["userAccount","手机号",120],
          ["card","身份证",200],
          ["picture","照片",110],
          ["plan","考试进度",100],
          ["createUserName","创建人",80],
          ["gmtCreate","创建时间",90],
          ["updateUserName","修改人",80],
          ["gmtModified","修改时间",90],
          ["teacherName","教练姓名",80],
          ["email","邮箱",180],
          // ["memo","备注"]
        ],
        pageNum:0,
        pageSize:6,
        total: null,
        isAddShow: false,
        isAltShow: false,
        isChooseShow: false,
        rules: {
          userName: [
            {required: true, message: '清输入姓名', tigger: 'blur'},
            { validator:validateName, trigger: 'blur'}
          ],
          userPassword: [
            {required: true, message: '清输入密码', tigger: 'blur'},
            { validator:validatePwd, trigger: 'blur'}
          ],
          userAccount: [
            {required: true, message: '清输入手机号', tigger: 'blur'},
            { validator: validatePhone, trigger: 'blur' }
          ],
          card: [
            {required: true, message: '清输入身份证', tigger: 'blur'},
            { validator: validateCard, trigger: 'blur' }
          ],
          picture: [
            {required: true, message: '清选择照片', tigger: 'blur'}
          ],
          email: [
            {required: true, message: '清输入邮箱', tigger: 'blur'},
            { validator: validateEmail, trigger: 'blur' }
          ],
        },
        rulesUpdate: {
          userName: [
            {required: true, message: '清输入姓名', tigger: 'blur'},
            { validator:validateName, trigger: 'blur'}
          ],
          userAccount: [
            {required: true, message: '清输入手机号', tigger: 'blur'},
            { validator: validatePhone, trigger: 'blur' }
          ],
          card: [
            {required: true, message: '清输入身份证', tigger: 'blur'},
            { validator: validateCard, trigger: 'blur' }
          ],
          picture: [
            {required: true, message: '清选择照片', tigger: 'blur'}
          ],
          email: [
            {required: true, message: '清输入邮箱', tigger: 'blur'},
            { validator: validateEmail, trigger: 'blur' }
          ],
        },
        add:{
          userName: 'a',
          userAccount:'13387330000',
          userPassword:'123456',
          picture:'',
          sex:0,
          card:'430224199901130000',
          email: '3223570002@qq.com',
        },
        alt:{
          userName: '',
          userAccount:'',
          userPassword:'',
          picture:'',
          sex:0,
          card:'',
          version: 0,
          id:''
        },
        headers:{
          authToken:''
        },
        imageUrl:'',
        ids:'',
        currentRowData:{},
        count: 3,
        teacherData: [],
        teacherTotal: 0,
        foreachUse: 0,
        chooseDialog: 0,

      }
    },
    //方法函数存放
    methods: {
      getList(){
        this.headers.authToken = window.localStorage.getItem("authToken")
        let params={
          	pageNum: this.pageNum,
          	pageSize: this.pageSize,
            userName: this.search.name,
            userAccount: this.search.phone
        }
        axios.post(this.action.selectList,params,{
          headers: {
            'authToken': this.headers.authToken
          }
        }).then((res)=>{
          // console.log(res.data.data.list)
          this.tableData=res.data.data.list
          this.total = res.data.data.total
        })
      },
      handleSizeChange(val){
        this.pageNum=1
        this.pageSize=val
        this.getList()
      },
      handleCurrentChange(val){
        this.pageNum=val
        this.getList()
      },
      beforeAvatarUpload (file) {
        const Format = file.type === 'image/jpeg' || file.type === 'image/jpg' || file.type === 'image/png' || file.type === 'image/gif'
        const isLt5M = file.size / 1024 / 1024 < 5

        if (!Format) {
          this.$message.error('上传头像图片只能是 JPG或者PNG 格式!')
        }
        if (!isLt5M) {
          this.$message.error('上传头像图片大小不能超过 5MB!')
        }
        return Format && isLt5M
      },
      handleAvatarSuccess (res, file) {
        // 部署需修改
        this.imageUrl = URL.createObjectURL(file.raw)
        //windows下部署环境
        // this.add.picture = 'http://localhost:8088/drive' + (res.data).substring(3)
        // this.alt.picture = 'http://localhost:8088/drive' + (res.data).substring(3)

        //linux下部署环境
        this.add.picture = 'http://47.102.36.69:8080/drive' + (res.data)
        this.alt.picture = 'http://47.102.36.69:8080/drive' + (res.data)
      },
      altSubmitStudent (refName){
        this.$refs[refName].validate((valid) => {
          //校验通过
          if(valid){
            // console.log(this.alt.picture)
            // console.log(this.currentRowData.picture)
            // console.log(this.currentRowData)
            let params ={
                email: this.alt.email==this.currentRowData.email?null:this.alt.email,
              	card: this.alt.card==this.currentRowData.card?null:this.alt.card,
              	picture: this.alt.picture==this.currentRowData.picture?null:this.alt.picture,
              	sex: this.alt.sex==this.currentRowData.sex?null:this.alt.sex,
              	userAccount: this.alt.userAccount==this.currentRowData.userAccount?null:this.alt.userAccount,
              	userName: this.alt.userName==this.currentRowData.userName?null:this.alt.userName,
                version: this.alt.version,
                id: this.alt.id
            }
            console.log(params)

            axios.put(this.action.altStudent,
            params,{
              headers: {
                'authToken': this.headers.authToken
              }
            }).then(res =>{
              if(res.data.code==='0'){
                this.$message({
                  type: 'success',
                  message: res.data.message
                })
                this.isAltShow=false
                this.getList()
              }else{
                this.$message({
                  type: 'error',
                  message: res.data.message
                })
              }
            })
          }else{
            return false;
          }
        })
      },
      addSubmitStudent (refName) {
        this.$refs[refName].validate((valid) => {
          if(valid){
            let params ={
              	card: this.add.card,
              	picture: this.add.picture,
              	sex: this.add.sex,
              	userAccount: this.add.userAccount,
              	userName: this.add.userName,
              	userPassword: this.add.userPassword,
                email: this.add.email
            }
            console.log(params)
            axios.post(this.action.addStudent,
            params,{
              headers: {
                'authToken': this.headers.authToken
              }
            }).then(res =>{
              if(res.data.code==='0'){
                this.$message({
                  type: 'success',
                  message: res.data.message
                })
                this.isAddShow=false
                this.getList()
              }else{
                this.$message({
                  type: 'error',
                  message: res.data.message
                })
              }
            })
          }else{
            return false;
          }
        });
      },
      getIds (tableData) {
        let ids = ''
        tableData.forEach((item, index) => {
          ids += item.id + ','
        })
        ids = ids.substring(0,ids.length-1)
        this.ids = ids
        console.log(this.ids)
      },
      deleteTableData(){
        if(this.ids!=''){
          this.$confirm('是否确定删除当前记录？', '确定删除？', {
            confirmButtonText: '删除',
            cancelButtonText: '取消'
          }).then(() => {
          axios.delete(this.action.deleteStudent.replace("{ids}",this.ids),{
                headers: {
                  'authToken': this.headers.authToken
                }}).then(res=>{
                if(res.data.code==='0'){
                  this.$message({
                    type: 'success',
                    message: res.data.message
                  })
                  this.getList()
                }else{
                  this.$message({
                    type: 'error',
                    message: res.data.message
                  })
                }
              })
          }).catch((err)=>{
            return ;
          })
        }else{
          this.$message({
            type: 'error',
            message: '请先选择列表项'
          })
        }
      },
      chooseTeacher(index, row, dialog) {
        console.log(index, row);
        this.isChooseShow=true
        this.currentRowData = row
        this.chooseDialog = dialog
      },
      updateStudent(index, row) {
        this.isAltShow=true
        this.imageUrl = row.picture
        // console.log(row.picture)
        // 修改表单填充
        this.alt = {
          userName: row.userName,
          userAccount: row.userAccount,
          picture: row.picture,
          sex: row.sex,
          card: row.card,
          version: row.version,
          id: row.id,
          email: row.email,
        }
        // 存储当前修改行的数据
        this.currentRowData = row

      },
      searchList(){
        this.pageNum = 0
        this.getList()
        // this.search={
        //   name:null,
        //   phone:null
        // }
      },
      load () {
        // 20替换总条数
        if(this.count< this.foreachUse){
          this.count += 2
        }else{
          this.count = this.foreachUse
          this.isOver = true
        }
      },
      chooseTeacherCheck(id){
        this.$confirm('是否绑定当前教练员？', '确定绑定？', {
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(() => {
          let  params
          // 为0的时候是 直接选择教练员
          if(this.chooseDialog==0){
          params= {
              fkTeacherId: id,
              id: this.currentRowData.id,
              fkUserId: this.currentRowData.fkUserId,
              version: this.currentRowData.version
            }
          }else{
            //重选
          params= {
              fkTeacherId: id,
              id: this.currentRowData.id,
              fkUserId: this.currentRowData.fkUserId,
              oldFkTeacherId:this.currentRowData.fkTeacherId,
              version: this.currentRowData.version
            }
          }

          // console.log(params)
          axios.put(this.chooseDialog==0?this.action.chooseTeacher:this.action.chooseReTeacher,
          params,{
            headers: {
              'authToken': this.headers.authToken
            }
          }).then(res =>{
            if(res.data.code==='0'){
              this.$message({
                type: 'success',
                message: res.data.message
              })
              this.isAltShow=false
              this.getList()
            }else{
              this.$message({
                type: 'error',
                message: res.data.message
              })
            }
          })



          //关闭抽屉
          this.isChooseShow = false
        }).catch((err)=>{
          return err;
        })
      },
      getTeacherList(){
        let params={
          	pageNum: this.pageNum,
          	pageSize: 100
        }
        axios.post(this.action.selectTeacherList,params,{
          headers: {
            'authToken': this.headers.authToken
          }
        }).then((res)=>{
          // console.log(res.data.data.list)
          // this.tableData=res.data.data.list
          // this.total = res.data.data.total
          this.teacherData= res.data.data.list
          let arr = res.data.data.list
          this.teacherTotal = res.data.data.total
          this.foreachUse = Math.ceil(res.data.data.total/4)
          let list = []
          arr.forEach((obj,index)=>{
            list.push(obj.photo)
          })
          this.srcList = list
          // console.log(list)
        })
      },
      resetPwd(){
        this.$confirm('是否重置密码？', '确定重置？', {
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(() => {

          axios.put(this.action.resetPwd.replace("{ids}",this.ids),null,
          {
            headers: {
              'authToken': this.headers.authToken
            }
          }).then(res =>{
            if(res.data.code==='0'){
              this.$message({
                type: 'success',
                message: res.data.message
              })
              this.getList()
            }else{
              this.$message({
                type: 'error',
                message: res.data.message
              })
            }
          })


        }).catch(err=>{
          return;
        })
      }
    },
    //页面加载时
    mounted() {
      this.getList()
      this.getTeacherList()
    },
    //组件注册
    components: {

    },
    //自定义函数
    directives:{

    },
    //过滤器
    filters:{
    //时间戳转换
      formatDate(obj){
        return  new Date(obj).toLocaleString().replace(/:\d{1,2}$/,' ');
      }
    },
    //计算机属性对象
    computed:{

    },
    //侦听属性
    watch:{

    }
  }
</script>

<style lang="scss" scoped="scoped">
.student-box{
  width: 100%;
  height: 100%;
  .infinite-list{
    height: 100%;
    .infinite-list-item{

      height: 180px;
      // background-color: brown;
      display: flex;
      justify-content: space-around;
      align-items: center;
      .teacherIntroduce{
        cursor: pointer;
        // border: 1px solid #e6e6e6;
        height: 180px;
        width: 200px;
        // background-color: lawngreen;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        &:active{
          background-color: #f5f7fb;
        }
        p{
          overflow: hidden;
          //溢出省略号
          text-overflow:ellipsis;
          ///将对象作为弹性伸缩盒子模型显示
          display:-webkit-box;
          //从上到下垂直排列子元素（设置伸缩盒子的子元素排列方式）
          -webkit-box-orient:vertical;
          //这个属性不是css的规范属性，需要组合上面两个属性，表示显示的行数。
          -webkit-line-clamp:2;
        }
      }
    }
  }
  // background-color: white;
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
  .listImg{
    width: 100px;
    height: 100px;
  }
  .operation{
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    // .el-table::-webkit-scrollbar {
    //   display: none;
    // }
    .operation-search{
      display: flex;

    }
  }
  .block{
    margin-top: 20px;
  }
  .addStudentform{
    padding: 0 20px;
    height: 680px;
    overflow-y: scroll;
    position: relative;
    overflow-x: hidden;
    text-align: left;
    .avatar-uploader .el-upload {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
      border-color: #409EFF;
    }
    .avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      line-height: 178px;
      text-align: center;
    }
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }
    .pictureDisable{
      position: absolute;
      top: 6rem;
      right: 0rem;
      width: 0%;
      input{
        background-color: white;
        color: white;
        border: none;
      }
    }
  }
}
</style>
