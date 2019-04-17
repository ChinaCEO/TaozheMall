<template>  
  <div class="onsale">
    <div class="title">
      <text class="title-txt">活动专区</text>
    </div>
    <div class="left-box" @click="onLeftBoxClick">

      <div class="box-title">
        <!-- <text>{{console}}</text> -->
        <text class="box-title-top">限时抢购</text>
        <div class="countdown-wrapper">
          <div class="countdown-box">
            <text class="countdown-txt">{{countdown.hour}}</text>
          </div>
          <text class="countdown-separator">:</text>
          <div class="countdown-box">
            <text class="countdown-txt">{{countdown.minute}}</text>
          </div>
          <text class="countdown-separator">:</text>
          <div class="countdown-box">
            <text class="countdown-txt">{{countdown.second}}</text>
          </div>
        </div>
        <!-- <text class="box-title-bottom">实惠好物等你来抢</text> -->
      </div> 
      <!-- <div class="buy-btn">
        <text class="buy-btn-txt">立即抢购</text>
      </div> -->
      <div>
        <image :src="countdownPicUrl" resize="contain" class="left-img"></image>
      </div>
    </div>
    <div class="right-box">
      <div class="right-top" @click="onRightTopClick">
        <div class="right-img-box">
          <image src="https://gd3.alicdn.com/imgextra/i3/47058671/TB28X.4buGSBuNjSspbXXciipXa_!!47058671.jpg_400x400.jpg_.webp" resize="cover" class="right-img"></image>
        </div>
        <div class="box-title right-top-title">
          <text class="box-title-top">实惠团购</text>
          <text class="box-title-bottom">拼着买更便宜</text>
        </div> 
        <div class="buy-btn">
          <text class="buy-btn-txt">立即抢购</text>
        </div>
        
      </div>
      <div class="right-bottom" @click="onRightBottomClick">
        <div class="right-img-box">
          <image src="http://img.alicdn.com/imgextra/i1/725677994/O1CN01n5as6O28vIdtVlESK_!!0-item_pic.jpg_430x430q90.jpg" resize="cover" class="right-img"></image>
        </div>
        <div class="box-title right-top-title">
          <text class="box-title-top">超大额券</text>
          <text class="box-title-bottom">让实惠更实惠</text>
        </div> 
        <div class="buy-btn">
          <text class="buy-btn-txt">立即抢购</text>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { getJumpBaseUrl } from "../util/getJumpBaseUrl.js";
  import config from '../util/mall.config.js';
  import { formatURL } from "../util/formatURL.js";
  import imgLocationSrc from '../util/imgLocationSrc.js';

  const storage = weex.requireModule('storage');
  const navigator = weex.requireModule("navigator-pri");
  const stream = weex.requireModule("stream");
  const dom = weex.requireModule("dom");

	export default {
		data() {
      return {
        console: '',
        onsaleCountDowmTime: 0,
        timeArr: [0,8,10,11,12,13,14,15,17,19,21,22,23],
        countdown: {
          hour: '00',
          minute: '00',
          second: '00'
        },
        countdownTimer: null,
        countdownPicTimer: null,
        countdownPicUrlArr: [],
        countdownPicUrl: '',
        countdownPicUrlArrLength: 3,
        requestOptions: {
          method: "taobao.tbk.ju.tqg.get",
          apiOptions: {
            fields: "pic_url",
            page_size: 3,
            page_no: 1,
            adzone_id: config.adzoneId,
            start_time: '',
            end_time: ''
          }
        }
      }
    },
    created() {
      let currentTime = new Date();
      this.getOnsaleCountDowmTime(currentTime)
      
      this.countdownTimer = setInterval(() => {
        if(this.onsaleCountDowmTime <= 0) {
          let currentTime = new Date();
          this.getOnsaleCountDowmTime(currentTime)
        }
        this.onsaleCountDowmTime --
        this.getCountdown()
      },1000)

      // 获取3张图片
      let formatedCurrentTime = this.formatTime(currentTime)
      let formatedCurrentTimeArr = formatedCurrentTime.split(' ')
      this.requestOptions.apiOptions.start_time = `${formatedCurrentTimeArr[0]} 00:00:00`
      this.requestOptions.apiOptions.end_time = `${formatedCurrentTimeArr[0]} ${formatedCurrentTimeArr[1].split(':')[0]}:10:00`
      this.requestOptions.apiOptions.page_size = this.countdownPicUrlArrLength
      this.requestOptions.apiOptions.page_no = Math.floor(Math.random()*20) 
      
      let url = formatURL(this.requestOptions.method,this.requestOptions.apiOptions)
      try {
        stream.fetch(
          {
            method: "GET",
            url: url,
            type: "json"            
          },
          res => { 
                        
            if (res.data.tbk_ju_tqg_get_response) {              
              let items = res.data.tbk_ju_tqg_get_response.results.results;
              
              items.forEach((item) => {
                this.countdownPicUrlArr.push(item.pic_url)
              })
              // this.console = this.countdownPicUrlArr
              this.countdownPicUrl = this.countdownPicUrlArr[0] 
              let i = 1
              this.countdownPicTimer = setInterval(() => {
                this.countdownPicUrl = this.countdownPicUrlArr[i]
                i++
                if(i >= this.countdownPicUrlArrLength){
                  i = 0
                }
              },3000)
            } else {
              this.countdownPicUrl = imgLocationSrc.goodsPics.sale1
            }
          }
        );
      } catch (err) {
        this.countdownPicUrl = imgLocationSrc.goodsPics.sale1
      }
    },
    beforeDestroy() {     
      clearInterval(this.countdownTimer)
      clearInterval(this.countdownPicTimer)
    },
    methods: {
      getOnsaleCountDowmTime(currentTime) {       
        let currentTimeHour = currentTime.getHours();
        let currentTimeMin = currentTime.getMinutes();
        let currentTimesec = currentTime.getSeconds();
        
        let index = -1
        let hour = currentTimeHour
        
        index = this.onCircle(hour)
        if(!this.timeArr[index + 1]){
          this.onsaleCountDowmTime = 24 * 3600 - (currentTimeHour * 3600 + currentTimeMin * 60 + currentTimesec)
        }else {
          this.onsaleCountDowmTime = this.timeArr[index + 1] * 3600 - (currentTimeHour * 3600 + currentTimeMin * 60 + currentTimesec)
        }       
      },
      getCountdown() {
        let hour = parseInt(this.onsaleCountDowmTime / 3600) 
        this.countdown.hour = hour< 10 ? '0' + hour : hour
        
        let minute = parseInt((this.onsaleCountDowmTime - hour * 3600) / 60) 
        this.countdown.minute = minute < 10 ? '0' + minute : minute
        
        let second = parseInt(this.onsaleCountDowmTime - hour * 3600 - minute * 60) 
        this.countdown.second = second < 10 ? '0' + second : second
      },
      onLeftBoxClick() {
        navigator.push({
          url: getJumpBaseUrl('snapUp', ''),
          animated: "true"
        })
      },
      onRightTopClick() {
        navigator.push({
          url: getJumpBaseUrl('jdd', ''),
          animated: "true"
        })
      },
      onRightBottomClick(){
        let webUrl = `https://mos.m.taobao.com/union/supercoupon?pid=${config.pid}`
        // let withBack = 'true'

        // let option = { 
        //   webUrl: webUrl, 
        //   withBack: withBack 
        // }
        
        navigator.push({
          url: "tblinkto://" + webUrl,
          animated: "true"
        })
      },
      onCircle(hour) {
        let index = -2
        for(let i = 0; i < this.timeArr.length; i++) {                              
          if(this.timeArr[i] === hour) {           
            index = i                 
            break;
          }else {
            index = -1
          }                     
        }
        if(index === -1) {
          hour--             
          index = this.onCircle(hour)
          
        }
        if(index !== -1) {
          return index
        }    
      },
      formatTime(date) {
        return `${date.getFullYear()}-${date.getMonth()+1<10?'0'+(date.getMonth()+1):date.getMonth()+1}-${date.getDate()<10?'0'+date.getDate():date.getDate()} ${date.getHours()<10?'0'+date.getHours():date.getHours()}:00:00`
      },
    }
	}
