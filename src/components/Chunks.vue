<template>
	<div class="chunks">
    
		<list class="list">
      <cell class="cell">
        <div class="B-box"  v-for="(item,i) in row1" :key="i" :index="i" @click="onItemClick">
          <image class="B-icon" :src="item.icon"></image>
          <text class="B-title">{{item.title}}</text>        
        </div>
      </cell>
      <cell>
        <div>
          <text>{{console}}</text>
        </div>
      </cell>
    </list>
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
            title: '特价好物',
            icon: 'file:///android_asset/images/icon/sales.png',
          }, 
          {
            title: '极有家',
            icon: 'file:///android_asset/images/icon/jiyoujia.png',
          },
          {
            title: '酷玩DIY',
            icon: 'file:///android_asset/images/icon/diy.png',
          }
        ],
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
          is_tmall: true, // 是否商城商品，设置为true表示该商品是属于淘宝商城商品，设置为false或不设置表示不判断这个属性
          has_coupon: true, // 是否有优惠券，设置为true表示该商品有优惠券，设置为false或不设置表示不判断这个属性
          is_overseas: false, // 是否海外商品，设置为true表示该商品是属于海外商品，设置为false或不设置表示不判断这个属性
          need_free_shipment: true, // 是否包邮，true表示包邮，空或false表示不限
          need_prepay: false
        },       
      }
    },
    methods: {
      onItemClick(e) {
        let index = e.target.attr.index

        switch(index) {
          
        }
        let url = this.getSearchUrl()

        navigator.push({
          url: getJumpBaseUrl("goodsShow",url),
          animated: "true"
        });
      },
      getSearchUrl() {
        let filtrate = this.searchFiltrate
         
        for(let key in filtrate) {
          if(filtrate[key]){
            Vue.set(this.searchRequestOptions.apiOptions, key, filtrate[key])
          }else {
            Vue.delete(this.searchRequestOptions.apiOptions, key)
          }         
        }
        let url = formatURL(this.searchRequestOptions.method,this.searchRequestOptions.apiOptions)
        return url;
      }
    },
  }
</script>

<style scoped>
	.chunks {
    /*width: 690px;*/
    /* height: 160px; */
    background-color: #fff;
    margin: 20px;
    border-radius: 30px;
    /*box-shadow: 3px 5px 3px rgba(0, 0, 0, .3);*/
    /*margin-top: -25px;*/
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
    /*padding-top: 5px;
    padding-bottom: 5px;
    padding-left: 5px;
    padding-right: 5px;*/
    padding-top: 10px;
  }
  .B-title {
    width: 140px;
    font-size: 24px;
    text-align: center;
    padding-top: 15px;
    color: #666;
  }
  .B-icon {
    width: 100px;
    height: 100px;
  }
</style>