import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Index from "../components/Index";
import module from "../components/module";
import module_topic from "../components/module_topic";
import topic_comment from "../components/topic_comment";
import Register from "../components/Register";
import Login from "../components/Login";
import Usercon from "../components/Usercon";
import InfoShow from "../components/InfoShow";
import PersonModule from "../components/PersonModule";
import PersonTopic from "../components/PersonTopic";
import ChangeEmail from "../components/ChangeEmail";
import ChangePassword from "../components/ChangePassword";
import createtopic from "../components/CreateTopic";
import createcomment from "../components/CreateComment";
import createmodule from "../components/createmodule";
import PersonFavorite from "../components/PersonFavorite";

Vue.use(Router)
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

export default new Router({
  mode: 'hash',
  routes: [
    {
      path: '/',
      name: 'category',
      component:Index,
      meta:{
        title: "万能墙"
      },
      redirect: '/module',
      children:[
          {
            path: '/module',
            name: 'module',
            component:module
          },
          {
            path: '/module_topic',
            name: 'module_topic',
            component:module_topic
          },
          {
            path: '/topic_comment',
            name: 'topic_comment',
            component:topic_comment
          },
          {
            path: 'createmodule',
            name:'createmodule,',
            component: createmodule
          },
          {
            path:'/createtopic',
            name:'createtopic',
            component:createtopic
          },
          {
            path: '/createcomment',
            name:'createcomment',
            component:createcomment
          }
      ]
    },
    {
      path: '/register',
      name: 'register',
      component: Register,
      meta:{
        title: "万能墙 注册"
      }
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
      meta:{
        title: "万能墙 登录"
      }
    },
    {
      path: '/usercon',
      name: 'usercon',
      component: Usercon,
      meta:{
        title: "万能墙 用户界面"
      },
      redirect: '/infoShow',
      children:[
        {
          path: '/infoShow',
          name: 'infoShow',
          component:InfoShow,
        },
        {
          path: '/changePassword',
          name: 'changePassword',
          component:ChangePassword
        },
        {
          path: '/changeEmail',
          name: 'changeEmail',
          component:ChangeEmail
        },
        {
          path: '/personModule',
          name: 'personModule',
          component:PersonModule
        },
        {
          path: '/personTopic',
          name: 'personTopic',
          component:PersonTopic
        },
        {
          path: '/personFavorite',
          name: 'personFavorite',
          component:PersonFavorite
        }
      ]
    }
  ]
})
