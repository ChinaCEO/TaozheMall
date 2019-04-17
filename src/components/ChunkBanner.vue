<template>
  <div class="chunk">
    <!-- <text>{{itemList}}</text> -->
    <image class="banner-img" :src="imgSrc" resize="cover"></image>
    <div class="item-wrapper">
      <div class="item" 
        v-for="(item, i) in itemList" 
        :key="i" 
        @click="onitemClick"
        :couponUrl="getCouponUrl(item)" >
        <image :src="picSrc(item)" style="width:210px;height:210px" resize="cover"></image>
        <text class="item-title">{{item.title}}</text>
        <div class="item-price">
          <text class="item-price-final">￥{{couponFinalPrice(item)}}</text>
          <text class="item-price-before">￥{{parseFloat(item.zk_final_price).toFixed(2)}}</text>
        </div>
      </div>
    </div>
    <div class="more" @click="onMoreClick"> 
      <text class="iconfont more-txt">查看更多&nbsp;&#xe631;</text>
    </div>
  </div>
</template>

<script>
  import { getJumpBaseUrl } from "../util/getJumpBaseUrl.js";

  const navigator = weex.requireModule("navigator-pri");
  
  export default {
    props: {
      imgSrc: {
        type: String
      },
      itemList: {
        type: Array
      },
      requestItemsId: {
        type: Number
      },
      requestType: {
        type: String
      },
      headerImgUrl: {
        type: String
      }
    },
    data() {
      return {
        console: '',
        moreParams: ''
      }
    },
    
    methods: {
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
      couponFinalPrice(item) {
        let _couponaAmount = "";
        
        if(this.requestType === 'uatm') {
          _couponaAmount = this.getCoupnAmount(item)
        }else if(this.requestType === 'material') {          
          _couponaAmount = item.coupon_amount
        }
               
        let _final = Math.round(item.zk_final_price * 100 - _couponaAmount*100) / 100
        return _final.toFixed(2)
      },
      getCoupnAmount(item) {
        let _couponaAmount = "";
        let jianPos = item.coupon_info.search("减")
        _couponaAmount = parseInt(item.coupon_info.slice(jianPos + 1, -1)) 

        return _couponaAmount
      }, 
      onitemClick(e) {
        let couponUrl = e.currentTarget.attr.couponUrl;
        
        navigator.push({
          url: "tblinkto://" + couponUrl,
          animated: "true"
        });
      },
      onMoreClick() {
        this.moreParams = {
          requestItemsId: this.requestItemsId,
          requestType: this.requestType,
          headerImgUrl: this.headerImgUrl
        }
        navigator.push({
          url: getJumpBaseUrl('findMore',this.moreParams),
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
    },
  }
</script>

<style scoped>
  .iconfont {
    font-family: iconfont;
  }

  .chunk {
    margin-bottom: 20px;
    /* background-color: #ffffff; */
    align-items: center;
  }
  .banner-img {
    width: 710px;
    height: 160px;
  }

  .item-wrapper {
    width: 710px;
    height: 300px;
    flex-direction: row;
    background-color: #fff;
    /* padding: 0 20px; */
    align-items: center;
    justify-content: center;
    padding-left: 20px;
  }

  .item {
    flex: 1;
    margin-right: 20px;
    background-color: #f8f8f8;
    /* align-items: center; */
    /* justify-content: center; */
  }

  .item-title {
    text-overflow: ellipsis;
    lines: 1;
    margin: 10px 0;
    font-size: 20px;
    color: #333;
  }

  .item-price {
    flex-direction: row;
  }

  .item-price-final {
    font-size: 20px;
    color: #ff6000;
    margin-right: 20px;
  }

  .item-price-before {
    font-size: 20px;
    color: #666;
    text-decoration: line-through;   
  }

  .more {
    width: 710px;
    height: 50px;
    align-items: center;
    justify-content: center;
    background-color: #fff;
  }

  .more-txt {
    font-size: 22px;
    color: #333;
  }
</style>