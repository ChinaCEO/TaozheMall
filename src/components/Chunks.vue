<template>
	<div class="chunks">
    
		<div class="list">
      <!-- <image class="size" src="file:///android_asset/images/icon/chunk-bg.png" resize="cover"></image> -->
      <div class="cell">
        <div class="B-box"  v-for="(item,i) in row1" :key="i" :index="i" @click="onItemClick">
          <image class="B-icon" :src="item.icon"></image>
          <text class="B-title">{{item.title}}</text>        
        </div>
      </div>
    </div>
	</div>
</template>

<script>
  import { formatURL } from "../util/formatURL.js";
  import config from '../util/mall.config.js';
  import { getJumpBaseUrl } from "../util/getJumpBaseUrl.js";

  const modal = weex.requireModule('modal');
  const dom = weex.requireModule("dom");
  const navigator = weex.requireModule("navigator-pri");

  export default {
    data () {
      return {
        console: '',
        row1: [
          {
            title: '天天9.9',
            icon: 'file:///android_asset/images/icon/99.png',
          }, 
          {
            title: '20元封顶',
            icon: 'file:///android_asset/images/icon/199.png',
          }, 
          {
            title: '每日精选',
            icon: 'file:///android_asset/images/icon/jingxuan.png',
          }, 
          {
            title: '爆款好货',
            icon: 'file:///android_asset/images/icon/baokuan.png',
          },
          {
            title: '大牌上新',
            icon: 'file:///android_asset/images/icon/pinpai.png',
          }
        ],
        searchFiltrate: {
          start_price: '',
          end_price: '',
          is_tmall: false, // 是否商城商品，设置为true表示该商品是属于淘宝商城商品，设置为false或不设置表示不判断这个属性
          has_coupon: true, // 是否有优惠券，设置为true表示该商品有优惠券，设置为false或不设置表示不判断这个属性
          is_overseas: false, // 是否海外商品，设置为true表示该商品是属于海外商品，设置为false或不设置表示不判断这个属性
          need_free_shipment: true, // 是否包邮，true表示包邮，空或false表示不限
          need_prepay: false
        },
        options: {   
          headerPicUrl: '',
          searchWordStr: '',
          searchFiltrateStr: '',
          q: null
        },
        queryWords: [
          '女装',
          '家居家装',
          '数码家电',
          '鞋包配饰',
          '美妆个护',
          '男装',
          '内衣',
          '母婴',
          '食品',
          '运动户外'
        ]       
      }
    },
    methods: {
      onItemClick(e) {
        let index = e.target.attr.index

        let searchWordStr = ''
        let searchFiltrateStr = ''
        let options = {}

        switch(index) {
          case 0: this.searchFiltrate.start_price = 0;
                  this.searchFiltrate.end_price = 10;
                  searchWordStr = this.getSearchWords(this.queryWords);
                  searchFiltrateStr = this.getSearchFiltrateStr(this.searchFiltrate)
                  this.options.headerPicUrl = 'http://img.alicdn.com/tps/i3/TB1owX4MXXXXXcbXpXXx6Mp7VXX-1440-380.png';
                  this.options.searchWordStr = this.getSearchWords(this.queryWords);
                  this.options.searchFiltrateStr = this.getSearchFiltrateStr(this.searchFiltrate);
                  navigator.push({
                    url: getJumpBaseUrl("searchGoodsShowWithTab",this.options),
                    animated: "true"
                  });          
                  break;
          case 1: this.searchFiltrate.start_price = 10;
                  this.searchFiltrate.end_price = 20;
                  searchWordStr = this.getSearchWords(this.queryWords);
                  searchFiltrateStr = this.getSearchFiltrateStr(this.searchFiltrate)
                  this.options.headerPicUrl = 'http://img.alicdn.com/tps/i3/TB1owX4MXXXXXcbXpXXx6Mp7VXX-1440-380.png';
                  this.options.searchWordStr = this.getSearchWords(this.queryWords);
                  this.options.searchFiltrateStr = this.getSearchFiltrateStr(this.searchFiltrate);
                  navigator.push({
                    url: getJumpBaseUrl("searchGoodsShowWithTab",this.options),
                    animated: "true"
                  });          
                  break;
          case 2: let webUrl = `https://mos.m.taobao.com/union/chaojidanpin20181201?pid=${config.pid}`
                  // let withBack = 'true'

                  // let option = { 
                  //   webUrl: webUrl, 
                  //   withBack: withBack 
                  // }
                  let url = 
                  navigator.push({
                    url: "tblinkto://" + webUrl,
                    animated: "true"
                  });          
                  break;
          case 3:  let option_baokuan = { 
                    headerPicUrl: 'https://gw.alicdn.com/tfs/TB18HP4yntYBeNjy1XdXXXXyVXa-750-275.jpg_q75.jpg',
                    materialId: 3837
                  }
                  
                  navigator.push({
                    url: getJumpBaseUrl('goodsShowNoTab', option_baokuan),
                    animated: "true"
                  })        
                  break;
          case 4: let webUrl_dapai = `https://mos.m.taobao.com/union/superbrand_nhj_nvwangjieersantao?pid=${config.pid}`
                  // let withBack_dapai = 'true'

                  // let option_dapai = { 
                  //   webUrl: webUrl_dapai, 
                  //   withBack: withBack_dapai 
                  // }
                  
                  navigator.push({
                    url: "tblinkto://" + webUrl_dapai,
                    animated: "true"
                  });          
                  break;
        }

        
      },
      getSearchWords(queryWords) {
        let urlStr = ''
        queryWords.forEach((item,i) => {
          urlStr += `${item}##`
        })
        urlStr = urlStr.slice(0,-2)
        return urlStr
      },
      getSearchFiltrateStr(searchFiltrate) {
        let str = ''
        for (let key in searchFiltrate) {
          str += `${key}=${searchFiltrate[key]}##`
        }
        str = str.slice(0,-2)
        return str
      }
    },
  }
</script>

<style scoped>
	.chunks {
    background-color: #fff;
    margin: 20px;
    border-radius: 30px;   
  }

  .size {
    width: 750px;
    height: 160px;
  }

  .cell {
    flex-direction: row;
    justify-content: center;
    align-items: center;
  }
  .B-box {
    width: 140px;
    justify-content: center;
    align-items: center;
    padding-top: 10px;
  }
  .B-title {
    width: 140px;
    font-size: 20px;
    text-align: center;
    padding-top: 15px;
    color: #666;
  }
  .B-icon {
    width: 80px;
    height: 80px;
  }
</style>