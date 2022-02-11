<template>
  <div class="nav-top">
      <i class="el-icon-switch-button" @click="outLogin()"></i>
      <p>{{this.xsg_time | formatDate}}</p>
      <img src="../assets/img/github.png" alt="进入我的github" onClick="location='https://github.com/zenbinhao'" draggable="false">
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: "",
    //数据存放
    data() {
      return {
        xsg_time: "",
        action:{
          loginOut:"drive/loginOut"
        },

      }
    },
    //方法函数存放
    methods: {
      xsg() {
        let timestamp = Date.parse(new Date())
        this.xsg_time = timestamp
      },
      outLogin(){
        this.$emit('func')
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
      this.xsg_time = Date.parse(new Date())
      setInterval(this.xsg, 1000)
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
.nav-top{
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  i{
    font-size: 20px;
    font-weight: 800;
    cursor: pointer;
  }
  img{
    cursor: pointer;
  }
}
</style>
