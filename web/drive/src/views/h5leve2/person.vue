<template>
  <div class="person-box">
    <p>&nbsp;</p>
    <div class="person-introduction">
      <img src="../../assets/img/touxiang.jpg" alt="">

      <div>
        <p>小东东</p>
        <p>&nbsp;</p>
        <span>VIP</span>
      </div>

    </div>
    <p>&nbsp;</p>



    <div class="person-operation">
      <div class="person-operation-list" v-for="(obj,index) in operationList" :key="index">
        <div>
        <i :class="obj[1]" style="font-size: 20px; font-weight: 800; color: #c9c9c9"></i>
        <span style="color: #707070;">
          &nbsp;&nbsp;
          {{obj[0]}}
        </span>
        </div>
        <i class="el-icon-arrow-right"></i>
      </div>
    </div>



    <div class="exit-button" @click="outLogin()">
      退出登录
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: "",
    //数据存放
    data() {
      return {
        action:{
          loginOut:"drive/loginOut",
        },
        operationList:[
        ["修改信息","el-icon-user-solid"],
        ["修改密码","el-icon-warning"],
        ["约课记录","el-icon-s-order"],
        ["约考记录","el-icon-s-promotion"],
        ["缴费信息","el-icon-s-opportunity"],
        ]
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
      }
    },
    //页面加载时
    mounted() {

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

<style scoped="scoped" lang="scss">
.person-box{
  height: 100%;
  width: 100%;
  background-color: #f4f4f4;
  .person-introduction{
    width: 90%;
    height: 25%;
    background-color: white;
    margin: 0 auto;
    border-radius: 10px 10px;
    box-shadow: 0 2px 10px rgba(125,126,128,0.16);
    display: flex;
    align-items: center;
    img{
      width: 100px;
      height: 100px;
      border-radius: 50%;
      margin-left: 5%;
    }
    div{
      margin-left: 8%;
      p{
        font-size: 20px;
        font-weight: 600;
      }
      span{
        font-weight: 800;
        margin-top: 5%;
        font-size: 20px;
        background: linear-gradient(to right, red, orange, yellow, green, yellow, orange, red, orange, yellow, green, yellow, orange, red);
        -webkit-background-clip: text;/*背景颜色以文本方式剪切*/
        -webkit-text-fill-color: transparent;/*文字填充为透明色让设置的渐变色显示出来，一定要设置为透明色不然设置的渐变色无法显示出来，会被遮挡住*/

        }
    }
  }
  .person-operation{
    width: 90%;
    height: 45%;
    background-color: white;
    margin: 0 auto;
    border-radius: 10px 10px;
    box-shadow: 0 2px 10px rgba(125,126,128,0.16);
    display: flex;
    align-items: center;
    flex-direction: column;
    justify-content: center;
    .person-operation-list{
      padding: 0 10%;
      width: 80%;
      height: 20%;
      font-size: 18px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      &:active{
        background-color: rgba(46,204,113,0.2);
      }
    }
  }
  .exit-button{
    width: 90%;
    height: 8%;
    background-color: #ee0a24;
    margin: 5% auto;
    border-radius: 10px 10px;
    box-shadow: 0 2px 10px rgba(125,126,128,0.16);
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    font-weight: 500;
  }
}
</style>
