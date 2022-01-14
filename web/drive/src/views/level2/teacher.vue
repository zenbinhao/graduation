<template>
  <div class="student-box">




    <!--头部按钮操作-->
    <div class="operation">
      <div>
        <el-button type="primary" @click="isAddShow=true">录入信息</el-button>
        <el-button type="danger" @click="deleteTableData()">批量删除</el-button>
      </div>
      <div class="operation-search">
        <el-button type="success" @click="getList()">显示所有</el-button>
        <el-input v-model="search.name" placeholder="姓名回车搜索" @keydown.enter.native="searchList()"></el-input>
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
              <img :src="scope.row[obj[0]]" v-if="obj[0]=='photo'" class="listImg">
             <span v-else-if="obj[0]=='sex'">
                {{scope.row[obj[0]]==0?"男":"女" }}
              </span>
              <span v-else-if="obj[0]=='gmtCreate' || obj[0]=='gmtModified'">
                {{scope.row[obj[0]] | formatDate}}
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
          <img :src="scope.row[obj[0]]" v-if="obj[0]=='photo'" class="listImg">
          <span v-else-if="obj[0]=='sex'">
            {{scope.row[obj[0]]==0?"男":"女" }}
          </span>
          <span v-else-if="obj[0]=='gmtCreate' || obj[0]=='gmtModified'">
            {{scope.row[obj[0]] | formatDate}}
          </span>
          <span v-else>
            {{scope.row[obj[0]]}}
          </span>
        </template>
      </el-table-column>
     <el-table-column
        label="操作"
        width="140">
      <template slot-scope="scope">
<!--        <el-button
          size="mini"
          type="success"
          @click="payAdd(scope.$index, scope.row)">缴费</el-button> -->
        <el-button
          size="mini"
          @click="updateStudent(scope.$index, scope.row)">修改</el-button>
