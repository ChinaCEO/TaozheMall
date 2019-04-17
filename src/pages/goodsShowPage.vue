<template>
  <div class="goods-show">
    <header class="header size" ref="header">         
      <image :src="headerPicUrl" class="size header-bgi" resize="cover"></image>
      <text class="back-btn iconfont" @click="onback">&#xe625;</text>
    </header>
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
            :style="{ height: (tabPageHeight - tabStyles.height) + 'px' }">
        
        <cell :ref="`header${index}`"></cell>
        <cell v-for="(item,key) in v"
              class="cell"
              :key="key"
              :accessible="true" >
          <wxc-pan-item  @wxcPanItemPan="wxcPanItemPan">
            <item :item="item"></item>
          </wxc-pan-item>
           
        </cell>
        <cell v-if="toHeaderBtnFlag">
          <div class="toHeader" @click="onToHeader">
            <text class="iconfont toHeader-icon">&#xe666;</text>
          </div> 
        </cell> 
        <cell class="footer" @appear="loadingMore">          
          <image style="width:70px;height:70px" src="file:///android_asset/images/loading.gif" v-if="loadingFlag"></image>
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

  const stream = weex.requireModule("stream");
  const modal = weex.requireModule('modal');
  const dom = weex.requireModule("dom");
  const navigator = weex.requireModule("navigator-pri");
  export default {
    components: { WxcTabPage, WxcPanItem, item },
    data: () => ({
      console: '',
      requestOptions: {
        method: "taobao.tbk.dg.optimus.material",
        apiOptions: {
          fields: "pic_url",
          page_size: 20,
          page_no: 1,
          adzone_id: config.adzoneId,
          material_id: 0
        }
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
        leftOffset: 10,
        rightOffset: 10
      },
      items: [],
      tabPageHeight: 1334,
      headerPicUrl: '',
      materialIdsArr: [],
      loadingFlag: true,
      currentPage: 0
    }),
    created () {
      this.tabPageHeight = WXEnvironment.deviceHeight;

      let bundleUrl = weex.config.bundleUrl
      bundleUrl = new String(bundleUrl);

      let urlString = bundleUrl.slice(bundleUrl.indexOf('?')+1)
      let optionArr = urlString.split('&') 

      this.headerPicUrl = optionArr[0].split('=')[1]
    
      let materialIds = optionArr[1].slice(optionArr[1].indexOf('=')+1).slice(0,-2)
      let materialIdsArr = materialIds.split('##')
      let itemArr = []
      materialIdsArr.forEach((item,i) => {
        itemArr = item.split('=')
        this.tabTitles.push({title: itemArr[0]})
        this.materialIdsArr.push(itemArr[1])
      });
     
      this.items = [...Array(this.tabTitles.length).keys()].map(i => []);

      this.requestOptions.apiOptions.material_id = this.materialIdsArr[0]
      this.fetch(res => {
        if(res.data.tbk_dg_optimus_material_response) {          
          let items = res.data.tbk_dg_optimus_material_response.result_list.map_data
          Vue.set(this.items, 0, items);      
        }else {
          this.loadingFlag = false
        }
      },err => {
        this.loadingFlag = false
      })
      
      // Vue.set(this.tabList, 0, this.demoList);
    },
    mounted() {
            
    },
    methods: {
      onback() {
        navigator.pop({
          animated: "false"
        })
      },
      fetch(resCallback,errCallback) {
        
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
      loadingMore() {
        if(!this.items[this.currentPage].length) {
          return
        }else {
          this.requestOptions.apiOptions.page_no++
          let url = formatURL(this.requestOptions.method,this.requestOptions.apiOptions)

          try {
            stream.fetch(
              {
                method: "GET",
                url: url,
                type: "json"            
              },
              res => { 
                if(res.data.tbk_dg_optimus_material_response) {          
                  let items = res.data.tbk_dg_optimus_material_response.result_list.map_data
                  this.items[this.currentPage].push.apply(this.items[this.currentPage],items)     
                }else {
                  this.loadingFlag = false
                }             
              }
            );
          } catch (err) {
            this.loadingFlag = false;
          }
        } 
      },
      wxcTabPageCurrentTabSelected (e) {
        let index = e.page
        this.currentPage = index

        this.requestOptions.apiOptions.material_id = this.materialIdsArr[index]
        this.fetch(res => {
          if(res.data.tbk_dg_optimus_material_response) {          
            let items = res.data.tbk_dg_optimus_material_response.result_list.map_data
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
      }
    }
  };
</script>