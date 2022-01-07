<template>
  <div class="admin-box">
    <el-container>
      <el-aside>
        <div class="aside-introduce">
          <img src="../assets/img/logo.png" draggable="false"/>
          <span>驾校管理系统</span>
        </div>
        <div class="english-introduce">
          <p>Driving School</p>
          <p>Management System</p>
        </div>
        <div class="administer-box">
          <img src="../assets/img/touxiang.jpg" draggable="false" >
          <span id="" style="color: #f5f7fb;">
           Administer
          </span>
          <span>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </span>
          <span>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </span>
        </div>
        <navLeft></navLeft>
      </el-aside>
      <el-container>
        <el-header>
          <navTop></navTop>
        </el-header>
        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
  import navLeft from '@/components/navLeft.vue'
  import navTop from '@/components/navTop.vue'
  export default {
    name: "",
    //数据存放
    data() {
      return {
        routerList:["/home"],
      }
    },
    //方法函数存放
    methods: {

    },
    //页面加载时
    mounted() {

    },
    //组件注册
    components: {
      navLeft,
      navTop
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
.admin-box{
  width: 100%;
  height: 100%;
  .el-container{
    width: 100%;
    height: 100%;
  }
  .el-header{
    background-color: #ffffff;
    color: #333;
    text-align: center;
  }

  .el-aside {
    background-color: #3e5771;
    color: #333;
    text-align: center;
    overflow: hidden;
  }

  .el-main {
    background-color: #f5f7fb;
    color: #333;
    text-align: center;
  }
  .aside-introduce{
    position: relative;
    height: 80px;
    display: flex;
    justify-content: center;
    align-items: center;
    img{
      width: 100px;
      border-radius: 10px 10px;
    }
    span{
      font-family: "仿宋";
      font-weight: 600;
      color: #faaf52;
      font-size: 25px;
    }
  }
  .english-introduce{
    margin-top: -20px;
    font-family: "ms reference sans serif";
    p:nth-child(1){
      color: #f5f7fb;
      font-size: 18px;
      font-weight: 800;
    }
    p:nth-child(2){
      color: #faaf52;
      font-size: 16px;
    }
  }
  .administer-box{
    margin: 70px 20px 20px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    &>img{
      width: 50px;
      height: 50px;
      border-radius: 50% 50%;
    }
    &>span{
      font-size: 14px;
    }
  }
}


</style>
