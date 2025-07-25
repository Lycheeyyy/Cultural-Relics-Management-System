<template>
    <div style="width:80%">
        <h2 style="margin-bottom:30px">添加用户</h2>
        <el-form :inline="true" :model="form" label-width="100px" :rules="rules" ref="ruleForm">
            <el-form-item label="名称">
                <el-input v-model="form.name" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item label="单位">
                <el-input v-model="form.address" placeholder="请输入单位名称"></el-input>
            </el-form-item>
            <el-form-item label="电话" prop="phone">
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
    name:'AddItem',
    data(){
        const checkPhone = (rule,value,callback) =>{
            if(!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)){
                callback(new Error('请输入合法的手机号'));
            }
            callback()
        };
        return{
            form:{},
            rules:{
                name:[
                    {required:true,message:'请输入姓名',trigger:'blur'}
                ],
                phone:[
                    {validator:checkPhone,trigger:'blur'}
                ]
            }

        }
    },
    methods:{
        save(){
            request.post('/item/save',this.form).then(res =>{
                if(res.code === '200'){
                    this.$notify.success('新增成功')
                    this.form={};
                }else{
                    this.$notify.error(res.msg)
                }
            })
        }
    }
}


</script>