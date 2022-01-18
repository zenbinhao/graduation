<template>
  <div class="news-box">
    <!-- <p>消息面板</p> -->
    <div class="news-info">
      <el-badge :value="viewDelExam.length==0?'':viewDelExam.length" class="item">
        <el-button size="small" @click="routerTo(0)">点我跳转——》约考信息处理</el-button>
      </el-badge>
      <el-divider content-position="left">未处理信息</el-divider>
      <!-- <p>&nbsp;</p> -->
      <div class="news-info-box">
        <span v-for="(obj,index) in viewDelExam" :key="index">
          姓名为：
          {{obj.userName}}
          &nbsp;
          &nbsp;
          &nbsp;
          &nbsp;
        </span>
      </div>
    </div>
    <div class="news-info">
      <el-badge :value="viewPassExam.length==0?'':viewPassExam.length" class="warning">
        <el-button size="small"  @click="routerTo(1)">点我跳转——》考试通过认定</el-button>
      </el-badge>
      <el-divider content-position="left">未处理信息</el-divider>
      <!-- <p>&nbsp;</p> -->
      <div class="news-info-box">
        <span v-for="(obj,index) in viewPassExam" :key="index">
          姓名为：
          {{obj.userName}}
          &nbsp;
          &nbsp;
          &nbsp;
          &nbsp;
        </span>
      </div>
    </div>
    <div class="news-info">
      <el-badge :value="viewPayment.length==0?'':viewPayment.length" class="item" type="primary">
        <el-button size="small" @click="routerTo(2)">点我跳转——》缴费信息认定</el-button>
      </el-badge>
      <el-divider content-position="left">未处理信息</el-divider>
      <!-- <p>&nbsp;</p> -->
      <div class="news-info-box">
        <span v-for="(obj,index) in viewPayment" :key="index">
          姓名为：
          {{obj.userName}}
          &nbsp;
          &nbsp;
          &nbsp;
          &nbsp;
        </span>
      </div>
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
        action: {
          selectList: "drive/view/",
        },
        tableData: {},
        headers:{
          authToken: '',
        },
        viewDelExam:[],
        viewPassExam:[],
        viewPayment:[],
        routerList:["/Exam","/PassExam","/Payment"],
        search:'',
      }
    },
    //方法函数存放
    methods: {
      getList(){
        this.headers.authToken = window.localStorage.getItem("authToken")
        axios.post(this.action.selectList,null,{
          headers: {
            'authToken': this.headers.authToken
          }
        }).then((res)=>{
          // console.log(res.data.data.list)
          this.viewDelExam=res.data.data.viewDelExam
          this.viewPassExam=res.data.data.viewPassExam
          this.viewPayment=res.data.data.viewPayment
          console.log(this.tableData)
          // this.total = res.data.data.total
        })
      },
      routerTo(index){

        this.$router.push(this.routerList[index])
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
.news-box{
  width: 100%;
  height: 100%;

  text-align: left;
  .news-info{
    border-radius: 5px;
    width: 100%;
    background-color: white;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;

    .news-info-box{
      // text-decoration: none;
      padding: 10px 0;
      margin: 0 20px;
      width: 100%;
      overflow-x: auto;
      overflow-y: hidden;
      // li{}
    }
  }
}
</style>
