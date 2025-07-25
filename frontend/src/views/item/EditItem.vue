<template>
    <div style="width:80%">
        <h2 style="margin-bottom:30px">编辑用户</h2>
        <el-form :inline="true" :model="form" label-width="100px">
            <el-form-item label="编号">
                <el-input v-model="form.itemName" disabled></el-input>
            </el-form-item>
            <el-form-item label="名称">
                <el-input v-model="form.name" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item label="单位">
                <el-input v-model="form.address" placeholder="请输入单位名称"></el-input>
            </el-form-item>
            <el-form-item label="电话">
                <el-input v-model="form.phone" placeholder="请输入联系电话"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
                <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
            </el-form-item>
        </el-form>

        <div style="text-align:center;margin-top:30px">
            <el-button type="primary" @click="save()" size="medium">提交</el-button>
        </div>

    </div>
</template>

<script>
import request from "@/utils/request"

export default {
    name:'EditItem',
    data(){
        return{
            form:{}
        }

    },
    created(){
        const id = this.$route.query.id
        request.get("/item/"+id).then(res =>{
            this.form = res.data
        })
    },
    methods:{
        save(){
            request.put('/item/update',this.form).then(res =>{
                if(res.code === '200'){
                    this.$notify.success('更新成功')
                    this.$router.push("/itemList")
                }else{
                    this.$notify.error(res.msg)
                }
            })
        }
    }
}


</script>