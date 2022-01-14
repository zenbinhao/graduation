<template>
  <div class="studentH5-box">
      <H5Top></H5Top>
      <H5Main></H5Main>
      <H5Bottom></H5Bottom>
  </div>
</template>

<script>
  import H5Bottom from '@/components/H5Bottom.vue'
  import H5Main from '@/components/H5Main.vue'
  import H5Top from '@/components/H5Top.vue'
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
H5Bottom,H5Top,H5Main
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
.studentH5-box{
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: stretch;
  // justify-content: ;
}
</style>
