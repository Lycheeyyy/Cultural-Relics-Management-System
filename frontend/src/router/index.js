import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/home/HomeView.vue'
import Layout from '@/views/Layout.vue'
import Cookies from 'js-cookie'

Vue.use(VueRouter)

const routes = [

  //  ====登录====
  {
    path: '/login',
    name: 'Login',
    component: ()=>import('@/views/login/Login.vue'),
  },
  //  ====主页====
  {
    path: '/',
    name: 'Layout',
    component:Layout,
    redirect:'/home',
    children:[
      {
        path: 'home',
        name: 'HomeView',
        component: () => import('../views/home/HomeView.vue'),
      },
        //  ====item====
        {
          path: 'itemList',
          name: 'Item',
          component: () => import('../views/item/Item.vue'),
        },
        {     
          path: 'additem',
          name: 'AddItem',
          component: () => import('../views/item/AddItem.vue'),
        },
        {
          path: 'edititem',
          name: 'EditItem',
          component: () => import('../views/item/EditItem.vue'),
        },
        //  ====admin====
        {
          path: 'adminList',
          name: 'Admin',
          component: () => import('../views/admin/List.vue'),
        },
        {
          path: 'addAdmin',
          name: 'AddAdmin',
          component: () => import('../views/admin/Add.vue'),
        },
        {
          path: 'editAdmin',
          name: 'EditAdmin',
          component: () => import('../views/admin/Edit.vue'),
        },
        //  ====category===
        {
          path: 'categoryList',
          name: 'Category',
          component: () => import('../views/category/List.vue'),
        },
        {
          path: 'addCategory',
          name: 'AddCategory',
          component: () => import('../views/category/Add.vue'),
        },
        {
          path: 'editCategory',
          name: 'EditCategory',
          component: () => import('../views/category/Edit.vue'),
        },

        //  ====ancient===
        {
          path: 'ancientList',
          name: 'Ancient',
          component: () => import('../views/ancient/List.vue'),
        },
        {
          path: 'addAncient',
          name: 'AddAncient',
          component: () => import('../views/ancient/Add.vue'),
        },
        {
          path: 'editAncient',
          name: 'EditAncient',
          component: () => import('../views/ancient/Edit.vue'),
        },

        //  ====borrow===
        {
          path: 'borrowList',
          name: 'Borrow',
          component: () => import('../views/borrow/List.vue'),
        },
        {
          path: 'addBorrow',
          name: 'AddBorrow',
          component: () => import('../views/borrow/Add.vue'),
        },
        {
          path: 'editBorrow',
          name: 'EditBorrow',
          component: () => import('../views/borrow/Edit.vue'),
        },
    ]
  },

  {
    path:"*",
    component:() => import('@/views/404.vue')
  },
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


router.beforeEach((to ,from,next)=>{
  if(to.path === '/login') next()
    const admin = Cookies.get("admin")
  if(!admin && to.path !== '/login') return next("/login")
  next()
})

export default router
