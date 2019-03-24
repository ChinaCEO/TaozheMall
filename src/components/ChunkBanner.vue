<template>
  <div class="chunk">
    <image class="banner-img" :src="imgSrc" resize="cover"></image>
    <div class="item-wrapper">
      <div class="item" 
        v-for="(item, i) in itemList" 
        :key="i" 
        @click="onitemClick"
        :couponUrl="item.coupon_click_url" >
        <image :src="item.pict_url" style="width:210px;height:210px" resize="cover"></image>
        <text class="item-title">{{item.title}}</text>
        <div class="item-price">
          <text class="item-price-final">￥{{couponFinalPrice(item)}}</text>
          <text class="item-price-before">￥{{item.zk_final_price}}</text>
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
      moreUrl: {
        type: String
      }
    },
    data() {
      return {
        console: ''
      }
    },
    methods: {
      couponInfoDeal(items) {
        items.forEach((item, i) => {
          let _coupon_info = "";
          // 记录"减的位置"
          let jianPos = item.coupon_info.search("减");
          _coupon_info = item.coupon_info.slice(jianPos + 1);
          items[i].coupon_info = _coupon_info;
        });
        return items;
      },
      couponFinalPrice(item) {
        let _couponaAmount = "";
        let jianPos = item.coupon_info.search("减")
        _couponaAmount = parseInt(item.coupon_info.slice(jianPos + 1, -1)) 
        
        let _final = Math.round(item.zk_final_price * 100 - _couponaAmount*100) / 100
        return _final.toFixed(2)
      },
      onitemClick(e) {
        let couponUrl = e.currentTarget.attr.couponUrl;
        
        navigator.push({
          url: getJumpBaseUrl("coupon", couponUrl),
          animated: "true"
        });
      },
      onMoreClick() {

        navigator.push({
          url: getJumpBaseUrl('findMore',this.moreUrl),
          animated: "true"
        });
      }
    },
  }
</script>

<style scoped>
  .iconfont {
    font-family: iconfont;
  }

  .chunk {
    margin-top: 20px;
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