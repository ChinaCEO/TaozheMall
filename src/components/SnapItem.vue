<template>
  <div class="item" @click="onItemClick">
    <image 
          :src="item.pic_url" 
          class="item-pic" 
          style="width:200px;height:200px"></image>
    <div class="item-main">
      <!-- <text>{{console}}</text> -->
      <div class="item-title-box">
        <text class="item-title" :class="[countdownFlag ? 'title-underline' : '']">{{item.title}}</text>
        <div class="item-countdown" v-if="countdownFlag">
          <div class="item-countdown-time">
            <text class="item-countdown-txt">{{countdownMin}}</text>
          </div>
          <text class="item-countdown-colon">:</text>
          <div class="item-countdown-time">
            <text class="item-countdown-txt">{{countdownsec}}</text>
          </div>
        </div>
      </div>
      <!-- 进度条 -->
      <div class="progress-bar-wrapper" v-if="time >= startTime">
        <div class="progress-bar"></div>
        <div class="progress-bar progress-bar-saled" :style="{'width': soldProgressWidth + 'px'}"></div>                  
        <text class="progress-bar-txt progress-bar-txt-saled">已抢{{item.sold_num}}件</text>                   
        <text class="progress-bar-txt progress-bar-txt-num">{{soldPerent}}</text>                
      </div>
      <!-- 开场时间 -->
      <div class="start-time-desciption" v-else>
        <text class="start-time-desciption-txt">{{!isTomorrow ? `今日${startTimeHour}` : `明日${startTimeHour}`}}开抢</text>
      </div>
      <!-- 价格 -->
      <div class="price">
        <div class="final-price">
          <text class="final-price-symbol">&yen;</text>
          <text class="final-price-num">{{item.zk_final_price}}</text>
        </div>
        <div class="reserve-price">
          <text class="reserve-price-num">&yen;{{item.reserve_price}}</text>
        </div>
      </div>
    </div>  
  </div>
</template>

<script>
  import { getJumpBaseUrl } from "../util/getJumpBaseUrl.js";

  const navigator = weex.requireModule("navigator-pri");

  export default {
    props: {
      item: {
        type: Object,
        default: {}
      },
      currentTime: {
        type: String,
        default: ''
      }
    },
    data() {
      return {
        console: '',
        countdownMin: '30',
        countdownsec: '00',
        countdownFlag: false,
        saledPercent: 0,
        timer: null,
        soldPerent: 0,
        soldProgressWidth: 0,
        time: 0,
        startTime: 0,
        startTimeHour: '',
        isTomorrow: false
      }
    },
    beforeMount() {
      let currentTimeArr = this.currentTime.split(' ')
      let time = new Date(`${currentTimeArr[0]}T${currentTimeArr[1]}`) - 0
      this.time = time

      let startTimeArr = this.item.start_time.split(' ')
      this.startTime = new Date(`${startTimeArr[0]}T${startTimeArr[1]}`) - 0
      this.startTimeHour = startTimeArr[1].split(':')[0] + ':00'

      let dateDiffer = new Date(startTimeArr[0]) - new Date(currentTimeArr[0])
      this.console = dateDiffer
      dateDiffer === 86400000 ? this.isTomorrow = true : this.isTomorrow = false

      let endTimeArr = this.item.end_time.split(' ')
      let endTime = new Date(`${endTimeArr[0]}T${endTimeArr[1]}`) - 0
      let lastTime = (endTime - time) / 1000
      if(lastTime <= 1800) {
        this.countdownFlag = true
        let countdownMin = 0
        let countdownsec = 0
        this.timer = setInterval(() => {
          countdownMin = Math.floor(lastTime / 60)
          countdownsec = lastTime - this.countdownMin * 60
          countdownMin = countdownMin < 0 ? 0 : countdownMin
          countdownsec = countdownsec < 0 ? 0 : countdownsec
          this.countdownMin = countdownMin < 10 ? `0${countdownMin}` : countdownMin
          this.countdownsec = countdownsec < 10 ? `0${countdownsec}` : countdownsec
          lastTime--
        },1000)
      }

      let soldPerent = this.item.sold_num /  this.item.total_amount
      this.soldPerent = Math.ceil(soldPerent * 100) + '%'
      this.soldProgressWidth = 260 * soldPerent
      
    },
    methods: {
      onItemClick() {
        navigator.push({
          url: getJumpBaseUrl("coupon", this.item.click_url),
          animated: "true"
        });
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

  .item-title {     
    color: #333;
    font-size: 24px;
    font-weight: 700;
    text-overflow: ellipsis;
    lines: 1;    
    line-height: 36px;
    margin-right: 5px;
  }

  .item-countdown {
    flex-direction: row;    
  }

  .item-countdown-time {
    padding: 3px 2px;
    background-color: #000;
    border-radius: 5px;
    justify-content: center;
    align-items: center;
    margin-left: 2px;
  }

  .item-countdown-txt {
    color: #fff;
    font-size: 24px;
  }

  .item-countdown-colon {
    color: #333;
    font-size: 24px;
    margin-left: 2px;
  }

  .title-underline {
    border-bottom-color: #FBC446;
    border-bottom-style: solid;
    border-bottom-width: 4px;
  }

  .progress-bar-wrapper {
    position: relative;
    margin-top: 60px;
    width: 260px;
  }

  .progress-bar {
    width: 260px;
    height: 24px;
    background-color: rgba(238, 235, 51, 0.4);
    border-radius: 20px;
    justify-content: center;
  }

  .progress-bar-saled {
    position: absolute;
    top: 0;
    left: 0;
    background-color: rgb(255, 251, 0);
  }

  .progress-bar-txt {
    position: absolute;
    color: rgb(129, 106, 3);
    font-size: 20px;
    
  }

  .progress-bar-txt-saled {
    left: 10px;
  }

  .progress-bar-txt-num {
    right: 10px;
  }

  .start-time-desciption {
    margin-top: 60px;
  }

  .start-time-desciption-txt {
    color: rgb(1,216,103);
    font-size: 24px;
  }

  .price {
    flex-direction: row;
    margin-top: 20px;
  }

  .final-price {
    flex-direction: row;
    margin-right: 10px;
  }

  .final-price-symbol {
    color: #333;
    font-size: 24px;
    margin-right: 3px;
    line-height: 40px;
  }

  .final-price-num {
    color: #333;
    font-size: 32px;
    font-weight: 700;
  }

  .reserve-price-num {
    color: #bbb;
    font-size: 20px;
    text-decoration: line-through;
    line-height: 36px;
  }
</style>