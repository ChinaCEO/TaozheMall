<template>
  <div class="item" @click="onItemClick">
    <image 
          :src="'https:'+item.pict_url" 
          class="item-pic" 
          style="width:200px;height:200px"></image>
    <div class="item-main">
      
      <div class="item-title-box" >
        <div class="title-icon-wrapper" style="width:40px;height:40px">
          <image  :src="imgSrc.tmallLogo" 
                class="title-icon" 
                style="width:28px;height:28px"
                v-if="item.user_type"></image>
        </div>
        
        <text class="item-title">{{item.title}}</text>
        <!-- <text>{{console}}</text> -->
      </div>
      <!-- 描述 -->
      <div class="description">
        <text class="description-txt">{{item.item_description}}</text>
      </div>
      <!-- 销量 -->
      <div class="sell-num">
        <text class="sell-num-txt">已拼{{item.sell_num}}件</text>
        <text class="sell-num-txt"  v-if="item.stock < 1000">/</text>
        <text class="sell-num-txt" v-if="item.stock < 1000">仅剩{{item.stock}}件</text>
      </div>
      <!-- 价格 -->
      <div class="price">
        <div class="jdd-price">
          <text class="jdd-price-txt">{{item.jdd_num}}人团</text>
          <text class="jdd-price-symbol">&yen;</text>
          <text class="jdd-price-num">{{item.jdd_price}}</text>
        </div>
        <div class="orig-price">
          <text class="orig-price-txt">单买价&yen;{{item.orig_price}}</text>
        </div>
      </div>
    </div>  
  </div>
</template>

<script>
  import { getJumpBaseUrl } from "../util/getJumpBaseUrl.js";
  import imgLocationSrc from '../util/imgLocationSrc.js';

  const navigator = weex.requireModule("navigator-pri");

  export default {
    props: {
      item: {
        type: Object
      }
    },
    data() {
      return {
        imgSrc: {
          tmallLogo: imgLocationSrc.logo.tmallLogo
        },
        console: ''
      }
    },
    methods: {
      onItemClick() {
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
        }else if(item.click_url) {
          if(item.click_url.indexOf('http') < 0){
            return 'https:' + item.click_url
          }else {
            return item.click_url
          } 
        }else if(item.item_url){
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
  .item {
    width: 750px;
    flex-direction: row;
    padding: 20px 15px 10px 15px;
    background-color: #fff;
  }

  .item-pic {
    margin-right: 20px;
  }

  .item-main {
    width:480px;  
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
    font-weight: 700;
    text-overflow: ellipsis;
    lines: 2;    
    line-height: 36px;
    padding-right: 28px;
  }

  .description {
    margin-top: 10px;
    margin-left: 32px;
  }

  .description-txt {
    color: #bbb;
    font-size: 22px;
    text-overflow: ellipsis;
    lines: 1;
  }

  .sell-num {
    flex-direction: row;
    margin-left: 32px;
    margin-top: 15px;
  }

  .sell-num-txt {
    color: #666;
    font-size: 24px;
    margin-right: 5px;
  }
  
  .price {
    flex-direction: row;
    margin-top: 20px;
    margin-left: 32px;
    padding-bottom: 10px;
  }

  .jdd-price {
    flex-direction: row;
  }

  .jdd-price-txt {
    color: #fa513a;
    font-size: 24px;
    margin-right: 10px;
    font-weight: 500;
    line-height: 40px;
  }

  .jdd-price-symbol {
    color: #fa513a;
    font-size: 24px;
    margin-right: 3px;
    line-height: 40px;
  }

  .jdd-price-num {
    color: #fa513a;
    font-size: 36px;
    margin-right: 10px;
    font-weight: 700;
    line-height: 32px;
  }

  .orig-price-txt {
    color: #666;
    font-size: 22px;
    text-decoration: line-through;
    line-height: 32px;
  }
</style>