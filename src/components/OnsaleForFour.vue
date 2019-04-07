<template>  
  <div class="onsale">
    <div class="title">
      <text class="title-txt">品质优选</text>      
    </div>
    <!-- <text>{{console}}</text> -->
    <div class="main-wrapper">
      <div class="wrapper-flex left-wrapper">
        <div class="wrapper-flex left-top-wrapper" @click="onLeftTop">
          <text class="title-size left-top-title">品牌券</text>
          <text class="description">你值得入手的品牌尖货推荐</text>
          <image :src="imgUrl.pinPai" resize="contain" class="img-size"></image>
        </div>
        <div class="wrapper-flex left-bottom-wrapper" @click="onLeftBottom">
          <text class="title-size left-bottom-title">精品推荐</text>
          <text class="description">精选好货领券直播</text>
          <image :src="imgUrl.gaoYong" resize="contain" class="img-size"></image>
        </div>
      </div>
      <div class="wrapper-flex right-wrapper">
        <div class="wrapper-flex right-top-wrapper">
          <text class="title-size right-top-title">好券直播</text>
          <text class="description">大牌好货推荐</text>
          <image :src="imgUrl.haoQuan" resize="contain" class="img-size"></image>
        </div>
        <div class="wrapper-flex right-bottom-wrapper" @click="onRightBottom">
          <text class="title-size right-bottom-title">母婴专区</text>
          <text class="description">分阶段母婴用品推荐</text>
          <image :src="imgUrl.muYing" resize="contain" class="img-size"></image>
        </div>
      </div>      
    </div>
  </div>
</template>

<script>
  import { getJumpBaseUrl } from "../util/getJumpBaseUrl.js";
  import config from '../util/mall.config.js';
  import { formatURL } from "../util/formatURL.js";

  const storage = weex.requireModule('storage');
  const navigator = weex.requireModule("navigator-pri");
  const stream = weex.requireModule("stream");
  const dom = weex.requireModule("dom");

	export default {
		data() {
      return {
        console: '',
        requestOptions: {
          method: "taobao.tbk.dg.optimus.material",
          apiOptions: {
            fields: "pic_url",
            page_size: 1,
            page_no: 1,
            adzone_id: config.adzoneId,
            material_id: 0
          }
        },
        materialId: {
          pinPai: 3793, // 品牌券
          haoQuan: 3788, // 好券直播
          gaoYong: 13366, // 高佣
          muYing: 4041 // 母婴
        },
        imgUrl: {
          pinPai: '',
          haoQuan: '',
          gaoYong: '',
          muYing: ''
        },
        gaoYongMaterialIds:{
          '综合': 13366,
          '女装': 13367,
          '家居家装': 13368,
          '数码家电': 13369,
          '鞋包配饰': 13370,
          '美妆个护': 13371,
          '男装': 13372,
          '内衣': 13373,
          '母婴': 13374,
          '食品': 13375,
          '运动户外': 13375
        },
        muyingMaterialIds: {
          '备孕': 4040,
          '0至6个月': 4041,
          '7至12个月': 4042,
          '1至3岁': 4043,
          '4至6岁': 4044,
          '7至12岁': 4045
        }
      }
    },
    created() {
      this.requestOptions.apiOptions.page_no = Math.ceil(Math.random() * 50)

      new Promise((resolve,reject) => {
        this.requestOptions.apiOptions.material_id = this.materialId.pinPai 
        this.fetch(res => {
          if(res.data.tbk_dg_optimus_material_response) {          
            let item = res.data.tbk_dg_optimus_material_response.result_list.map_data[0]
            resolve(item.white_image)
            
          }
        })
      })
      .then((val) => {
        this.imgUrl.pinPai = val
        return new Promise((resolve,reject) => {
          this.requestOptions.apiOptions.material_id = this.materialId.haoQuan 
          this.fetch(res => {
            if(res.data.tbk_dg_optimus_material_response) {
              let item = res.data.tbk_dg_optimus_material_response.result_list.map_data[0]
              resolve(item.white_image)
            }
          })
        })       
      })
      .then((val) => {
        this.imgUrl.haoQuan = val
        return new Promise((resolve,reject) => {
          this.requestOptions.apiOptions.material_id = this.materialId.gaoYong 
          this.fetch(res => {            
            if(res.data.tbk_dg_optimus_material_response) {
              let item = res.data.tbk_dg_optimus_material_response.result_list.map_data[0]
              resolve(item.white_image)
            }
          })
        })       
      })
      .then((val) => {
        this.imgUrl.gaoYong = val
        return new Promise((resolve,reject) => {
          this.requestOptions.apiOptions.material_id = this.materialId.muYing 
          this.fetch(res => {
            if(res.data.tbk_dg_optimus_material_response) {
              let item = res.data.tbk_dg_optimus_material_response.result_list.map_data[0]
              resolve(item.white_image)
            }
          })
        })       
      })
      .then((val) => {
        this.imgUrl.muYing = val
      })
    },
    beforeDestroy() {     
      
    },
    methods: {
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
      onLeftTop() {
        let webUrl = `https://mos.m.taobao.com/taokeapp/20181111ysppcjbkg?pid=${config.pid}`
        let withBack = 'true'

        let option = { 
          webUrl: webUrl, 
          withBack: withBack 
        }
        
        navigator.push({
          url: getJumpBaseUrl('coupon', option),
          animated: "true"
        })
      },
      onLeftBottom() {
        let gaoYongMaterialIdsStr = ''
        let gaoYongMaterialIds = this.gaoYongMaterialIds
        for (let key in gaoYongMaterialIds) {
          gaoYongMaterialIdsStr += `${key}=${gaoYongMaterialIds[key]}##`
        }
        let option = { 
          headerPicUrl: 'file:///android_asset/images/header/renqi.png',
          gaoYongMaterialIds: gaoYongMaterialIdsStr
        }
        
        navigator.push({
          url: getJumpBaseUrl('goodsShow', option),
          animated: "true"
        })
      },
      onRightBottom() {
        let muyingMaterialIdsStr = ''
        let muyingMaterialIds = this.muyingMaterialIds
        for (let key in muyingMaterialIds) {
          muyingMaterialIdsStr += `${key}=${muyingMaterialIds[key]}##`
        }
        let option = { 
          headerPicUrl: 'file:///android_asset/images/header/infant.png',
          muyingMaterialIds: muyingMaterialIdsStr
        }
        
        navigator.push({
          url: getJumpBaseUrl('goodsShow', option),
          animated: "true"
        })
      }     
    }
	}
