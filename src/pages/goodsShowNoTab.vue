<template>
  <div class="goods-show">
    <header class="header size">         
      <image :src="headerPicUrl" class="size header-bgi" resize="cover"></image>
      <text class="back-btn iconfont" @click="onback">&#xe625;</text>
    </header>
    
    <list class="item-container"
          :style="{ height: tabPageHeight + 'px' }"
          @scroll="onScroll" >
      
      <cell ref="header"></cell>
      <cell v-for="(item,key) in items"
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
    height: 260px;
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
  import { Utils } from 'weex-ui';
  import item from '../components/SingleItem.vue';
  import { formatURL } from "../util/formatURL.js";
  import config from '../util/mall.config.js';
  import imgLocationSrc from '../util/imgLocationSrc.js';

  const stream = weex.requireModule("stream");
  const navigator = weex.requireModule("navigator-pri");
  const dom = weex.requireModule("dom");
  export default {
    components: { item },
    data: () => ({
      console: '',
      imgSrc: {
        loading: imgLocationSrc.gif.loading
      },
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
      tabPageHeight: 1334,
      option: {
        headerPicUrl: '',
        materialId: ''
      },
      items: [],
      headerPicUrl: '',
      loadingFlag: true,
      toHeaderBtnFlag: false
    }),
    created () {
      this.tabPageHeight = Utils.env.getPageHeight() - 130;
      
      let bundleUrl = weex.config.bundleUrl
      bundleUrl = new String(bundleUrl);
      
      let urlString = bundleUrl.slice(bundleUrl.indexOf('?')+1)
      let optionArr = urlString.split('&') 

      let key = ''
      let val = ''
      optionArr.forEach(item => {
        key = item.split('=')[0]
        val = item.split('=')[1]
        this.option[key] = val
      });
      
      this.headerPicUrl = this.option.headerPicUrl
      this.requestOptions.apiOptions.material_id = this.option.materialId
      
      this.fetch(res => {
        
        if(res.data.tbk_dg_optimus_material_response) {          
          let items = res.data.tbk_dg_optimus_material_response.result_list.map_data
          this.items = items  
        }else {
          this.loadingFlag = false
        }
      },err => {
        this.loadingFlag = false
      })
      
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
        if(!this.items.length) {
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
                  this.items.push.apply(this.items,items)     
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
      onToHeader() {
        dom.scrollToElement(this.$refs.header, { offset: 0, animated: 'true' });
      },
      onScroll(e) {
        
        e.contentOffset.y < -3000
          ? (this.toHeaderBtnFlag = true)
          : (this.toHeaderBtnFlag = false)
      },
    }
  };
</script>