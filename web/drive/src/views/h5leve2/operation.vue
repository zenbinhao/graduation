<template>
  <div class="h5Index-box">

    <!-- 轮播 -->
    <el-carousel :interval="4000"  height="200px" indicator-position="none">
      <el-carousel-item v-for="(obj,index) in luboList" :key="index">
        <h3 class="medium">
          <img :src="obj">
        </h3>
      </el-carousel-item>
    </el-carousel>





    <!-- 操作 -->
    <div class="main-operation">

      <div class="main-operation-button" v-for="(obj,index) in buttonList" @click="OperationRediect(index)">
        <span :style="obj[2]">
          <i :class="obj[0]"></i>
        </span>
        <p>
          {{obj[1]}}
        </p>
      </div>
    </div>


    <!-- 预约课程操作 -->
    <transition name="el-fade-in-linear">
      <div v-show="isSubCourseShow" class="transition-box" @click="isSubCourseShow=!isSubCourseShow" v-loading.fullscreen.lock="fullscreenLoading">
        <div @click.stop class="subCourse" v-if="isToRusult==0">
          您确认进行课程预约吗？
          <el-button type="success" plain @click="subCourseSubmit()">预约</el-button>
        </div>
        <!-- <div @click.stop class="subResult"> -->
        <el-result icon="success" :title="subResultMessage"  v-else-if="isToRusult==1" class="subResult" @click.native.stop>
          <template slot="extra">
            <el-button type="primary" size="medium" @click="isToRusult=0;isSubCourseShow=!isSubCourseShow">返回</el-button>
          </template>
        </el-result>
        <el-result icon="warning" :title="subResultMessage"  v-else class="subResult" @click.native.stop>
          <template slot="extra">
            <el-button type="primary" size="medium" @click="isToRusult=0;isSubCourseShow=!isSubCourseShow">返回</el-button>
          </template>
        </el-result>
        <!-- </div> -->
      </div>
    </transition>


    <!-- 预约考试操作 -->
    <transition name="el-fade-in-linear">
      <div v-show="isSubExamShow" class="transition-box" @click="isSubExamShow=!isSubExamShow" v-loading.fullscreen.lock="fullscreenLoading">
        <div @click.stop class="subCourse" v-if="isExamRusult==0">
          您确认进行考试预约缴费吗？
          <span style="text-align: left;">缴费转账方式：</span>
         <el-radio-group v-model="payWay" size="medium">
            <el-radio-button :label="0">微信</el-radio-button>
            <el-radio-button :label="1">支付宝</el-radio-button>
            <el-radio-button :label="2">现金</el-radio-button>
            <el-radio-button :label="3">其他</el-radio-button>
          </el-radio-group>
          <el-button type="success" plain @click="subExamSubmit()">预约</el-button>
        </div>
        <el-result icon="success" :title="subResultMessage"  v-else-if="isExamRusult==1" class="subResult" @click.native.stop>
          <template slot="extra">
            <el-button type="primary" size="medium" @click="isExamRusult=0;isSubExamShow=!isSubExamShow">返回</el-button>
          </template>
        </el-result>
        <el-result icon="warning" :title="subResultMessage"  v-else class="subResult" @click.native.stop>
          <template slot="extra">
            <el-button type="primary" size="medium" @click="isExamRusult=0;isSubExamShow=!isSubExamShow">返回</el-button>
          </template>
        </el-result>
      </div>
    </transition>





    <!-- 介绍 -->
    <div class="conpany-introduction" v-for="obj in companyList">
      <p>{{obj[0]}}</p>
      <div>{{obj[1]}}</div>
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
          subCourse: "drive/h5Student/insertCourseSub",
          subExam: "drive/h5Student/examPay"
        },
        buttonList:[
          ["el-icon-date","约课","background-color: rgba(46,204,113,0.6);"],
          ["el-icon-sunny","约考","background-color: rgba(52,152,219,0.6);"]
        ],
        companyList:[
          ["公告","暂无公告"],
          ["地址","湖南省常德市武陵区湖南文理学院3栋-409"],
          ["联系我们","13387330595"],
        ],
        luboList:[
          require('@/assets/img/index1.jpg'),
          require('@/assets/img/index2.jpg'),
          require('@/assets/img/index3.jpg'),
          require('@/assets/img/index4.jpg')
        ],
        isSubCourseShow: false,
        isSubExamShow: false,
        isToRusult:0,
        fullscreenLoading: false,
        isSubExamShow: false,
        isExamRusult: 0,
        subResultMessage: '',
        payWay: 0,
      }
    },
    //方法函数存放
    methods: {
      OperationRediect(index){
        switch(index){
          case 0:
            this.yueke()
          break
          case 1:
            this.yuekao()
          break
        }
      },
      yueke(){
        console.log("预约课程")
        this.isSubCourseShow = !this.isSubCourseShow

      },
      yuekao(){
        console.log("预约考试")
        this.isSubExamShow= !this.isSubExamShow
      },
      subCourseSubmit(){
        this.fullscreenLoading = true
        axios.post(this.action.subCourse,null,{
          headers:{
            'authToken': window.localStorage.getItem("authToken")
          }
        }).then((res)=>{
          if(res.data.code==0){
            this.isToRusult=1
          }else{
            this.isToRusult=2
          }
          this.subResultMessage= res.data.message
          this.fullscreenLoading = false
        })

      },
      subExamSubmit(){
        this.fullscreenLoading = true
        let params={
          payWay: this.payWay,
        }
        // console.log()
        axios.post(this.action.subExam,params,{
          headers:{
            'authToken': window.localStorage.getItem("authToken")
          }
        }).then((res)=>{
          if(res.data.code==0){
            this.isExamRusult=1
          }else{
            this.isExamRusult=2
          }
          this.subResultMessage= res.data.message
          this.fullscreenLoading = false
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

<style lang="scss" scoped="scoped">
.h5Index-box{
  height: 100%;
  width: 100%;
  background-color: #f4f4f4;
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
    .subCourse{
      width: 80%;
      height: 25%;
      background-color: #ffffff;
      border-radius: 10px;
      display: flex;
      justify-content: space-around;
      align-items: center;
      flex-direction: column;
    }
    .subResult{
      width: 80%;
      height: 35%;
      background-color: #ffffff;
      border-radius: 10px;
    }
  }
  .medium{
    width: 100%;
    height: 100%;
    img{
      width: 100%;
      height: 100%;
    }
  }
  .conpany-introduction{
    width: 96%;
    height: 12%;
    margin: 3% auto;
    display: flex;
    justify-content: space-between;
    flex-direction: column;
    p{
      color: #8f8f8f;
    }
    div{
      width: 90%;
      height: 50%;
      border-radius: 5px 5px;
      background-color: white;
      box-shadow: 0 2px 10px rgba(125,126,128,0.16);
      display: flex;
      // justify-content: center;
      align-items: center;
      padding: 1% 5%;
      color: #707070;
      font-size: 14px;
    }
  }
  .main-operation{
    width: 90%;
    height: 13%;
    background-color: white;
    margin: 2% auto;
    border-radius: 10px 10px;
    box-shadow: 0 2px 10px rgba(125,126,128,0.16);
    display: flex;
    align-items: center;
    padding: 4% 5%;
    .main-operation-button{
      height: 100%;
      width: 20%;
      display: flex;
      flex-direction: column;
      align-items: center;
      &:active{
        background-color: rgba(46,204,113,0.2);
      }
      span{
        width: 90%;
        height: 90%;
        background-color: rgba(46,204,113,0.6);
        // rgba(52,152,219,0.6);
        border-radius: 10px 10px;
        display: flex;
        justify-content: center;
        align-items: center;
        i{
          font-size: 45px;
          font-weight: 300;
          color: white;
        }
      }
      p{
        color: #959595;
        font-size: 12px;
      }
    }
  }

  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }

}
</style>
