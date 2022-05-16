<template>
  <div id="app">
<!--    <el-row>-->
<!--      <el-button>默认按钮</el-button>-->
<!--      <el-button type="primary">主要按钮</el-button>-->
<!--      <el-button type="success">成功按钮</el-button>-->
<!--      <el-button type="info">信息按钮</el-button>-->
<!--      <el-button type="warning">警告按钮</el-button>-->
<!--      <el-button type="danger">危险按钮</el-button>-->
<!--    </el-row>-->
    <el-container style="height: 500px; border: 1px solid #eee">
<!--      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">-->
<!--&lt;!&ndash;        <div>&ndash;&gt;-->
<!--&lt;!&ndash;          <ul class="list">&ndash;&gt;-->
<!--&lt;!&ndash;            <li  v-for="(item,index) in listData" :key="index" :v-if="listData">{{item.album_title}}</li>&ndash;&gt;-->
<!--&lt;!&ndash;          </ul>&ndash;&gt;-->
<!--&lt;!&ndash;        </div>&ndash;&gt;-->
<!--        <el-menu router="">-->
<!--&lt;!&ndash;          <el-submenu v-for="(item,index) in $router.options.routes" :key="index" :index="Index+' '">&ndash;&gt;-->
<!--&lt;!&ndash;            <template slot="title">&ndash;&gt;-->
<!--&lt;!&ndash;              <i class="el-icon-setting"></i>{{item.name}}&ndash;&gt;-->
<!--&lt;!&ndash;            </template>&ndash;&gt;-->
<!--&lt;!&ndash;            <el-menu-item v-for="(item2,index2) in item.children" :key="index" :index="item2.path">&ndash;&gt;-->
<!--&lt;!&ndash;              <template slot="title">&ndash;&gt;-->
<!--&lt;!&ndash;                <i class="el-icon-setting"></i>{{item2.name}}&ndash;&gt;-->
<!--&lt;!&ndash;              </template>&ndash;&gt;-->
<!--&lt;!&ndash;            </el-menu-item>&ndash;&gt;-->
<!--&lt;!&ndash;          </el-submenu>&ndash;&gt;-->
<!--          <el-menu-item @click="$router.push('/module')">-->
<!--              <i class="el-icon-setting"></i>主题-->
<!--          </el-menu-item>-->
<!--          <el-menu-item @click="$router.push('/module_topic')">-->
<!--            <i class="el-icon-setting"></i>话题-->
<!--          </el-menu-item>-->
<!--        </el-menu>-->
<!--      </el-aside>-->

      <el-container>
        <router-view></router-view>

      </el-container>
    </el-container>


<!--    <ul class="infinite-list" v-infinite-scroll="load" style="overflow:auto">-->
<!--      <li v-for="i in count" class="infinite-list-item">{{ i }}</li>-->
<!--    </ul>-->

  </div>
</template>

<style>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }

  .el-aside {
    color: #333;
  }
</style>

<script>
  export default {
    data () {
      return {
        count: 0
      }
    },
    methods: {
      load () {
        this.count += 1
      }
    }
  }
</script>
