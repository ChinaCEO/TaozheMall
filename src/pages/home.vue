<template>
  <waterfall 
    class="water-fall" 
    ref="waterfall" 
    show-scrollbar="true"
    :column-width="columnWidth" 
    :column-count="columnCount" 
    :column-gap="columnGap" 
    :left-gap='leftGap' 
    :right-gap='rightGap' 
    @scroll="onScroll" >
      <refresh class="refresh" @pullingdown="onpullingdown" @refresh="onrefresh">
        <text class="iconfont refresh-pre-icon">&#xe665;</text>
        <!-- <image style="width:100px;height:100px" :src="imgSrc.pullLoading"></image> -->
      </refresh>
      <!-- <header style="position: sticky;"><text style="color: green;">{{console}}</text></header> -->
      <header class="sticky-header">
        <div class="sticky-header-box" :class="[!scrollFlag ? 'sticky-header-scroll' : '']" :style="{opacity:headOpacity}">            
          <search-bar-dis v-if="scrollFlag ? true : false"></search-bar-dis>
        </div>
      </header>
      
      <header class="header" ref="header">
        <banner></banner>
        <chunks></chunks>
        <onsale></onsale>
        <onsale-four></onsale-four>
        <div class="main-title-box">
          <image style="width:500px;height:80px" :src="imgSrc.title.nanZhuangRec" v-if="loadingFlag"></image>            
        </div>
        <scoller-row :list="lists.man20"></scoller-row>   
        <div class="main-title-box">
          <image style="width:500px;height:80px" :src="imgSrc.title.nvZhuangRec" v-if="loadingFlag"></image>
        </div> 
        <scoller-row :list="lists.woman20"></scoller-row>        
      </header>
      <header>
        <div class="main-title-box">
          <image style="width:500px;height:80px" :src="imgSrc.title.haoQuanTuiJian" v-if="loadingFlag"></image>
        </div>
      </header>
      <cell class="cell" v-for="(item) in items" :key="item.num_iid" :ref="'item'+item.num_iid">
        <item :item="item"></item>
      </cell>
      <header v-if="toHeaderBtnFlag">
        <div class="toHeader" @click="onToHeader">
          <text class="iconfont">&#xe666;</text>
        </div>           
      </header>
      <!-- <loading :display="loadingFlag ? 'show' : 'hide'" class="loading" @loading="onloading" ref="loading">
          <text class="indicator-text" v-if="!loadingFlag">加载更多...</text>
          <loading-indicator class="loading-indicator" v-if="loadingFlag"></loading-indicator>
          <text class="indicator-text" v-if="loadingFlag">玩命加载中...</text>
          <text class="indicator-text" v-if="footerFlag">没有更多了...</text>
      </loading> -->
      <header class="footer" ref="footer" @appear="onloadmore">
        <image style="width:70px;height:70px" :src="imgSrc.loading" v-if="loadingFlag"></image>
        <text class="indicator-text" v-else>没有更多了...</text>
      </header>
  </waterfall>
</template>

