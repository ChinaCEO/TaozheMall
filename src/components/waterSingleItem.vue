<template>
  <div class="item" @click="itemOnClick" :couponUrl="couponUrl">
    <image class="item-photo" :src="imgSrc" resize="cover"></image>
    <text class="item-title">{{title}}</text>
    <div class="item-price-box">
      <div class="coupon">
        <text class="iconfont coupon-icon">&#xe617;</text>
        <text class="coupon-text">{{couponAmount}}元</text>
      </div>
      <div class="price">
        <text class="zk-price-txt">折后价</text>
        <text class="zk-price-num">￥{{couponFinalPrice}}</text>
      </div>
    </div>
  </div>
</template>

<script>
  import { formatURL } from "../util/formatURL.js";
  import { getJumpBaseUrl } from "../util/getJumpBaseUrl.js";

  const navigator = weex.requireModule("navigator-pri");

  export default {
    props: [
      'requestType',
      'couponUrl', 
      'imgSrc', 
      'title',
      'couponAmount',
      'couponFinalPrice'
    ],
    data() {
      return {
        console: ''
      }
    },
    methods: {
      itemOnClick(e) { 
          
        let couponUrl =  e.currentTarget.attr.couponUrl;
        
        navigator.push({
          url: getJumpBaseUrl("coupon", couponUrl),
          animated: "true"
        });
      }   
    }
  }
</script>

<style scoped>
  .iconfont {
    color: #ff6000;
    font-size: 40px;
    font-family: iconfont;
  }

  .item {
    /*height: 450px;*/
    background-color: #ffffff;
    border-width: 2px;
    border-color: #ebecee;  
  }

  .item-photo {
    width: auto;
    height: 360px;
    margin: 2px;
    margin-bottom: 10px;
  }

  .item-title {
    margin: 10px;
    font-size: 26;
    font-weight: 500;
    lines: 2;
    text-overflow: ellipsis;
  }

  .item-price-box {
    flex-direction: row;
    padding-bottom: 10px;
    margin-right: 10px;
    margin-left: 10px;
    align-items: center;
    justify-content: space-between;
  }

  .coupon {
    flex-direction: row;
    align-items: center;
  }

  .coupon-icon {
    margin-right: 2px;
  }

  .coupon-text {
    color: #fe9f92;
    font-size: 24px;
  }

  .price {
    flex-direction: row;
    align-items: center;
  }

  .zk-price-txt {
    color: #666;
    font-size: 24px;
  }

  .zk-price-num {
    color: #fa513a;
    font-size: 32px;
  }
</style>