</script>

<style scoped>
	.onsale {
    height: 700px;
    margin: 20px;
    background-color: #fff;
    border-radius: 30px;
    flex-direction: row;
    /*justify-content: center;*/
    position: relative;
    padding-bottom: 10px;
  }
  
  .title {
    position: absolute;
    left: 0;
    top: 18px;
    background-image: linear-gradient(to bottom,#04D2B9,#099E8A);
    width: 180px;
    height: 60px;
    border-top-right-radius: 60px;
    border-bottom-right-radius: 60px;
    justify-content: center;
    align-items: center;
    border-width: 2px;
    border-color: #04D2B9;
    border-style: solid;
  }

  .title-txt {
    color: #fff;
    font-size: 32px;
  }

  .main-wrapper {
    flex-direction: row;
    flex: 1;
    margin-top: 70px;
    padding: 10px; 
  }

  .wrapper-flex {
    flex: 1;
  }

  .left-wrapper {
    border-right-width: 3px;
    border-right-style: solid;
    border-right-color: #ebecee;
    padding-left: 10px;
  }

  .right-wrapper {
    padding-right: 10px;
  }


  .left-top-wrapper {
    /* background-color: red; */
    border-bottom-width: 3px;
    border-bottom-style: solid;
    border-bottom-color: #ebecee;    
  }

  
  .right-top-wrapper {
    /* background-color: yellow; */
    border-bottom-width: 3px;
    border-bottom-style: solid;
    border-bottom-color: #ebecee;
    padding-left: 20px;    
  }

  .right-bottom-wrapper {
    padding-left: 20px;
  }

  .description {
    color: #666;
    font-size: 22px;
    margin-top: 10px;
    lines: 1;
  }

  .img-size {
    margin-top: 20px;
    width: auto;
    height: 180px;
    margin-right: 10px;
  }

  .title-size {
    font-weight: 500;
    font-size: 32px;
    margin-top: 10px;
  }

  .left-top-title {    
    color: #069D86;
  }

  .left-bottom-title {
    color: #178DC1;
  }

  .right-top-title {   
    color: #FF4418;
  }

  .right-bottom-title {
    color: #FF9B1F;
  }
</style>