<script>
  import Banner from "../components/Banner.vue";
  import Chunks from "../components/Chunks.vue";
  import Onsale from "../components/Onsale.vue";
  import OnsaleFour from "../components/OnsaleForFour.vue";
  import ScollerRow from "../components/ScollerRow.vue";
  import SearchBarDis from "../components/SearchBarDis.vue";
  import Item from '../components/waterSingleItem.vue';
  import { formatURL } from "../util/formatURL.js";
  import { getJumpBaseUrl } from "../util/getJumpBaseUrl.js";
  import CryptoJS from "crypto-js";
  import config from '../util/mall.config.js';
  import imgLocationSrc from '../util/imgLocationSrc.js';

  const dom = weex.requireModule("dom");
  const stream = weex.requireModule("stream");
  const animation = weex.requireModule("animation");
  const navigator = weex.requireModule("navigator-pri");
  const modal = weex.requireModule('modal')
  export default {
    data() {
      return {
        console: "",
        imgSrc: {
          title: {
            nanZhuangRec: imgLocationSrc.title.nanZhuangRec,
            nvZhuangRec: imgLocationSrc.title.nvZhuangRec,
            haoQuanTuiJian: imgLocationSrc.title.haoQuanTuiJian
          },
          loading: imgLocationSrc.gif.loading,
          pullLoading: imgLocationSrc.gif.pullLoading
        },
        requestOptions: {
          method: "taobao.tbk.dg.item.coupon.get",
          apiOptions: {
            platform: "2",
            page_size: "20",
            page_no: 1,
            adzone_id: config.adzoneId
          }
        },
        uatmRequestOptions: { // 选品库api
          method: 'taobao.tbk.uatm.favorites.item.get',
          apiOptions: {
            adzone_id: config.adzoneId,
            platform: 2,
            page_size: 10,
            page_no: 1,
            fields: 'num_iid,title,pict_url,zk_final_price,user_type,volume,coupon_click_url,coupon_info',
            favorites_id: ''     
          }
        },
        favoritesId: {
          man20: '19344940',
          woman20: '19344941'
        },
        lists: {
          man20: [],
          woman20:[]
        },
        items: [],
        columnWidth: "auto",
        columnCount: "2",
        columnGap: "10",
        leftGap: "20",
        rightGap: "20",
        scrollFlag: false,
        headOpacity: 0,
        loadingFlag: true,  // 控制底部加载状态
        toHeaderBtnFlag: false,
        footerFlag: false,
        upNumId: "",
        upFlag: false
      };
    },
    components: {
      Banner,
      Chunks,
      Onsale,
      OnsaleFour,
      ScollerRow,
      SearchBarDis,
      Item
    },
    created() {
      this.uatmRequestOptions.apiOptions.favorites_id = this.favoritesId.man20
      this.uatmFetch(res => {
        
        if(res.data.tbk_uatm_favorites_item_get_response) {
          let _items = res.data.tbk_uatm_favorites_item_get_response.results.uatm_tbk_item
          
          this.lists.man20 = _items           
        }else {
          modal.toast({
            message: '网络异常',
            duration: 0.3
          })
        }
      },err => {
        modal.toast({
          message: '网络异常',
          duration: 0.3
        })
      })

      this.uatmRequestOptions.apiOptions.favorites_id = this.favoritesId.woman20
      this.uatmFetch(res => {
        if(res.data.tbk_uatm_favorites_item_get_response) {
          let _items = res.data.tbk_uatm_favorites_item_get_response.results.uatm_tbk_item
          this.lists.woman20 = _items           
        }else {
          modal.toast({
            message: '网络异常',
            duration: 0.3
          })
        }
      },err => {
        modal.toast({
          message: '网络异常',
          duration: 0.3
        })
      })
      
      this.loadingFlag = true;
      this.couponFetch(res => {
        if (res.data.tbk_dg_item_coupon_get_response) {              
          let items = res.data.tbk_dg_item_coupon_get_response.results.tbk_coupon;
          this.items = this.couponInfoDeal(items);
        } else {
          this.loadingFlag = false;
        }
      },err => {
        this.loadingFlag = false;
      })
    },
    methods: {
      couponFetch(resCallback,errCallback) {
        let url = formatURL(this.requestOptions.method,this.requestOptions.apiOptions)
        
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
      onpullingdown(e) {
        console.log(e)
      },
      onScroll(e) {       
        e.contentOffset.y < -3000
          ? (this.toHeaderBtnFlag = true)
          : (this.toHeaderBtnFlag = false);
        if (e.contentOffset.y < -110) {
          this.scrollFlag = true;
          // store.commit("changeScrollFlag", true);
          if (this.headOpacity <= 1) {
            this.headOpacity = (Math.abs(e.contentOffset.y) - 110) / 150;
          } else {
            this.headOpacity = 1;
          }
        } else {
          this.scrollFlag = false;
        }
      },
      onloadmore() {
        if(!this.items.length) {
          return;
        }
        this.footerFlag = true;
        this.requestOptions.apiOptions.page_no++
        
        let url = formatURL(
          this.requestOptions.method,
          this.requestOptions.apiOptions
        );
        
        try {
          stream.fetch(
            {
              method: "GET",
              url: url,
              type: "json"
            },
            res => {
              if (res.data.tbk_dg_item_coupon_get_response.results.tbk_coupon) {
                let items = res.data.tbk_dg_item_coupon_get_response.results.tbk_coupon;
                items = this.couponInfoDeal(items);
                items.forEach(item => {
                  this.items.push(item);
                });
              } else {
                this.footerFlag = false;
              }
            }
          );
        } catch (err) {
          this.footerFlag = false;
        }

      },
      onToHeader(e) {
        dom.scrollToElement(this.$refs.header, { offset: 0, animated: 'true' });
      }
    }
  };
</script>

<style scoped>
.iconfont {
  color: #ff6000;
  font-size: 40px;
  font-family: iconfont;
  /*font-weight: bold;*/
}

.water-fall {
  margin-bottom: 90px;
  background-color: #f8f8f8;
}

.refresh {
  align-items: center;
  justify-content: center;
}

.refresh-pre-icon {
  font-weight: 700;
}

.sticky-header {
  position: sticky;
}

.sticky-header-box {  
  flex-direction: row;
  align-items: flex-end;
  justify-content: center;
  height: 150px;
  padding-bottom: 20px;
  /* background-color: rgba(250,81,58, 0.9); */
  background-color: rgba(235, 236, 238, 0.95);
}

/* 头部背景显示 */
.sticky-header-scroll {
  position: absolute;
  top: 0;
}


.main-title-box {
  width: 710px;
  padding: 15px;
  margin: 0 20px;
  border-bottom-width: 3px;
  border-bottom-style: solid;
  border-bottom-color: #ebecee;
  align-items: center;
  background-color: #fff;
}

.cell {
  padding-top: 6px;
  padding-bottom: 6px;
}

.item {
  /*height: 450px;*/
  background-color: #ffffff;
  background-color: #fff;
  border-width: 2px;
  border-color: #ebecee;
}

.item-photo {
  width: auto;
  height: 360px;
  margin: 2px;
  margin-bottom: 10px;
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

.water-footer {
  align-items: center;
  padding: 20px;
  /*border-top-width: 3px;
    border-top-style: solid;
    border-top-color: #ebecee;*/
}

.water-footer-text {
  font-size: 30px;
}

.loading {
  width: 750px;
  height: 200px;
  -ms-flex-align: center;
  -webkit-align-items: center;
  -webkit-box-align: center;
  align-items: center;
}

.indicator-text {
  color: #bbb;
  font-size: 20px;
  text-align: center;
}

.loading-indicator {
  width: 40px;
  height: 40px;
  color: #bbb;
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