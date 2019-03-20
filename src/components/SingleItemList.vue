<template>
  <scroller 
    class="item-list" 
    @loadmore="$emit('onloadmore')"
    @scroll="onScroll" 
    loadmoreoffset="50" >
    
    <text ref="header"></text>    
    <div 
      class="item" 
      v-for="(item,i) in list" 
      :key="i" 
      :url="item.coupon_share_url ? item.coupon_share_url : item.item_url" 
      @click="onitemClick" > 

      <image 
        :src="item.pict_url" 
        class="item-pic" 
        style="width:200px;height:200px"></image>
      <div class="item-main">
        <div class="item-title-box">
          <image 
            src="file:///android_asset/images/tmall-logo.jpg" 
            class="title-icon" 
            style="width:28px;height:28px"
            v-if="item._istmall == 'true'"></image>
          <text class="item-title">{{item.title}}</text>
        </div>
        <!-- 描述标签 -->
        <div class="item-description">
          <text class="item-description-item" v-for="(el,j) in item._item_description_arr" :key="j" v-if="el">{{el}}</text>
        </div> 
        <!-- 原价及销量 -->
        <div class="sales-price">
          <text class="sales-price-txt" v-if="item.coupon_amount">折后价</text>
          <text class="sales-price-txt" v-else>原价</text>    
          <text class="sales-price-num" v-if="item.reserve_price && !item.coupon_amount">&yen;{{item.reserve_price}}</text>
          <text class="sales-price-num" v-else>&yen;{{item.zk_final_price}}</text>
          <text class="sales-volume">已售{{item.volume}}件</text>
        </div>        
        <!-- 券后价/现价 -->
        <div class="zk-price-box">
          <div class="zk-price-wrap" v-if="!item.coupon_amount">
            <text class="zk-price">现价</text>
            <text class="zk-price-icon">&yen;</text>
            <text class="zk-price-num">{{item.zk_final_price}}</text>
          </div>
          
          <!-- <text>{{item.coupon_amount}}</text> -->
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
    <div class="footer">
      <image style="width:70px;height:70px" src="file:///android_asset/images/loading.gif"></image>
    </div>
    <div class="tohead" @click="onToHead" v-if="toHeaderBtnFlag">
      <text class="iconfont">&#xe666;</text>
    </div>
  </scroller>
    
</template>

<script>
  // import toHeader from "./ToHeader.vue";
  import { store } from "../store.js";
  import { getJumpBaseUrl } from "../util/getJumpBaseUrl.js";

  // const stream = weex.requireModule("stream");
  const navigator = weex.requireModule("navigator-pri");
  const dom = weex.requireModule("dom");
  const modal = weex.requireModule('modal');
  export default {
    props: ['list'],
    data() {
      return {
        isTmall: false,
        toHeaderBtnFlag: false,
        console: ''
      }
    },
    computed: {
      
    },
    mounted() {
      
    },
    watch: {
      list() {
        if(!this.list.length) {
          dom.scrollToElement(this.$refs.header, { offset: 0, animated: 'false' });
        }
      }
    },

    components: {
      // toHeader
    },
    methods: {
      couponFinalPrice(zk_final_price,coupon_amount) {

        let _couponFinalPrice = Math.round(zk_final_price * 100 - coupon_amount*100) / 100
        return _couponFinalPrice
      },
      onToHead() {
        
        dom.scrollToElement(this.$refs.header, { offset: 0, animated: 'true' });
      },
      onScroll(e) {
        
        e.contentOffset.y < -2000
          ? (this.toHeaderBtnFlag = true)
          : (this.toHeaderBtnFlag = false)
      },
      onitemClick(e) {
        let url = e.currentTarget.attr.url;
        if(url.indexOf('https:') != 0) {
          url = 'https:' + url 
        }
        
        store.commit("setCouponUrl", url);
        
        navigator.push({
          url: getJumpBaseUrl("coupon", url),
          animated: "true"
        });
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
    padding: 10px 15px 30px 15px;
    
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
    height: 200px;
    align-items: center;
    justify-content: flex-start;
    padding-bottom: 20px;
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