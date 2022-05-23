<template>
  <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
    <el-form-item label="板块名称" prop="mname">
      <el-input v-model="ruleForm.mname"></el-input>
    </el-form-item>
    <!--    <el-form-item label="邮箱" prop="email">-->
    <!--      <el-input v-model="ruleForm.email"></el-input>-->
    <!--    </el-form-item>-->
    <!--    <el-form-item label="密码" prop="password">-->
    <!--      <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>-->
    <!--    </el-form-item>-->

    <!--    <el-form-item label="板块名称" prop="mname">-->
    <!--      <el-input v-model="ruleForm.mname"></el-input>-->
    <!--    </el-form-item>-->

    <!--  <el-form-item label="板块" prop="region">-->
    <!--    <el-select v-model="ruleForm.region" placeholder="请选择活动区域">-->
    <!--      <el-option label="区域一" value="shanghai"></el-option>-->
    <!--      <el-option label="区域二" value="beijing"></el-option>-->
    <!--    </el-select>-->
    <!--  </el-form-item>-->
    <el-form-item label="板块简介" prop="intro">
      <el-input type="textarea" v-model="ruleForm.intro"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
      <!--      <el-button @click="test()">test</el-button>-->
    </el-form-item>
  </el-form>
</template>

<script>
    export default {
        name: "createmodule",
      data() {
        return {
          ruleForm: {
            email:'',
            password:'',
            mname:'',
            intro:''
          },
          rules: {
            mname: [
              { required: true, message: '请输入话题名称', trigger: 'blur' },
              { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
            ],
            intro: [
              { required: true, message: '请输入话题简介', trigger: 'blur' },
              { min: 2, max: 50, message: '长度在 2 到 50 个字', trigger: 'blur' }
            ]
          }
        };
      },
      methods: {
        submitForm(formName) {
          const _this=this;
          // let mname=this.$route.query.mname;
          this.$refs[formName].validate((valid) => {
            if (valid) {
              // alert('submit!');

              if(_this.Varall._isLogin){
                // alert(_this.Varall._email);
                // alert(_this.Varall._password);

                const params = new URLSearchParams();
                params.append('mname',_this.ruleForm.mname);
                params.append('intro',_this.ruleForm.intro);
                params.append('email', _this.Varall._email);
                params.append('password', _this.Varall._password);
                this.$axios.post('http://1.116.57.190:8080/module/createModule',params).then(function (resp) {
                  console.log(resp)
                  if(resp.data.code=="499"){
                    alert("创建主题失败：身份验证错误！原因可能是密码错误或邮箱输入错误")
                  }else if(resp.data.code=="410"){
                    alert("创建主题失败：主题已存在")
                  }else{
                    alert("主题创建成功！")
                    _this.$router.replace({
                      path:'/module',
                      name:'module'
                    })
                  }
                })
              }else{
                alert("请登录！！！")
              }
            } else {
              // console.log('error submit!!');
              return false;
            }
          });
        },
        resetForm(formName) {
          this.$refs[formName].resetFields();
        },
        test(){
          console.log(this.ruleForm)
        }
      }
    }
</script>

<style scoped>

</style>
