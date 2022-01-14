<template>
  <div class="h5-bottom">
    <div class="area" @click="routerTo(0)">
      <span :class="heightLight==0?'change':''">
        业务首页
      </span>
    </div>
    <div class="area" @click="routerTo(1)">
      <span :class="heightLight==1?'change':''">
        个人中心
      </span>
    </div>
  </div>
</template>

<script>
  export default {
    name: "",
    //数据存放
    data() {
      return {
        heightLight: 0,
        routerList:["/Operation","/Person"]
      }
    },
    //方法函数存放
    methods: {
      routerTo(i){
        this.heightLight=i
        this.$router.push(this.routerList[i])
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

<style lang="scss" scoped="scoped">
.h5-bottom{
  width: 100%;
  height: 7%;
  display: flex;
  align-items: center;
  justify-content: center;
  .area{
    display: flex;
    align-items: center;
    justify-content: center;
    width: 50%;
    height: 100%;
    color: #707070;
    .change{
      color: #f0932b;
      font-weight: 800;
    }
  }
}
</style>
