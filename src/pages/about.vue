<template>  
  <scoller class="scoller">
    <header class="header">
      <text class="back-btn iconfont" @click="onback">&#xe625;</text>
      <div class="header-warpper size">
        
        <div class="logo">
          <image :src="imgSrc.logo" style="width:200px;height:200px"></image>
        </div>
        <div class="version">
          <text class="version-txt">当前版本：</text>
          <text class="version-txt">v1.0.0</text>
        </div>   
      </div>
    </header>
    <div class="description">
      <text class="description-txt">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;淘折商城，是一款基于电子商城导购、优惠券获取、优惠券搜索及购物推荐的一款综合类APP，其中包括限时抢购、精品推荐、母婴商品推荐等导购板块，让购物更加便捷实惠。</text>
    </div>
    <div class="contact">
      <div class="contact-us">
        <text class="contact-txt">联系我们</text>
        <text class="contact-txt">QQ：&nbsp;&nbsp;504435165（申请时请填写淘折意见反馈）</text>
      </div>
      <div class="cooperation">
        <text class="contact-txt">商务合作：</text>
        <text class="contact-txt">QQ：&nbsp;&nbsp;504435165（申请时请填写淘折商务合作）</text>
      </div>      
    </div>
  </scoller>  
</template>

<script>
  import imgLocationSrc from '../util/imgLocationSrc.js';

  const navigator = weex.requireModule('navigator')

  export default {
    data() {
      return {
        imgSrc: {
          logo: imgLocationSrc.logo.mallLogo
        },
      }
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
  }

  .scoller {
    background-color: #fff;
  }

  .header {
    position: sticky;  
    background-color: #FB3159;
    padding-bottom: 20px; 
    padding-top: 60px;   
  }


  .size {
    width: 750px;
    height: 300px;
  }

  .header-warpper {
    position: relative;
    align-items: center;
  }

  .back-btn {
    position: absolute;
    top: 80px;
    left: 20px;
    color: #fff;
    font-size: 40px;
  }

  .logo {
    position: absolute;
    top: 50px;
    align-items: center;
  }

  .version {
    position: absolute;
    bottom: 0;
    align-items: center;
    flex-direction: row;
  }

  .version-txt {
    color: #fff;
    font-size: 24px;
  }

  .description {
    margin: 30px 20px;
  }

  .description-txt {
    color: #333;
    font-size: 24px;
    line-height: 40px;
  }

  .contact {
    margin: 50px 20px;
  }

  .contact-txt {
    color: #333;
    font-size: 24px;
    line-height: 36px;
  }

  .cooperation {
    margin-top: 20px;
  }
</style>
