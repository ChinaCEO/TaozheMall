<template>
  <list @scroll="onscroll">
    <header class="header" ref="header">
      <div class="header-wrapper size">
        <image src="http://img.alicdn.com/tfs/TB1afcmiVzqK1RjSZFoXXbfcXXa-750-275.png_q75.jpg" class="size header-bgi" resize="cover"></image>
        <text class="back-btn iconfont" @click="onback">&#xe625;</text>
      </div>               
    </header>
    <!-- <cell><text>{{console}}</text></cell> -->
    <cell v-for="(item,i) in items" :key="i">
      <jddItem :item="item"></jddItem>
    </cell>
    <cell>
      <div class="toHeader" @click="onToHeader" v-if="toHeaderBtnFlag">
        <text class="iconfont toHeader-icon">&#xe666;</text>
      </div> 
    </cell> 
    <cell class="footer" @appear="loadingMore">          
      <image style="width:70px;height:70px" src="file:///android_asset/images/loading.gif" v-if="loadingFlag"></image>
      <text class="footer-text" v-else>~没有更多了~</text>        
    </cell>
  </list>
</template>

<script>
  import { formatURL } from "../util/formatURL.js";
  import config from '../util/mall.config.js';
  import jddItem from '../components/JddItem.vue';

  const stream = weex.requireModule("stream");
  const modal = weex.requireModule('modal');
  const dom = weex.requireModule("dom");
  const navigator = weex.requireModule("navigator-pri");
  const animation = weex.requireModule("animation");

  export default {
    data() {
      return {
        console: '',
        materialRequestOptions: { // 通用物料api
          method: 'taobao.tbk.dg.optimus.material',
          apiOptions: {
            adzone_id: config.adzoneId,
            page_size: 20,
            page_no: 1,
            material_id: '4071'      
          }
        },
        items: [],
        loadingFlag: true,
        toHeaderBtnFlag: false
      }
    },
    components: {
      jddItem
    },
    created() {
      
      let url = formatURL(this.materialRequestOptions.method,this.materialRequestOptions.apiOptions)
     
      try {
        stream.fetch(
          {
            method: "GET",
            url: url,
            type: "json"            
          },
          res => {            
            if (res.data.tbk_dg_optimus_material_response) {              
              let items = res.data.tbk_dg_optimus_material_response.result_list.map_data;
              this.items = items
              
            } else {
              this.loadingFlag = false;
            }
          }
        );
      } catch (err) {
        this.loadingFlag = false;
      }
    },
    methods: {
      onback() {       
        navigator.pop({
          animated: "false"
        })
      },
      loadingMore() {
        this.materialRequestOptions.apiOptions.page_no ++
        
        let url = formatURL(this.materialRequestOptions.method,this.materialRequestOptions.apiOptions)
     
        try {
          stream.fetch(
            {
              method: "GET",
              url: url,
              type: "json"            
            },
            res => {       
              if (res.data.tbk_dg_optimus_material_response) {              
                let items = res.data.tbk_dg_optimus_material_response.result_list.map_data;
                this.items.push.apply(this.items,items)
                
              } else {
                this.loadingFlag = false;
              }
            }
          );
        } catch (err) {
          this.loadingFlag = false;
        }
      },
      onscroll(e){
        e.contentOffset.y < -3000 ? (this.toHeaderBtnFlag = true) : (this.toHeaderBtnFlag = false)
      },
      onToHeader() {       
        dom.scrollToElement(this.$refs.header, { offset: 0, animated: 'true' });
      },     
    }
  }  
</script>

<style scoped>
  .iconfont {    
    font-family: iconfont;
  }

  .header {
    position: sticky;
    
  }

  .header-wrapper {
    justify-content: center;
    background-color: #fff;
  }

  .size {
    width: 750px;
    height: 160px;
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

</style>