<template>
    <div>
      <div style="margin-bottom:20px">
        <el-input style="width: 240px" placeholder="请输入文物名称" v-model="params.itemName"></el-input>
        <el-input style="width: 240px ;margin-left: 5px;" placeholder="请输入文物编码" v-model="params.itemNo"></el-input>
        <el-input style="width: 240px ;margin-left: 5px;" placeholder="请输入用户名称" v-model="params.userName"></el-input>
        <el-button style="margin-left: 5px" type="primary" @click="load()">
          <i class="el-icon-search"></i> 搜索
        </el-button>
        <el-button style="margin-left: 5px" type="warning" @click="reset()">
          <i class="el-icon-refresh"></i> 重置
        </el-button>
      </div>
  
  
      <el-table :data="tableData" stripe row-key="id" default-expand-all>
              <el-table-column prop="id" label="序号"></el-table-column>
              <el-table-column prop="itemName" label="文物名称"></el-table-column>
              <el-table-column prop="itemNo" label="文物编码" width="200"></el-table-column>
              <el-table-column prop="userAddress" label="用户单位"></el-table-column>
              <el-table-column prop="userName" label="用户名"></el-table-column>
              <el-table-column prop="userPhone" label="用户手机号"></el-table-column>
              <el-table-column prop="createtime" label="创建时间"></el-table-column>
              <el-table-column prop="updatetime" label="更新时间"></el-table-column>

              <el-table-column label="操作" width="280">
                <template v-slot="scope">

                    <!--<el-button type="primary" @click="$router.push('/editBorrow?id='+scope.row.id)">编辑</el-button>-->
                    <el-button type="primary" @click="handleReturn(scope.row.id)">已归还</el-button>
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
import Cookies from 'js-cookie'

export default {
  name: 'BorrowList',
  data(){
        return {
          admin:Cookies.get('admin')?JSON.parse(Cookies.get('admin')):{},
            tableData:[],
            total:0,
            params:{
              pageNum:1,
              pageSize:10,
              itemName:'',
              itemNo:'',
              userName:''
            },
            rules:{
              name:[
                {required:true,message:"请输入文物名称",trigger:'blur'},
              ]
            },
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

            request.get('/borrow/page',{
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
            itemName:'',
            itemNo:'',
            userName:''
          }
          this.load()
        },

        handleCurrentChange(pageNum){
          this.params.pageNum = pageNum;
          this.load()
        },
        del(id){
            request.delete("/borrow/delete/" + id).then(res => {
                if(res.code === '200'){
                    this.$notify.success('删除成功')
                    this.load()
                }else{
                    this.$notify.error(res.msg)
                }
            })
        },
         async handleReturn(id) {
            try {
              await this.$confirm('确认文物已归还？状态将变更为"库存中"', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              });
              await request.put(`/borrow/return/${id}`);
              this.$message.success('归还成功');

              // 刷新表格数据或删除对应行
              this.load(); 
            } catch (error) {
              if (error !== 'cancel') {
                this.$message.error('操作失败: ' + (error.response?.data?.message || error.message));
              }
            }
          }
      }
}
</script>

<style scoped>

</style>
  