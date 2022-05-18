<template>

  <div>
    <el-button type="primary" icon="el-icon-back" @click="goback">返回</el-button>
    <el-button type="primary" style="float: right" icon="el-icon-circle-plus" @click="create_topic()">添加话题</el-button>
    <el-table
      :data="tableData"
      border
      style="width: 100%">

      <el-table-column
        prop="ttime"
        label="日期"
        width="200">
      </el-table-column>

      <el-table-column
        prop="tcontent"
        label="话题内容"
        width="870">
      </el-table-column>

      <el-table-column
        prop="uname"
        label="创建者"
        width="100">
      </el-table-column>

      <el-table-column
        fixed="right"
        label="操作"
        width="200">
        <template slot-scope="scope">
          <el-button v-on:click="moreinfro(scope.row)" type="text" size="small">查看</el-button>
          <el-button @click="tlike(scope.row)" type="text" size="small">点赞
          （
            <span>
              {{scope.row.clike}}
            </span>
            ）
          </el-button>
          <el-button @click="favorite(scope.row)" type="text" size="small">收藏</el-button>
          <el-button v-on:click="deletetopic(scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div>
      <el-pagination
        background
        layout="prev, pager, next"
        page-size="5"
        :total="total"
        @current-change="page"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
  import Varall from "./Varall";

  export default {
    name: "module_topic",
    methods:{
      page(currentPage){
        alert(currentPage)
        const _this=this;
        let mname = this.$route.query.mname;
        this.$axios.post('http://localhost:8080/topic/showTopicByModule?mname='+mname+'&pageNum='+currentPage+'&pageSize=5').then(function (resp) {
          alert(resp.data)
          _this.tableData=resp.data.body.list
          _this.total=resp.data.body.total
        })
      },
      goback(){
        this.$router.push({
          path: "/module"
        })
      },
      moreinfro(num){
        this.Varall._currenttime = num.ttime;
        sessionStorage.setItem("currenttime",num.ttime);
        this.$router.push({
          path: "/topic_comment", query: {
            ttime: num.ttime
          }
        })
      },tlike(num){
        // alert("点赞？")
        const _this=this;
        if(_this.Varall._isLogin){
          const params = new URLSearchParams();
          params.append('email', _this.Varall._email);
          params.append('password', _this.Varall._password);
          params.append('ttime',num.ttime);
          params.append('mname',_this.Varall._currentmname);
          this.$axios.post('http://localhost:8080/topic/likeTopic',params).then(function (resp) {
            console.log(resp)
            if(resp.data.code=="499"){
              alert("点赞失败：身份验证错误！原因可能是密码错误或邮箱输入错误")
            }else if(resp.data.code=="422"){
              alert("您已经赞过该话题啦！看看别的话题吧")
            }else if(resp.data.code=="421"){
              alert("点赞失败！话题不见了！？")
            }else{
              // alert("点赞成功！已有"+_this.clike+'人点赞该评论！')
              alert("点赞成功！")
              location.reload();
            }
          })
        }else{
          alert("请登录！！！")
        }
      },
      favorite(num){
        const _this=this;
        if(_this.Varall._isLogin){
          const params = new URLSearchParams();
          params.append('email', _this.Varall._email);
          params.append('password', _this.Varall._password);
          params.append('ttime',num.ttime);
          params.append('mname',_this.Varall._currentmname);
          this.$axios.post('http://localhost:8080/topic/collectTopic',params).then(function (resp) {
            alert(params);
            if(resp.data.code=="499"){
              alert("收藏失败：身份验证错误！原因可能是密码错误或邮箱输入错误")
            }else if(resp.data.code=="423"){
              alert("您已经收藏过该话题啦！看看别的话题吧")
            }else if(resp.data.code=="421"){
              alert("收藏失败！话题不见了？！")
            }else{
              alert("话题收藏成功！")
              // location.reload();
            }
          })
        }else{
          alert("请登录！！！")
        }
      },
      deletetopic(num){
        const _this=this;
        if(_this.Varall._isLogin){
          if(confirm('确定要删除该话题吗？')==true){
          const params = new URLSearchParams();
          params.append('email', _this.Varall._email);
          params.append('password', _this.Varall._password);
          params.append('ttime',num.ttime);
          params.append('mname',_this.Varall._currentmname);

          this.$axios.post('http://localhost:8080/topic/deleteTopic',params).then(function (resp) {
            console.log(resp)
            if(resp.data.code=="499"){
              alert("删除话题失败：身份验证错误！原因可能是密码错误或邮箱输入错误")
            }else if(resp.data.code=="488"){
              alert("删除失败！该话题不是由当前用户创建！")
            }else if(resp.data.code=="421"){
              alert("删除话题失败！话题不见了？！")
            }
            else{
              alert("话题删除成功！")
              // _this.$router.go(0);
              location.reload();
            }
          })
          }
        }else{
          alert("请登录！！！")
        }
      },
      create_topic(){
        // alert(mname);
        this.$router.push({
          path:"/createtopic",query:{
            mname:this.$route.query.mname
          }
        })
      }
    },created(){
      const _this=this;
      let mname = this.$route.query.mname;
      this.$axios.post('http://localhost:8080/topic/showTopicByModule?mname='+mname+'&pageNum=1&pageSize=5').then(function (resp) {
        console.log(resp.data)
        _this.tableData=resp.data.body.list
        _this.total=resp.data.body.total
      })

      // alert('1')
    },
    data(){
      return {
        total:null,
        tableData:null
      }
    }
  }
</script>

<style scoped>

</style>
