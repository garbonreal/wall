<template>
  <div>
    <el-container width="100%">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <el-menu router="" style="height: 600px">
          <el-menu-item @click="$router.push('/infoShow')" style="height: 15%">
            <i class="el-icon-user"></i>个人信息
          </el-menu-item>
          <el-menu-item @click="$router.push('/changePassword')" style="height: 15%">
            <i class="el-icon-edit"></i>修改密码
          </el-menu-item>
          <el-menu-item @click="$router.push('/changeEmail')" style="height: 15%">
            <i class="el-icon-phone-outline"></i>个人邮箱
          </el-menu-item>
          <el-menu-item @click="$router.push('/personFavorite')" style="height: 15%">
            <i class="el-icon-star-off"></i>我的收藏
          </el-menu-item>
          <el-menu-item @click="$router.push('/personModule')" style="height: 15%">
            <i class="el-icon-takeaway-box"></i>我的板块
          </el-menu-item>
          <el-menu-item @click="$router.push('/personTopic')" style="height: 15%">
            <i class="el-icon-chat-line-round"></i>我的话题
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <router-view></router-view>
      </el-container>
    </el-container>
  </div>
</template>

<script>
  import axios from "axios";

  export default {
    name: "Usercon",
    created() {
      if(this.Varall._isLogin === false){
        this.$router.push("/");
      }
    },
    data() {
      var checkEmail = (rule, value, callback) => {
        const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
        if (!value) {
          return callback(new Error('邮箱不能为空'))
        }
        setTimeout(() => {
          if (mailReg.test(value)) {
            callback()
          } else {
            callback(new Error('请输入正确的邮箱格式'))
          }
        }, 100)
      };
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };
      return {
        ruleForm: {
          email: '',
          pass: ''
        },
        rules: {
          email: [
            { validator: checkEmail, trigger: 'blur' }
          ],
          pass: [
            { validator: validatePass, trigger: 'blur' }
          ]
        }
      };
    },
    methods: {
      submitForm(formName) {
        var _this = this;
        function lllogin() {
          const params = new URLSearchParams();
          params.append('email', _this.ruleForm.email);
          params.append('password', _this.ruleForm.pass);
          axios.post('http://localhost:8080/user/login', params)
            .then(function (resp) {
              if(resp.data.code==="401"){
                alert("用户不存在！");
              }else if(resp.data.code==="402"){
                alert("密码错误！");
              }else if(resp.data.code==="200"){
                sessionStorage.setItem("uname",resp.data.body.uname);
                sessionStorage.setItem("isLogin",resp.data.body.isLogin);
                sessionStorage.setItem("email",resp.data.body.email);
                sessionStorage.setItem("password",resp.data.body.password);
                sessionStorage.setItem("sex",resp.data.body.sex);
                sessionStorage.setItem("age",resp.data.body.age);
                sessionStorage.setItem("motto",resp.data.body.motto);
                _this.Varall._uname=resp.data.body.uname;
                _this.Varall._isLogin = true;
                _this.Varall._email = resp.data.body.email;
                _this.Varall._password = _this.ruleForm.pass;
                _this.Varall._sex = resp.data.body.sex;
                _this.Varall._age = resp.data.body.age;
                _this.Varall._motto = resp.data.body.motto;
                _this.$router.push("/");
              }else{
                alert("未知错误！");
              }
            })
        }
        this.$refs[formName].validate((valid) => {
          if (valid) {
            lllogin();
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
    }
  }
</script>

<style scoped>

</style>
