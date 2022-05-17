<template>

    <div>
      <el-button type="primary" icon="el-icon-back" @click="goback">返回</el-button>
      <el-button type="primary" style="float: right" icon="el-icon-circle-plus" @click="create_comment()">添加评论</el-button>
      <el-table
        :data="tableData"
        border
        style="width: 100%;">
<!--        <el-table-column-->
<!--          prop="num"-->
<!--          label="楼层数"-->
<!--          width="80">-->
<!--        </el-table-column>-->

        <el-table-column
          prop="ctime"
          label="评论时间"
          width="180">
        </el-table-column>

        <el-table-column
          prop="ccontent"
          label="评论内容"
          width="850">
        </el-table-column>

<!--        <el-table-column-->
<!--          prop="clike"-->
<!--          label="点赞数"-->
<!--          width="180"-->
<!--          v-if="show" key="0">-->
<!--        </el-table-column>-->

        <el-table-column
          prop="uname"
          label="用户"
          width="100">
        </el-table-column>

        <el-table-column
          fixed="right"
          label="操作"
          width="150"
        style="height: 100%">
          <template slot-scope="scope">
            <el-button v-on:click="likecomment(scope.row)" type="text" size="small">点赞 (

              <span prop="clike">
                {{scope.row.clike}}
              </span>
              )</el-button>
            <el-button v-on:click="deletecomment(scope.row)" type="text" size="small">删除评论</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background
        layout="prev, pager, next"
        page-size="5"
        :total="total"
        @current-change="page"
      >
      </el-pagination>
    </div>
</template>

<script>
    export default {
      name: "topic_comment",
      methods:{
        page(currentPage){
          alert(currentPage)
          const _this=this;
          let ttime = _this.$route.query.ttime;
          this.$axios.post('http://localhost:8080/comment/showCommentByTopic?ttime='+ttime+'&pageNum='+currentPage+'&pageSize=5'+'&mname='+_this.Varall._currentmname).then(function (resp) {
            console.log(resp)
            _this.tableData=resp.data.body.list
            _this.total=resp.data.body.total
          })
        },
        goback(){
          const _this = this;
          this.$router.push({
            path: "/module_topic", query: {
              mname: _this.Varall._currentmname
            }
          })
        },
        deletecomment(num){
          const _this=this;
          if(_this.Varall._isLogin){
            // alert("已经登录")
            if(confirm('确定要删除该评论吗？')==true){
            const params = new URLSearchParams();
            params.append('email', _this.Varall._email);
            params.append('password', _this.Varall._password);
            let ttime = this.$route.query.ttime;
            params.append('ttime',ttime);
            params.append('mname',_this.Varall._currentmname);
            params.append('ctime',num.ctime);
            // alert(num.num)
            // alert(tname)
            this.$axios.post('http://localhost:8080/comment/deleteComment',params).then(function (resp) {
              console.log(resp)
              if(resp.data.code=="499"){
                alert("删除评论失败：身份验证错误！原因可能是密码错误或邮箱输入错误")
              }else if(resp.data.code=="431"){
                alert("删除评论失败：评论不见了？！")
              }else if(resp.data.code=="488"){
                alert("删除评论失败：该评论不是由该用户创建！")
              } else{
                alert("评论删除成功！")
                // _this.$router.go(0);
                location.reload();
              }
            })
            }
          }else{
            alert("请登录！！！")
          }
        },
        likecomment(num){
          // alert("点赞？")
          const _this=this;
          if(_this.Varall._isLogin){
            // alert("已经登录")
            const params = new URLSearchParams();
            params.append('email', _this.Varall._email);
            params.append('password', _this.Varall._password);
            let tname = this.$route.query.tname;
            params.append('tname',tname);
            params.append('num',num.num);
            params.append('mname',_this.Varall._currentmname);
            this.$axios.post('http://localhost:8080/comment/likeComment',params).then(function (resp) {
              console.log(resp)
              if(resp.data.code=="499"){
                alert("点赞失败：身份验证错误！原因可能是密码错误或邮箱输入错误")
              }else if(resp.data.code=="432"){
                alert("您已经赞过该评论啦！")
              }else if(resp.data.code=="431"){
                alert("评论不见了？！！")
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
        create_comment(){
          // alert(mname);
          this.$router.push({
            path:"/createcomment",query:{
              ttime:this.$route.query.ttime
            }
          })
        }
      },created(){
        const _this=this;
        let ttime = this.$route.query.ttime;
        this.$axios.post('http://localhost:8080/comment/showCommentByTopic?ttime='+ttime+'&pageNum=1&pageSize=5'+'&mname='+_this.Varall._currentmname).then(function (resp) {
          console.log(resp)
          _this.tableData=resp.data.body.list;
          _this.total=resp.data.body.total;
          // _this.clike=resp.data.body.clike;
        })

        // alert('1')
      },
      data(){
        return {
          total:null,
          tableData:null,
          // clike:null
        }
      }
    }
</script>

<!--<style scoped>-->

<!--</style>-->
<style>
  .container_lefts .el-table body,
  .container_lefts .el-table th.is-leaf,
  .container_lefts .el-table--border,
  .container_lefts .el-table--group {
    border: none;
    cursor: pointer;
  }
  .container_lefts .el-table::before {
    height: 0;
  }
</style>
