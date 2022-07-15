<template>
  <!-- 右侧机型条件筛选菜单 -->
  <van-popup v-model="show"
             position="right"
             @close="closeRightMenu"
             :style="{ width: '89%',height:'100%' }">
    <transition enter-active-class="animated fadeInRight"
                leave-active-class="animated flash">
      <div v-if="show"
           style="background-color: #f8f8f9;">
        <!-- 品牌筛选 -->
        <div class="option-brand">
          <p class="option-title">品牌</p>
          <van-button v-for="(brandList, index) in brandList"
                      :key="index"
                      @click="brandClick(index)"
                      :class="brandList.brand_id==brandOption?'hover':''"
                      class="option-button1"
                      type="default">{{ brandList.brand_name }}</van-button>
        </div>
        <!-- 处理器筛选 -->
        <div class="option-cpu">
          <p class="option-title">处理器</p>
          <van-button v-for="(cpuList, index) in cpuList"
                      :key="index"
                      @click="cpuClick(index)"
                      :class="cpuList.cpu_id==cpuOption?'hover':''"
                      class="option-button1"
                      type="default">{{ cpuList.cpu_name }}</van-button>
        </div>
        <!-- 功能特点(可多选)筛选 -->
        <div class="option-characteristic">
          <p class="option-title">功能特点(可多选)</p>
          <van-button v-for="(characteristicList, index) in characteristicList"
                      :key="index"
                      @click="characteristicClick(index)"
                      :class="characteristicList.characteristic_selection?'hover':''"
                      class="option-button2"
                      type="default">{{ characteristicList.characteristic_name }}</van-button>
        </div>
        <!-- 拍摄特点(可多选)筛选 -->
        <div class="option-shoot">
          <p class="option-title">拍摄特点(可多选)</p>
          <van-button v-for="(shootList, index) in shootList"
                      :key="index"
                      @click="shootClick(index)"
                      :class="shootList.shoot_selection?'hover':''"
                      class="option-button2"
                      type="default">{{ shootList.shoot_name }}</van-button>
        </div>
        <!-- 价格范围筛选 -->
        <div class="option-price">
          <p class="option-title">价格范围(元)</p>
          <van-button v-for="(priceList, index) in priceList"
                      :key="index"
                      @click="priceClick(index)"
                      :class="priceList.price_id==priceOption?'hover':''"
                      class="option-button2"
                      type="default">{{ priceList.price_name }}</van-button>
          <div style="width: 100%;text-align:center;margin: 4% 0 0 0;">
            <input type="number"
                   placeholder="最低价格(元)"
                   class="price-input"
                   v-model="modelOption[0].option_price_low">——
            <input type="number"
                   placeholder="最高价格(元)"
                   class="price-input"
                   v-model="modelOption[0].option_price_high">
          </div>
        </div>
        <!-- 重量(克)筛选 -->
        <div class="option-weight">
          <p class="option-title">重量(克)</p>
          <van-button v-for="(weightList, index) in weightList"
                      :key="index"
                      @click="weightClick(index)"
                      :class="weightList.weight_id==weightOption?'hover':''"
                      class="option-button2"
                      type="default">{{ weightList.weight_name }}</van-button>
        </div>
        <!-- 电池容量筛选 -->
        <div class="option-battery">
          <p class="option-title">电池容量</p>
          <van-button v-for="(batteryList, index) in batteryList"
                      :key="index"
                      @click="batteryClick(index)"
                      :class="batteryList.battery_id==batteryOption?'hover':''"
                      class="option-button2"
                      type="default">{{ batteryList.battery_name }}</van-button>
        </div>
        <!-- 屏幕类型筛选 -->
        <div class="option-screen">
          <p class="option-title">屏幕类型</p>
          <van-button v-for="(screenList, index) in screenList"
                      :key="index"
                      @click="screenClick(index)"
                      :class="screenList.screen_id==screenOption?'hover':''"
                      class="option-button1"
                      type="default">{{ screenList.screen_name }}</van-button>
        </div>
        <!-- 屏幕尺寸筛选 -->
        <div class="option-size">
          <p class="option-title">屏幕尺寸</p>
          <van-button v-for="(sizeList, index) in sizeList"
                      :key="index"
                      @click="sizeClick(index)"
                      :class="sizeList.size_id==sizeOption?'hover':''"
                      class="option-button2"
                      type="default">{{ sizeList.size_name }}</van-button>
        </div>
        <!-- 分辨率筛选 -->
        <div class="option-resolving_power">
          <p class="option-title">分辨率</p>
          <van-button v-for="(resolvingPowerList, index) in resolvingPowerList"
                      :key="index"
                      @click="resolvingPowerClick(index)"
                      :class="resolvingPowerList.resolvingPower_id==resolvingPowerOption?'hover':''"
                      class="option-button1"
                      type="default">{{ resolvingPowerList.resolvingPower_name }}P</van-button>
        </div>
        <!-- 底部操作菜单 -->
        <div class="option-button">
          <div class="option-button-detail"
               @click="clearOption()">重置</div>
          <div class="option-button-detail"
               @click="startSearch()"
               style="background-color: #2d8cf0;color: white">确认</div>
        </div>
      </div>
    </transition>
  </van-popup>
