<template>
  <div id="app">
    <el-container>
      <el-header height="80px" style="background-color: #EBEBEB; border-color: cadetblue">
        <router-link to="/">
          <el-button class="logo-text" style="font-size:3em;" id="logo">万能墙</el-button>
        </router-link>
        <span class="row-bg" style="float: right" v-show="!this.Varall._isLogin">
          <router-link to="/register">
            <el-button type="primary">注册</el-button>
          </router-link>
          <router-link to="/login">
            <el-button type="primary">登录</el-button>
          </router-link>
        </span>

        <el-dropdown v-show="this.Varall._isLogin" style="float: right" >
          <span class="el-dropdown-link">
            <el-button type="primary">
              {{this.Varall._uname}}<i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="handleCommand('/usercon')">用户信息</el-dropdown-item>
            <el-dropdown-item @click.native="checkOut()">注销</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

<!--        <span class="row-bg" style="float: right" >-->
<!--          <router-link to="/usercon">-->
<!--            <el-button type="primary">{{this.Varall._uname}}</el-button>-->
<!--          </router-link>-->
<!--        </span>-->
      </el-header>
    </el-container>
    <el-container>
      <router-view></router-view>
    </el-container>


  </div>
</template>

<style>

  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }

  .el-header {
    background-color: #EBEBEB;
    color: #333;
    line-height: 60px;
  }

  .el-aside {
    color: #EBEBEB;
  }

  #logo{
    color: darkseagreen;
    background-color: #EBEBEB;
    border: none;
  }

  .el-button--primary {
    color: #fff;
    background-color: darkseagreen;
    border-color: #42b983;
  }
</style>

<script>
  export default {
    data () {
      return {
        count: 0
      }
    },
    created() {
      if(sessionStorage.getItem("isLogin")!==""&&sessionStorage.getItem("isLogin")!=null){
        this.Varall._isLogin = sessionStorage.getItem("isLogin");
        this.Varall._uname =sessionStorage.getItem("uname");
        this.Varall._email =sessionStorage.getItem("email");
        this.Varall._password =sessionStorage.getItem("password");
        this.Varall._sex =sessionStorage.getItem("sex");
        this.Varall._age =sessionStorage.getItem("age");
        this.Varall._motto =sessionStorage.getItem("motto");
        this.Varall._currentmname =sessionStorage.getItem("currentmname");
        this.Varall._currenttname =sessionStorage.getItem("currenttname");
      }
    },
    methods: {
      load () {
        this.count += 1;
      },
      handleCommand(command) {
        this.$router.push(command);
      },
      checkOut(){
        sessionStorage.clear();
        this.Varall._uname=null;
        this.Varall._isLogin = false;
        this.Varall._email = null;
        this.Varall._password = null;
        this.Varall._sex = null;
        this.Varall._age = null;
        this.Varall._motto = null;
        if(this.$route.path==='/'){
          this.$router.go(0);
        }else{
          this.$router.push("/");
        }
      }
    }
  }
</script>
