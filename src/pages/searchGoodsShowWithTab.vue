<template>
  <div class="goods-show">
    <header class="header size" :ref="`header${key}`" >         
      <image :src="options.headerPicUrl" class="size header-bgi" resize="cover"></image>
      <text class="back-btn iconfont" @click="onback">&#xe625;</text>
    </header>
    <!-- <text>{{console}}</text> -->
    <wxc-tab-page ref="wxc-tab-page"
                :tab-titles="tabTitles"
                :tab-styles="tabStyles"
                title-type="text"
                need-slider="true"
                :tab-page-height="tabPageHeight"
                @wxcTabPageCurrentTabSelected="wxcTabPageCurrentTabSelected" >
      <list v-for="(v,index) in items"
            :key="index"
            class="item-container"
            :style="{ height: (tabPageHeight - tabStyles.height) + 'px' }"
            @scroll="onScroll" >
        
        <cell></cell>
        <cell v-for="(item,key) in v"
              class="cell"
              :key="key"
              :accessible="true" >
          <wxc-pan-item  @wxcPanItemPan="wxcPanItemPan">
            <item :item="item"></item>
          </wxc-pan-item>
           
        </cell>
        <cell>
          <div class="toHeader" @click="onToHeader" v-if="toHeaderBtnFlag">
            <text class="iconfont toHeader-icon">&#xe666;</text>
          </div> 
        </cell> 
        <cell class="footer" @appear="loadingMore">          
          <image style="width:70px;height:70px" :src="imgSrc.loading" v-if="loadingFlag"></image>
          <text class="footer-text" v-else>~没有更多了~</text>        
        </cell>
      </list>
    </wxc-tab-page>

  </div> 
