<template>
  <div class="h5main-box">
    <router-view/>
  </div>
</template>

<script>
  export default {
    name: "",
    //数据存放
    data() {
      return {

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
.h5main-box{
  width: 100%;
  height: 86%;
  overflow: auto;
  background-color: #f4f4f4;
  // display: flex;
  // align-items: center;
  // justify-content: center;
}
</style>
