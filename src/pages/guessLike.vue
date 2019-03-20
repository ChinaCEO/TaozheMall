<template>
  <waterfall 
    class="guess-like" 
    ref="guessLike" 
    show-scrollbar="false"
    :column-width="columnWidth" 
    :column-count="columnCount" 
    :column-gap="columnGap" 
    :left-gap='leftGap' 
    :right-gap='rightGap'
    @scroll="onScroll" >
    <header class="header" ref="header">
      <div class="header-wrapper">
        <text class="title">猜你喜欢</text>
      </div>      
    </header>
    <header>
      <text>{{console}}</text>
    </header>
    <cell class="cell" v-for="(item) in items" :key="item.num_iid" :ref="'item'+item.num_iid">
      <div class="item" @click="itemOnClick" :couponUrl="item.coupon_click_url">
        <image class="item-photo" :src="item.white_image ? item.white_image : item.pict_url" resize="cover"></image>
        <text class="item-title">{{item.title}}</text>
        <div class="item-price-box">
          <div class="coupon">
            <text class="iconfont coupon-icon">&#xe617;</text>
            <text class="coupon-text">{{item.coupon_amount}}元</text>
          </div>
          <div class="price">
            <text class="zk-price-txt">折后价</text>
            <text class="zk-price-num">￥{{couponFinalPrice(item.zk_final_price,item.coupon_amount)}}</text>
          </div>
        </div>
      </div>
    </cell>
    <header v-if="toHeaderBtnFlag">
      <div class="toHeader" @click="onToHeader">
        <text class="iconfont">&#xe666;</text>
      </div>           
    </header>
    <header class="footer" ref="footer" @appear="onloadmore">
      <image style="width:70px;height:70px" src="file:///android_asset/images/loading.gif" v-if="loadingFlag"></image>
      <text class="indicator-text" v-else>没有更多了...</text>
    </header>
  </waterfall>
</template>

<script>
  import { formatURL } from "../util/formatURL.js";

  const getImei = weex.requireModule('getImei');
  const stream = weex.requireModule("stream");
  const modal = weex.requireModule('modal');
  const dom = weex.requireModule("dom");

  export default {
    data() {
      return {
        requestOptions: {
          method: "taobao.tbk.dg.optimus.material",
          apiOptions: {
            adzone_id: "91627500240",
            page_size: "20",
            page_no: 1,
            material_id: "6708",
            device_value: '',
            device_encrypt: '	MD5',
            device_type: 'IMEI'
          }
        },
        columnWidth: "auto",
        columnCount: "2",
        columnGap: "10",
        leftGap: "20",
        rightGap: "20",
        loadingFlag: true,
        items: [],
        toHeaderBtnFlag: false,
        console: ''      
      }
    },
    components: {
      
    },
    beforeMount() {
      getImei.getIMEI(res => {
        if(res && res != '000000000000000') {
          this.requestOptions.apiOptions.device_value = res
        }else {
          this.requestOptions.apiOptions.device_value = ''
          this.requestOptions.apiOptions.device_encrypt = ''
          this.requestOptions.apiOptions.device_type = ''
        }       
      })

      this.fetch(res => {
        
        if(res.data.tbk_dg_optimus_material_response) {
          let data = res.data.tbk_dg_optimus_material_response.result_list.map_data
          this.items = data  
        }else {
          this.loadingFlag = false
          modal.toast({
            message: '网络异常',
            duration: 0.3
          })
        }    
      },err => {
        this.loadingFlag = false
        modal.toast({
          message: '网络异常',
          duration: 0.3
        })
      })
    },
    methods: {
      // 公共方法
      fetch(seccCallback,errCallback) {

        let url = formatURL(this.requestOptions.method,this.requestOptions.apiOptions)
        
        try {
          stream.fetch(
            {
              method: "GET",
              url: url,
              type: "json"
            },
            res => {
              if(typeof seccCallback === "function") {
                seccCallback(res)
              }  
                        
            }
          );
        } catch (err) {
          if(typeof errCallback === "function") {
            errCallback(err)
          }
        }      
      }, 
      couponFinalPrice(zk_final_price,coupon_amount) {

        let _couponFinalPrice = Math.round(zk_final_price * 100 - coupon_amount*100) / 100
        return _couponFinalPrice
      },
      onScroll(e) {
        
        e.contentOffset.y < -1000
          ? (this.toHeaderBtnFlag = true)
          : (this.toHeaderBtnFlag = false);
      },
      onToHeader(e) {
        dom.scrollToElement(this.$refs.header, { offset: 0, animated: 'true' });
      },
      onloadmore() {
        this.requestOptions.apiOptions.page_no ++
        this.fetch(res => {
          if(res.data.tbk_dg_optimus_material_response) {
            let data = res.data.tbk_dg_optimus_material_response.result_list.map_data
            data.forEach(el => {
              this.items.push(el)
            });

          }else {
            this.loadingFlag = false  
          }
        },err => {
          this.loadingFlag = false
        })
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
  
  .guess-like {
    margin-bottom: 90px;
  }

  .header {
    position: sticky;
      
  }

  .header-wrapper {
    height: 130px;
    margin:0 20px 20px 20px;
    padding-bottom: 10px; 

    border-bottom-style: solid;
    border-bottom-width: 2px;
    border-bottom-color: #ebecee;
    align-items: center;
    justify-content: flex-end;
    flex:1;
    background-color: #fff;
    
  }

  .title {
    color: #fa513a;
    font-size: 36px;
  }

  .cell {
    padding-top: 6px;
    padding-bottom: 6px;
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
    margin: 1px;
    margin-bottom: 0;
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

  .toHeader {
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

  .footer {
    width: 750px;
    padding-top: 10px;
    padding-bottom: 10px;
    align-items: center;  
  }
</style>