</template>

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
    height: 250px;
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

  .item-container {
    width: 750px;
    margin-top: 10px; 
    background-color: #f8f8f8;
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
<script>
  import { WxcTabPage, WxcPanItem, Utils, BindEnv } from 'weex-ui';
  import item from '../components/SingleItem.vue';
  import { formatURL } from "../util/formatURL.js";
  import config from '../util/mall.config.js';
  import imgLocationSrc from '../util/imgLocationSrc.js';

  const stream = weex.requireModule("stream");
  const modal = weex.requireModule('modal');
  const dom = weex.requireModule("dom");
  const navigator = weex.requireModule("navigator-pri");
  export default {
    components: { WxcTabPage, WxcPanItem, item },
    data: () => ({
      console: '',
      imgSrc: {
        loading: imgLocationSrc.gif.loading
      },
      searchRequestOptions: {
        method: 'taobao.tbk.dg.material.optional',
        apiOptions: {
          adzone_id: config.adzoneId,
          platform: '2',
          page_size: "20",
          page_no: 1,
          // material_id: 6707,
          sort: 'total_sales_des', // 排序_des（降序），排序_asc（升序），销量（total_sales），淘客佣金比率（tk_rate）， 累计推广量（tk_total_sales），总支出佣金（tk_total_commi），价格（price）
          q: '' // 查询词         
        }
      },
      searchFiltrate: {
        start_price: '',
        end_price: '',
        is_tmall: false, // 是否商城商品，设置为true表示该商品是属于淘宝商城商品，设置为false或不设置表示不判断这个属性
        has_coupon: true, // 是否有优惠券，设置为true表示该商品有优惠券，设置为false或不设置表示不判断这个属性
        is_overseas: false, // 是否海外商品，设置为true表示该商品是属于海外商品，设置为false或不设置表示不判断这个属性
        need_free_shipment: true, // 是否包邮，true表示包邮，空或false表示不限
        need_prepay: false
      },
      tabTitles: [],
      tabStyles: {
        bgColor: '#FFFFFF',
        titleColor: '#333333',
        activeTitleColor: '#fa513a',
        activeBgColor: '#FFFFFF',
        isActiveTitleBold: false,
        width: 160,       
        height: 80,
        fontSize: 24,
        activeFontSize: 28,
        hasActiveBottom: true,
        activeBottomColor: '#fa513a',
        activeBottomHeight: 3,
        activeBottomWidth: 130,
        textPaddingLeft: 0,
        textPaddingRight: 0,
        normalBottomColor: '#ebecee',
        normalBottomHeight: 1,
        leftOffset: 0,
        rightOffset: 0
      },
      items: [],
      tabPageHeight: 1334,
      materialIdsArr: [],
      loadingFlag: true,
      currentPage: 0,
      options: {   
        headerPicUrl: '',
        searchWordStr: '',
        searchFiltrateStr: '',
        q: null
      },
      toHeaderBtnFlag: true
    }),
    created () {
      this.tabPageHeight = Utils.env.getPageHeight() - 120;

      let bundleUrl = weex.config.bundleUrl
      bundleUrl = new String(bundleUrl);
      
      let urlString = bundleUrl.slice(bundleUrl.indexOf('?')+1)
      let optionArr = urlString.split('&')
      let key = ''
      let val = ''
      optionArr.forEach((item,i) => {
        key = item.slice(0,item.indexOf('='))
        val = item.slice(item.indexOf('=')+1)
        this.options[key] = val
        
      })

      let titleArr = this.options.searchWordStr.split('##') 
      titleArr.forEach((item,i) => {      
        this.tabTitles.push({title:item})      
      })

      if(this.options.searchFiltrateStr !== null) {
        let searchFiltrateStr = this.options.searchFiltrateStr
        let searchFiltrateArr = searchFiltrateStr.split('##')
        
        let key = ''
        let val = ''
        searchFiltrateArr.forEach(item => {
          key = item.split('=')[0]
          val = item.split('=')[1]
          this.searchFiltrate[key] = val
        })        
      }
      
      if(this.options.q == 'null') {
        this.searchRequestOptions.apiOptions.q = this.tabTitles[this.currentPage].title
      }else {
        this.searchRequestOptions.apiOptions.q = this.options.q
      }

      

      this.items = [...Array(this.tabTitles.length).keys()].map(i => []);
      
      this.fetch(res => { 
        
        if(res.data.tbk_dg_material_optional_response) {  
                                  
          let items = res.data.tbk_dg_material_optional_response.result_list.map_data
          
          Vue.set(this.items, 0, items);
          
        }else {
          this.loadingFlag = false
        }
      },err => {
        this.loadingFlag = false
      })
    },
    methods: {
      onback() {
        navigator.pop({
          animated: "false"
        })
      },
      fetch(resCallback,errCallback) {
        this.loadingFlag = true
        let filtrate = this.searchFiltrate
        
        for(let key in filtrate) {
          if(filtrate[key]){
            Vue.set(this.searchRequestOptions.apiOptions, key, filtrate[key])
          }else {
            Vue.delete(this.searchRequestOptions.apiOptions, key)
          }         
        }
        
        let url = formatURL(this.searchRequestOptions.method,this.searchRequestOptions.apiOptions)
        
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
      loadingMore() {
        
        if(!this.items[this.currentPage].length) {
          return
        }else {
          this.searchRequestOptions.apiOptions.page_no++
          this.fetch(res => {
            if(res.data.tbk_dg_material_optional_response) {          
              let items = res.data.tbk_dg_material_optional_response.result_list.map_data
              this.items[this.currentPage].push.apply(this.items[this.currentPage],items)     
            }else {
              this.loadingFlag = false
            }
          },err => {
            this.loadingFlag = false;
          })
        } 
      },
      wxcTabPageCurrentTabSelected (e) {
        let index = e.page
        this.currentPage = index
    
        this.searchRequestOptions.apiOptions.q = this.tabTitles[index].title
        this.fetch(res => {
          if(res.data.tbk_dg_material_optional_response) {          
            let items = res.data.tbk_dg_material_optional_response.result_list.map_data
            Vue.set(this.items, index, items);      
          }else {
            this.loadingFlag = false
          }
        },err => {
          this.loadingFlag = false
        })
      },
      wxcPanItemPan (e) {
        if (BindEnv.supportsEBForAndroid()) {
          this.$refs['wxc-tab-page'].bindExp(e.element);
        }
      },
      onToHeader() {
        // this.console = this.$refs['header0']
        dom.scrollToElement(this.$refs['header0'][0], { offset: 0, animated: 'true' });
      },
      // onScroll(e) {
        
      //   e.contentOffset.y < -3000
      //     ? (this.toHeaderBtnFlag = true)
      //     : (this.toHeaderBtnFlag = false)
      // },
    }
  };
</script>