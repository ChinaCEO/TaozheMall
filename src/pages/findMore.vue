<template>
  <waterfall 
    class="water-fall" 
    ref="waterfall" 
    :column-width="columnWidth" 
    :column-count="columnCount" 
    :column-gap="columnGap" 
    :left-gap='leftGap' 
    :right-gap='rightGap' 
    @scroll="onScroll" >
    <header class="header size" ref="header">         
      <image :src="headerImgUrl" class="size header-bgi" resize="cover"></image>
      <text class="back-btn iconfont" @click="onback">&#xe625;</text>
    </header>
    <header>
      <!-- <textarea
         type="text"
         autofocus=""
         placeholder=""
         style="width: 750px;height:500px;font-size:18px;"
         :value="console">
      </textarea> -->
      <!-- <text>{{console}}</text> -->
    </header>
    <cell class="cell" v-for="item in items" :key="item.num_iid" :ref="'item'+item.num_iid">
      <item :coupon-url="item.coupon_share_url ? `https://${item.coupon_share_url}` : item.coupon_click_url" 
            :img-src="item.white_image ? item.white_image : item.pict_url"
            :title="item.title"
            :coupon-amount="item.coupon_amount ? item.coupon_amount :getCoupnAmount(item)"
            :coupon-final-price="couponFinalPrice(item)" ></item>
    </cell>
    <header v-if="toHeaderBtnFlag">
      <div class="toHeader" @click="onToHeader">
        <text class="iconfont toHeader-icon">&#xe666;</text>
      </div>           
    </header>
    <header ref="footer" @appear="onloadmore">
      <div class="footer">
        <image style="width:70px;height:70px" src="file:///android_asset/images/loading.gif" v-if="loadingFlag"></image>
        <text class="footer-text" v-else>~没有更多了~</text>
      </div>     
    </header>
  </waterfall>
</template>

