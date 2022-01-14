<template>
  <div class="teacherH5-box">
    <H5Top></H5Top>
    <div class="teacherH5-main">
      <div class="main-list" v-for="(obj,index) in tableData" :key="index">
        <p class="main-list-p">学员姓名：{{obj.userName}}</p>
        <p class="main-list-p">手机号号码：{{obj.userAccount}}</p>
        <p class="main-list-p">当前考试进度：{{planList[obj.plan]}}</p>
        <p class="main-list-p">预约时间：{{obj.gmtCreate | formatDate}}</p>
        <el-button type="success" size="mini" @click="isResponseShow=!isResponseShow;currentRowData=obj">回复</el-button>
      </div>
    </div>
    <transition name="el-fade-in-linear">
      <div v-show="isResponseShow" class="transition-box" @click="isResponseShow=!isResponseShow" v-loading.fullscreen.lock="fullscreenLoading">
        <div @click.stop class="response-box" v-if="isToRusult==0">
          回复：
          <p>&nbsp;</p>
          <el-input
            type="textarea"
            placeholder="请输入内容"
            v-model="textarea"
            maxlength="30"
            show-word-limit
          >
          </el-input>
          <p>&nbsp;</p>
          <el-button type="success" size="mini" @click="sendResponse()">发送</el-button>
        </div>
        <el-result icon="success" title="成功回复"  v-else-if="isToRusult==1" class="Result" @click.native.stop>
          <template slot="extra">
            <el-button type="primary" size="medium" @click="isToRusult=0;isResponseShow=!isResponseShow">返回</el-button>
          </template>
        </el-result>
        <el-result icon="danger" title="系统异常稍后再试"  v-else class="Result" @click.native.stop>
          <template slot="extra">
            <el-button type="primary" size="medium" @click="isToRusult=0;isResponseShow=!isResponseShow">返回</el-button>
          </template>
        </el-result>
      </div>
    </transition>
    <div class="teacherH5-bottom" @click="outLogin()">
      退出
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import H5Top from '@/components/H5Top.vue'
  export default {
    name: "",
    //数据存放
    data() {
      return {
        action:{
          loginOut: "drive/loginOut",
          selectList: "drive/h5Teacher/mySub",
          responseSub: "drive/h5Teacher/updateResponse"
        },
        headers:{
          authToken:''
        },
        planList:["科目一","科目二","科目三","科目四","已毕业"],
        tableData:[],
        // total:null,
        textarea: '',
        isResponseShow: false,
        fullscreenLoading: false,
        isToRusult: 0,
        currentRowData:{},

      }
    },
    //方法函数存放
    methods: {
      outLogin(){
        axios.get(this.action.loginOut,{
          headers:{
            'authToken': window.localStorage.getItem('authToken')
          }
        }).then((res)=>{
          if(res.data.code==='0'){
            this.$message({
              type: 'success',
              message: res.data.message
            })
          }else{
            this.$message({
              type: 'error',
              message: res.data.message
            })
          }
          this.$router.push('/')
        })
      },
      getList(){
        this.headers.authToken = window.localStorage.getItem("authToken")
        let params={
          	pageNum: 1,
          	pageSize: 100,
            isResponse: 0,
            teacherId: null
        }
        axios.post(this.action.selectList,params,{
          headers: {
            'authToken': this.headers.authToken
          }
        }).then((res)=>{
          // console.log(res.data.data.list)
          this.tableData=res.data.data.list
          // this.total = res.data.data.total
          console.log(res)
        })
      },
      sendResponse(){
        if(this.textarea.length<10){
          this.$message({
            type: 'error',
            message: '回复不能低于10个字'
          })
          return;
        }
        this.fullscreenLoading = true
        let params={
          email: this.currentRowData.email,
          id: this.currentRowData.id,
          responseContent: this.textarea,
          version: this.currentRowData.version
        }
        axios.put(this.action.responseSub,params,{
          headers: {
            'authToken': this.headers.authToken
          }
        }).then((res)=>{
          console.log(res)
          //关闭遮罩层
          this.fullscreenLoading = false

          if(res.data.code==0){
            //成功打开成功提示显示
            this.isToRusult=1
          }else{
            //失败打开失败提示显示
            this.isToRusult=2
          }
          this.getList()
        })
      }
    },
    //页面加载时
    mounted() {
      this.getList()
    },
    //组件注册
    components: {
      H5Top,
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

<style scoped="scoped" lang="scss">
.teacherH5-box{
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: stretch;
  background-color: #f4f4f4;
  // justify-content: ;
  .transition-box{
    width: 100%;
    height: 100%;
    border-radius: 4px;
    background-color: rgba($color: #000000, $alpha: 0.8);
    // color: #fff;
    position: absolute;
    top: 0;
    z-index: 99;
    display: flex;
    justify-content: center;
    align-items: center;
    .Result{
      width: 80%;
      height: 35%;
      background-color: #ffffff;
      border-radius: 10px;
    }
    .response-box{
      width: 80%;
      padding: 5% 5%;
      // height: 16%;
      background-color: #ffffff;
      border-radius: 10px;
      display: flex;
      justify-content: space-around;
      flex-direction: column;
    }
  }
  .teacherH5-main{

    overflow: auto;
    width: 100%;
    height: 86%;
    &::-webkit-scrollbar{
      display:none
    }
    .main-list{
      padding: 2% 5%;
      width: 80%;
      // height: 21%;
      background-color: white;
      margin: 5% auto;
      border-radius: 10px 10px;
      box-shadow: 0 2px 10px rgba(125,126,128,0.16);
      display: flex;
      flex-direction: column;
      align-items: stretch;
      .main-list-p{
        margin-bottom: 2%;
      }
    }
  }
  .teacherH5-bottom{
    width: 100%;
    height: 7%;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: white;
    box-shadow: 0 2px 10px rgba(125,126,128,0.16);
  }
}
</style>
