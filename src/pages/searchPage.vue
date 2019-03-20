<template>
    <div class="search-page-header">
        <header class="header"> 
            <div class="header-box">
                <text class="back-btn iconfont" @click="onback">&#xe625;</text>
                <input
                   type="text"
                   autofocus="true"
                   placeholder="搜索商品、店铺等"
                   return-key-type="search" 
                   singleline="true"                  
                   class="search-bar"
                   ref="input"
                   :value="value"
                   @input="oninput"
                   @return="onreturn"
                   @focus="onfocus"
                   @blur="onblur" >
                </input>
                <text class="iconfont search-icon">&#xe639;</text>
                <div class="clear" @click="oninputClear" v-if="showInputClearBtn">
                  <text class="iconfont clear-icon">&#xe657;</text>
                </div>
            </div> 
            
        </header>
        <!-- <text>{{console}}</text> -->
        <!-- 搜索记录 -->
        <div class="search-record" v-if="!showResult && !showRecommend">
          
          <div class="search-title">
            <text class="search-title-txt">最近搜索</text>
            <div class="clear-search-record" v-if="hasRecord" @click="onclearRecord">
                <text class="iconfont clear-search-record-icon">&#xe657;</text>
            </div>
          </div>
          
          <div class="search-record-main" :class="[hasRecord ? '' : 'jc-center']">
            <text class="search-norecord-tip"  v-if="!hasRecord">还没有搜索记录噢~</text>            
            <text class="search-record-tip search-record-txt" 
              v-for="(item,i) in searchRecord" 
              :key="i" 
              @click="onrecordSearch"
              v-else>{{item}}</text>           
          </div>
        </div>

        <!-- 搜索页 -->
        
        <div class="search-result" v-if="showResult && !showRecommend" ref="searchResult" :style="{height: searchResultHeight + 'px'}">
          
          <div class="rank-box">
            <text class="rank-txt" :class="[sortWords.isCommon ?  'select-color' : '']" @click="onCommon">综合</text>
            <text class="rank-txt" :class="[sortWords.isVolume ?  'select-color' : '']" @click="onVolume">销量</text>
            <div class="price-sort" @click="onPrice">
              <text class="iconfont rank-txt" :class="[sortWords.isPrice ?  'select-color' : '']">价格</text>
              <div class="price-sort-arrow">
                <text class="iconfont rank-txt price-sort-arrow-up" :class="[sortWords.isPriceUp ?  'select-color' : '']">&#xe662;</text>
                <text class="iconfont rank-txt price-sort-arrow-down" :class="[sortWords.isPriceDown ?  'select-color' : '']">&#xe662;</text>
              </div>             
            </div>           
            <text class="iconfont rank-txt" @click="onfiltrate" :class="[isFiltrate ?  'select-color' : '']">筛选&#xe612;</text>
          </div>
          <item-list :list="searchResultList" v-if="hasResultFlag" @onloadmore="onloadmore"></item-list>
          <div class="no-result-box" v-else>
            <text class="no-result">没有搜索到相关产品~</text>
          </div>
          
          <WxcPopup :show="isPopupShow"
                    pos="right"
                    width="550"
                    popup-color="#FFFFFF"
                    stand-out="0"
                    @wxcPopupOverlayClicked="onpopupOverlayClicked">

            <scroller class="popup-scroller" show-scrollbar="false">
  
              <!-- 价格区间 -->
              <div class="price-choose">
                <text class="choose-title">价格区间(元)</text>
                <div class="price-input-box">
                  <input type="number"
                        placeholder="最低价"
                        max-length="8" 
                        return-key-type="done"
                        singleline="true"
                        class="price-input" 
                        @input="onStartPriceInput"
                        :value="filtrate.start_price"
                        @change="onStartPriceInputChange"/>
                  <text class="price-toline">———</text>
                  <input type="number"
                        placeholder="最高价"
                        max-length="8" 
                        return-key-type="done"
                        singleline="true"
                        class="price-input" 
                        @input="onEndPriceInput"
                        :value="filtrate.end_price"
                        @change="onEndPriceInputChange"
                        ref="endPriceInput"/>
                </div>
              </div>
              <!-- 发货地 -->
              <div class="position">
                <div class="choose-title-witharrow">
                  <text class="choose-title">发货地</text>
                  <text class="iconfont choose-title position-arrow" v-if="!positionListShow" @click="onpositionArrowClick">&#xe632;</text>
                  <text class="iconfont choose-title position-arrow" v-else  @click="onpositionArrowClick">&#xe62f;</text>
                </div>
                
                <div class="current-city">
                  <wxc-grid-select 
                    :list="choiseCityDeal"
                    :single="true"
                    cols="1"
                    :customStyles="gridSelectStyle"
                    @select="params => onchoiseCityCilck(params)"
                    v-if="choiseCity">
                  </wxc-grid-select>
                  <text class="location-line" v-if="choiseCity"></text>

                  <!-- 定位 -->
                  <div class="geo-box" :class="[locationSelectFlag ? 'geo-box-select' : '']" @click="onLocationSelect">
                    <text class="iconfont location-btn" :class="[locationSelectFlag ? 'location-btn-select' : '']" ref="location">{{location}}&nbsp;&#xe660;</text>
                    <image v-if="locationSelectFlag" class="btn-icon" src="https://gw.alicdn.com/tfs/TB1IAByhgMPMeJjy1XdXXasrXXa-38-34.png"></image>
                  </div>
                  <text class="iconfont reset-location-btn" @click="onregeo">&#xe65a;定位</text>
                </div>

                <div class="city-data" v-if="positionListShow">
                  <text class="choose-title choose-city-title">热门城市</text>
                  
                  <wxc-grid-select 
                    :list="cityDatadeal.hotCity"
                    :single="true"
                    cols="3"
                    :customStyles="gridSelectStyle"
                    @select="params => onHotCitySelect(params)">
                  </wxc-grid-select>

                  <text class="choose-title choose-city-title">城市</text>
                  
                  <wxc-grid-select 
                    :list="cityDatadeal.cities"
                    :single="true"
                    cols="3"
                    :customStyles="gridSelectStyle"
                    @select="params => onCitiesSelect(params)">
                  </wxc-grid-select>                
                </div>
              </div>

              <!-- 店铺评分 -->
              <div class="saler-dsr">
                <text class="choose-title">店铺评分(整数0-50000)</text>
                <div class="price-input-box">
                  <input type="number"
                        placeholder="起始分"
                        max-length="5" 
                        return-key-type="done"
                        singleline="true"
                        class="price-input" 
                        @input="onDsrInput"
                        @change="onDsrChange"
                        :value="filtrate.start_dsr"/>
                </div>
              </div>

              <!-- 服务 -->
              <div class="service-box">
                <text class="choose-title choose-city-title">服务</text>                  
                <wxc-grid-select 
                  :list="service"
                  :cols="3"
                  :customStyles="gridSelectStyle"
                  @select="params => onServiceSelect(params)">
                </wxc-grid-select>
              </div>
              
            </scroller>
            <div class="popup-footer">
              <div class="popup-footer-box">
                <text class="popup-footer-btn popup-footer-btn-reset" @click="onFilterReset">重置</text>
                <text class="popup-footer-btn popup-footer-btn-confirm" @click="onFilterConfirm">确定</text>
              </div>              
            </div>            
          </WxcPopup>
        </div>  

        <!-- 搜索推荐 -->
       
        <scroller class="recommend" v-if="showRecommend">
          <div class="recommend-tip" v-for="(tip,i) in recommend" :key="i">
            <text class="recommend-tip-txt" @click="onrecommendTipCilck">{{tip[0]}}</text>
          </div>
        </scroller>
        
    </div>
