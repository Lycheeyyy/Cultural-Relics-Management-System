<template>
    <div style="width:80%">

        <h2 style="margin-bottom:30px">编辑文物</h2>
        <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="100px">
            <el-form-item label="名称" prop="name">
                <el-input v-model="form.name" placeholder="请输入名称"></el-input>
            </el-form-item>
            <el-form-item label="描述" prop="description">
                <el-input v-model="form.description" placeholder="请输入备注"></el-input>
            </el-form-item>

            <el-form-item label="朝代" prop="dynasty">
                <el-input v-model="form.dynasty" placeholder="请输入朝代"></el-input>
            </el-form-item>
            <el-form-item label="来源" prop="address">
                <el-input v-model="form.address" placeholder="请输入来源"></el-input>
            </el-form-item>
            <el-form-item label="分类" prop="category">
               <el-cascader
               style="width: 200px;"
               :props="{
                value:'name',
                label:'name'
               }"
               v-model="form.categories"
               :options="categories"
               @change="handleChange"></el-cascader>
            </el-form-item>
            <el-form-item label="编码" prop="noNumber">
                <el-input v-model="form.noNumber" placeholder="请输入编码"></el-input>
            </el-form-item>
            <el-form-item label="状态" prop="state">
                  <el-radio v-model="form.state" label="已展出">已展出</el-radio>
                  <el-radio v-model="form.state" label="修复中">修复中</el-radio>
                  <el-radio v-model="form.state" label="已外借">已外借</el-radio>
                  <el-radio v-model="form.state" label="库存中">库存中</el-radio>
            </el-form-item>
            <el-form-item label="照片" prop="photo">
                <el-input v-model="form.photo" placeholder="请输入照片"></el-input>
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
    name:'EditAncient',
    data(){
        return{
            radio:'1',
            form: {
                  name: '',
                  description: '',
                  dynasty: '',
                  address: '',
                  noNumber: '',
                  state: '',
                  photo: '',
                  categories:[]
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
        request.get('/category/tree').then(res =>{
            this.categories = res.data
        })
        const id = this.$route.query.id
        request.get("/ancient/"+id).then(res =>{
            this.form = res.data
            if(this.form.category){
                this.form.categories = this.form.category.split('>')
            }
        })
    },
    methods:{
        handleChange(val){
            console.log(val)
        },
        save(){
            request.put('/ancient/update',this.form).then(res =>{
                console.log("添加返回值：", res.data);
                if(res.code === '200'){
                    this.$notify.success('更新成功')
                    this.$router.push("/ancientList")
                }else{
                    this.$notify.error(res.msg)
                }
            })
        }
    }
}


</script>