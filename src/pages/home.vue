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
        <!-- <header style="position: sticky;"><text style="color: green;">{{console}}</text></header> -->
        <header class="sticky-header">
          <div class="sticky-header-box" :class="[!scrollFlag ? 'sticky-header-scroll' : '']" :style="{opacity:headOpacity}">            
            <search-bar-dis v-if="scrollFlag ? true : false"></search-bar-dis>
          </div>
        </header>
        <!-- <refresh class="refresh" @pullingdown="onpullingdown">
          <loading-indicator class="indicator"></loading-indicator>     
        </refresh> -->
        <header class="header" ref="header">
            <banner></banner>
            <chunks></chunks>
            <onsale></onsale>
            <div class="main-title-box">
                <text class="main-title">热销专区</text>
            </div>
            <scoller-row></scoller-row>
            <div class="main-title-box">
                <text class="main-title">精品推荐</text>
            </div>
        </header>
        <cell class="cell" v-for="(item) in items" :key="item.num_iid" :ref="'item'+item.num_iid">
            <div class="item" @click="itemOnClick" :couponUrl="item.coupon_click_url">
                <image class="item-photo" :src="item.pict_url" resize="cover"></image>
                <text class="item-title">{{item.title}}</text>
                <div class="item-price-box">
                    <div class="coupon">
                        <text class="iconfont coupon-icon">&#xe617;</text>
                        <text class="coupon-text">{{item.coupon_info}}</text>
                    </div>
                    <div class="price">
                        <text class="zk-price-txt">折后价</text>
                        <text class="zk-price-num">￥{{item.zk_final_price}}</text>
                    </div>
                </div>
            </div>
        </cell>
        <!-- <header class="water-footer" v-if="footerFlag" ref="footer">
      <text class="water-footer-text">~没有更多了,我是有底线的~</text>                
    </header> -->
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
          <image style="width:70px;height:70px" src="file:///android_asset/images/loading.gif" v-if="loadingFlag"></image>
          <text class="indicator-text" v-else>没有更多了...</text>
        </header>
    </waterfall>
</template>

<script>
  import Banner from "../components/Banner.vue";
  import Chunks from "../components/Chunks.vue";
  import Onsale from "../components/Onsale.vue";
  import ScollerRow from "../components/ScollerRow.vue";
  import SearchBarDis from "../components/SearchBarDis.vue";
  // import { store } from "../store.js";
  import { formatURL } from "../util/formatURL.js";
  import { getJumpBaseUrl } from "../util/getJumpBaseUrl.js";
  import CryptoJS from "crypto-js";

  const dom = weex.requireModule("dom");
  const stream = weex.requireModule("stream");
  const animation = weex.requireModule("animation");
  const navigator = weex.requireModule("navigator-pri");
  const modal = weex.requireModule('modal')
  export default {
    data() {
      return {
        requestOptions: {
          method: "taobao.tbk.dg.item.coupon.get",
          apiOptions: {
            adzone_id: "91627500240",
            platform: "2",
            page_size: "40",
            page_no: 1
          }
        },
        items: [],
        columnWidth: "auto",
        columnCount: "2",
        columnGap: "10",
        leftGap: "20",
        rightGap: "20",
        console: "",
        scrollFlag: false,
        // headerShow: true,
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
      ScollerRow,
      SearchBarDis
    },
    created() {
      
      let self = this;
      let url = formatURL(
        this.requestOptions.method,
        this.requestOptions.apiOptions
      );
      this.footerFlag = false;
      stream.fetch(
        {
          method: "GET",
          url: url,
          type: "json"
        },
        res => {
          try {
            if (res.data) {
              let items = res.data.tbk_dg_item_coupon_get_response.results.tbk_coupon;
              self.items = self.couponInfoDeal(items);
            } else {
              self.footerFlag = true;
            }
          } catch (err) {
            console.log(err);
          }
        }
      );
    },
    mounted() {},
    updated(e) {
      if (this.upFlag) {
        let el = this.$refs[this.upNumId][0];
        dom.scrollToElement(el, { offset: -950, animated: false });
        this.upFlag = false;
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
      onScroll(e) {
        // console.log(e)
        // this.console = this.$refs.waterfall
        e.contentOffset.y < -1000
          ? (this.toHeaderBtnFlag = true)
          : (this.toHeaderBtnFlag = false);
        if (e.contentOffset.y < -110) {
          this.scrollFlag = true;
          // store.commit("changeScrollFlag", true);
          if (this.headOpacity <= 1) {
            this.headOpacity = (Math.abs(e.contentOffset.y) - 110) / 250;
          } else {
            this.headOpacity = 1;
          }
        } else {
          this.scrollFlag = false;
          // store.commit("changeScrollFlag", false);
          // dom.scrollToElement(this.$refs.header,{offset:0,animated:false})
        }
      },
      // onloading(e) {
      //   this.loadingFlag = true;
      //   this.footerFlag = false;
      //   this.requestOptions.apiOptions.page_no++;
      //   let self = this;
      //   let url = formatURL(
      //     this.requestOptions.method,
      //     this.requestOptions.apiOptions
      //   );
        
      //   try {
      //     stream.fetch(
      //       {
      //         method: "GET",
      //         url: url,
      //         type: "json"
      //       },
      //       res => {
      //         if (res.data.tbk_dg_item_coupon_get_response.results.tbk_coupon) {
      //           let items =
      //             res.data.tbk_dg_item_coupon_get_response.results.tbk_coupon;
      //           items = self.couponInfoDeal(items);
      //           items.forEach(item => {
      //             self.items.push(item);
      //           });
      //           self.loadingFlag = false;
      //           self.upNumId = "item" + items[0].num_iid;
      //           self.upFlag = true;

      //           // let p = getComponentRect('viewport')
      //           // dom.scrollToElement(el,{offset:0,animated:true})
      //         } else {
      //           self.footerFlag = true;
      //         }
      //       }
      //     );
      //   } catch (err) {
      //     console.log(err);
      //   }

      //   setTimeout(() => {
      //     this.loadingFlag = false;
      //     // this.footerFlag = true
      //   }, 5000);
      // },
      onloadmore() {
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
      },
      itemOnClick(e) {      
        let couponUrl = e.currentTarget.attr.couponUrl;
        // store.commit("setCouponUrl", couponUrl);
        
        navigator.push({
          url: getJumpBaseUrl("coupon", couponUrl),
          animated: "true"
        });
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
}

.sticky-header {
  position: sticky;
  /*top:-100px;*/
  /* align-items: center;
    justify-content: center;*/
  /*background-image: linear-gradient(to top,#F51F23,#D41E21);*/
  /*position: relative;  */
  /*align-items: center;*/
}

.sticky-header-box {  
  flex-direction: row;
  align-items: flex-end;
  justify-content: center;
  height: 130px;
  padding-bottom: 20px;
  background-color: rgba(250,81,58, 0.9);
}

/* 头部背景显示 */
.sticky-header-scroll {
  position: absolute;
  top: 0;
}

.logo {
  font-size: 24px;
  background-color: rgba(51, 51, 51, 0.5);
  width: 70px;
  height: 70px;
  align-items: center;
  justify-content: center;
  border-radius: 70px;
}

.logo-letter {
  font-size: 20px;
  color: #fff;
}

.main-title-box {
  padding: 15px;
  margin: 20px;
  margin-top: 0;
  border-bottom-width: 3px;
  border-bottom-style: solid;
  border-bottom-color: #ebecee;
  align-items: center;
}

.main-title {
  color: #333;
  font-size: 36px;
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