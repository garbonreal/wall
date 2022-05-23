<template>
  <div style="margin: auto; position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);">
    <el-container>
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" style="align-content: center">
        <el-form-item label="邮箱" prop="email">
          <el-input type="text" v-model="ruleForm.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pass">
          <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
        </el-form-item>
      </el-form>
    </el-container>
  </div>
</template>

<script>
  import axios from "axios";

  export default {
    name: "Login",
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
          axios.post('http://1.116.57.190:8080/user/login', params)
            .then(function (resp) {
              if(resp.data.code==="401"){
                alert("用户不存在！");
              }else if(resp.data.code==="402"){
                alert("密码错误！");
              }else if(resp.data.code==="200"){
                sessionStorage.setItem("uname",resp.data.body.uname);
                sessionStorage.setItem("isLogin",true);
                sessionStorage.setItem("email",resp.data.body.email);
                sessionStorage.setItem("password",_this.ruleForm.pass);
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
