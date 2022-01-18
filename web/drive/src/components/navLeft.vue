<template>
  <div class="navLeft-box">
   <el-menu
     default-active="2"
     class="el-menu-vertical-demo"
     background-color="#3e5771"
     text-color="#faaf52"
     active-text-color="#f5f7fb"
     :unique-opened="true"
     @open="handleOpen"
     @close="handleClose">
      <el-menu-item index="2" @click="routerToManager(0)">
        <i class="el-icon-s-home"></i>
        <span slot="title">首页</span>
      </el-menu-item>
     <el-submenu index="1">
       <template slot="title">
         <i class="el-icon-s-shop"></i>
         <span>主要业务</span>
       </template>
         <el-menu-item index="1-1" @click="routerToManager(1)">学员信息管理</el-menu-item>
         <el-menu-item index="1-2" @click="routerToManager(2)">教练员信息管理</el-menu-item>
         <el-menu-item index="1-3" @click="routerToManager(3)">统一约考</el-menu-item>
         <!-- <el-menu-item index="1-4">收费信息</el-menu-item> -->
         <!-- <el-menu-item index="1-5">考试信息</el-menu-item> -->
     </el-submenu>
     <el-submenu index="2">
       <template slot="title">
         <i class="el-icon-s-order"></i>
         <span slot="title">记录认定</span>
       </template>
       <el-menu-item index="2-1" @click="routerToManager(4)">收费信息认定</el-menu-item>
       <el-menu-item index="2-2" @click="routerToManager(5)">教练员排课记录</el-menu-item>
       <el-menu-item index="2-3" @click="routerToManager(6)">考后认定</el-menu-item>
     </el-submenu>
     <!-- <el-submenu index="4">
       <template slot="title">
         <i class="el-icon-setting"></i>
         <span slot="title">系统功能</span>
       </template>
       <el-menu-item index="4-1">数据字典</el-menu-item>
       <el-menu-item index="4-2">操作记录</el-menu-item>
     </el-submenu> -->
   </el-menu>
  </div>
</template>

<script>
  export default {
    name: "",
    //数据存放
    data() {
      return {
        routerList:["/Home","/Student","/Teacher","Exam","/Payment","/CourseRecord","/PassExam"],
      }
    },
    //方法函数存放
    methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },
      routerToManager(index){
        console.log(index)
        this.$router.push(this.routerList[index])
      },

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

<style lang="scss" scoped="scoped">
.navLeft-box{
  // background-color: black;
  text-align: left;
  overflow: auto;
  &::-webkit-scrollbar {
    display: none;
  }
}
</style>
