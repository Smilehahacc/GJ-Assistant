<template>
  <transition
    enter-active-class="animated slideInRight">
    <div id='app'>
      <!-- 导航栏 -->
      <van-sticky>
        <van-nav-bar
          title="站内热点"
          left-text="返回"
          left-arrow
          style="height: 100%;line-height: 60px"
          @click-left="back()">
        </van-nav-bar>
      </van-sticky>

      <!-- 数据展示图表 -->
      <div class="chart">
        <chart id="chart1"
          theme="light"
          :option="polar1"
          autoresize>

        </chart>
        <van-button
          icon="replay"
          size="small"
          color="linear-gradient(to right, #4bb0ff, #6149f6)"
          :loading="loading1"
          @click="refreshTop(1)"
          class="refresh-button">
          刷新</van-button>
      </div>
      <div class="chart">
        <chart id="chart2"
          theme="default"
          :option="polar2"
          autoresize>
        </chart>
        <van-button
          icon="replay"
          size="small"
          color="linear-gradient(to right, #4bb0ff, #6149f6)"
          :loading="loading2"
          @click="refreshTop(2)"
          class="refresh-button">
          刷新</van-button>
      </div>
      <div class="chart">
        <chart id="chart3"
          theme="default"
          :option="polar3"
          autoresize>
        </chart>
        <van-button
          icon="replay"
          size="small"
          color="linear-gradient(to right, #4bb0ff, #6149f6)"
          :loading="loading3"
          @click="refreshTop(3)"
          class="refresh-button">
          刷新</van-button>
      </div>
      <div class="chart">
        <chart id="chart4"
          theme="default"
          :option="polar4"
          autoresize>
        </chart>
        <van-button
          icon="replay"
          size="small"
          color="linear-gradient(to right, #4bb0ff, #6149f6)"
          :loading="loading4"
          @click="refreshTop(4)"
          class="refresh-button">
          刷新</van-button>
      </div>
      <div class="tips">
        <p>*平台收集数据，仅供参考</p>
      </div>
    </div>
  </transition>
