<template>
  <div style="width: 100%">
<!--    <el-button type="primary" style="float: right" icon="el-icon-circle-plus" @click="create_module()">添加板块</el-button>-->
    <el-table
      :data="tableData"
      border
      style="width: 100%">

        <el-table-column
          prop="mname"
          label="主题"
          width="150">
        </el-table-column>

        <el-table-column
          prop="intro"
          label="简介"
          width="1060">
        </el-table-column>

        <el-table-column v-if='showClose' prop="mid">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button v-on:click="moreinfro(scope.row)" type="text" size="small">查看</el-button>
  <!--          <el-button @click="favorite(scope.row)" type="text" size="small">收藏</el-button>-->
<!--            <el-button @click="deletemodule(scope.row)" type="text" size="small">删除</el-button>-->

          </template>
        </el-table-column>
    </el-table>

    <el-pagination
      background
      layout="prev, pager, next"
      page-size="6"
      :total="total"
      @current-change="page"
    >
    </el-pagination>

  </div>

</template>

<script>
  export default {
    methods: {
      create_module(){
        this.$router.push({
          path:"/createmodule"
        })
      },
      deletemodule(num){
        const _this=this;
        if(_this.Varall._isLogin){
          // alert("已经登录")
          // assert(num.mname)
          if(confirm('确定要删除该板块吗？')==true){
          const params = new URLSearchParams();
          params.append('email', _this.Varall._email);
          params.append('password', _this.Varall._password);
          params.append('mname',num.mname);
          // assert(num.mname)
          this.$axios.post('http://localhost:8080/module/deleteModule',params).then(function (resp) {
            console.log(resp)
            if(resp.data.code=="499"){
              alert("删除失败：身份验证错误！原因可能是密码错误或邮箱输入错误")
            }else if(resp.data.code=="411") {
              alert("删除失败：主题不存在！")
            }else if(resp.data.code=="488") {
              alert("删除失败：无权限！此主题并非当前用户创建！")
            }else{
              alert("话题删除成功！")
              location.reload();
            }
          })
          }
        }else{
          alert("请登录！！！")
        }
      },
      handleClick(row) {
        console.log(row);
      }, moreinfro(num) {
        this.Varall._currentmname = num.mname;
        sessionStorage.setItem("currentmname",num.mname);
        this.$router.push({
          path: "/module_topic", query: {
            mname: num.mname
          }
        })
      },favorite(num){

      },
      page(currentPage){
        const _this=this
        this.$axios.post('http://localhost:8080/module/showModule?pageNum='+currentPage+'&pageSize=6').then(function (resp) {
          _this.tableData=resp.data.body.list
          _this.total=resp.data.body.pages
        })
      }

    },
    created(){
      const _this=this
      this.$axios.post('http://localhost:8080/module/showModule?pageNum=1&pageSize=6').then(function (resp) {
        _this.tableData=resp.data.body.list
        _this.total=resp.data.body.pages
      })
    },
    data(){
      return {
        total:null,
        tableData:null
      }
    }
  }
</script>
