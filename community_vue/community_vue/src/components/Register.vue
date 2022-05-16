<template>
  <div>
    <el-container>
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" style="align-content: center">
        <el-form-item label="邮箱" prop="email">
          <el-input type="text" v-model="ruleForm.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pass">
          <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="name">
          <el-input  type="text" v-model="ruleForm.name" autocomplete="off"></el-input>
        </el-form-item>
<!--        <el-form-item label="验证码" prop="code">-->
<!--          <el-col :span="14">-->
<!--            <el-input  type="text" v-model="ruleForm.code" autocomplete="off"></el-input>-->
<!--          </el-col>-->
<!--          <el-col :span="1"><p/></el-col>-->
<!--          <el-col :span="8" >-->
<!--            <el-button  type="primary" v-show="show" @click="sendEmail('ruleForm')">发送验证码</el-button>-->
<!--            <el-button  type="primary" disabled v-show="!show">稍后{{count}}</el-button>-->
<!--          </el-col>-->
<!--        </el-form-item>-->
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-container>
  </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: "Register",
      data() {

        function isvalidUsername(str) {
          const reg = /^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]|[a-zA-Z0-9])*$/;
          return reg.test(str);
        }
        function isvalidCode(str) {
          const reg = /^[0-9]{4}$/;
          return reg.test(str);
        }
        var checkName = (rule, value, callback) => {
          if (value === "") {
            callback(new Error("请输入用户名"));
          } else if (!isvalidUsername(value)) {
            callback(
              new Error(
                "用户名要求不能有特殊字符！"
              )
            );
          }else{
            callback()
          }
        };
        // 不需要验证码
        // var checkCode = (rule, value, callback) => {
        //   if (value === "") {
        //     callback(new Error("请输入验证码"));
        //   } else if (!isvalidCode(value)) {
        //     callback(
        //       new Error(
        //         "请输入正确验证码！"
        //       )
        //     );
        //   }else{
        //     callback()
        //   }
        // };
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
        var validatePass2 = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请再次输入密码'));
          } else if (value !== this.ruleForm.pass) {
            callback(new Error('两次输入密码不一致!'));
          } else {
            callback();
          }
        };
        return {
          ruleForm: {
            email: '',
            pass: '',
            checkPass: '',
            name: '',
            code: ''
          },
          show: true,
          count: null,
          timer: null,
          rules: {
            email: [
              { validator: checkEmail, trigger: 'blur' }
            ],
            pass: [
              { validator: validatePass, trigger: 'blur' }
            ],
            checkPass: [
              { validator: validatePass2, trigger: 'blur' }
            ],
            name: [
              { validator: checkName, trigger: 'blur' }
            ],
            // code: [
            //   { validator: checkCode, trigger: 'blur' }
            // ]
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
            params.append('uname', _this.ruleForm.name);
            params.append('code', _this.ruleForm.code);
            axios.post('http://localhost:8080/user/register', params)
              .then(function (resp) {
                if(resp.data.code==="403"){
                  alert("邮箱已注册！");
                }else if(resp.data.code==="666"){
                  alert("验证码错误！");
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
                  alert("注册成功！");
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
        },
        resetForm(formName) {
          this.$refs[formName].resetFields();
        },
        sendEmail(formName){

          var _this = this;
          function getCode(){

            const params = new URLSearchParams();
            params.append('email', _this.ruleForm.email);
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
          if (this.ruleForm.email==="") {
            alert("邮箱不能为空");
            return
          }
          setTimeout(() => {
            if (mailReg.test(this.ruleForm.email)) {
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
