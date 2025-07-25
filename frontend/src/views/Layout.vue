<template>
  <div>
        <!--头部区域-->
        <div style="height:60px;line-height: 60px;background-color: white; margin-bottom:2px ;display: flex;">


            <div style="width:300px">
                <span style="margin-left: 10px;font-size:24px">文物藏品管理系统</span>
            </div>
            <div style=" margin-left: auto;width: 100px;">
            <el-select v-model="$i18n.locale" @change="changeLanguage" >
              <el-option label="中文" value="zh"/>
              <el-option label="English" value="en"/>
            </el-select>
          </div>
            <div style="text-align: right;padding-right: 20px;padding-left: 20px;">
                <el-dropdown size="medium">
                    <span class="el-dropdown-link" style="cursor: pointer;">
                        {{ admin.username }} <i class="el-icon-arrow-down cl-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown" style="margin-top: -5px;">
                        <el-dropdown-item><div style="width: 50px;text-align: center;" @click="logout">退出</div></el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>

        </div>


        <!--侧边栏和主体-->
        <div style="display: flex">
            <!--侧边栏导航-->
            <div style="width: 200px; min-height: 100vh; overflow: hidden;margin-right: 2px;background-color: white;">
              <el-menu :default-active="$route.path" router class="el-menu-demo">
                <el-menu-item index="/" style="height">{{ $t('message.home') }}</el-menu-item>
            
                <el-submenu index="item">
                  <template #title>{{ $t('message.manageUser') }}</template>
                  <el-menu-item index="/addItem">{{ $t('message.addUser') }}</el-menu-item>
                  <el-menu-item index="/itemList">{{ $t('message.UserList') }}</el-menu-item>
                </el-submenu>
            
                <el-submenu index="admin">
                  <template #title>{{ $t('message.manageAdmin') }}</template>
                  <el-menu-item index="/addAdmin">{{ $t('message.addAdmin') }}</el-menu-item>
                  <el-menu-item index="/adminList">{{ $t('message.AdminList') }}</el-menu-item>
                </el-submenu>

                <el-submenu index="category">
                  <template #title>{{ $t('message.manageCategory') }}</template>
                  <el-menu-item index="/addCategory">{{ $t('message.CategoryList') }}</el-menu-item>
                  <el-menu-item index="/categoryList">{{ $t('message.addCategory') }}</el-menu-item>
                </el-submenu>

                <el-submenu index="ancient">
                  <template #title>{{ $t('message.manageitem') }}</template>
                  <el-menu-item index="/addAncient">{{ $t('message.addItem') }}</el-menu-item>
                  <el-menu-item index="/ancientList">{{ $t('message.ItemList') }}</el-menu-item>
                </el-submenu>

                <el-submenu index="borrow">
                  <template #title>{{ $t('message.manageBorrow') }}</template>
                  <el-menu-item index="/addBorrow">{{ $t('message.addBorrow') }}</el-menu-item>
                  <el-menu-item index="/borrowList">{{ $t('message.BorrowList') }}</el-menu-item>
                </el-submenu>


              </el-menu>

              
            </div>
    
            <!--主体数据-->
            <div style="flex:1; width:0; background-color:white; padding:10px;">
              <router-view/>
            </div>
        </div>
  </div>
</template>

<script>
import Cookies from 'js-cookie';

export default {
    name:"Layout",
    data(){
        return {
            admin:Cookies.get('admin')?JSON.parse(Cookies.get('admin')) : {}
        }
    },
    methods:{
        logout(){
            Cookies.remove('admin')
            this.$router.push('/login')
        },
        changeLanguage() {
             return this.$i18n?.locale || 'zh'
        }
    }
}
</script>

<style>

</style>