<!--        <el-button
          size="mini"
          type="danger"
          @click="chooseTeacher(scope.$index, scope.row)">择师</el-button> -->
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
      title="教练员信息录入"
      :visible.sync="isAddShow"
      direction="ltr"
      size="40%"
      >
      <el-form class="addStudentform" :rules="rules" ref="addStudentForm" :model="add" label-width="110px">
        <el-form-item prop="name" label="教练员姓名 :">
          <el-input placeholder="请填写教练员姓名" v-model="add.name">
          </el-input>
        </el-form-item>
        <el-form-item prop="phone" label="手机号(账号) :">
          <el-input placeholder="请填写教练员手机号" v-model="add.phone">
          </el-input>
        </el-form-item>
        <el-form-item prop="userPassword" label="教练员密码 :">
          <el-input placeholder="请填写教练员密码" v-model="add.userPassword" show-password>
          </el-input>
        </el-form-item>
        <el-form-item prop="email" label="邮箱 :">
          <el-input placeholder="请填写邮箱" v-model="add.email">
          </el-input>
        </el-form-item>
        <el-form-item label="性别 :" prop="sex">
            <el-radio-group v-model="add.sex">
              <el-radio :label="0">男</el-radio>
              <el-radio :label="1">女</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item prop="age" label="年龄 :">
          <el-input-number v-model="add.age"  :min="20" :max="70" ></el-input-number>
        </el-form-item>
        <el-form-item prop="introduction" label="简介 :">
          <el-input
            type="textarea"
            placeholder="请输入简介内容"
            maxlength="30"
            show-word-limit
            v-model="add.introduction"
          >
          </el-input>
        </el-form-item>
      <el-form-item label="照片 :" prop="photo">
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
            <input v-model="add.photo" type="text"/>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addSubmitStudent('addStudentForm')">提交</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>







    <!-- 修改功能的抽屉 -->
        <el-drawer
          title="教练员信息修改"
          :visible.sync="isAltShow"
          direction="rtl"
          size="40%"
          >
          <el-form class="addStudentform" :rules="rulesUpdate" ref="altStudentForm" :model="alt" label-width="110px">
            <el-form-item prop="name" label="教练员姓名 :">
                <el-input placeholder="请填写教练员姓名" v-model="alt.name">
                </el-input>
              </el-form-item>
              <el-form-item prop="phone" label="手机号(账号) :">
                <el-input placeholder="请填写教练员手机号" v-model="alt.phone">
                </el-input>
              </el-form-item>
              <el-form-item prop="email" label="邮箱 :">
                <el-input placeholder="请填写邮箱" v-model="alt.email">
                </el-input>
              </el-form-item>
              <el-form-item label="性别 :" prop="sex">
                  <el-radio-group v-model="alt.sex">
                    <el-radio :label="0">男</el-radio>
                    <el-radio :label="1">女</el-radio>
                  </el-radio-group>
              </el-form-item>
              <el-form-item prop="age" label="年龄 :">
                <el-input-number v-model="alt.age"  :min="20" :max="70" ></el-input-number>
              </el-form-item>
              <el-form-item prop="introduction" label="简介 :">
                <el-input
                  type="textarea"
                  placeholder="请输入简介内容"
                  maxlength="30"
                  show-word-limit
                  v-model="alt.introduction"
                >
                </el-input>
              </el-form-item>
            <el-form-item label="照片 :" prop="photo">
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
                  <input v-model="alt.photo" type="text"/>
                </div>
              </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="altSubmitStudent('altStudentForm')">提交</el-button>
            </el-form-item>
          </el-form>
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
          name: null
        },
        tableData: [],
        action: {
          add: "drive/teacher/",
          del: "drive/teacher/{ids}",
          alt: "drive/teacher/",
          list: "drive/teacher/page"
        },
        planTableShow:["科目一","科目二","科目三","科目四","已毕业"],
        tableHead:[
          ["name","姓名",90],
          ["sex","性别",50],
          ["phone","手机号",120],
          ["photo","照片",110],
          ["age","年龄",50],
          ["introduction","教练员简介",100],
          ["studentNumber","总学员数量",100],
          ["email","邮箱",180],
          ["createUserName","创建人",80],
          ["gmtCreate","创建时间",90],
          ["updateUserName","修改人",80],
          ["gmtModified","修改时间",90],
          // ["memo","备注"]
        ],
        pageNum:0,
        pageSize:6,
        total: null,
        isAddShow: false,
        isAltShow: false,
        isChooseShow: false,
        rules: {
          name: [
            {required: true, message: '清输入姓名', tigger: 'blur'},
            { validator:validateName, trigger: 'blur'}
          ],
          userPassword: [
            {required: true, message: '清输入密码', tigger: 'blur'},
            { validator:validatePwd, trigger: 'blur'}
          ],
          phone: [
            {required: true, message: '清输入手机号', tigger: 'blur'},
            { validator: validatePhone, trigger: 'blur' }
          ],
          introduction: [
            {required: true, message: '清输入简介', tigger: 'blur'}
          ],
          photo: [
            {required: true, message: '清选择照片', tigger: 'blur'}
          ],
          email: [
            {required: true, message: '清输入邮箱', tigger: 'blur'},
            { validator: validateEmail, trigger: 'blur' }
          ],
        },
        rulesUpdate: {
          name: [
            {required: true, message: '清输入姓名', tigger: 'blur'},
            { validator:validateName, trigger: 'blur'}
          ],
          phone: [
            {required: true, message: '清输入手机号', tigger: 'blur'},
            { validator: validatePhone, trigger: 'blur' }
          ],
          introduction: [
            {required: true, message: '清输入简介', tigger: 'blur'}
          ],
          photo: [
            {required: true, message: '清选择照片', tigger: 'blur'}
          ],
          email: [
            {required: true, message: '清输入邮箱', tigger: 'blur'},
            { validator: validateEmail, trigger: 'blur' }
          ],
        },
        add:{
          name: '隔壁老王',
          phone:'13387330000',
          userPassword:'123456',
          photo:'',
          sex: 0,
          age: 30,
          introduction:'简介',
          email: '211125371@qq.com',
        },
        alt:{
          name: null,
          phone: null,
          photo: null,
          sex: null,
          age: null,
          introduction: null,
          fkUserId: null,
          version: null,
          id:null,
          email:null,
        },
        headers:{
          authToken:''
        },
        imageUrl:'',
        ids:'',
        currentRowData:{},
      }
    },
    //方法函数存放
    methods: {
      getList(){
        this.headers.authToken = window.localStorage.getItem("authToken")
        let params={
          	pageNum: this.pageNum,
            pageSize: this.pageSize,
          	name: this.search.name
        }
        axios.post(this.action.list,params,{
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
        this.add.photo = 'http://localhost:8088/drive' + (res.data).substring(3)
        this.alt.photo = 'http://localhost:8088/drive' + (res.data).substring(3)
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
              	name: this.alt.name==this.currentRowData.name?null:this.alt.name,
              	photo: this.alt.photo==this.currentRowData.photo?null:this.alt.photo,
              	sex: this.alt.sex==this.currentRowData.sex?null:this.alt.sex,
              	phone: this.alt.phone==this.currentRowData.phone?null:this.alt.phone,
              	age: this.alt.age==this.currentRowData.age?null:this.alt.age,
                introduction: this.alt.introduction==this.currentRowData.introduction?null:this.alt.introduction,
                fkUserId: this.alt.fkUserId,
                version: this.alt.version,
                id: this.alt.id,
            }
            console.log(params)

            axios.put(this.action.alt,
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
                email: this.add.email,
              	name: this.add.name,
              	photo: this.add.photo,
              	phone: this.add.phone,
              	introduction: this.add.introduction,
              	sex: this.add.sex,
              	age: this.add.age,
                userPassword: this.add.userPassword
            }
            console.log(params)
            axios.post(this.action.add,
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
          axios.delete(this.action.del.replace("{ids}",this.ids),{
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
      updateStudent(index, row) {
        this.isAltShow=true
        this.imageUrl = row.photo
        // console.log(row.picture)
        // 修改表单填充
        this.alt = {
          name: row.name,
          phone: row.phone,
          photo: row.photo,
          sex: row.sex,
          age: row.age,
          version: row.version,
          id: row.id,
          introduction: row.introduction,
          fkUserId: row.fkUserId,
          email: row.email
        }
        // 存储当前修改行的数据
        this.currentRowData = row

      },
      searchList(){
        this.pageNum = 0
        this.getList()
        this.search={
          name:null,
          phone:null
        }
      },


    },
    //页面加载时
    mounted() {
      this.getList()
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
    height: 800px;
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
