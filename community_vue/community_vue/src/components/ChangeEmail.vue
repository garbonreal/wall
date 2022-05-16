<template>
  <el-container style="margin: auto; position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);">
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" style="align-content: center">
      <el-form-item label="旧邮箱">
        <el-input disabled type="text" v-model="ruleForm.oldemail"></el-input>
      </el-form-item>
      <el-form-item label="新邮箱" prop="newemail">
        <el-input type="text" v-model="ruleForm.newemail"></el-input>
      </el-form-item>
      <el-form-item label="验证码" prop="code">
        <el-col :span="14">
          <el-input  type="text" v-model="ruleForm.code" autocomplete="off"></el-input>
        </el-col>
        <el-col :span="1"><p/></el-col>
        <el-col :span="8" >
          <el-button  type="primary" v-show="show" @click="sendEmail('ruleForm')">发送验证码</el-button>
          <el-button  type="primary" disabled v-show="!show">稍后{{count}}</el-button>
        </el-col>
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
    name: "ChangeEmail",
    created() {
      this.ruleForm.oldemail = this.Varall._email;
    },
    data() {
      function isvalidCode(str) {
        const reg = /^[0-9]{4}$/;
        return reg.test(str);
      }
      var checkCode = (rule, value, callback) => {
        if (value === "") {
          callback(new Error("请输入验证码"));
        } else if (!isvalidCode(value)) {
          callback(
            new Error(
              "请输入正确验证码！"
            )
          );
        }else{
          callback()
        }
      };
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
      return {
        ruleForm: {
          oldemail: '',
          newemail: '',
          code: ''
        },
        show: true,
        count: null,
        timer: null,
        rules: {
          newemail: [
            { validator: checkEmail, trigger: 'blur' }
          ],
          code: [
            { validator: checkCode, trigger: 'blur' }
          ]
        }
      };
    },
    methods: {
      submitForm(formName) {
        var _this = this;
        function lllogin() {
          const params = new URLSearchParams();
          params.append('oldemail', _this.Varall._email);
          params.append('newemail', _this.ruleForm.newemail);
          params.append('password', _this.Varall._password);
          params.append('code', _this.ruleForm.code);
          axios.post('http://localhost:8080/user/changeEmail', params)
            .then(function (resp) {
              if(resp.data.code==="499"){
                alert("身份验证错误！");
              }else if(resp.data.code==="666"){
                alert("验证码错误！");
              }else if(resp.data.code==="403"){
                alert("邮箱已被注册！");
              }else if(resp.data.code==="200"){
                sessionStorage.setItem("uname",resp.data.body.uname);
                sessionStorage.setItem("isLogin",true);
                sessionStorage.setItem("email",resp.data.body.email);
                sessionStorage.setItem("sex",resp.data.body.sex);
                sessionStorage.setItem("age",resp.data.body.age);
                sessionStorage.setItem("motto",resp.data.body.motto);
                _this.oldemail = resp.data.body.email;
                _this.Varall._uname=resp.data.body.uname;
                _this.Varall._isLogin = true;
                _this.Varall._email = resp.data.body.email;
                _this.Varall._sex = resp.data.body.sex;
                _this.Varall._age = resp.data.body.age;
                _this.Varall._motto = resp.data.body.motto;
                alert("修改成功！");
                _this.$router.go(0);
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
      },
      sendEmail(formName){
        var _this = this;
        function getCode(){

          const params = new URLSearchParams();
          params.append('email', _this.ruleForm.newemail);
          axios.post('http://localhost:8080/sms/sendEmailCode', params)
            .then(function (resp) {
              const TIME_COUNT = 30;
              if (!_this.timer) {
                _this.count = TIME_COUNT;
                _this.show = false;
                _this.timer = setInterval(() => {
                  if (_this.count > 0 && _this.count <= TIME_COUNT) {
                    _this.count--;
                  } else {
                    _this.show = true;
                    clearInterval(_this.timer);
                    _this.timer = null;
                  }
                }, 1000);
              }
            })
        }

        const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
        if (this.ruleForm.newemail==="") {
          alert("邮箱不能为空");
          return
        }
        setTimeout(() => {
          if (mailReg.test(this.ruleForm.newemail)) {
            getCode();
          } else {
            alert("请输入正确的邮箱格式");
            return
          }
        }, 100)
      }
    }
  }
</script>

<style scoped>

</style>
