<template>
  <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
<!--    <el-form-item label="话题名称" prop="tname">-->
<!--      <el-input v-model="ruleForm.tname"></el-input>-->
<!--    </el-form-item>-->
    <el-form-item label="话题内容" prop="intro">
      <el-input type="textarea" v-model="ruleForm.intro"></el-input>
    </el-form-item>
    <el-form-item label="隐私" prop="tanonymous">
      <el-radio-group v-model="ruleForm.tanonymous">
        <el-radio :label="0">匿名</el-radio>
        <el-radio :label="1">公开</el-radio>
      </el-radio-group>
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
    name: "createtopic",
    data() {
      return {
        ruleForm: {
          email:'',
          password:'',
          mname:'',
          intro:'',
          tanonymous:'',
          ttime:''
        },
        rules: {
          intro: [
            { required: true, message: '请输入话题内容', trigger: 'blur' },
            { min: 2, max: 50, message: '长度在 2 到 50 个字', trigger: 'blur' }
          ]
        }
      };
    },
    methods: {
        getdate() {
          var gettime
          let yy = new Date().getFullYear();
          let mm = new Date().getMonth()+1;
          let dd = new Date().getDate();
          let hh = new Date().getHours();
          let mf = new Date().getMinutes()<10 ? '0'+new Date().getMinutes() : new Date().getMinutes();
          let ss = new Date().getSeconds()<10 ? '0'+new Date().getSeconds() : new Date().getSeconds();
          gettime = yy+'-'+mm+'-'+ dd +' '+hh+':'+mf+':'+ss;
          return gettime
        },
      submitForm(formName) {
        const _this=this;
        let mname=this.$route.query.mname;
        _this.ruleForm.ttime = this.getdate();
        this.$refs[formName].validate((valid) => {
          if (valid) {
            // alert('submit!');

            if(_this.Varall._isLogin){
            // alert(_this.Varall._email);
            // alert(_this.Varall._password);

            const params = new URLSearchParams();
            params.append('intro',_this.ruleForm.intro);
            params.append('email', _this.Varall._email);
            params.append('password', _this.Varall._password);
            params.append('tanonymous', _this.ruleForm.tanonymous);
            params.append('ttime', _this.ruleForm.ttime);
            params.append('mname', mname);

            alert(params)

            this.$axios.post('http://localhost:8080/topic/createTopic',params).then(function (resp) {
              console.log(resp)
              if(resp.data.code=="499"){
                alert("创建话题失败：身份验证错误！原因可能是密码错误或邮箱输入错误")
              }else if(resp.data.code=="420"){
                alert("创建话题失败：同名话题已存在！")
              }else{
                alert("话题创建成功！")
                // _this.$router.replace({
                //   path:'/module',
                //   name:'module'
                // })
                // 当val为-1时回到上一页
                _this.$router.go(-1);
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
