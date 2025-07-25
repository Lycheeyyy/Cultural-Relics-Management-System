<template>
    <div>
  
      <div style="margin-bottom:20px">
        <el-input style="width: 240px" placeholder="请输入名称" v-model="params.name"></el-input>
        <el-input style="width: 240px;margin-left: 5px;" placeholder="请输入单位" v-model="params.time"></el-input>
        <el-button style="margin-left: 5px" type="primary" @click="load()">
          <i class="el-icon-search"></i> 搜索
        </el-button>
        <el-button style="margin-left: 5px" type="warning" @click="reset()">
          <i class="el-icon-refresh"></i> 重置
        </el-button>
      </div>
  
  
      <el-table :data="tableData" stripe>
              <el-table-column prop="id" label="序号"></el-table-column>
              <el-table-column prop="itemName" label="编号"></el-table-column>  
              <el-table-column prop="name" label="名称"></el-table-column>
              <el-table-column prop="address" label="单位"></el-table-column>
              <el-table-column prop="phone" label="电话"></el-table-column>
              <el-table-column prop="email" label="邮箱"></el-table-column>
              <el-table-column prop="createtime" label="创建时间"></el-table-column>
              <el-table-column prop="updatetime" label="更新时间"></el-table-column>

              <el-table-column label="操作">
                <template v-slot="scope">
                    <el-button type="primary" style="width: 50px;" @click="$router.push('/edititem?id='+scope.row.id)">编辑</el-button>
                    <el-popconfirm
                        style="margin-left : 5px"
                        title="您确定删除这行数据吗?"
                        @confirm="del(scope.row.id)"
                    >
                        <el-button type="danger" slot="reference" >删除</el-button>
                    </el-popconfirm>
                </template>
              </el-table-column>
      </el-table>
  
      <!--分页-->
      <div style="margin-top:20px">
        <el-pagination
            background
            :current-page="params.pageNum"
            :page-size="params.pageSize"
            layout="prev,pager,next"
            @current-change="handleCurrentChange"
            :total="total">
      </el-pagination>
      </div>
  
    </div>
</template>
  
<script>
import request from '@/utils/request'

export default {
  name: 'Item',
  data(){
        return {
            tableData:[],
            total:0,
            params:{
              pageNum:1,
              pageSize:10,
              name:'',
              address:''
            }
        }
    },
    created(){
        this.load()
    },
    methods:{
        load(){
            //fetch('http://localhost:8080/item/list').then(res => res.json()).then(res =>{
            //    console.log(res)
            //    this.tableData = res
            //})

            request.get('/item/page',{
              params:this.params
            }).then(res => {
              if(res.code === '200'){
                this.tableData = res.data.list
                this.total = res.data.total
              }

            })
        },
        reset(){
          this.params ={
            pageNum:1,
            pageSize:10,
            name:'',
            address:''
          }
          this.load()
        },

        handleCurrentChange(pageNum){
          this.params.pageNum = pageNum;
          this.load()
        },
        del(id){
            request.delete("/item/delete/" + id).then(res => {
                if(res.code === '200'){
                    this.$notify.success('删除成功')
                    this.load()
                }else{
                    this.$notify.error(res.msg)
                }
            })
        }
      }
}
</script>

<style scoped>

</style>
  