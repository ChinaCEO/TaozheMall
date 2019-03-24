<template>
  <scroller>
    <header class="header" ref="header">
      <div class="header-wrapper">
        <text class="title">分类</text>
      </div> 
      <div class="input-wrapper">            
        <search-bar-dis></search-bar-dis>
      </div>     
    </header>
    
    <div class="chunk-wrapper" v-for="(item,i) in list" :key="i">
      <div class="chunk" v-for="(chunk,j) in item" :key="'chunk'+j" :class="[chunk._bgc]" @click="onItemClick">
        <text class="chunk-title">{{chunk.title}}</text>
        <image :src="chunk.img_url" style="width:200px;height:200px" class="chunk-img"></image>
      </div>
    </div>
    <div class="footer"></div>
  </scroller>
</template>

<script>
  import SearchBarDis from "../components/SearchBarDis.vue";
  import { getJumpBaseUrl } from "../util/getJumpBaseUrl.js";
  import { store } from '../store.js'

  const navigator = weex.requireModule("navigator-pri");
  export default {
    data() {
      return {
        items: [
          {
            title: '女装',
            img_url: ''
          },
          {
            title: '男装',
            img_url: ''
          },
          {
            title: '食品',
            img_url: ''
          },
          {
            title: '手机',
            img_url: ''
          },
          {
            title: '男鞋',
            img_url: ''
          },
          {
            title: '女鞋',
            img_url: ''
          },
          {
            title: '汽车用品',
            img_url: ''
          },
          {
            title: '家居家装',
            img_url: ''
          },
          {
            title: '家电',
            img_url: ''
          },
          {
            title: '配饰',
            img_url: ''
          },
          {
            title: '美妆个护',
            img_url: ''
          },
          {
            title: '内衣',
            img_url: ''
          },
          {
            title: '母婴',
            img_url: ''
          },
          {
            title: '运动鞋',
            img_url: ''
          },
          {
            title: '运动衣',
            img_url: ''
          },
          {
            title: '图书',
            img_url: ''
          },
          {
            title: '家纺',
            img_url: ''
          },
          {
            title: '电脑配件',
            img_url: ''
          }          
        ],
        cols: 3,
        console: ''      
      }
    },
    computed: {
      itemsInit() {        
        this.items.forEach((el,i) => {
          i % 2 ? el._bgc = 'bgc-white' : el._bgc = 'bgc-gray' 
          el.img_url = `file:///android_asset/images/category/${el.title}.png`        
        })
        
        return this.items
      },
      list() {
        let list = []
        
        for(let i=0; i<this.itemsInit.length; i+this.cols) {
          list.push(this.itemsInit.slice(i,i+=this.cols))
        }

        return list
      }
    },
    components: {
      SearchBarDis
    },
    methods: {
      onItemClick(e) {
        
        navigator.push({
          url: getJumpBaseUrl("searchPage",e.currentTarget.children[0].attr.value),
          animated: "false"
        });
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

  .bgc-white {
    background-color: #fff;
  }

  .bgc-gray {
    background-color: #f9f9f9;
  }
  
  .header {
    position: sticky;      
  }

  .header-wrapper {
    height: 130px;
    margin:0 20px;
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

  .input-wrapper {
    width: 750px;
    height: 80px;
    align-items: center;
    justify-content: center;
    background-color: #EDF2F6;
  }

  .chunk-wrapper {
    flex-direction: row;
    margin: 0 20px;
  }

  .chunk {   
    flex:1;
    align-items: center;
    justify-content: center;
    /* border-style: solid;
    border-width: 2px;
    border-color: #fa513a; */
  }

  .chunk-title {
    color: #666;
    font-size: 28px;
    margin-top: 20px;
  }

  .chunk-img {
    margin: 25px 0;
  }

  .footer {
    padding: 55px 0;
  }
</style>
