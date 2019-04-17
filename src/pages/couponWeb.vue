<template>
  <div class="coupon-web">
    <!-- <text>{{console}}</text> -->
    <header class="header"> 
      <div class="header-box">
        <text class="back-btn iconfont" @click="onback">&#xe625;</text>
        <text class="back-btn close-btn iconfont" @click="onclose" v-if="withBack">&#xe626;</text>
        <text class="title">商品详情</text> 
      </div>              
    </header>
    <webpri ref="webview" 
            class="web" 
            :src="couponUrl" 
            :style="{height: couponWebHeight + 'px'}"
            @pagefinish="onpagefinish" ></webpri>   
  </div>
</template>

<script>
  const domModule = weex.requireModule('dom');
  const navigator = weex.requireModule('navigator')
  const webview = weex.requireModule('webview-pri');

  import { store } from '../store.js'
  import { Utils } from 'weex-ui';

  export default {
    data () {
      return {
        console: '',
        couponUrl: '',
        withBack: false,        
        canGoBack: false
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

      let urlString = bundleUrl.slice(bundleUrl.indexOf('?')+1) 
      
      let optionArr = urlString.split('&')
      
      let couponUrl = ''
      if(optionArr[0].split('=')[0] === 'webUrl') {
        couponUrl = decodeURIComponent(optionArr[0].split('=')[1])
        this.couponUrl = couponUrl

        if(optionArr[1].split('=')[1] == 'true') {
          this.withBack = true
        }else {
          this.withBack = false
        }
      }else {
        couponUrl = decodeURIComponent(optionArr[1].split('=')[1])
        this.couponUrl = couponUrl

        if(optionArr[0].split('=')[1] == 'true') {
          this.withBack = true
        }else {
          this.withBack = false
        }
      }
     

    },
    methods: {
      onpagefinish(e){
        this.canGoBack = e.canGoBack
      },
      onback(){
        if(!this.withBack || !this.canGoBack) {
          this.onclose()
          return;
        }
         
        webview.goBack(this.$refs.webview)
      },
      onclose() {
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
    /* height: 150px; */
    flex-direction: row;
    align-items: center;
    justify-content: center;
    background-color: #f8f8f8;
    position: relative;
    padding-top: 80px;
    padding-bottom: 20px;
  }

  .back-btn {
    position: absolute;
    left: 30px;
    top: 50px;
    color: #FF6000;
    font-size: 40px;
    line-height: 100px;
  }

  .close-btn {
    font-size: 36px;
    left: 100px;
  }

  .title {
    font-size: 36px;
    color: #fa513a;
  }

  .web {
    width: 750px;
  }
</style>