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
    <header class="banner">
      <banner-sm></banner-sm>      
    </header>
    <!-- <header>
      <text>{{console}}</text>
    </header> -->
    <cell class="cell" v-for="(item) in items" :key="item.num_iid" :ref="'item'+item.num_iid">
      <item :item="item"></item>
    </cell>
    <header v-if="toHeaderBtnFlag">
      <div class="toHeader" @click="onToHeader">
        <text class="iconfont">&#xe666;</text>
      </div>           
    </header>
    <header class="footer" ref="footer" @appear="onloadmore">
      <image style="width:70px;height:70px" :src="imgSrc.loading" v-if="loadingFlag"></image>
      <text class="indicator-text" v-else>~没有更多了~</text>
    </header>
  </waterfall>
</template>

<script>
  import { formatURL } from "../util/formatURL.js";
  import { getJumpBaseUrl } from "../util/getJumpBaseUrl.js";
  import config from '../util/mall.config.js';
  import BannerSm from '../components/BannerSm.vue';
  import Item from '../components/waterSingleItem.vue';
  import imgLocationSrc from '../util/imgLocationSrc.js';

  const getImei = weex.requireModule('getImei');
  const stream = weex.requireModule("stream");
  const modal = weex.requireModule('modal');
  const dom = weex.requireModule("dom");
  const navigator = weex.requireModule("navigator-pri");

  export default {
    data() {
      return {
        imgSrc: {
          loading: imgLocationSrc.gif.loading,
          
        },
        requestOptions: {
          method: "taobao.tbk.dg.optimus.material",
          apiOptions: {
            adzone_id: config.adzoneId,
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
      BannerSm,
      Item
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
  
      onScroll(e) {
        
        e.contentOffset.y < -1000
          ? (this.toHeaderBtnFlag = true)
          : (this.toHeaderBtnFlag = false);
      },
      onToHeader(e) {
        dom.scrollToElement(this.$refs.header, { offset: 0, animated: 'true' });
      },
      onloadmore() {
        if(!this.items.length) {
          return;
        }
        this.requestOptions.apiOptions.page_no ++
        this.fetch(res => {
          if(res.data.tbk_dg_optimus_material_response) {
            let data = res.data.tbk_dg_optimus_material_response.result_list.map_data
            this.items.push.apply(this.items, data)

          }else {
            this.loadingFlag = false  
          }
        },err => {
          this.loadingFlag = false
        })
      },
      
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
    /* margin:0 20px 20px 20px; */
    margin-bottom: 20px;
    padding: 0 20px;
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

  .indicator-text {
    color: #666;
    font-size: 28px;
  }
</style>