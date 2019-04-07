<template>
  <waterfall 
    class="find" 
    ref="find" 
    show-scrollbar="true"
    :column-width="columnWidth" 
    :column-count="columnCount" 
    :column-gap="columnGap" 
    :left-gap='leftGap' 
    :right-gap='rightGap'
    @scroll="onScroll" >
    <header class="sticky-header" ref="header" :style="{height: headerHeight + 'px'}">
      <div class="sticky-header-wrapper" :style="{opacity:headOpacity}">
        <text class="sticky-header-title">发现·精彩</text>
      </div>      
    </header>
    <header>   
      <div class="header-wrapper size">
        <image src="file:///android_asset/images/header/find-header.jpg" class="size header-bgi" resize="cover"></image>        
        <text class="title">发现·精彩</text>
        
        <div class="slider-wrapper">
          <wxc-ep-slider :slider-id="autoSliderId"
                          :card-length='cardLength'
                          ref="wxc-ep-slider"
                          :card-s="cardSize"
                          :auto-play="true"
                          :interval="1000" >
            <wxc-pan-item v-for="(item,index) in sliderItems[0]"
                          :key="index"
                          :ext-id="index"
                          class="slider slider-size"                     
                          @wxcPanItemPan="wxcPanItemPan"
                          @wxcPanItemClicked="wxcPanItemClicked"
                          :slot="`card${index}_${autoSliderId}`"
                          :accessible="true" 
                          :aria-label="item.item_url" >
              <image :src="item.pic_url" class="slider-size" resize="cover"></image>
            
              <div class="slider-item-inner">
                <div class="slider-saler-logo">
                  <div class="slider-saler-logo-wrapper">
                    <image :src="item.saler_logo" class="slider-saler-logo-size" resize="cover"></image>
                  </div>              
                </div>
                <div class="slider-item-title">
                  <text class="slider-item-title-txt">{{item.title}}</text>
                </div>
              </div>
            </wxc-pan-item>
          
            <wxc-pan-item v-for="(item,index) in sliderItemsDealed"
                          :key="index"
                          :ext-id="index"
                          class="slider slider-size"                     
                          @wxcPanItemPan="wxcPanItemPan"
                          @wxcPanItemClicked="wxcPanItemClicked"
                          :slot="`card${index}_${autoSliderId}`"
                          :accessible="true" 
                          :aria-label="item.item_url" >
              <image :src="item.pic_url" class="slider-size" resize="cover"></image>
            
              <div class="slider-item-inner">
                <div class="slider-saler-logo">
                  <div class="slider-saler-logo-wrapper">
                    <image :src="item.saler_logo" class="slider-saler-logo-size" resize="cover"></image>
                  </div>              
                </div>
                <div class="slider-item-title">
                  <text class="slider-item-title-txt">{{item.title}}</text>
                </div>
              </div>
            </wxc-pan-item>
          </wxc-ep-slider>
        </div>
        
      </div>      
    </header>
    
    <header>
      <div class="hot-title">
        <image src="file:///android_asset/images/find-slider-title.png" class="slider-title-size" resize="cover"></image>
      </div>      
    </header>
    <header v-for="(el,j) in bannerItems" :key="j" :itemUrl="el.item_url" class="chunk-banner-wrapper">

      <!-- 块banner -->
      <chunk-banner 
        :img-src="el.img_src" 
        :item-list="chunkBannerItemList(j)"
        :request-items-id="getMoreItemsId(j)"
        :request-type="getMoreItemsType(j)"
        :header-img-url="getHeaderImgUrl(j)" ></chunk-banner>
    </header>
    
    <header>
      <div class="hot-title">
        <image src="file:///android_asset/images/find-hot-title.png" class="hot-title-size" resize="cover"></image>
      </div>      
    </header>
    <!-- 结果页 -->
    <cell class="cell" v-for="item in hotResultList" :key="item.num_iid" :ref="'item'+item.num_iid">
      <item :coupon-url='`https://${item.coupon_share_url}`' 
            :img-src='item.white_image ? item.white_image : item.pict_url'
            :title='item.title'
            :coupon-amount='item.coupon_amount'
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
        <text class="indicator-text" v-else>~没有更多了~</text>
      </div>     
    </header>
  </waterfall>
