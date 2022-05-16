<template>
  <div>
  <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
    <el-form-item label="评论内容" prop="content">
      <el-input type="textarea" v-model="ruleForm.content"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>
  </div>
</template>

<script>
  export default {
    name: "createcomment",
    data() {
      return {
        ruleForm: {
          content:'',
          email:'',
          password:'',
          tname:''
        },
        rules: {
          content: [
            { required: true, message: '请输入评论', trigger: 'blur' },
            { min: 1, max: 1000, message: '长度在 1 到 1000 个字', trigger: 'blur' }
          ]
        }
      };
    },
    methods: {
      submitForm(formName) {
        const _this=this;
        let tname=this.$route.query.tname;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            // alert('submit!');

            if(_this.Varall._isLogin){
              // alert(_this.Varall._email);
              // alert(_this.Varall._password);

              const params = new URLSearchParams();
              params.append('tname',tname);
              params.append('content',_this.ruleForm.content);
              params.append('email', _this.Varall._email);
              params.append('password', _this.Varall._password);
              params.append('mname', _this.Varall._currentmname);
              this.$axios.post('http://localhost:8080/comment/createComment',params).then(function (resp) {
                console.log(resp)
                if(resp.data.code=="499"){
                  alert("评论失败：身份验证错误！原因可能是密码错误或邮箱输入错误")
                }else if(resp.data.code=="421"){
                  alert("评论失败：请检查话题是否存在")
                }
                else{
                  alert("评论发表成功！")
                  _this.$router.go(-1);
                  // location.reload();
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
      }
    }
  }
</script>

<style scoped>

</style>