</script>

<style scoped>
	.onsale {
    /*height: 450px;*/
    margin: 20px;
    background-color: #fff;
    border-radius: 30px;
    flex-direction: row;
    /*justify-content: center;*/
    position: relative;
    padding-bottom: 20px;
  }
  
  .title {
    position: absolute;
    left: 0;
    top: 18px;
    background-image: linear-gradient(to top,#EB6A12,#FFA42A);
    width: 180px;
    height: 60px;
    border-top-right-radius: 60px;
    border-bottom-right-radius: 60px;
    justify-content: center;
    align-items: center;
    border-width: 2px;
    border-color: #F5B488;
    border-style: solid;
  }

  .title-txt {
    color: #fff;
    font-size: 32px;
  }

  .left-box {
    border-right-width: 2px;
    border-right-color: #ebecee;
    margin: 20px;
    /*position: relative;*/
    /*background-color: red;*/
    width: 340px;
  }

  .box-title {
    margin-top: 65px;
  }

  .box-title-top {
    font-size: 36px;
    color: #fa513a;
  }

  .countdown-wrapper {
    flex-direction: row;
    align-items: center;
    margin: 10px 0 30px 0;
  }

  .countdown-box {
    justify-content: center;
    align-items: center;
    background-color:#fa513a;
    padding: 3px;
  }

  .countdown-txt {
    font-size: 24px;
    color: #fff;
  }

  .countdown-separator {
    margin: 0 5px;
    font-size: 24px;
    color: #333;
  }

  .box-title-bottom {
    font-size: 24px;
    margin-bottom: 15px;
    color: #666;
  }

  .buy-btn {
    width: 140px;
    height: 50px;
    background-color: #fb3519;
    border-radius: 10px;
    justify-content: center;
    align-items: center;
    margin-bottom: 15px;
  }

  .buy-btn-txt {
    color: #fff;
    font-size: 28px;
  }

  .left-img {
    width: 320px;
    height: 200px;
  }

  .right-box {
    width: 320px;
  }
  
  .right-top {
    
    border-bottom-width: 2px;
    border-bottom-color: #ebecee;
    border-bottom-style: solid;
    padding-bottom: 35px;
    position: relative;
  }

  .right-top-title {
    margin-top: 40px;
  }

  .right-bottom {    
    position: relative;
  }

  .right-img-box {
    position: absolute;
    right: 5px;
    top: 5px;
  }

  .right-img {
    width: 165px;
    height: 200px;
  }
</style>
