<template>
  <div class="coupon-web">
    <header class="header"> 
      <div class="header-box">
        <text class="back-btn iconfont" @click="onback">&#xe625;</text>
        <text class="title">领券中心</text> 
      </div>              
    </header>
    <webpri class="web" :src="couponUrl" :style="{height: couponWebHeight + 'px'}"></webpri>
    <!-- <text>{{console}}</text> -->
  </div>
</template>

<script>
  const domModule = weex.requireModule('dom');
  const navigator = weex.requireModule('navigator')
  // const webview = weex.requireModule('webview');

  import { store } from '../store.js'
  import { Utils } from 'weex-ui';

  export default {
    data () {
      return {
        couponUrl: '',        
        console: ''
      }
    },
    computed: {
      couponWebHeight() {
        return Utils.env.getPageHeight();
      }
    },
   
    mounted() {
      let bundleUrl = weex.config.bundleUrl
          bundleUrl = new String(bundleUrl);
      let couponUrl = bundleUrl.slice(bundleUrl.search(/\=/)+1) 
      couponUrl = decodeURIComponent(couponUrl)
      this.couponUrl = couponUrl
      // if (WXEnvironment.platform === 'android') {  
         
      //     this.couponUrl = couponUrl
          
      // } else if (WXEnvironment.platform === 'iOS') {  
          
      //     this.couponUrl = couponUrl
      // } else {  
          
      //     this.couponUrl = couponUrl
      // }  
         
    },
    methods: {
      onback() {
        navigator.pop({
          animated: "true"
        })
      }
    }
  }
</script>

<style scoped>
  .iconfont { 
    font-family: iconfont;
    /*font-weight: bold;*/   
  }

  .header {
    position: sticky;
  }

  .header-box {
    height: 100px;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    background-color: #f8f8f8;
    position: relative;
  }

  .back-btn {
    position: absolute;
    left: 30px;
    color: #FF6000;
    font-size: 40px;
    line-height: 100px;
  }

  .title {
    font-size: 36px;
    color: #fa513a;
  }

  .web {
    width: 750px;
  }
</style>