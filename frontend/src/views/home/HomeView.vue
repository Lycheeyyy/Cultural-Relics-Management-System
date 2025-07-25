<template>

  <div>

    <div style="margin: 20px 0;">
      
      <el-select class="input" v-model="timeRange" placeholder="请选择" @change="load">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
    </div>
    <el-card>
      <div id="line" style="width: 100%;height: 400px;"></div>
    </el-card>
  </div>
</template>

<script>
import Cookies from 'js-cookie';
import request from '@/utils/request';
import * as echarts from 'echarts';

export default {
  data(){
    return{
      admin:Cookies.get('admin')?JSON.parse(Cookies.get('admin')):{},
      lineBox:null,
      timeRange:'week',
      options:[
        {label:'最近一周',value:'week'},
        {label:'最近一个月',value:'month'},
        {label:'最近两个月',value:'month2'},
        {label:'最近三个月',value:'month3'},
      ],
      chartOption:{
  title: {
    text: '文物出借统计'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: ['外借数量']
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  toolbox: {
    feature: {
      saveAsImage: {}
    }
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '外借数量',
      type: 'line',
      stack: 'Total',
      data: [120, 132, 101, 134, 90, 230, 210]
    },

  ]
}
    }
  },
  mounted(){
    this.load()
  },
  methods:{
    showMessage() {
      alert(this.$t('message.hello'))
    },
    load(){
      if(!this.lineBox){
        this.lineBox = echarts.init(document.getElementById('line'))
      }
    //  request.get('/borrow/lineCharts/'+this.timeRange).then(res =>{
    //  this.chartOption.xAxis.data = res.data.date
    //  this.chartOption.series[0].data = res.data.borrow
    //  //  this.chartOption.series[1].data = res.data.retur
//
      this.lineBox.setOption(this.chartOption);
    //  })
    }
  },
  created(){
    request.get('/admin/'+ this.admin.id).then(res => {
      this.admin = res.data
    })
  }
}
</script>

<style>
.item{
  margin: 10px 0;
}
</style>