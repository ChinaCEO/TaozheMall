<template>  
  <scroller scroll-direction="horizontal" class="scroller">
    
    <div class="panel" 
        v-for="item in list" 
        :key="item.num_iid"
        :url="getCouponUrl(item)" 
        @click="onitemClick">
      <image class="item-photo" :src="picSrc(item)" resize="cover"></image>
      <text class="item-title">{{item.title}}</text>
      
      <div class="price">          
        <text class="zk-price">￥{{couponFinalPrice(item)}}</text>
        <text class="reserve-price">￥{{parseFloat(item.zk_final_price).toFixed(2)}}</text>
      </div> 
    </div>
  </scroller> 
</template>

<script>
  const navigator = weex.requireModule("navigator-pri");
  
	export default {
    props: {
      list: {
        type: Array
      }, 
    },
		data() {
      return {
        console: ''
      }
    },
    methods: {
      onitemClick(e) {
        let url = e.currentTarget.attr.url;
        
        navigator.push({
          url: "tblinkto://" + url,
          animated: "true"
        });
      },
      picSrc(item) {
        if(item.white_image) {
          if(item.white_image.indexOf('http') >= 0) {
            return item.white_image
          }else {
            return `https://${item.white_image}`
          }
        }else {
          if(item.pict_url.indexOf('http') >= 0) {
            return item.pict_url
          }else {
            return `https://${item.pict_url}`
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
	.scroller {
    height: 450px;
    flex-direction: row;
    margin-right: 20px;
    margin-bottom: 20px;
    margin-left: 20px;
  }

  .panel {   
    width: 300px;
    background-color: #fff;
    border-width: 2px;
    border-style: solid;
    border-color: #ebecee;
    margin-right: 10px;
  }

  .item-photo {
    width: 300px;
    height: 300px;
  }

  .item-title {
    margin: 10px;
    font-size: 26;
    font-weight: 500;
    lines:2;
    text-overflow: ellipsis;
  }

  .price {
    flex-direction: row;
    align-items: center;    
  }

  .zk-price {
    color: #fa513a;
    font-size: 32px;
    margin: 5px;
  }

  .reserve-price {
    color: #bbb;
    font-size: 24px;
    margin: 5px;
    text-decoration: line-through;
  }
</style>