</template>

<script>
  // import searchResult from "../components/SearchResult.vue";
  import { formatURL } from "../util/formatURL.js";
  import { cityData } from "../util/cityData.js";
  import { WxcPopup } from 'weex-ui';
  import { WxcGridSelect } from 'weex-ui';
  import { Utils } from 'weex-ui';
  import itemList from "../components/SingleItemList.vue";
  import { store } from '../store.js'
  


  const domModule = weex.requireModule('dom');
  const navigator = weex.requireModule('navigator');
  const storage = weex.requireModule('storage');
  const stream = weex.requireModule("stream");
  const modal = weex.requireModule('modal');
  const animation = weex.requireModule('animation');
  const position = weex.requireModule('position');

  export default {
    data () {
      return {
        requestOptions: {
          method: 'taobao.tbk.dg.material.optional',
          apiOptions: {
            adzone_id: '91627500240',
            platform: '2',
            page_size: "20",
            page_no: 1,
            sort: 'tk_rate_des', // 排序_des（降序），排序_asc（升序），销量（total_sales），淘客佣金比率（tk_rate）， 累计推广量（tk_total_sales），总支出佣金（tk_total_commi），价格（price）
            q: '', // 查询词
            material_id: 6707
          }
        },
        filtrate: {
          start_price: '',
          end_price: '',
          start_dsr: '',
          is_tmall: false, // 是否商城商品，设置为true表示该商品是属于淘宝商城商品，设置为false或不设置表示不判断这个属性
          has_coupon: false, // 是否有优惠券，设置为true表示该商品有优惠券，设置为false或不设置表示不判断这个属性
          is_overseas: false, // 是否海外商品，设置为true表示该商品是属于海外商品，设置为false或不设置表示不判断这个属性
          need_free_shipment: false, // 是否包邮，true表示包邮，空或false表示不限
          need_prepay: false
        },
        location: '定位中', // 定位城市
        choiseCity: '', //选择城市
        locationSelectFlag: false,  // 定位城市是否被选中      
        searchResultList:[], // 搜索结果
        console: '',
        value: '', // 搜索框的值
        showInputClearBtn: false, // 搜索框清楚键
        hasRecord: false,
        searchRecord: [],
        recommend: [], // 搜索输入的推荐
        showRecommend: false,
        showResult: false,
        hasResultFlag: true, // 是否搜索到相关产品
        toSearchFlag: false,
        isPopupShow: false, // 筛选弹层       
        cityData: cityData, // 城市数据
        gridSelectStyle: { // 选择器样式
          lineSpacing: '20px',
          width: '150px',
          height: '50px',
          color: '#666666',
          checkedColor: '#fe9f92',
          borderColor: '#f8f8f8',
          checkedBorderColor: '#fe9f92',
          backgroundColor: '#f8f8f8',
          checkedBackgroundColor: '#f8f8f8'
        },
        positionListShow: false,
        service: [
          {title: '天猫',checked:false},
          {title: '优惠券',checked:false},
          {title: '海外商品',checked:false},
          {title: '包邮',checked:false},
          {title: '消费者保障',checked:false}
        ],
        sortWords: {
          isCommon: false, // 综合排序       
          isVolume: false, //销量
          isPrice: false, // 价格
          isPriceUp: false, // 价格升序
          isPriceDown: false // 价格降序
        },
        isFiltrate: false, // 筛选
        onSearchInputFlag: false
      }
    },
    computed: {
      cityDatadeal() {
         this.cityData.hotCity.forEach(el => {
           el.title = el.cityName
          //  el.checked = false 
               
         })
         this.cityData.cities.forEach(el => {
           el.title = el.cityName
          //  el.checked = false    
         })        
        return this.cityData
      },
      choiseCityDeal() {
        return [{
          title: this.choiseCity,
          checked: true
        }]
      },
      geoLocation() {
        return [
          {title: `${this.location}&nbsp;&#xe660;`, checked: false}
        ]
      },
      searchResultHeight() {
        return  Utils.env.getPageHeight() + 120;
      }
     
    },
    
    beforeMount() {
      
      position.getUserLocation(res => {

        if(res.data.positionCity) {
          this.location = res.data.positionCity
        }else {
           this.location = '定位失败'
        }        
      },err => {
        this.location = "定位失败"
      })
      storage.getItem('searchRecord', e => {
        if(e.result === "success" && e.data) {
         
          this.searchRecord = e.data.split('$$$')
          this.hasRecord = true
        }else {
          this.hasRecord = false
        }       
      })      
    },
    mounted() {
      
    },
    components: { 
      itemList,
      WxcPopup,
      WxcGridSelect
    },
    methods: {
      // 公共方法
      searchResultListDealed(list) {
        list.forEach((el,i) => {
          
          // 是否来自天猫
          let tmallUrl = el.item_url.indexOf('tmall.com');
          if (tmallUrl >= 0) {
            list[i]._istmall = 'true'
          }else {
            list[i]._istmall = 'false'
          }
          // 描述处理
          list[i]._item_description_arr = el.item_description.split(' ')
        });
        return list;
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
      getSearchResult() {
        this.searchResultList = []
        this.fetch(res => {
          if(res.data.tbk_dg_material_optional_response.result_list.map_data.length) {                            
            let _searchResultList = res.data.tbk_dg_material_optional_response.result_list.map_data
            
            this.searchResultList = this.searchResultListDealed(_searchResultList)
            this.hasResultFlag = true         
          }else {
            this.hasResultFlag = false
          }
        },err => {
          this.hasResultFlag = false
        })
      },
      // 公共方法结束

      onback() {
        navigator.pop({
          animated: "false"
        })
      },
      oninput(e) {
        
        this.value = e.value;
        this.showInputClearBtn =true

        if(!e.value) {
          this.showInputClearBtn = false  
          this.recommend = []
          this.showRecommend = false  
        }else {
          if(!this.toSearchFlag || !this.showResult) {
            this.showRecommend = true
          }         
          let url = `https://suggest.taobao.com/sug?q=${e.value}&code=utf-8&area=c2c&nick=&sid=null`
          stream.fetch(
            {
              method: "GET",
              url: url,
              type: "json"
            },
            res => {
              try {
                this.recommend = res.data.result
              } catch (err) {
                console.log(err)
              }
            }
          )
        }
      },
      oninputClear() {
        this.value = '';
        this.showInputClearBtn = false
        this.$refs['input'].focus(); 
        
      },
      onreturn() {
        if(this.value) {
          this.showRecommend = false
          this.toSearch(this.value,true)
        }        
      },
      onfocus() {
        this.showResult = false
      },
      onblur() {
        this.showResult = true
      },
      toSearch(value,addRecord) {
        this.$refs['input'].blur(); 
        this.showRecommend = false    
        let self = this;
        this.toSearchFlag = true
        if(value) {
          this.searchResultList = []
          this.showResult = true
          this.hasResultFlag = true
          this.requestOptions.apiOptions.q = value //查询词
          
          this.fetch(res => {
           if(res.data.tbk_dg_material_optional_response.result_list.map_data.length) {                            
              let _searchResultList = res.data.tbk_dg_material_optional_response.result_list.map_data

              this.searchResultList = this.searchResultListDealed(_searchResultList)
              this.hasResultFlag = true  
        
            }else {
              this.hasResultFlag = false
            } 
          },(err) => {
            this.hasResultFlag = false
          })
    
          if(addRecord === true) {
           
            for(let i = 0; i < this.searchRecord.length; i++) {
              if(this.searchRecord[i] == value) {
                this.searchRecord.splice(i,1)
              }              
            }

            this.searchRecord.unshift(value)
            this.hasRecord = true
            let searchRecordString = this.searchRecord.length > 1 ? this.searchRecord.join('$$$') : this.searchRecord[0]
            storage.setItem('searchRecord', searchRecordString)                    
          }        
          
        }else {
          this.showInputClearBtn = false
        }
        
      },
      onclearRecord() {
        this.searchRecord = []
        this.hasRecord = false

        storage.removeItem('searchRecord')
      },
      // 点击搜索推荐
      onrecommendTipCilck(e) { 
        if(e.currentTarget.attr.value) {
          this.value = e.currentTarget.attr.value
          this.toSearch(e.currentTarget.attr.value,true)
        }       
      },
      // 点击搜索记录
      onrecordSearch(e) {
        if(e.currentTarget.attr.value) {
          this.value = e.currentTarget.attr.value
          this.toSearch(e.currentTarget.attr.value,false)
        }   
      },
      onloadmore() {
        // modal.toast({
        //   message: '加载中...',
        //   duration: 0.1
        // })
        this.requestOptions.apiOptions.page_no++
        
        this.fetch(res => {
          if(res.data.tbk_dg_material_optional_response.result_list.map_data.length) {
            let _searchResultList = res.data.tbk_dg_material_optional_response.result_list.map_data
            _searchResultList = this.searchResultListDealed(_searchResultList)
            _searchResultList.forEach(el => {
              this.searchResultList.push(el)
            })
          }else {
            modal.toast({
              message: '没有更多了',
              duration: 0.3
            })
          }
        },err => {
          modal.toast({
            message: '没有更多了',
            duration: 0.3
          })
        })
        
      },
      onfiltrate() {
        this.isPopupShow = true
        this.$refs['input'].blur() 
      },
      onpopupOverlayClicked() {
        this.isPopupShow = false
        // this.$refs['input'].focus() 
      },
      onStartPriceInput(e) {
        this.filtrate.start_price = e.value

      },
      onStartPriceInputChange(e) {
        let value = parseFloat(e.value)
        if(!value || value === NaN || value < 0) {
          if(this.filtrate.end_price) {
            this.filtrate.start_price = ''
          }else {
            this.filtrate.start_price = 0
          }
        }else {
          if(this.filtrate.end_price && this.filtrate.end_price < value) {
             modal.toast({
              message: '最低价应小于最高价',
              duration: 0.2
            })
            this.filtrate.start_price = 0
          }else {
            this.filtrate.start_price = value
          }
        }
      },
      onEndPriceInput(e) {
        this.filtrate.end_price = e.value
      },
      onEndPriceInputChange(e){

        if(e.value == '') {
          this.filtrate.end_price = ''
          return;
        }

        let value = parseFloat(e.value)
        if(!value || value === NaN || value < 0){
          if(this.filtrate.start_price) {
            this.filtrate.end_price = ''
          }else {
            this.filtrate.end_price = ''
          }
        }else {
          if(this.filtrate.start_price &&  value < this.filtrate.start_price) {
            
             modal.toast({
              message: '最高价应大于最低价',
              duration: 0.2
            })
            this.filtrate.end_price = ''
          }else {
            this.filtrate.end_price = value
          }
        }
      },
      // 重新定位
      onregeo() {
        this.location = "定位中..."
        let opacity = 0
      
        let timer = setInterval(() => {
          this.flashingEffect(this.$refs.location,opacity)
          opacity = opacity === 0 ? 1 : 0
        },900)
        
        position.getUserLocation(res => {
          
          if(res.data.positionCity){
            this.location = res.data.positionCity
            clearInterval(timer)
          }else {
            this.location = "定位失败" 
            clearInterval(timer)           
          } 
                   
        },err => {
          clearInterval(timer)
          this.location = "定位失败"         
        })
      },
      flashingEffect(ref,opacity) {
        animation.transition(ref, {
          styles: {
              opacity: opacity
          },
          duration: 800, //ms
          timingFunction: 'ease',
          needLayout:false,
          delay: 0 //ms
          })
      },
      onHotCitySelect({selectIndex, checked, checkedList}) {
               
        this.locationSelectFlag = false
       
        this.cityData.hotCity.forEach((item,i) => {
           
          if(selectIndex === i) {           
            item.checked = checked                           
          }else {
            item.checked = false
          }         
        })
        if(!checked) {
          this.choiseCity = '' 
          return        
        }
        this.choiseCity = checkedList[0].title
 
        this.cityDatadeal.cities = this.cityDatadeal.cities.map((item, i) => {
          let { checked } = item;
          checked = false
          return {
            ...item,
            checked
          }
        })
        
       
      },
      onCitiesSelect({selectIndex, checked, checkedList}) {
        
        this.locationSelectFlag = false
        this.cityData.cities.forEach((item,i) => {
           
          if(selectIndex === i) {           
            item.checked = checked                           
          }else {
            item.checked = false
          }         
        })
        if(!checked) {
          this.choiseCity = ''
          return;
        }
        
        this.choiseCity = checkedList[0].title

        this.cityDatadeal.hotCity = this.cityDatadeal.hotCity.map((item, i) => {
          let { checked } = item;
          checked = false
          return {
            ...item,
            checked
          }
        })
  
      },
      onchoiseCityCilck({selectIndex, checked, checkedList}) {        
        this.onchoiseCityClear()
      },
      onchoiseCityClear() {
        this.choiseCity = ''
        this.cityDatadeal.cities = this.cityDatadeal.cities.map((item, i) => {
          let { checked } = item;
          checked = false
          return {
            ...item,
            checked
          }
        })
        this.cityDatadeal.hotCity = this.cityDatadeal.hotCity.map((item, i) => {
          let { checked } = item;
          checked = false
          return {
            ...item,
            checked
          }
        })
      },
      onpositionArrowClick() {
        this.positionListShow = !this.positionListShow
      },
      onLocationSelect() {
        this.onchoiseCityClear()        
        this.locationSelectFlag = !this.locationSelectFlag
      },
      // 店铺评分
      onDsrInput(e) {
        this.filtrate.start_dsr = e.value
      },
      onDsrChange(e) {
        let value = parseInt(e.value)

        if(!value && value !=0 || value === NaN || value < 0) {
          this.filtrate.start_dsr = ''
          modal.toast({
            message: '店铺评分应为正整数',
            duration: 0.2
          })
        }else {
          if(value > 50000) {
            this.filtrate.start_dsr = 50000
            modal.toast({
              message: '店铺评分应小于50000',
              duration: 0.2
            })
          }else {
            this.filtrate.start_dsr = value
          }
        }
      },
      onServiceSelect({selectIndex, checked, checkedList}) {

        switch(selectIndex) {
          case 0: this.filtrate.is_tmall = !this.filtrate.is_tmall; this.service[0].checked = !this.service[0].checked; break;
          case 1: this.filtrate.has_coupon = !this.filtrate.has_coupon; this.service[1].checked = !this.service[1].checked; break;
          case 2: this.filtrate.is_overseas = !this.filtrate.is_overseas; this.service[2].checked = !this.service[2].checked; break;
          case 3: this.filtrate.need_free_shipment = !this.filtrate.need_free_shipment; this.service[3].checked = !this.service[3].checked; break;
          case 4: this.filtrate.need_prepay = !this.filtrate.need_prepay; this.service[4].checked = !this.service[4].checked;
        }

      },
      onFilterReset() {
        this.onchoiseCityClear()
        this.locationSelectFlag = false
        this.filtrate =  {
          start_price: '',
          end_price: '',
          start_dsr: '',
          is_tmall: false,
          has_coupon: false,
          is_overseas: false,
          need_free_shipment: false,
          need_prepay: false
        } 
        this.service = [
          {title: '天猫',checked:false},
          {title: '优惠券',checked:false},
          {title: '海外商品',checked:false},
          {title: '包邮',checked:false},
          {title: '消费者保障',checked:false}
        ]
      },
      onFilterConfirm() {
        this.isPopupShow = false
        let filtrate = this.filtrate
        for(let key in filtrate) {
          if(filtrate[key] || this.locationSelectFlag || this.choiseCity){
            this.isFiltrate = true
            break
          }else {
            this.isFiltrate = false
          }          
        }
        for(let key in filtrate) {
          if(filtrate[key]){
            Vue.set(this.requestOptions.apiOptions, key, filtrate[key])
          }else {
            Vue.delete(this.requestOptions.apiOptions, key)
          }         
        }
        if(this.locationSelectFlag || this.choiseCity) {
          let itemloc = this.locationSelectFlag ? this.location : this.choiseCity
          Vue.set(this.requestOptions.apiOptions, 'itemloc', itemloc)
          
        }else {
          Vue.delete(this.requestOptions.apiOptions, 'itemloc')         
        }
        
        this.getSearchResult()                  
      },

      onCommon() {
        this.requestOptions.apiOptions.sort = 'tk_total_sales_des'
        for(let key in this.sortWords) { 
          this.sortWords[key] = false
        }        
        this.sortWords.isCommon = true
        this.getSearchResult()
      },
      onVolume() {
        this.requestOptions.apiOptions.sort = 'total_sales_des'
        for(let key in this.sortWords) { 
          this.sortWords[key] = false
        }
        this.sortWords.isVolume = true
        this.getSearchResult()
      },
      onPrice() {
      
        this.requestOptions.apiOptions.sort = !this.sortWords.isPrice ? 'price_asc' : this.requestOptions.apiOptions.sort === 'price_asc' ? 'price_des' : 'price_asc'
        
        for(let key in this.sortWords) { 
          this.sortWords[key] = false
        }
        this.requestOptions.apiOptions.sort === 'price_asc' ? this.sortWords.isPriceUp = true : this.sortWords.isPriceDown = true
        this.sortWords.isPrice = true
        this.getSearchResult()
      }

    }
  }
</script>

<style scoped>
  .iconfont {   
    font-family: iconfont;
    /*font-weight: bold;*/
  }

  .select-color {
    color: #fa513a;
  }

  .header {
    position: sticky;
  }

  .header-box {
    height: 80px;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    background-color: #fff;
    position: relative;
    border-bottom-style: solid;
    border-bottom-width: 2px;
    border-bottom-color: #ebecee; 
  }

  .back-btn {
    position: absolute;
    left: 20px;
    line-height: 80px;
    font-size: 40px; 
    color: #fa513a
  }

  .search-bar {
    width: 650px;
    height: 60px;
    margin-left: 20px;
    padding-left: 70px;
    border-radius: 30px;
    background-color: #f8f8f8;
    font-size: 26px;
    color: #333;
    placeholder-color: #bbb;
  }
  
  .search-icon {
    position: absolute;
    left: 80px;  
    font-size: 36px; 
    color: #bbb;
    line-height: 80px;
  }

  .clear {
    width: 30px;
    height: 30px;
    background-color: #666;
    border-radius: 30px;
    align-items: center;
    justify-content: center;
    position: absolute;
    right: 60px;
    top: 25px;
  }

  .clear-icon {
    font-size: 20px;
    color: #fff;
  }

  .search-record {
    margin-top: 25px;
    margin-left: 15px;
    margin-right: 15px;
  }

  .search-title {
    flex-direction: row;
    justify-content: space-between;
  }

  .search-title-txt{
    color: #bbb;
    font-size: 24px;
  }

  .clear-search-record {
    width: 28px;
    height: 28px;
    background-color: #bbb;
    border-radius: 28px;
    align-items: center;
    justify-content: center;
  }

  .clear-search-record-icon {
    font-size: 16px;
    color: #fff;
  }

  .search-record-main {
    width: 720px;
    margin-top: 25px;
    flex-direction: row;
    flex-wrap: wrap;
    align-content:flex-start;
    height: 200px;
  }

  .jc-center {
    justify-content: center;    
  }

  .search-record-tip {
    height:50px;
    align-items: center;
    justify-content: center;
    padding-left: 15px;
    padding-right: 15px;
    background-color: #ebecee;
    margin-right: 20px;
    margin-bottom: 20px;
    border-radius: 20px;
  }

  .search-record-txt {
    font-size: 24px;
    color: #333;
    line-height: 50px;
  }

  .recommend-tip {
    width: 750px;
    height: 80px;
    padding: 0 15px;
    background-color: #fff;
    border-bottom-style: solid;
    border-bottom-width: 2px;
    border-bottom-color: #ebecee;
    justify-content: space-between;
    /* align-items: center; */
  }

  .recommend-tip-txt {
    font-size: 28px;
    color: #666;
    line-height: 80px;
  }

  .search-result {
    width: 750px;
    /* height: 1110px; */
  }

  /* 搜索结果 */
  .rank-box {
    height: 70px;
    border-bottom-style: solid;
    border-bottom-width: 2px;
    border-bottom-color: #ebecee;
    padding: 0 70px;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
  }

  .rank-txt {
    font-size: 28px;
    color: #666;
    line-height: 70px;
  }

  .price-sort {
    flex-direction: row;
    height: 70px;
  }

  .price-sort-arrow {
    width: 28px;
    height: 70px;
    position: relative;
  }

  .price-sort-arrow-up {
    position: absolute;
    left: 0;
    top: -5px;
    transform: rotate(180deg);
  }

  .price-sort-arrow-down {
    position: absolute;
    left: 0;
    top: 5px;
  }

  .no-result-box {
    width:750px;
    align-items: center;
    margin-top: 30px;
  }

  .no-result {
    font-size: 24px;
    color: #666;    
  }

  /* 筛选弹窗样式 */
  .btn {
    font-size: 24px;
    color: #666; 
    background-color: #f8f8f8;    
  }

  .popup-scroller {
    /* height: 1120px; */
    padding: 50px 20px 0 20px;
    border-bottom-style: solid;
    border-bottom-width: 2px;
    border-bottom-color: #ebecee;
  }

  .choose-title {
    font-size: 24px;
    color: #666; 
  }

  .price-input-box {
    margin-top: 20px;
    flex-direction: row;
    justify-content: space-between;
  }

  .price-input {
    width: 200px;
    placeholder-color: #bbb;
    padding: 10px 0;
    background-color: #f8f8f8; 
    font-size: 24px;
    color: #666;
    border-radius: 30px;
    text-align: center;
  }

  .price-toline {
    color: #bbb;
    line-height: 44px;
  }

  .choose-title-witharrow {
    margin-top: 40px;
    flex-direction: row;
    justify-content: space-between;
  }

  .position-arrow {
    line-height: 28px;
    color: #bbb;
  }

  .current-city {
    margin-top: 20px;
    flex-direction: row;
  }

  .reset-location-btn {
    font-size: 24px;
    color: #bbb;    
    line-height: 50px; 
  }

  .city-data {
    margin-top: 20px;
  }

  .choose-city-title {
    margin-top: 20px;
    margin-bottom: 20px;
  }

  .location-line {
    height: 50px;
    border-left-style: solid;
    border-left-width: 2px;
    border-left-color: #ebecee;
    margin-right: 20px;
    margin-left: 20px;
  }

  .saler-dsr {
    margin-top: 40px;
  }

  .service-box {
    margin-top: 40px;
    margin-bottom: 20px;
  }

  /* 底部 */
  .popup-footer {
    height: 80px;
    justify-content: center;
    align-items: flex-end;
  }

  .popup-footer-box {
    flex-direction: row;
    margin-right: 20px;
    height: 60px;
    border-radius: 30px;
  }

  .popup-footer-btn {
    line-height: 60px;
    color: #fff;
    font-size: 28px;
    padding: 0 40px;   
  }

  .popup-footer-btn-reset {
    background-image: linear-gradient( to top right, #FFC000, #FF9402);
  }

  .popup-footer-btn-confirm {
    background-image: linear-gradient( to right, #FF7800, #FE5305);
  }

  /* 定位 */
  .geo-box {
    justify-content: center;
    border-radius: 8px;
    border-style: solid;
    border-width: 2px;
    margin-right: 20px; 
    position: relative;
    border-color: #f8f8f8;
    background-color: #f8f8f8;    
  }

  .geo-box-select {
    border-color: #fe9f92;
  }

  .location-btn {
    width: 150px;
    height: 50px;
    text-align: center;
    font-size: 24px;
    color: #666;
    line-height: 50px;
  }

  .location-btn-select {
    color: #fe9f92;
  }

  .btn-icon {
    position: absolute;
    right: 0;
    bottom: 0;
    width: 38px;
    height: 34px;
  }
  /* 定位end */
</style>
