<template>
  <div class="student-box">
    <!-- 教练员安排记录页面 -->



    <!--头部按钮操作-->
    <div class="operation">
      <div>

        <!-- <el-button type="primary" @click="isAddShow=true">录入信息</el-button> -->
        <!-- <el-button type="danger" @click="deleteTableData()">批量删除</el-button> -->
      </div>
      <div class="operation-search">
        <el-button type="success" @click="search={};getList()">显示所有</el-button>
        <el-input v-model="search.name" placeholder="学员姓名回车搜索" @keydown.enter.native="searchList()"></el-input>
        <el-input v-model="search.phone" placeholder="学员手机号回车搜索" @keydown.enter.native="searchList()"></el-input>
      </div>
    </div>







    <!--表格信息-->
    <el-table
      :data="tableData"
      style="width: 100%">
      <!-- 下拉详情 -->
      <el-table-column
      type="expand">
        <template slot-scope="scope">
         <el-form label-position="right" inline class="demo-table-expand" label-width="90px">
            <el-form-item :label="obj[1]+':'" v-for="(obj,index) in tableHead" :key="index">
              <span v-if="obj[0]=='isResponse'" :style="scope.row[obj[0]]==0?'color:red;font-weight: 800;':'color:green; font-weight: 800;'">
                {{ isRsponseTableShow[scope.row[obj[0]]] }}
              </span>
              <span v-else>
                {{scope.row[obj[0]]}}
              </span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
     <el-table-column
        v-for="(obj,index) in tableHead" :key="index"
        :label="obj[1]"
        :width="obj[2]"
        >
         <template slot-scope="scope">
          <span v-if="obj[0]=='isResponse'" :style="scope.row[obj[0]]==0?'color:red;font-weight: 800;':'color:green; font-weight: 800;'">
            {{ isRsponseTableShow[scope.row[obj[0]]] }}
          </span>
          <span v-else>
            {{scope.row[obj[0]]}}
          </span>
        </template>
      </el-table-column>
<!--     <el-table-column
        label="操作"
        width="80" >
      <template slot-scope="scope">
        <el-button
          size="mini"
          type="primary"
          @click="updateState(scope.$index, scope.row,1)" v-show="scope.row.isCheck==0&&scope.row.content==0">已缴</el-button>
        <el-button
          size="mini"
          type="warning"
          @click="examIsCheck(scope.row.id,scope.row.fkUserId)" v-show="scope.row.isCheck==0&&scope.row.content>0">已缴</el-button>
          暂时不提供驳回通道
       <el-button
          size="mini"
          type="danger"
          @click="updateState(scope.$index, scope.row,2)" v-show="scope.row.isCheck==0">驳回</el-button>
      </template>
      </el-table-column> -->
    </el-table>






    <!-- 表格底部 分页工具 -->
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[6, 10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
    <p>&nbsp;</p>

  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: "",
    //数据存放
    data() {
      return {
        search: {
          name: null,
          phone: null
        },
        tableData: [],
        action: {
          selectList: "drive/courseSub/page",
        },
        isRsponseTableShow:["未受理","已回复"],
        tableHead:[
          ["userName","姓名",90],
          ["userAccount","手机号",120],
          // ["sex","性别",50],
          ["isResponse","是否回应",80],
          ["responseContent","回应内容","auto"],
          // ["isCheck","收费认定",80],
          // ["createUserName","创建人",80],
          // ["gmtCreate","创建时间",160],
          // ["updateUserName","修改人",80],
          // ["gmtModified","修改时间",160],
        ],
        pageNum:0,
        pageSize:6,
        total: null,
        headers:{
          authToken:''
        },
        tableData:[],
      }
    },
    //方法函数存放
    methods: {
      getList(){
        this.headers.authToken = window.localStorage.getItem("authToken")
        let params={
          	pageNum: this.pageNum,
          	pageSize: this.pageSize,
            userName: this.search.name,
            userAccount: this.search.phone
        }
        axios.post(this.action.selectList,params,{
          headers: {
            'authToken': this.headers.authToken
          }
        }).then((res)=>{
          // console.log(res.data.data.list)
          this.tableData=res.data.data.list
          this.total = res.data.data.total
          console.log(this.tableData)
        })
      },
      handleSizeChange(val){
        this.pageNum=1
        this.pageSize=val
        this.getList()
      },
      handleCurrentChange(val){
        this.pageNum=val
        this.getList()
      },

      searchList(){
        this.pageNum = 0
        this.getList()
        // this.search={
        //   name:null,
        //   phone:null
        // }
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
.student-box{
  width: 100%;
  height: 100%;
  // background-color: white;
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
  .listImg{
    width: 100px;
    height: 100px;
  }
  .operation{
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    // .el-table::-webkit-scrollbar {
    //   display: none;
    // }
    .operation-search{
      display: flex;

    }
  }
  .block{
    margin-top: 20px;
  }

}
</style>