<script>
  import Item from '../components/waterSingleItem.vue';
  import { formatURL } from "../util/formatURL.js";
  import config from '../util/mall.config.js';

  const stream = weex.requireModule("stream");
  const modal = weex.requireModule('modal');
  const dom = weex.requireModule("dom");
  const navigator = weex.requireModule("navigator-pri");

  export default {
    data() {
      return {
        console: '',
        items: [],
        headerImgUrl: '',
        requestItemsId: '',
        requestType: '',
        columnWidth: "auto",
        columnCount: "2",
        columnGap: "10",
        leftGap: "10",
        rightGap: "10",
        materialRequestOptions: { // 通用物料api
          method: 'taobao.tbk.dg.optimus.material',
          apiOptions: {
            adzone_id: config.adzoneId,
            page_size: 20,
            page_no: 1,
            material_id: ''      
          }
        },
        uatmRequestOptions: { // 选品库api
          method: 'taobao.tbk.uatm.favorites.item.get',
          apiOptions: {
            adzone_id: config.adzoneId,
            platform: 2,
            page_size: 20,
            page_no: 1,
            fields: 'num_iid,title,pict_url,zk_final_price,user_type,volume,coupon_click_url,coupon_info',
            favorites_id: ''     
          }
        },
        toHeaderBtnFlag: false,
        loadingFlag: true
      }
    },
    mounted() {
      
      let bundleUrl = weex.config.bundleUrl
      bundleUrl = new String(bundleUrl);
      let params = bundleUrl.slice(bundleUrl.indexOf('?')+1) 
      let paramsArr = params.split('&')
      
      paramsArr.forEach(el => {
        let singleParamArr = el.split('=')
        switch(singleParamArr[0]) {
          case 'requestItemsId': this.requestItemsId = singleParamArr[1]; break;
          case 'requestType': this.requestType = singleParamArr[1]; break;
          case 'headerImgUrl': this.headerImgUrl = singleParamArr[1]; break;
        }      
      });
      if(this.requestType === 'uatm') {
        this.uatmRequestOptions.apiOptions.favorites_id = this.requestItemsId
        this.getUatmRequest()
      }else if(this.requestType === 'material') {
        this.materialRequestOptions.apiOptions.material_id = this.requestItemsId
        this.getMaterialRequest()
      }

    },
    components: {
      Item
    },
    methods: {
      onback() {
        navigator.pop({
          animated: "false"
        })
      },
      onScroll(e) {
        e.contentOffset.y < -3000
          ? (this.toHeaderBtnFlag = true)
          : (this.toHeaderBtnFlag = false);
      },
      onToHeader(e) {
        dom.scrollToElement(this.$refs.header, { offset: 0, animated: 'true' });
      },
      onloadmore() {
        let _items = []
        if(!this.items.length) {
          return false 
        } 
        if(this.loadingFlag) {
         
          if(this.requestType === 'uatm') {
            
            this.uatmRequestOptions.apiOptions.page_no ++ 
            
            this.uatmFetch(res => {
              
              if(res.data.tbk_uatm_favorites_item_get_response) {
                this.console = res
                _items = res.data.tbk_uatm_favorites_item_get_response.results.uatm_tbk_item               
                this.items.push.apply(this.items, _items)
              }else {
                this.loadingFlag = false 
              }
            },err => {
              this.loadingFlag = false
            })

          }else if(this.requestType === 'material') { 
            this.materialRequestOptions.apiOptions.page_no ++         
            this.materialFetch(res => {
              if(res.data.tbk_dg_optimus_material_response) {
                _items = res.data.tbk_dg_optimus_material_response.result_list.map_data               
                this.items.push.apply(this.items, _items)

              }else {
                this.loadingFlag = false  
              }
            },err => {
              this.loadingFlag = false
            })
          }
        }else {
          return false
        }       
      },
      // 公共方法
      uatmFetch(resCallback,errCallback) {

        let url = formatURL(this.uatmRequestOptions.method,this.uatmRequestOptions.apiOptions)
        
        try {
          stream.fetch(
            {
              method: "GET",
              url: url,
              type: "json"            
            },
            res => {
              // this.console = res.data
              // let obj = JSON.parse(res.data)
              
              if(typeof resCallback === "function") {
                resCallback(res)
              }              
            },
            proRes => {
              
            }
          );
        } catch (err) {
          this.console = 222
          if(typeof errCallback === "function") {
            errCallback(err)
          }
        }      
      },
      getUatmRequest() {
        this.loadingFlag = true
        let _items = []
        this.uatmFetch(res => {
          
          if(res.data.tbk_uatm_favorites_item_get_response) {
            _items = res.data.tbk_uatm_favorites_item_get_response.results.uatm_tbk_item
            this.items = _items           
          }else {
            this.loadingFlag = false
          }
        },err => {
          
          this.loadingFlag = false
          modal.toast({
            message: '网络异常',
            duration: 0.3
          })
        })
        
      },
      materialFetch(resCallback,errCallback) {

        let url = formatURL(this.materialRequestOptions.method,this.materialRequestOptions.apiOptions)
        
        try {
          stream.fetch(
            {
              method: "GET",
              url: url,
              type: "json"
            },
            res => {
              if(typeof resCallback === "function") {
                resCallback(res)
              }              
            }
          );
        } catch (err) {
          if(typeof errCallback === "function") {
            errCallback(err)
          }
        }      
      },
      getMaterialRequest(itemArrName) {
        this.loadingFlag = true
        this.materialFetch(res => {
          if(res.data.tbk_dg_optimus_material_response) {
            let data = res.data.tbk_dg_optimus_material_response.result_list.map_data
            this.items = data 
          }else {
            this.loadingFlag = false
          }
        },err => {
          this.loadingFlag = false
          modal.toast({
            message: '网络异常',
            duration: 0.3
          })
        })
      },
      getCoupnAmount(item) {
        let _couponaAmount = "";
        let jianPos = item.coupon_info.search("减")
        _couponaAmount = parseInt(item.coupon_info.slice(jianPos + 1, -1)) 

        return _couponaAmount
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
      // 公共方法结束
    },
  }
</script>

<style scoped>
  .iconfont {    
    font-family: iconfont;
  }

  .header {
    position: sticky;
    justify-content: center;
    background-color: #fff;
  }

  .size {
    width: 750px;
    height: 200px;
  }

  .back-btn {
    font-size: 40px; 
    color: #fff;
    margin: 0 20px; 
  }

  .header-bgi {
    position: absolute;
    top: 0;
    background-color: #fff;
  }

  .cell {
    padding-top: 12px;
  }

  .footer {
    width: 750px;
    height: 80px;    
    padding-bottom: 20px;
    align-items: center;   
  }

  .footer-text {
    color: #666;
    font-size: 24px;
    margin-top:10px;
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
  
  .toHeader-icon {
    color: #ff6000;
    font-size: 40px;
  }
</style>