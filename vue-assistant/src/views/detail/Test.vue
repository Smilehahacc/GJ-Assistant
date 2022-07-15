<template>
  <div id="app">
    <div class="detail-content"
         v-html="getAppointStr1('操作系统','<br/>','上市日期：2019年11月01日<br/>发布会时间：国内：2019年09月26日； 国外：2019年09月19日<br/>手机类型：5G手机 4G手机 3G手机 智能手机 平板手机 音乐手机 游戏手机<br/>屏幕类型：全面屏 瀑布屏<br/>出厂系统内核：Android 10<br/>操作系统：EMUI 10<br/>CPU型号：海思 麒麟 990 5G<br/>机身颜色：亮黑色 星河银 罗兰紫 翡冷翠<br/>解锁方式：屏幕指纹识别 面部识别<br/>',true)">
    </div>
    <div class="detail-content"
         v-html="getAppointStr1('出厂系统内核','<br/>','上市日期：2019年11月01日<br/>发布会时间：国内：2019年09月26日； 国外：2019年09月19日<br/>手机类型：5G手机 4G手机 3G手机 智能手机 平板手机 音乐手机 游戏手机<br/>屏幕类型：全面屏 瀑布屏<br/>出厂系统内核：Android 10<br/>操作系统：EMUI 10<br/>CPU型号：海思 麒麟 990 5G<br/>机身颜色：亮黑色 星河银 罗兰紫 翡冷翠<br/>解锁方式：屏幕指纹识别 面部识别<br/>',true)">
    </div>
  </div>
</template>

<script>
export default {
  name: 'hello',
  data () {
    return {
      articleUrl: ''
    }
  },
  mounted () {

  },
  methods: {
    // 从某指定字符串开始，截取由其开始到最后的字符串
    getAppointStr (strBegin, strOriginal, isNeed) {
      try {
        var index = strOriginal.lastIndexOf(strBegin)
        if (isNeed) {
          strOriginal = strOriginal.substring(index, strOriginal.length)
        } else {
          strOriginal = strOriginal.substring(index + strBegin.length + 1, strOriginal.length)
        }
        return strOriginal
      } catch (e) {
        return '无'
      }
    },
    // 从某指定字符串开始，截取由其开始到指定字符串结束的字符串
    getAppointStr1 (strBegin, strEnd, strOriginal, isNeed) {
      try {
        var index = strOriginal.indexOf(strBegin)
        var strNew = this.getAppointStr(strBegin, strOriginal, true)
        var index1 = strNew.indexOf(strEnd)
        if (isNeed) {
          strOriginal = strOriginal.substring(index, index + strBegin.length + index1 - 1)
        } else {
          strOriginal = strOriginal.substring(index + strBegin.length + 1, index + strBegin.length + index1 - 1)
        }
        return strOriginal
      } catch (e) {
        return '无'
      }
    }
  },
  created () {

  }
}

</script>
<style scoped>
</style>