</template>
<script>
import ECharts from 'vue-echarts'
export default {
  name: 'popular',
  components: {},
  data () {
    return {
      loading1: false,
      loading2: false,
      loading3: false,
      loading4: false,
      myChart: '',
      polar1: {
        title: {
          text: '站内热门手机品牌排行',
          subtext: '实时数据',
          x: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '品牌：{b} <br/>{a} : {c} ({d}%)'
        },
        series: [
          {
            name: '检索次数',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: [],
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      },
      polar2: {
        title: {
          text: '站内热门处理器排行',
          subtext: '实时数据',
          x: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '处理器：{b} <br/>{a} : {c} ({d}%)'
        },
        series: [
          {
            name: '检索次数',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: [],
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      },
      polar3: {
        color: ['#409EFF'],
        title: {
          text: '站内热门机型卖点排行',
          subtext: '实时数据',
          x: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: [],
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '检索次数',
            type: 'bar',
            barWidth: '60%',
            data: []
          }
        ]
      },
      polar4: {
        color: ['#F56C6C'],
        title: {
          text: '站内热门相机卖点排行',
          subtext: '实时数据',
          x: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: [],
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '检索次数',
            type: 'bar',
            barWidth: '60%',
            data: []
          }
        ]
      }
    }
  },
  methods: {
    back () {
      this.$router.go(-1)
    },
    // 获取用户喜欢的品牌
    getTopBrand () {
      this.loading1 = true
      this.$axios
        .post('/getTopBrand', {})
        .then((data) => {
          if (data.data.length !== 0) {
            this.getChartData1(data.data)
          }
          this.loading1 = false
        })
        .catch((error) => {
          console.log(error)
          this.loading1 = false
          this.$message.error(
            '获取用户最喜欢的品牌失败' + error.status + ',' + error.statusText
          )
        })
    },
    // 获取用户喜欢的cpu
    getTopCpu () {
      this.loading2 = true
      this.$axios
        .post('/getTopCpu', {})
        .then((data) => {
          if (data.data.length !== 0) {
            this.getChartData2(data.data)
          }
          this.loading2 = false
        })
        .catch((error) => {
          console.log(error)
          this.loading2 = false
          this.$message.error(
            '获取用户最喜欢的cpu失败' + error.status + ',' + error.statusText
          )
        })
    },
    // 获取用户喜欢的机型特点
    getTopCharacter () {
      this.loading3 = true
      this.$axios
        .post('/getTopCharacter', {})
        .then((data) => {
          if (data.data.length !== 0) {
            this.getChartData3(data.data)
          }
          this.loading3 = false
        })
        .catch((error) => {
          console.log(error)
          this.loading3 = false
          this.$message.error(
            '获取用户最喜欢的机型特点失败' +
              error.status +
              ',' +
              error.statusText
          )
        })
    },
    // 获取用户喜欢的相机特点
    getTopCamera () {
      this.loading4 = true
      this.$axios
        .post('/getTopCamera', {})
        .then((data) => {
          if (data.data.length !== 0) {
            this.getChartData4(data.data)
          }
          this.loading4 = false
        })
        .catch((error) => {
          console.log(error)
          this.loading4 = false
          this.$message.error(
            '获取用户最喜欢的相机特点失败' +
              error.status +
              ',' +
              error.statusText
          )
        })
    },
    // 数据转换，显示图表
    getChartData1 (list) {
      let data = []
      let other = 0
      for (var i = 0; i < list.length; i++) {
        if (i < 8) {
          data.push({ name: list[i].option, value: list[i].count })
        } else {
          other += list[i].count
        }
      }
      data.push({ name: '其他品牌', value: other })
      this.polar1.series[0].data = data
    },
    getChartData2 (list) {
      let data = []
      let other = 0
      for (var i = 0; i < list.length; i++) {
        if (i < 8) {
          data.push({ name: list[i].option, value: list[i].count })
        } else {
          other += list[i].count
        }
      }
      data.push({ name: '其他处理器', value: other })
      this.polar2.series[0].data = data
    },
    getChartData3 (list) {
      let data = []
      let dataTitle = []
      data.push(list.option_refresh_rate)
      data.push(list.option_refresh_5g)
      data.push(list.option_nfc)
      data.push(list.option_infrared)
      data.push(list.option_headset)
      data.push(list.option_typec)
      data.push(list.option_waterproof)
      data.push(list.option_ufs)
      data.push(list.option_hifi)
      dataTitle.push('高刷新率')
      dataTitle.push('5G网络')
      dataTitle.push('NFC')
      dataTitle.push('红外遥控')
      dataTitle.push('耳机孔')
      dataTitle.push('USB Type-C')
      dataTitle.push('防水防尘')
      dataTitle.push('UFS高速存储')
      dataTitle.push('高保真HiFi音质')
      this.polar3.series[0].data = data
      this.polar3.xAxis[0].data = dataTitle
    },
    getChartData4 (list) {
      let data = []
      let dataTitle = []
      data.push(list.option_ois)
      data.push(list.option_zoom)
      data.push(list.option_wide_angle)
      data.push(list.option_micro)
      data.push(list.option_tof)
      data.push(list.option_hfr)
      data.push(list.option_slow)
      dataTitle.push('光学防抖')
      dataTitle.push('光学变焦')
      dataTitle.push('超广角')
      dataTitle.push('微距')
      dataTitle.push('TOF立体感应')
      dataTitle.push('4K/60帧录像')
      dataTitle.push('960帧慢动作')
      this.polar4.series[0].data = data
      this.polar4.xAxis[0].data = dataTitle
    },
    initChart (chart, polar) {
      let myChart = ECharts.init(document.getElementById(chart))
      myChart.setOption(polar, true)
    },
    // 点击刷新按钮
    refreshTop (val) {
      if (val === 1) {
        this.refreshLoading('chart1', 0)
        this.getTopBrand()
        this.refreshLoading('chart1', 1)
      } else if (val === 2) {
        this.refreshLoading('chart2', 0)
        this.getTopCpu()
        this.refreshLoading('chart1', 1)
      } else if (val === 3) {
        this.refreshLoading('chart3', 0)
        this.getTopCharacter()
        this.refreshLoading('chart1', 1)
      } else {
        this.refreshLoading('chart4', 0)
        this.getTopCamera()
        this.refreshLoading('chart4', 1)
      }
      this.$notify({ type: 'success', message: '刷新完成！' })
    },
    // 启动刷新动画
    refreshLoading (chart, action) {
      let myChart = ECharts.init(document.getElementById(chart))
      if (action === 0) {
        myChart.showLoading()
      } else if (action === 1) {
        myChart.hideLoading()
      }
    }
  },
  // 监听路由变化，实时更新参数
  watch: {
    polar1: {
      handler (newVal, oldVal) {
        if (this.myChart) {
          if (newVal) {
            this.myChart.setOption(newVal)
          } else {
            this.myChart.setOption(oldVal)
          }
        } else {
          this.initChart('chart1', this.polar1)
        }
      },
      deep: true
    },
    polar2: {
      handler (newVal, oldVal) {
        if (this.myChart) {
          if (newVal) {
            this.myChart.setOption(newVal)
          } else {
            this.myChart.setOption(oldVal)
          }
        } else {
          this.initChart('chart2', this.polar2)
        }
      },
      deep: true
    },
    polar3: {
      handler (newVal, oldVal) {
        if (this.myChart) {
          if (newVal) {
            this.myChart.setOption(newVal)
          } else {
            this.myChart.setOption(oldVal)
          }
        } else {
          this.initChart('chart3', this.polar3)
        }
      },
      deep: true
    },
    polar4: {
      handler (newVal, oldVal) {
        if (this.myChart) {
          if (newVal) {
            this.myChart.setOption(newVal)
          } else {
            this.myChart.setOption(oldVal)
          }
        } else {
          this.initChart('chart4', this.polar4)
        }
      },
      deep: true
    }
  },
  activated () {
    this.getTopBrand()
    this.getTopCpu()
    this.getTopCharacter()
    this.getTopCamera()
  },
  created () {}
}
</script>
<style scoped>
.title {
  font-size: 20px;
  color: #303133;
}
.chart {
  float: left;
  width: 100%;
  height: 400px;
  margin: 20px 0 60px 0;
}
/* 刷新按钮 */
.refresh-button {
  float: right;
  margin: 0 4% 30px 0;
}
.tips {
  float: left;
  height: 40px;
  color: #c0c4cc;
  margin: 20px 0 0 8%;
  font-size: 16px;
}
</style>