</template>

<script>
  import { WxcEpSlider, WxcPanItem, BindEnv } from 'weex-ui';
  import ChunkBanner from '../components/ChunkBanner.vue';
  import Item from '../components/WaterSingleItem.vue';
  import { formatURL } from "../util/formatURL.js";
  import config from '../util/mall.config.js';

  const stream = weex.requireModule("stream");
  const modal = weex.requireModule('modal');
  const dom = weex.requireModule("dom");
  const navigator = weex.requireModule("navigator-pri");
  const animation = weex.requireModule("animation");
  export default {
    data() {
      return {
        console: '',        
        autoSliderId: 1,
        cardSize: {
          width: 440,
          height: 220,
          spacing: 20,
          scale: 1
        },
        columnWidth: "auto",
        columnCount: "2",
        columnGap: "10",
        leftGap: "0",
        rightGap: "0",
        sliderItems: [
          {
            title: '佐丹奴跨界合作-折射有趣灵魂',
            saler_nick: '佐丹奴官方旗舰店',
            label: '新品推广',
            pic_url: 'http://gtms03.alicdn.com/tps/i3/TB1HvTTMOLaK1RjSZFxSuumPFXa.jpg',
            item_url: 'https://s.click.taobao.com/t?e=m%3D2%26s%3DqeBJPWadnnUcQipKwQzePCperVdZeJviK7Vc7tFgwiFRAdhuF14FMcCMDwBVXjwBt4hWD5k2kjMrqjSR7PHsIYA%2BctsFsY%2B%2FT4r1ZK4%2B0gYhEBo4WDGaveAHzrCXfkqNDeI1I34CqBFAFEHVckI7b70C5V1zD%2Byq9s86iV2%2F1mTwdM%2FKpa7%2Foxd2u8ajBB%2BTI%2B0eShuxei5lMmW5IerAc%2BwPLJ%2B6HFkhM8CKw7Wbdu%2FZXGJcQHTELgg0PPxj%2F%2BhXGYrz2%2Bj1z2BfsZ%2BHfEwFpporYQmk4xUociFdo98Qxt6aK2EJpOMVKB98ph5U3qOcftR3FUvrS2ifglZiCiVtvUPyEgNQzg%2FACFEIW1fQzwBafAYY9F5Y1wXe3D1UK9HcQ4OolEgeW%2BCdadSWLlHNzz0lSO1OX%2BX1NVtJVESd3eG8Hw2wWKcovcYOae24fhW0',
            saler_logo: 'http://img.alicdn.com/shop-logo/1b/f0/T1g4bSFcpcXXb1upjX.jpg'
          },
          {
            title: '夏季新品 短袖T恤第四批',
            saler_nick: '英爵伦男装旗舰店',
            label: '优惠促销',
            pic_url: 'http://gtms03.alicdn.com/tps/i3/TB1dHe_KY2pK1RjSZFsSuuNlXXa.jpg',
            item_url: 'https://s.click.taobao.com/t?e=m%3D2%26s%3DUDGKuw8%2FdSIcQipKwQzePCperVdZeJviK7Vc7tFgwiFRAdhuF14FMRv0WwCcgMc6lovu%2FCElQOsrqjSR7PHsIYA%2BctsFsY%2B%2FT4r1ZK4%2B0gYhEBo4WDGavfurrSWRdTutZnzupmkY57%2BM%2BhtH71aX6m7RW4yYr9cxVDRcjpSFvpd%2BPGXoAieI2mY9hpAAaXcf8Igslecrxt7m9gdMys3pcMxC6MStqlw%2BindRv4o52K7WJGOxhi6PIMmg%2BBgdAr22aXvhOrX387VcNjqEs2IhfdtVEIf4tytvCMdbdxUV%2BAM8cEBxspUrJfCrpLbOiWAV7xOuDcL3qTKZK6rHvww7mFxrIKoMTjLsHG2U0cvHDktQ7fufGh7mA%2B8KqCGAWV%2Bt0WIWpWko3PzHJ5HyRm%2FgE5xHsrat2RLvfDnG5Q6aIetHykeh407aZWfbNbcKrRmaomfkDJRs%2BhU%3D',
            saler_logo: 'http://img.alicdn.com/shop-logo/a3/07/TB1BLXndmcqBKNjSZFgSut_kXXa.jpg'
          },
          {
            title: '春季多样穿搭专辑',
            saler_nick: '秋水伊人官方旗舰店',
            label: '优惠促销',
            pic_url: 'http://gtms03.alicdn.com/tps/i3/TB1p_i.MSzqK1RjSZFLSuwn2XXa.jpg',
            item_url: 'https://s.click.taobao.com/t?e=m%3D2%26s%3DclL7WvZRzvIcQipKwQzePCperVdZeJviK7Vc7tFgwiFRAdhuF14FMV7865p7Xalyt4hWD5k2kjMrqjSR7PHsIYA%2BctsFsY%2B%2FT4r1ZK4%2B0gYhEBo4WDGavXTl8N39axzbKKH5QkoZz7fO54LQ%2FVw1L%2FJgK3KRx%2F6B8uo3sUwlHMwW95D0t3aOittvUdx5il7wdrdVNusZXvMzpO4DW7Hjw3POrhtaJZUwnCEK1hYMLpoWcd25QYPAPqAx9%2BvyIhjwHFJXdzz%2FKpu7L4vjk3JI1AsoiHVmoYV6u8E1Tjlt8v0%2FsKnloQca439xGXOivt2tXDY6hLNiIX2nrh6iS9DI9NRKMrCTJVaCVmrszOPWrNtYUNtlAkZHGC7C%2F3B4yEFh0Hkac9pX1ypJfWQG%2BPADjl%2F811MNYqi8NG6knbOR81ScIQrWFgwumsYMXU3NNCg%2F',
            saler_logo: 'http://img.alicdn.com/shop-logo/6b/29/TB1VxOXfN9YBuNjy0FfSutIsVXa.jpg'
          },
          {
            title: '融融春意',
            saler_nick: '三枪官方旗舰店',
            label: '新品推广',
            pic_url: 'http://gtms03.alicdn.com/tps/i3/TB1Aws.LW6qK1RjSZFmSut0PFXa.jpg',
            item_url: 'https://s.click.taobao.com/t?e=m%3D2%26s%3DagCBZTrwMdEcQipKwQzePCperVdZeJviK7Vc7tFgwiFRAdhuF14FMYQlgmvEm1cB79%2FTFaMDK6QrqjSR7PHsIYA%2BctsFsY%2B%2FT4r1ZK4%2B0gYhEBo4WDGavcN54XOM8cCvcsq%2BmgCK7p%2FcHtRpEUy6ROeDSqQwHsrHopd2ATeWpR0W95D0t3aOimW2DYBEmYvrmO0oPhsdJpjI%2BHJx2cUfQJYowEPZs8T%2FYMdnbUofDWLy%2B%2BIQZj2wDvOf7pCGlRWjiTZRYDBQOEE9JUjtTl%2Fl9RON158I4I3Uw3%2FR4W2ZBqEhhQs2DjqgEA%3D%3D',
            saler_logo: 'http://img.alicdn.com/shop-logo/i2/383602586/O1CN01krjfkM1UyQj2b9VFV_!!383602586.jpg'
          },
          {
            title: '春夏热卖女童礼服公主裙',
            saler_nick: 'ivybride服饰旗舰店',
            label: '优惠促销',
            pic_url: 'http://gtms03.alicdn.com/tps/i3/TB1IQb9MrPpK1RjSZFFSuu5PpXa.jpg',
            item_url: 'https://s.click.taobao.com/t?e=m%3D2%26s%3Dmg%2B%2FUnlnqIUcQipKwQzePCperVdZeJviK7Vc7tFgwiFRAdhuF14FMaaA5WMFljaXt4hWD5k2kjMrqjSR7PHsIYA%2BctsFsY%2B%2FT4r1ZK4%2B0gYhEBo4WDGavfqdnkL5xD%2F%2FSj%2FPTelccwWM%2BhtH71aX6m7RW4yYr9cxVDRcjpSFvpd%2BPGXoAieI2mY9hpAAaXcf8Igslecrxt7m9gdMys3pcMxC6MStqlw%2BTYqfMmEOmaZGTReaigjMrLc3Hj52BolFaXvhOrX387VcNjqEs2IhfdtVEIf4tytvCMdbdxUV%2BAM8cEBxspUrJfCrpLbOiWAV7xOuDcL3qTKZK6rHvww7mETsiw0lhWz30CzuqNRGLsJQ7fufGh7mAz%2BxIRSEkLFz0WIWpWko3PzhRrc%2B8wVA%2F2vHYLSYNVBUfDnG5Q6aIevk%2Fk%2BpZSkqa7u02npBckd%2FomfkDJRs%2BhU%3D',
            saler_logo: 'http://img.alicdn.com/shop-logo/ff/43/TB10yp3MXXXXXa4apXXSutbFXXX.jpg'
          },
        ],
        bannerItems: [
          {
            title: '女装',
            img_src: 'file:///android_asset/images/banners/woman.jpg',
            item_url: ''
          },
          {
            title: '母婴',
            img_src: 'file:///android_asset/images/banners/infant.jpg',
            item_url: ''
          },
          {
            title: '大牌',
            img_src: 'file:///android_asset/images/banners/famous.jpg',
            item_url: ''
          },
          {
            title: '潮流',
            img_src: 'file:///android_asset/images/banners/fashion.jpg',
            item_url: ''
          },
          // {
          //   title: '有好货',
          //   img_src: 'http://img.alicdn.com/tps/i2/TB1v9XRMXXXXXcgXVXXx6Mp7VXX-1440-380.png',
          //   item_url: ''
          // },
          // {
          //   title: '极有家',
          //   img_src: 'http://img.alicdn.com/tps/TB1_S.nKpXXXXXPXpXXXXXXXXXX-1440-380.png',
          //   item_url: ''
          // },
          // {
          //   title: '酷动城',
          //   img_src: 'http://img.alicdn.com/tps/TB18AAmKpXXXXamXpXXXXXXXXXX-1440-380.png',
          //   item_url: ''
          // },
          // {
          //   title: 'DIY',
          //   img_src: 'http://img.alicdn.com/tps/TB12PovKpXXXXXcXXXXXXXXXXXX-1440-380.png',
          //   item_url: ''
          // }
        ],
        loadingFlag: true,
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
            page_size: 3,
            page_no: 1,
            fields: 'num_iid,title,pict_url,zk_final_price,user_type,volume,coupon_click_url,coupon_info',
            favorites_id: ''     
          }
        },
        materialId: {
          hotRecommend: 4094,
          infant: 4041,
          goodStuffs: 4092,
          fashionGoods: 4093
        },
        uatmFavoritesId: {
          woman: 19278990,
          infant: 19279502,
          goodStuffs: 19279610,
          fashionGoods: 19279629
        },
        hotResultList: [],
        womanItems: [],
        infantItems: [],
        goodStuffsItems: [],
        fashionGoodsItems: [],
        toHeaderBtnFlag: false,
        scrollFlag: false,
        headOpacity: 0,
        headerHeight: 0
      }
    },
    computed: {
      sliderItemsDealed() {
        let itemArr = this.sliderItems
        
        itemArr.push(this.sliderItems[0],this.sliderItems[1])
        
        itemArr.unshift(this.sliderItems[this.sliderItems.length-4],this.sliderItems[this.sliderItems.length-3])
        return this.sliderItems
      },
      cardLength() {
        return this.sliderItems.length 
      }
    },
    components: {
      WxcEpSlider,
      WxcPanItem,
      ChunkBanner,
      Item
    },
    beforeMount() {
      
      this.uatmRequestOptions.apiOptions.favorites_id = this.uatmFavoritesId.woman
      this.getUatmRequest('womanItems')

      this.uatmRequestOptions.apiOptions.favorites_id = this.uatmFavoritesId.infant
      this.getUatmRequest('infantItems')
      
      this.uatmRequestOptions.apiOptions.favorites_id = this.uatmFavoritesId.goodStuffs
      this.getUatmRequest('goodStuffsItems')
      
      this.uatmRequestOptions.apiOptions.favorites_id = this.uatmFavoritesId.fashionGoods
      this.getUatmRequest('fashionGoodsItems')
           
      this.materialRequestOptions.apiOptions.material_id = this.materialId.hotRecommend
      this.getMaterialRequest('hotResultList',20)
            
    },
    methods: {
      wxcPanItemPan (e) {
        if (BindEnv.supportsEBForAndroid()) {
          this.$refs['wxc-ep-slider'].clearAutoPlay();
          this.$refs['wxc-ep-slider'].bindExp(e.element)
        }
      },
      wxcPanItemClicked (e) {
       
      },
     
      onScroll(e) {

        if(e.contentOffset.y < -130) {
          // this.scrollFlag = true

          this.headerHeight = 130
          if(this.headOpacity <= 1) {
            this.headOpacity = (Math.abs(e.contentOffset.y) - 130) / 150;
          }else {
            this.headOpacity = 1
          }
        }else {
          this.headerHeight = 0
          // this.scrollFlag = false
          this.headOpacity = 0
        }
       
        e.contentOffset.y < -3000
          ? (this.toHeaderBtnFlag = true)
          : (this.toHeaderBtnFlag = false);
      },
      onToHeader(e) {
        dom.scrollToElement(this.$refs.header, { offset: 0, animated: 'true' });
      },
      onloadmore() {
        if(!this.hotResultList.length) {
          return false 
        } 
        this.materialRequestOptions.apiOptions.page_no ++
        this.materialRequestOptions.apiOptions.page_size = 20
        this.materialFetch(res => {
          if(res.data.tbk_dg_optimus_material_response) {
            let data = res.data.tbk_dg_optimus_material_response.result_list.map_data
           
            this.hotResultList.push.apply(this.hotResultList, data)

          }else {
            this.loadingFlag = false  
          }
        },err => {
          this.loadingFlag = false
        })
      },
   
      chunkBannerItemList(index){
        switch(index) {
          case 0: return this.womanItems; break;
          case 1: return this.infantItems; break;
          case 2: return this.goodStuffsItems; break;
          case 3: return this.fashionGoodsItems; break;
        }
      },
      getMoreItemsId(index) {
        switch(index) {
          case 0: return this.uatmFavoritesId.woman; break;
          case 1: return this.materialId.infant; break;
          case 2: return this.materialId.goodStuffs; break;
          case 3: return this.materialId.fashionGoods; break;
        }
      },
      getMoreItemsType(index) {
        // uatm为选品库 material为通用物料
        switch(index) {
          case 0: return 'uatm'; break;
          case 1: return 'material'; break;
          case 2: return 'material'; break;
          case 3: return 'material'; break;
        }
      },
      getHeaderImgUrl(index) { // 获取头部图片
        switch(index) {
          case 0: return 'http://img.alicdn.com/tps/i4/TB1tyZqLVXXXXXKXFXXx6Mp7VXX-1440-380.png'; break;
          case 1: return 'http://img.alicdn.com/tps/i4/TB1sIJTMXXXXXbFXVXXx6Mp7VXX-1440-380.png'; break;
          case 2: return 'http://img.alicdn.com/tps/i2/TB1v9XRMXXXXXcgXVXXx6Mp7VXX-1440-380.png'; break;
          case 3: return 'file:///android_asset/images/header/fashion.jpg'; break;
        }
      },
      
      couponFinalPrice(item) {       
        let _final = Math.round(item.zk_final_price * 100 - item.coupon_amount*100) / 100
        return _final.toFixed(2)
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
      getUatmRequest(itemArrName) {
        this.loadingFlag = true
        let _items = []
        this.uatmFetch(res => {
          if(res.data.tbk_uatm_favorites_item_get_response) {
            _items = res.data.tbk_uatm_favorites_item_get_response.results.uatm_tbk_item
            this[itemArrName] = _items           
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
      materialFetch(resCallback,errCallback,pageSize) {
        if(pageSize) {
          this.materialRequestOptions.apiOptions.page_size = pageSize
        }        
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
      getMaterialRequest(itemArrName,pageSize) {
        this.loadingFlag = true
        this.materialFetch(res => {
          if(res.data.tbk_dg_optimus_material_response) {
            let data = res.data.tbk_dg_optimus_material_response.result_list.map_data
            this[itemArrName] = data 
          }else {
            this.loadingFlag = false
          }
        },err => {
          this.loadingFlag = false
          modal.toast({
            message: '网络异常',
            duration: 0.3
          })
        },pageSize)
      }
      // 公共方法结束
    }
  }
</script>

<style scoped>
  .iconfont {    
    font-family: iconfont;
  }

  .find {
    background-color: #ebecee;
  }

  .sticky-header {
    position: sticky;
  }

  .sticky-header-wrapper {
    
    padding-bottom: 10px; 
    /* border-bottom-style: solid;
    border-bottom-width: 2px;
    border-bottom-color: #ebecee; */
    align-items: center;
    justify-content: flex-end;
    flex:1;
    background-color: rgb(255, 255, 255);
  }

  .sticky-header-title {
    color: #fa513a;
    font-size: 36px;
  }

  .header {
    position: relative;
  }

  .size {
    width: 750px;
    height: 400px;
  }

  .header-wrapper {
    padding-bottom: 20px; 
    border-bottom-style: solid;
    border-bottom-width: 2px;
    border-bottom-color: #ebecee;
    align-items: center;
    justify-content: flex-end;
    background-color: #fff;
    position: relative;   
  }

  .header-bgi {
    position: absolute;
    top: 0;
  }

  .title {
    color: #fff;
    font-size: 36px;
    margin-bottom: 20px;
  }

  .slider-wrapper {   
    height: 220px;
  }
 
  .slider-title {
    align-items: center;
    justify-content: center;
    margin-bottom: 20px;   
  }

  .slider-title-size {
    width: 500px;
    height: 100px;
  }

  .slider-size {
    width: 440px;
    height: 220px;
  }
 
  .slider {   
    align-items: center;
    justify-content: center;
    position: relative;   
  }

  .slider-item-inner {
    position: absolute;
    bottom: 10px;
    left: 0;
    flex-direction: row;
  }

  .slider-saler-logo {
    padding: 5px;
    background-color: rgba(0, 0, 0, 0.6);
  }

  .slider-saler-logo-wrapper {
    width: 60px;
    height: 60px;
    border-radius: 60px;
  }

  .slider-saler-logo-size {
    width: 60px;
    height: 60px;
  }

  .slider-item-title {
    height: 70px;
    width: 300px;
    padding-left: 10px;
    padding-right: 10px;
    /* background-color: rgba(0, 0, 0, 0.4); */
    background-image: linear-gradient(to right, rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0));
    justify-content: center;
  }

  .slider-item-title-txt {
    color: #fff;
    font-size: 20px;
    lines: 2;
    font-weight: 500;
    line-height: 35px;
  }

  .slider-label {
    position: absolute;
    right: 0;
    top: 0;
    justify-content: center;
    align-items: center;
    background-color: rgba(251,53,26, .6);
    
  }

  .slider-label-txt {
    color: #fff;
    font-size: 20px;
  }

  .chunk-banner-wrapper {
    justify-content: center;
    align-items: center;
  }

  .hot-title {
    width: 750px;
    /* margin-top: 20px; */
    background-color: #fff;
    align-items: center;
    justify-content: center;
  }

  .hot-title-size {
    width: 495px;
    height: 100px;
  }

  .footer {
    width: 750px;
    height: 150px;    
    padding-bottom: 20px;
    align-items: center;  
  }

  .indicator-text {
    color: #666;
    font-size: 24px;
    margin-top:10px;
  }

  .cell {
    padding-top: 12px;
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