</template>

<script>
export default {
  name: 'rightMenu',
  props: {
    show: {
      type: Boolean,
      require: true
    }
  },
  data () {
    return {
      choiceIndex: -1,
      brandOption: -1,
      cpuOption: -1,
      priceOption: -1,
      weightOption: -1,
      batteryOption: -1,
      screenOption: -1,
      sizeOption: -1,
      resolvingPowerOption: -1,
      modelOptionBack: [],
      modelOption: [{
        option_cpu: '',
        option_brand: '',
        option_refresh_rate: '',
        option_5g: '',
        option_nfc: '',
        option_infrared: '',
        option_headset: '',
        option_typec: '',
        option_waterproof: '',
        option_ufs: '',
        option_hifi: '',
        option_ois: '',
        option_zoom: '',
        option_wide_angle: '',
        option_micro: '',
        option_tof: '',
        option_hfr: '',
        option_slow: '',
        option_price_low: '',
        option_price_high: '',
        option_weight_low: '',
        option_weight_high: '',
        option_battery_low: '',
        option_battery_high: '',
        option_screen: '',
        option_size_low: '',
        option_size_high: '',
        option_resolving_power: ''
      }],
      brandList: [{
        brand_id: 0,
        brand_name: '华为'
      }, {
        brand_id: 1,
        brand_name: '小米'
      }, {
        brand_id: 2,
        brand_name: 'OPPO'
      }, {
        brand_id: 3,
        brand_name: 'vivo'
      }, {
        brand_id: 4,
        brand_name: '苹果'
      }, {
        brand_id: 5,
        brand_name: '三星'
      }, {
        brand_id: 6,
        brand_name: '荣耀'
      }, {
        brand_id: 7,
        brand_name: '一加'
      }, {
        brand_id: 8,
        brand_name: '魅族'
      }, {
        brand_id: 9,
        brand_name: '美图'
      }, {
        brand_id: 10,
        brand_name: '努比亚'
      }, {
        brand_id: 11,
        brand_name: '联想'
      }],
      cpuList: [{
        cpu_id: 0,
        cpu_name: '骁龙865'
      }, {
        cpu_id: 1,
        cpu_name: '骁龙855'
      }, {
        cpu_id: 2,
        cpu_name: '骁龙845'
      }, {
        cpu_id: 3,
        cpu_name: '骁龙730'
      }, {
        cpu_id: 4,
        cpu_name: '骁龙710'
      }, {
        cpu_id: 5,
        cpu_name: '麒麟990'
      }, {
        cpu_id: 6,
        cpu_name: '麒麟980'
      }, {
        cpu_id: 7,
        cpu_name: '麒麟810'
      }, {
        cpu_id: 8,
        cpu_name: '麒麟710'
      }, {
        cpu_id: 9,
        cpu_name: '苹果'
      }, {
        cpu_id: 10,
        cpu_name: 'Exynos'
      }, {
        cpu_id: 11,
        cpu_name: '联发科'
      }],
      characteristicList: [{
        characteristic_id: 0,
        characteristic_selection: false,
        characteristic_name: '高刷新率'
      }, {
        characteristic_id: 1,
        characteristic_selection: false,
        characteristic_name: '5G网络'
      }, {
        characteristic_id: 2,
        characteristic_selection: false,
        characteristic_name: 'NFC'
      }, {
        characteristic_id: 3,
        characteristic_selection: false,
        characteristic_name: '红外遥控'
      }, {
        characteristic_id: 4,
        characteristic_selection: false,
        characteristic_name: '3.5mm耳机孔'
      }, {
        characteristic_id: 5,
        characteristic_selection: false,
        characteristic_name: 'USB Type-C'
      }, {
        characteristic_id: 6,
        characteristic_selection: false,
        characteristic_name: '防水防尘'
      }, {
        characteristic_id: 7,
        characteristic_selection: false,
        characteristic_name: 'UFS高速存储'
      }, {
        characteristic_id: 8,
        characteristic_selection: false,
        characteristic_name: '高保真HiFi音质'
      }],
      shootList: [{
        shoot_id: 0,
        shoot_selection: false,
        shoot_name: '光学防抖'
      }, {
        shoot_id: 1,
        shoot_selection: false,
        shoot_name: '光学变焦'
      }, {
        shoot_id: 2,
        shoot_selection: false,
        shoot_name: '超广角'
      }, {
        shoot_id: 3,
        shoot_selection: false,
        shoot_name: '微距'
      }, {
        shoot_id: 4,
        shoot_selection: false,
        shoot_name: 'TOF立体感应'
      }, {
        shoot_id: 5,
        shoot_selection: false,
        shoot_name: '4K/60帧录像'
      }, {
        shoot_id: 6,
        shoot_selection: false,
        shoot_name: '960帧慢动作'
      }],
      priceList: [{
        price_id: 0,
        price_low: '0',
        price_high: '999',
        price_name: '0-999'
      }, {
        price_id: 1,
        price_low: '1000',
        price_high: '1500',
        price_name: '1000-1500'
      }, {
        price_id: 2,
        price_low: '1500',
        price_high: '2000',
        price_name: '1500-2000'
      }, {
        price_id: 3,
        price_low: '2000',
        price_high: '3000',
        price_name: '2000-3000'
      }, {
        price_id: 4,
        price_low: '3000',
        price_high: '4000',
        price_name: '3000-4000'
      }, {
        price_id: 5,
        price_low: '4000',
        price_high: '5000',
        price_name: '4000-5000'
      }, {
        price_id: 6,
        price_low: '5000',
        price_high: '6000',
        price_name: '5000-6000'
      }, {
        price_id: 7,
        price_low: '6000',
        price_high: '20000',
        price_name: '6000-20000'
      }],
      weightList: [{
        weight_id: 0,
        weight_low: '0',
        weight_high: '160',
        weight_name: '160g以下(轻)'
      }, {
        weight_id: 1,
        weight_low: '160',
        weight_high: '180',
        weight_name: '160-180g'
      }, {
        weight_id: 2,
        weight_low: '180',
        weight_high: '200',
        weight_name: '180-200g'
      }, {
        weight_id: 3,
        weight_low: '200',
        weight_high: '',
        weight_name: '200g以上(重)'
      }],
      batteryList: [{
        battery_id: 0,
        battery_low: '0',
        battery_high: '3000',
        battery_name: '3000mAh以下'
      }, {
        battery_id: 1,
        battery_low: '3000',
        battery_high: '3500',
        battery_name: '3000-3500mAh'
      }, {
        battery_id: 2,
        battery_low: '3500',
        battery_high: '4000',
        battery_name: '3500-4000mAh'
      }, {
        battery_id: 3,
        battery_low: '4000',
        battery_high: '5000',
        battery_name: '4000-5000mAh'
      }, {
        battery_id: 4,
        battery_low: '5000',
        battery_high: '',
        battery_name: '5000mAh以上'
      }],
      screenList: [{
        screen_id: 0,
        screen_name: 'LCD'
      }, {
        screen_id: 1,
        screen_name: 'OLED'
      }],
      sizeList: [{
        size_id: 0,
        size_low: '0',
        size_high: '4.99',
        size_name: '4.99英寸以下'
      }, {
        size_id: 1,
        size_low: '5.0',
        size_high: '5.49',
        size_name: '5.0-5.49英寸'
      }, {
        size_id: 2,
        size_low: '5.5',
        size_high: '5.99',
        size_name: '5.5-5.99英寸'
      }, {
        size_id: 3,
        size_low: '6.0',
        size_high: '6.49',
        size_name: '6.0-6.49英寸'
      }, {
        size_id: 4,
        size_low: '6.5',
        size_high: '',
        size_name: '6.5英寸以上'
      }],
      resolvingPowerList: [{
        resolvingPower_id: 0,
        resolvingPower_name: '720'
      }, {
        resolvingPower_id: 1,
        resolvingPower_name: '1080'
      }, {
        resolvingPower_id: 2,
        resolvingPower_name: '1440'
      }]
    }
  },
  methods: {
    closeRightMenu () {
      this.$emit('closeRightMenuShow', false)
    },
    // 清空筛选条件
    clearOption () {
      this.choiceIndex = -1
      this.brandOption = -1
      this.cpuOption = -1
      this.priceOption = -1
      this.weightOption = -1
      this.batteryOption = -1
      this.screenOption = -1
      this.sizeOption = -1
      this.resolvingPowerOption = -1
      for (var i = 0; i < this.characteristicList.length; i++) {
        this.characteristicList[i].characteristic_selection = false
      }
      for (i = 0; i < this.shootList.length; i++) {
        this.shootList[i].shoot_selection = false
      }
      this.modelOption = JSON.parse(JSON.stringify(this.modelOptionBack))
      this.$notify({ type: 'success', message: '重置成功！' })
    },
    // 开始查询
    startSearch () {
      this.modelOption[0].option_refresh_rate = this.characteristicList[0].characteristic_selection ? 'T' : ''
      this.modelOption[0].option_5g = this.characteristicList[1].characteristic_selection ? 'T' : ''
      this.modelOption[0].option_nfc = this.characteristicList[2].characteristic_selection ? 'T' : ''
      this.modelOption[0].option_infrared = this.characteristicList[3].characteristic_selection ? 'T' : ''
      this.modelOption[0].option_headset = this.characteristicList[4].characteristic_selection ? 'T' : ''
      this.modelOption[0].option_typec = this.characteristicList[5].characteristic_selection ? 'T' : ''
      this.modelOption[0].option_waterproof = this.characteristicList[6].characteristic_selection ? 'T' : ''
      this.modelOption[0].option_ufs = this.characteristicList[7].characteristic_selection ? 'T' : ''
      this.modelOption[0].option_hifi = this.characteristicList[8].characteristic_selection ? 'T' : ''
      this.modelOption[0].option_ois = this.shootList[0].shoot_selection ? 'T' : ''
      this.modelOption[0].option_zoom = this.shootList[1].shoot_selection ? 'T' : ''
      this.modelOption[0].option_wide_angle = this.shootList[2].shoot_selection ? 'T' : ''
      this.modelOption[0].option_micro = this.shootList[3].shoot_selection ? 'T' : ''
      this.modelOption[0].option_tof = this.shootList[4].shoot_selection ? 'T' : ''
      this.modelOption[0].option_hfr = this.shootList[5].shoot_selection ? 'T' : ''
      this.modelOption[0].option_slow = this.shootList[6].shoot_selection ? 'T' : ''
      this.$emit('startSearch', this.modelOption)
    },
    // 点击品牌选项
    brandClick (index) {
      if (this.brandOption === index) {
        this.brandOption = -1
        this.modelOption[0].option_brand = ''
      } else {
        this.brandOption = index
        this.modelOption[0].option_brand = this.brandList[index].brand_name
      }
    },
    // 点击cpu选项
    cpuClick (index) {
      if (this.cpuOption === index) {
        this.cpuOption = -1
        this.modelOption[0].option_cpu = ''
      } else {
        this.cpuOption = index
        this.modelOption[0].option_cpu = this.cpuList[index].cpu_name
      }
    },
    // 点击功能特点选项
    characteristicClick (index) {
      this.characteristicList[index].characteristic_selection = !this.characteristicList[index].characteristic_selection
    },
    // 点击拍摄特点选项
    shootClick (index) {
      this.shootList[index].shoot_selection = !this.shootList[index].shoot_selection
    },
    // 点击价格范围选项
    priceClick (index) {
      if (this.priceOption === index) {
        this.modelOption[0].option_price_low = ''
        this.modelOption[0].option_price_high = ''
        this.priceOption = -1
      } else {
        this.modelOption[0].option_price_low = this.priceList[index].price_low
        this.modelOption[0].option_price_high = this.priceList[index].price_high
        this.priceOption = index
      }
    },
    // 点击重量选项
    weightClick (index) {
      if (this.weightOption === index) {
        this.modelOption[0].option_weight_low = ''
        this.modelOption[0].option_weight_high = ''
        this.weightOption = -1
      } else {
        this.modelOption[0].option_weight_low = this.weightList[index].weight_low
        this.modelOption[0].option_weight_high = this.weightList[index].weight_high
        this.weightOption = index
      }
    },
    // 点击电池容量选项
    batteryClick (index) {
      if (this.batteryOption === index) {
        this.modelOption[0].option_battery_low = ''
        this.modelOption[0].option_battery_high = ''
        this.batteryOption = -1
      } else {
        this.modelOption[0].option_battery_low = this.batteryList[index].battery_low
        this.modelOption[0].option_battery_high = this.batteryList[index].battery_high
        this.batteryOption = index
      }
    },
    // 点击电池容量选项
    screenClick (index) {
      if (this.screenOption === index) {
        this.modelOption[0].option_screen = ''
        this.screenOption = -1
      } else {
        this.modelOption[0].option_screen = this.screenList[index].screen_name
        this.screenOption = index
      }
    },
    // 点击屏幕尺寸选项
    sizeClick (index) {
      if (this.sizeOption === index) {
        this.modelOption[0].option_size_low = ''
        this.modelOption[0].option_size_high = ''
        this.sizeOption = -1
      } else {
        this.modelOption[0].option_size_low = this.sizeList[index].size_low
        this.modelOption[0].option_size_high = this.sizeList[index].size_high
        this.sizeOption = index
      }
    },
    // 点击屏幕分辨率选项
    resolvingPowerClick (index) {
      if (this.resolvingPowerOption === index) {
        this.modelOption[0].option_resolving_power = ''
        this.resolvingPowerOption = -1
      } else {
        this.modelOption[0].option_resolving_power = this.resolvingPowerList[index].resolvingPower_name
        this.resolvingPowerOption = index
      }
    }
  },
  mounted () {
  },
  created () {
    this.modelOptionBack = JSON.parse(JSON.stringify(this.modelOption))
  }
}
</script>

