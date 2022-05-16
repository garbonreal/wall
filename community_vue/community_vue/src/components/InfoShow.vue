<template>
    <el-container style="margin: auto; position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);">
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" style="align-content: center">
        <el-form-item label="昵称" prop="uname">
          <el-input type="text" v-model="ruleForm.uname"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="ruleForm.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">女</el-radio>
            <el-radio :label="0">保密</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input  type="text" v-model="ruleForm.age"></el-input>
        </el-form-item>
        <el-form-item label="个人简介" prop="motto">
            <el-input  type="text" v-model="ruleForm.motto"></el-input>
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
    name: "InfoShow",
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
      var checkMotto = (rule, value, callback) => {
        const $Motto = /[/\\()<>{}[\] ]/gi;
        setTimeout(() => {
          if ($Motto.test(value)) {
            callback(new Error('不能包含特殊字符'))
          } else {
            callback()
          }
        }, 100)
      };
      var checkSex = (rule, value, callback) => {
        if(value!=null){
          callback()
        }else{
          callback(new Error('请选择'))
        }
      };
      var checkAge = (rule, value, callback) => {
        const age = /^([1-9][0-9]*)$/;
        setTimeout(() => {
          if (age.test(value)) {
            if(parseInt(value)>=0&&parseInt(value)<=120){
              callback()
            }else{
              callback(new Error("请输入正确年龄"))
            }
          } else {
            callback(new Error('请正确填写'))
          }
        }, 100)
      };
      return {
        ruleForm: {
          email: '',
          motto: '',
          uname: '',
          sex: '',
          age: ''
        },
        rules: {
          email: [
            { validator: checkEmail, trigger: 'blur' }
          ],
          motto: [
            { validator: checkMotto, trigger: 'blur' }
          ],
          uname: [
            { validator: checkName, trigger: 'blur' }
          ],
          sex: [
            { validator: checkSex, trigger: 'blur' }
          ],
          age: [
            { validator: checkAge, trigger: 'blur' }
          ]
        }
      };
    },
    created() {
      this.email = this.Varall._email;
      this.motto = this.Varall._motto;
      this.uname = this.Varall._uname;
      this.sex = this.Varall._sex;
      this.age = this.Varall._age;
      this.ruleForm.email = this.email;
      this.ruleForm.motto = this.motto;
      this.ruleForm.uname = this.uname;
      this.ruleForm.sex = this.sex;
      if(this.age===-1||this.age==="-1"){
        this.ruleForm.age = null;
      }else{
        this.ruleForm.age = this.age;
      }
    },
    methods: {
      submitForm(formName) {
        var _this = this;
        function lllogin() {
          const params = new URLSearchParams();
          params.append('email', _this.Varall._email);
          params.append('password',  _this.Varall._password);
          params.append('uname', _this.ruleForm.uname);
          params.append('sex', _this.ruleForm.sex);
          if(_this.ruleForm.age==null)_this.ruleForm.age=-1;
          params.append('age', _this.ruleForm.age);
          params.append('motto', _this.ruleForm.motto);
          axios.post('http://localhost:8080/user/modifybasic', params)
            .then(function (resp) {
              if(resp.data.code==="499"){
                alert("身份验证错误！");
              }else if(resp.data.code==="200"){
                sessionStorage.setItem("uname",resp.data.body.uname);
                sessionStorage.setItem("isLogin",true);
                sessionStorage.setItem("email",resp.data.body.email);
                sessionStorage.setItem("sex",resp.data.body.sex);
                sessionStorage.setItem("age",resp.data.body.age);
                sessionStorage.setItem("motto",resp.data.body.motto);
                _this.Varall._uname=resp.data.body.uname;
                _this.Varall._isLogin = true;
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
