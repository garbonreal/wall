<template>
  <el-container style="margin: auto; position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);">
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" style="align-content: center">
      <el-form-item label="旧密码" prop="oldpassword">
        <el-input type="password" v-model="ruleForm.oldpassword"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newpassword">
        <el-input type="password" v-model="ruleForm.newpassword"></el-input>
      </el-form-item>
      <el-form-item label="验证密码" prop="checkpassword">
        <el-input type="password" v-model="ruleForm.checkpassword"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">更改</el-button>
      </el-form-item>
    </el-form>
  </el-container>
</template>

<script>
  import axios from "axios";

  export default {
    name: "ChangePassword",
    data() {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm.checkpassword !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.newpassword) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        ruleForm: {
          oldpassword: '',
          newpassword: '',
          checkpassword: ''
        },
        rules: {
          oldpassword: [
            { validator: validatePass, trigger: 'blur' }
          ],
          newpassword: [
            { validator: validatePass, trigger: 'blur' }
          ],
          checkpassword: [
            { validator: validatePass2, trigger: 'blur' }
          ]
        }
      };
    },
    methods: {
      submitForm(formName) {
        var _this = this;
        function lllogin() {
          const params = new URLSearchParams();
          params.append('email', _this.Varall._email);
          params.append('newpassword', _this.ruleForm.newpassword);
          params.append('oldpassword', _this.ruleForm.oldpassword);
          axios.post('http://localhost:8080/user/changePassword', params)
            .then(function (resp) {
              if(resp.data.code==="499"){
                alert("密码错误！");
              }else if(resp.data.code==="200"){
                sessionStorage.setItem("uname",resp.data.body.uname);
                sessionStorage.setItem("isLogin",true);
                sessionStorage.setItem("password",_this.ruleForm.newpassword);
                sessionStorage.setItem("email",resp.data.body.email);
                sessionStorage.setItem("sex",resp.data.body.sex);
                sessionStorage.setItem("age",resp.data.body.age);
                sessionStorage.setItem("motto",resp.data.body.motto);
                _this.Varall._uname=resp.data.body.uname;
                _this.Varall._isLogin = true;
                _this.Varall._password = _this.ruleForm.newpassword;
                _this.Varall._email = resp.data.body.email;
                _this.Varall._sex = resp.data.body.sex;
                _this.Varall._age = resp.data.body.age;
                _this.Varall._motto = resp.data.body.motto;
                alert("修改成功！");
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
