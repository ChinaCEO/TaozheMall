<template>
    
  <div  class="item" 
        @click="onitemClick" > 
    <image 
      :src="picSrc(item)"
      class="item-pic" 
      style="width:200px;height:200px"></image>
    <div class="item-main">
      <div class="item-title-box">
        <image 
          :src="imgSrc.tmallLogo"
          class="title-icon" 
          style="width:28px;height:28px"
          v-if="item.user_type"></image>
        <text class="item-title">{{item.title}}</text>
        <!-- <text>{{console}}</text> -->
      </div>
      <!-- 描述标签 -->
      <div class="item-description">
        <text class="item-description-item" v-for="(el,j) in itemDescriptionArr" :key="j" v-if="el">{{el}}</text>
      </div> 
      <!-- 原价及销量 -->
      <div class="sales-price">
        <text class="sales-price-txt" v-if="item.coupon_amount">折后价</text>
        <text class="sales-price-txt" v-else>原价</text>    
        <text class="sales-price-num" v-if="item.reserve_price && !item.coupon_amount">&yen;{{item.reserve_price}}</text>
        <text class="sales-price-num" v-else>&yen;{{item.zk_final_price}}</text>
        <text class="sales-volume">已售{{item.volume >= 10000 ? `${(item.volume/10000).toFixed(1)}万` : item.volume}}件</text>
      </div>        
      <!-- 券后价/现价 -->
      <div class="zk-price-box">
        <div class="zk-price-wrap" v-if="!item.coupon_amount">
          <text class="zk-price">现价</text>
          <text class="zk-price-icon">&yen;</text>
          <text class="zk-price-num">{{item.zk_final_price}}</text>
        </div>
        
        <div class="zk-price-wrap" v-else>
          <text class="zk-price">券后价</text>
          <text class="zk-price-icon zk-price-coupon-icon">&yen;</text>
          <text class="zk-price-coupon-num">{{couponFinalPrice(item.zk_final_price,item.coupon_amount)}}</text>
        </div>
        
      </div> 
        <!-- 优惠券信息 -->
      <div class="coupon">
        <text class="iconfont coupon-icon"  v-if="item.coupon_amount">&#xe617;</text>
        <text class="coupon-text"  v-if="item.coupon_amount">{{item.coupon_amount}}元</text>
      </div>
      <!-- 商家信息 -->
      <!-- <div class="saler">
        <text class="saler-nick">{{item.nick}}</text>         
      </div> -->
    </div>
  </div>   
</template>

<script>
  // import toHeader from "./ToHeader.vue";
  import { getJumpBaseUrl } from "../util/getJumpBaseUrl.js";
  import imgLocationSrc from '../util/imgLocationSrc.js';

  // const stream = weex.requireModule("stream");
  const navigator = weex.requireModule("navigator-pri");
  const dom = weex.requireModule("dom");
  const modal = weex.requireModule('modal');
  export default {
    props: {
      item: {
        type: String,
        default: ''
      }
    },
    data() {
      return {
        console: '',
        imgSrc: {
          tmallLogo: imgLocationSrc.logo.tmallLogo
        },
      }
    },
    computed: {
      itemDescriptionArr() {
        let arr = []
        if(this.item.item_description) {
          arr = this.item.item_description.split(' ')
        }

        return arr
      }
    },
    methods: {
      couponFinalPrice(zk_final_price,coupon_amount) {

        let _couponFinalPrice = Math.round(zk_final_price * 100 - coupon_amount*100) / 100
        return _couponFinalPrice
      },
      picSrc(item) {
        if(item.white_image) {
          if(item.white_image.indexOf('http') >= 0) {
            return item.white_image
          }else {
            return `https:${item.white_image}`
          }
        }else {
          if(item.pict_url.indexOf('http') >= 0) {
            return item.pict_url
          }else {
            return `https:${item.pict_url}`
          }
        }
      },
      onitemClick(e) {
        let url = this.getCouponUrl(this.item)
    
        navigator.push({
          url: "tblinkto://" + url,
          animated: "true"
        });
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
    color: #FF6000;
    font-size: 32px;
    font-family: iconfont;
  }

  .item {
    /* height: 260px; */
    width: 750px;
    flex-direction: row;
    /* justify-content: space-between; */
    padding: 30px 15px 10px 15px;
    background-color: #fff;
  }

  .item-pic {
    margin-right: 20px;
  }

  .item-main {
    border-bottom-style: solid;
    border-bottom-width: 2px;
    border-bottom-color: #ebecee;
  }
  
  .item-title-box {   
    flex-direction: row;        
  }

  .title-icon {
    margin-right: 8px;
    margin-top: 5px;
  }

  .item-title { 
    width:480px;  
    color: #333;
    font-size: 28px;
    font-weight: 500;
    text-overflow: ellipsis;
    lines: 2;    
    line-height: 36px;
    padding-right: 26px;
  }

  .item-description {
    flex-direction: row;
    margin-top: 10px;
    width:480px;
    padding-right: 26px;
    height: 30px;
  }

  .item-description-item {
    font-size: 20px;
    color: #bbbbbb;
    background-color: #ebecee;
    height: 28px;
    padding: 0 10px;
    border-radius: 10px;
    line-height: 28px;
    margin-right: 10px;
    lines: 1; 
    max-width: 450px;
    text-overflow: ellipsis;
  }

  .sales-price {
    flex-direction: row;
    margin-top: 16px;
    width:480px;
  }

  .sales-price-txt {
    font-size: 20px;
    color: #bbbbbb;
    margin-right: 12px;
  }

  .sales-price-num {
    font-size: 20px;
    color: #bbbbbb;
    margin-right: 50px;
    text-decoration: line-through;
  }

  .sales-volume {
    font-size: 20px;
    color: #bbbbbb;
  }

  .zk-price-box {
    flex-direction: row;
    width:480px;
    margin-top: 16px;
  }

  .zk-price-wrap {
    flex-direction: row;
  }

  .zk-price {
    font-size: 24px;
    color: #bbbbbb;
    margin-right: 10px;
    line-height: 50px;
  }

  .zk-price-icon {
    font-size: 24px;
    color: #333;
    margin-right: 6px;
    line-height: 50px;
  }

  .zk-price-coupon-icon {    
    color: #fa513a;    
  }

  .zk-price-num {
    font-size: 36px;
    color: #333;
  }

  .zk-price-coupon-num {
    font-size: 36px;
    color: #fa513a;
  }

  .coupon {
    height: 40px;
    flex-direction: row;
    align-items: center;
    padding-bottom: 10px;
  }

  .coupon-icon {
    margin-right: 5px;
  }

  .coupon-text {
    color: #fa513a;
    font-size: 20px;
    line-height: 34px;
  }

  /* .saler {
    margin-top: 10px
  }

  .saler-nick {
    font-size: 20px;
    color: #666;
  } */

  .footer {
    width: 750px;
    height: 160px;
    align-items: center;
    justify-content: flex-start;
    background-color: #fff;
    margin-bottom: 20px;
  }

  .indicator-text {
    color: #666;
    font-size: 28px;
  }

  .iconfont {
    color: #ff6000;
    font-size: 40px;
    font-family: iconfont;
  }

  .tohead {
    position: fixed;
    bottom: 130px;
    right: 20px;
    width: 80px;
    height: 80px;
    background-color: rgba(255, 255, 255, .8);
    border-width: 2px;
    border-color: #ebecee;
    border-radius: 80px;
    align-items: center;
    justify-content: center;
  } 
</style>