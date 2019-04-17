<template>
  <div class="item" @click="itemOnClick" 
      :couponUrl="getCouponUrl(item)">
    <image class="item-photo" :src="picSrc" resize="cover"></image>
    <text class="item-title">{{item.title}}</text>
    <div class="item-price-box">
      <div class="coupon">
        <text class="iconfont coupon-icon">&#xe617;</text>
        <text class="coupon-text">{{item.coupon_amount ? item.coupon_amount :getCoupnAmount(item)}}元</text>
      </div>
      <div class="price">
        <text class="zk-price-txt">折后价</text>
        <text class="zk-price-num">￥{{couponFinalPrice(item)}}</text>
      </div>
    </div>
  </div>
</template>

<script>
  import { formatURL } from "../util/formatURL.js";
  import { getJumpBaseUrl } from "../util/getJumpBaseUrl.js";

  const navigator = weex.requireModule("navigator-pri");
  const bcJump = weex.requireModule("bcJump");

  export default {
    props: ['item'],
    data() {
      return {
        console: ''       
      }
    },
    computed: {
      picSrc() {
        if(this.item.white_image) {
          if(this.item.white_image.indexOf('http') >= 0) {
            return this.item.white_image
          }else {
            return `https:${this.item.white_image}`
          }
        }else {
          if(this.item.pict_url.indexOf('http') >= 0) {
            return this.item.pict_url
          }else {
            return `https:${this.item.pict_url}`
          }
        }
      }
    },
    methods: {
      itemOnClick(e) { 
          
        let couponUrl =  e.currentTarget.attr.couponUrl;
        
        navigator.push({
          url: "tblinkto://" + couponUrl,
          animated: "true"
        });
        
      },
      getCoupnAmount(item) {
        let _couponaAmount = "";
        let jianPos = item.coupon_info.search("减")
        _couponaAmount = parseInt(item.coupon_info.slice(jianPos + 1, -1)) 

        return _couponaAmount
      },
      couponFinalPrice(item) {
        let _couponaAmount = 0;
        
        if(item.coupon_amount) {
           _couponaAmount = item.coupon_amount
        }else {
          _couponaAmount = this.getCoupnAmount(item)
        }
               
        let _final = Math.round(item.zk_final_price * 100 - parseInt(_couponaAmount)*100) / 100
        return _final.toFixed(2)
      },
      getCouponUrl(item) {
        if(item.coupon_share_url) {
          if(item.coupon_share_url.indexOf('http') < 0){
            return 'https:' + item.coupon_share_url
          }else {
            return item.coupon_share_url
          }         
        }else if(item.coupon_click_url) {
          if(item.coupon_click_url.indexOf('http') < 0){
            return 'https:' + item.coupon_click_url
          }else {
            return item.coupon_click_url
          } 
        }else {
          if(item.item_url.indexOf('http') < 0){
            return 'https:' + item.item_url
          }else {
            return item.item_url
          } 
        }
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