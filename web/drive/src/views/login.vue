<template>
  <div class="login-demo"
  v-loading ="loading">
    <div class="login-input">
      <h2>驾校管理系统登录</h2>
       <el-input
         prefix-icon="el-icon-user-solid"
         v-model="account">
       </el-input>
        <el-input
          prefix-icon="el-icon-s-opportunity"
          v-model="pwd"
          show-password>
        </el-input>
        <el-button type="primary" @click="login()">登录</el-button>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import qs from 'qs'
  export default {
    name: "",
    //数据存放
    data() {
      return {
        account: "",
        pwd: "",
        action: {
            login: "drive/login",
            loginIs: "drive/stateRe"
        },
        loading: false
      }
    },
    //方法函数存放
    methods: {
      login(){
        this.loading =true
        console.log(this.account)
        console.log(this.pwd)
        let params = {
          userAccount: this.account,
          password: this.pwd
        }
        setTimeout(() => {
        axios.post(this.action.login,params).then((res)=>{
          if(res.data.code==0){

            this.$message({
              type: 'success',
              message: res.data.message
            })
            window.localStorage.setItem("authToken",res.data.data.authToken)

            if(res.data.data.userType==1){
              this.$router.push('/admin')
            }else if(res.data.data.userType==0){
              this.$router.push('/TheStudent')
            }else if(res.data.data.userType==2){
              this.$router.push('/TheTeacher')
            }else{
              alert("警告")
            }

          }else{
            this.$message({
              type: 'error',
              message: res.data.message
            })
          }
          this.loading = false
        })
        },500)
      },
      loginIs(){
        let authToken = window.localStorage.getItem("authToken")
        axios.get(this.action.loginIs,{
        headers:{
            'authToken': authToken
        }
        }).then((res)=>{
          if(res.data.code==0){
            if(res.data.data.userType==1){
              this.$router.push('/admin')
            }else if(res.data.data.userType==0){
              this.$router.push('/TheStudent')
            }else if(res.data.data.userType==2){
              this.$router.push('/TheTeacher')
            }else{
              this.$message({
                type: 'error',
                message: '警告'
              })
            }
          }else{
            //未登录或者掉线则回登录页面
            this.$router.push('/')
          }
        })
      }
    },
    //页面加载时
    mounted() {
      this.loginIs()
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
.login-demo{
  background-color: #2d3a48;
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  .login-input{
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    // display: inline-block;
    width: 450px;
    height: 210px;
    h2{
      text-align: center;
      color: white;
    }
  }
}
</style>
