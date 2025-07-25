<template>
    <div style="width:80%">
        <h2 style="margin-bottom:30px">新增外借藏品记录</h2>
        <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="100px">

            <el-form-item label="编号" prop="noNumber">
                <el-select v-model="form.noNumber" clearable filterable placeholder="请选择" @change="selAncient">
                    <el-option
                        v-for="item in ancients"
                        :key="item.id"
                        :label="item.noNumber"
                        :value="item.noNumber"
                    ></el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="名称" prop="itemName" disabled>
                <el-input v-model="form.itemName" disabled placeholder="请输入名称"></el-input>
            </el-form-item>
            <el-form-item label="所属单位" prop="userAddress">
                <el-input v-model="form.userAddress" disabled placeholder="请输入所属单位"></el-input>
            </el-form-item>
            <el-form-item label="用户名" prop="userName">
                <el-select v-model="form.userName" filterable placeholder="请选择" @change="selUser">
                    <el-option
                        v-for="item in users"
                        :key="item.name"
                        :label="item.name"
                        :value="item.name"
                    ></el-option>
                </el-select>
            </el-form-item>
            
            <el-form-item label="联系电话" prop="userPhone">
                <el-input v-model="form.userPhone" disabled placeholder="请输入联系电话"></el-input>
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
    name:'AddBorrow',
    data(){
        return{
            form:{
                categories:[],
                phone:'',
            },
            ancients:[],
            users:[],
            categories:[],
                cascaderProps: {
                value: 'id',    // 建议使用id作为value
                label: 'name',   // 显示name字段
                children: 'children',
                checkStrictly: true // 是否可选中任意一级
            },
            rules:{
                userName:[
                    {required:true,message:'请输入分类名称',trigger:'blur'},
                ],
                noNumber:[
                    {required:true,message:'请输入分类名称',trigger:'blur'},
                ]
            }

        }
    },
    created(){
        request.get('/ancient/list').then(res =>{
            this.ancients = res.data
        })
        request.get('/item/list').then(res =>{
            this.users = res.data
        })
    },
    methods:{
        handleChange(value){
            console.log(value)
        },
        save(){
            this.$refs['ruleForm'].validate((valid)=>{
                if(valid){
                    request.post('/borrow/save',this.form).then(res =>{
                        if(res.code === '200'){
                            this.$notify.success('新增成功')
                            this.$refs['ruleForm'].resetFields()
                        }else{
                            this.$notify.error(res.msg)
                        }
                    })
                }
            })
            
        },
        selAncient(){
            const ancient = this.ancients.find( v => v.noNumber === this.form.noNumber)
            this.form.itemName = ancient.name
            this.form.userAddress = ancient.address
            this.form.itemNo = ancient.noNumber

        },
        selUser(){
            const user = this.users.find( v => v.name === this.form.userName)
           this.form.userPhone = user.phone

                        
        }
          
                    
    }
}


</script>