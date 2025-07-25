<template>
    <div style="width:80%">

        <h2 style="margin-bottom:30px">编辑外借藏品记录</h2>
        <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="100px">

            <el-form-item label="编号" prop="noNumber">
                <el-input v-model="form.noNumber" disabled  placeholder="请输入编号"></el-input>
            </el-form-item>

            <el-form-item label="名称" prop="itemName" >
                <el-input v-model="form.itemName"  placeholder="请输入名称"></el-input>
            </el-form-item>
            <el-form-item label="所属单位" prop="userAddress">
                <el-input v-model="form.userAddress"  placeholder="请输入所属单位"></el-input>
            </el-form-item>
            <el-form-item label="用户名" prop="userName">
                 <el-input v-model="form.userName"  placeholder="请输入用户名"></el-input>
            </el-form-item>
            
            <el-form-item label="联系电话" prop="userPhone">
                <el-input v-model="form.userPhone"  placeholder="请输入联系电话"></el-input>
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
    name:'EditBorrow',
    data(){
        return{
            form: {
              noNumber: '',      
              itemName: '',      
              userAddress: '',   
              userName: '',      
              userPhone: '', 
              categories: []
            },
            categories: [], 
            rules:{
                name:[
                    {required:true,message:'请输入文物名称',trigger:'blur'},
                ]
            }
        }

    },
    created(){
        const id = this.$route.query.id
        request.get("/borrow/"+id).then(res =>{
            this.form = res.data
            this.form.noNumber = res.data.itemNo 
        })
    },
    methods:{
        save(){
            request.put('/borrow/update',this.form).then(res =>{
                console.log("添加返回值：", res.data);
                if(res.code === '200'){
                    this.$notify.success('更新成功')
                    this.$router.push("/borrowList")
                }else{
                    this.$notify.error(res.msg)
                }
            })
        }
    }
}


</script>