<style scoped>
/* 左侧菜单按钮 */
.menu-choice {
  width: 100%;
  height: 60px;
  /* border-bottom: 2px solid#dcdee26e; */
  text-align: left;
  padding: 0 0 0 20px;
  font-size: 20px;
  line-height: 60px;
  color: #515a6e;
}

.menu-choice.hover {
  background-color: #dcdee26e;
}
/* 机型筛选条件 */
.option-brand,
.option-cpu,
.option-characteristic,
.option-shoot,
.option-price,
.option-weight,
.option-battery,
.option-screen,
.option-size,
.option-resolving_power {
  width: 100%;
  min-height: 80px;
  height: auto;
  margin: 0 0 10px 0;
  padding: 0 0 20px 0;
  font-size: 16px;
  color: #515a6e;
  text-align: left;
  background-color: white;
}
.option-button {
  width: 100%;
  height: 50px;
  font-size: 18px;
  line-height: 50px;
}

.option-button-detail {
  float: left;
  width: 50%;
  height: 100%;
}

.option-title {
  font-size: 18px;
  color: #17233d;
  height: 50px;
  line-height: 50px;
  margin: 0 0 0 7%;
}

/* 第一类选择按钮 */
.option-button1 {
  height: 30px;
  line-height: 30px;
  text-align: center;
  width: 24%;
  font-size: 13px;
  margin: 4% 0 0 7%;
  border-radius: 6px;
  background-color: #f8f8f9;
}

/* 第二类选择按钮 */
.option-button2 {
  height: 30px;
  line-height: 30px;
  text-align: center;
  width: 38%;
  font-size: 13px;
  margin: 4% 4% 0 7%;
  border-radius: 6px;
  background-color: #f8f8f9;
}

.option-button1.hover,
.option-button2.hover {
  color: white;
  background-color: #2d8cf0;
}

.price-input {
  width: 120px;
  height: 30px;
  font-size: 13px;
  line-height: 30px;
  text-align: center;
  border-style: none;
  border-bottom-style: solid;
  border-bottom-width: thin;
  border-bottom-color: #17233d;
}
</style>
