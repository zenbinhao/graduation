<template>
  <div class="student-box" v-loading.fullscreen.lock="fullscreenLoading">
    <el-radio-group v-model="radio" @change="getList()">
      <el-radio-button :label="1">科目一</el-radio-button>
      <el-radio-button :label="2">科目二</el-radio-button>
      <el-radio-button :label="3">科目三</el-radio-button>
      <el-radio-button :label="4">科目四</el-radio-button>
    </el-radio-group>
    <p>&nbsp;</p>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <span>请描述您的回复，回复不能少于10字</span>
      <p>&nbsp;</p>
      <el-input
        type="textarea"
        placeholder="请输入内容"
        v-model="textarea"
        maxlength="50"
        show-word-limit
      />
      <p>&nbsp;</p>
      <el-button type="info" @click="delResponse()">回复</el-button>
    </el-dialog>

    <!--头部按钮操作-->
    <div class="operation">
      <div>
        <el-button type="primary" @click="openDialog()">处理回复</el-button>
        <!-- <el-button type="primary" @click="isAddShow=true">录入信息</el-button> -->
        <!-- <el-button type="danger" @click="deleteTableData()">批量删除</el-button> -->
      </div>
      <div class="operation-search">
        <!-- <el-button type="success" @click="getList()">显示所有</el-button> -->
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
        width="30"/>
      <!-- 下拉详情 -->
      <el-table-column
      type="expand">
        <template slot-scope="scope">
         <el-form label-position="right" inline class="demo-table-expand" label-width="100px">
            <el-form-item :label="obj[1]+':'" v-for="(obj,index) in tableHead" :key="index">
              <span v-if="obj[0]=='gmtCreate' || obj[0]=='gmtModified'">
                {{scope.row[obj[0]] | formatDate}}
              </span>
              <span v-else-if="obj[0]=='subject'">
                {{ subjectTableShow[scope.row[obj[0]]] }}
              </span>
              <span v-else-if="obj[0]=='isResponse'" :style="scope.row[obj[0]]==0?'color:red;font-weight: 800;':'color:green; font-weight: 800;'">
                {{ isResponseShow[scope.row[obj[0]]] }}
              </span>
              <span v-else-if="obj[0]=='isPass'" :style="scope.row[obj[0]]==0?'color:red;font-weight: 800;':'color:green; font-weight: 800;'">
                {{ isPassTableShow[scope.row[obj[0]]] }}
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
<!--          <img :src="scope.row[obj[0]]" v-if="index==4" class="listImg">
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
          </span> -->
          <span v-if="obj[0]=='gmtCreate' || obj[0]=='gmtModified'">
            {{scope.row[obj[0]] | formatDate}}
          </span>
          <span v-else-if="obj[0]=='subject'">
            {{ subjectTableShow[scope.row[obj[0]]] }}
          </span>
          <span v-else-if="obj[0]=='isResponse'" :style="scope.row[obj[0]]==0?'color:red;font-weight: 800;':'color:green; font-weight: 800;'">
            {{ isResponseShow[scope.row[obj[0]]] }}
          </span>
          <span v-else-if="obj[0]=='isPass'" :style="scope.row[obj[0]]==0?'color:red;font-weight: 800;':'color:green; font-weight: 800;'">
            {{ isPassTableShow[scope.row[obj[0]]] }}
          </span>
          <span v-else>
            {{scope.row[obj[0]]}}
          </span>
        </template>
      </el-table-column>
     <el-table-column
        label="操作"
        width="180" >
      <template slot-scope="scope">
        <el-button
          size="mini"
          type="success"
          @click="updateCheck(scope.row.fkUserId,1)" v-show="scope.row.isResponse==1&&scope.row.isPass==0">通过</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="updateCheck(scope.row.fkUserId,2)" v-show="scope.row.isResponse==1&&scope.row.isPass==0">不通过</el-button>
          <!-- 暂时不提供驳回通道 -->
<!--        <el-button
          size="mini"
          type="danger"
          @click="updateState(scope.$index, scope.row,2)" v-show="scope.row.isCheck==0">驳回</el-button> -->
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

  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: "",
    //数据存放
    data() {
      return {
        radio: 1,
        search: {
          name: null,
          phone: null
        },
        tableData: [],
        action: {
          selectList: "drive/examSub/page",
          del: "drive/examSub/del",
          pass: "drive/examSub/pass/{id}",
          noPass: "drive/examSub/noPass/{id}"
        },
        isPassTableShow:["未处理","通过","不通过"],
        subjectTableShow:["报名阶段","科目一","科目二","科目三","科目四","其他"],
        isResponseShow:["未受理","已回复"],
        tableHead:[
          ["userName","姓名",90],
          ["userAccount","手机号",120],
          ["email","邮箱",180],
          ["subject","考试科目",80],
          ["isResponse","是否受理",80],
          // ["isPass","考试通过认定",120],
          ["content","处理内容",200],
          ["createUserName","创建人",80],
          ["gmtCreate","创建时间",160],
          ["updateUserName","修改人",80],
          ["gmtModified","修改时间",160],
        ],
        dialogVisible: false,
        pageNum:0,
        pageSize:6,
        total: null,
        headers:{
          authToken:''
        },
        imageUrl:'',
        tableData:[],
        ids: '',
        emails: '',
        textarea:'',
        fullscreenLoading: false
      }
    },
    //方法函数存放
    methods: {
      getList(){
        this.headers.authToken = window.localStorage.getItem("authToken")

        let params={
          	pageNum: this.pageNum,
          	pageSize: this.pageSize,
            subject: this.radio
        }
        axios.post(this.action.selectList,params,{
          headers: {
            'authToken': this.headers.authToken
          }
        }).then((res)=>{
          // console.log(res.data.data.list)
          this.tableData=res.data.data.list
          this.total = res.data.data.total
          console.log(res)
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

      searchList(){
        this.pageNum = 0
        this.getList()
        // this.search={
        //   name:null,
        //   phone:null
        // }
      },
      updateCheck(index,state){
          console.log(index)
          this.$confirm(state==1?"是否确定通过":"是否确定为不及格", '确定？', {
            confirmButtonText: '确定',
            cancelButtonText: '取消'
          }).then(() => {
              axios.put(state==1?this.action.pass.replace("{id}",index):this.action.noPass.replace("{id}",index),null,{
                headers: {
                  'authToken': this.headers.authToken
                }
              }).then((res)=>{
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
            return;
          })

      },
      getIds(tableData){
        //ids的获取
        let ids = ''
        let emails = ''
        tableData.forEach((item, index) => {
          ids += item.id + ','
          emails+=item.email+ ','
        })
        ids = ids.substring(0,ids.length-1)
        emails = emails.substring(0,emails.length-1)
        this.ids = ids
        this.emails = emails
        console.log(this.ids)
        console.log(this.emails)
        //eamils的获取
      },
      handleClose(done) {
        done();
      },
      delResponse(){
        if(this.textarea.length<10){
          this.$message({
            type: "warning",
            message: "请输入超过10个字"
          })
          return
        }
        this.fullscreenLoading=true
        let params={
            ids: this.ids,
            emails: this.emails,
            content: this.textarea,
        }
        console.log(params)
        axios.put(this.action.del,params,{
          headers: {
            'authToken': this.headers.authToken
          }
        }).then((res)=>{
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
          this.fullscreenLoading = false
          this.dialogVisible =false
        })
      },
      openDialog(){
        if(this.ids.length>1){
          this.dialogVisible=!this.dialogVisible
        }else{
          this.$message({
            type: "warning",
            message: "请先选择列表项"
          })
        }
      }
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

}
</style>
