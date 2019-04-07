<template>
  <div>
    <header class="header size">      
      <text class="back-btn iconfont" @click="onback">&#xe625;</text>
      <image src="file:///android_asset/images/snap-logo.png" class="header-bgi" style="width:200px;height:80px;" resize="cover"></image>
    </header>
    
    <wxc-tab-page ref="wxc-tab-page"
                  :tab-titles="tabTitles"
                  :tab-styles="tabStyles"
                  need-slider="true"
                  is-tab-view="true"
                  :tab-page-height="tabPageHeight"
                  :title-use-slot="true"
                  click-animation="true"
                  @wxcTabPageCurrentTabSelected="wxcTabPageCurrentTabSelected">
     
      <div v-for="(item,i) in tabTitles" 
          :key="`tabTitles${i}`"
          :slot="`tab-title-${i}`"
          class="tab-title"> 
        <div class="tab-title-hour" :class="[currentPage === i && (item.slogan === '已开抢' || item.slogan === '抢购进行中') ? 'tab-title-active-before' : currentPage === i && (item.slogan === '即将开场' || item.slogan === '明日开场') ? 'tab-title-active-after': '']">
          <text class="tab-title-hour-txt" :class="[currentPage === i ? 'tab-title-txt-active': '']">{{item.title}}</text>  
        </div>                         
        <text class="tab-title-slogan" :style="{color: currentPage === i && (item.slogan === '已开抢' || item.slogan === '抢购进行中') ? 'rgba(250,81,58,.9)' : currentPage === i && (item.slogan === '即将开场' || item.slogan === '明日开场') ? 'rgba(1,216,103,.9)' : '#bbb'}">{{item.slogan}}</text>
      </div>
      <!-- <div><text style="fontSize:40px;">{{console}}</text></div>       -->
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
            <snap-item :item="item" :currentTime="currentTime"></snap-item>
          </wxc-pan-item>
           
        </cell>
        <!-- <cell v-if="toHeaderBtnFlag">
          <div class="toHeader" @click="onToHeader">
            <text class="iconfont toHeader-icon">&#xe666;</text>
          </div> 
        </cell>  -->
        <cell class="footer" @appear="loadingMore">          
          <image style="width:70px;height:70px" src="file:///android_asset/images/loading.gif" v-if="loadingFlag"></image>
          <text class="footer-text" v-else>~没有更多了~</text>        
        </cell>
      </list>
          
    </wxc-tab-page>
  </div>
  <!-- <scroller><text>{{console}}</text></scroller> -->
</template>

<script>
  import { WxcTabPage, WxcPanItem, Utils, BindEnv } from 'weex-ui';
  import snapItem from '../components/SnapItem.vue';
  import { formatURL } from "../util/formatURL.js";
  import config from '../util/mall.config.js';

  const navigator = weex.requireModule("navigator-pri");
  const stream = weex.requireModule("stream");
  const dom = weex.requireModule("dom");

  export default {
    components: { WxcTabPage, WxcPanItem, snapItem },
    data() {
      return {
        console: '',
        timeArr: [0,8,10,11,12,13,14,15,17,19,21,22,23],
        tabTitles: [],
        items:[],
        tabStyles: {
          bgColor: '#FFFFFF',
          titleColor: '#333',
          activeTitleColor: '#fff',
          activeBgColor: '#fff',
          isActiveTitleBold: false,
          width: 150,       
          height: 100,
          fontSize: 28,
          activeFontSize: 32,
          hasActiveBottom: false,
          textPaddingLeft: 0,
          textPaddingRight: 0,
          normalBottomColor: '#ebecee',
          normalBottomHeight: 1,
          leftOffset: 0,
          rightOffset: 10
        },
        currentPage: 0,
        timeRequestOptions: {
          method: "taobao.time.get"          
        },
        requestOptions: {
          method: "taobao.tbk.ju.tqg.get",
          apiOptions: {
            fields: "click_url,pic_url,reserve_price,zk_final_price,total_amount,sold_num,title,category_name,start_time,end_time",
            page_size: "20",
            page_no: 1,
            adzone_id: config.adzoneId,
            start_time: '',
            end_time: ''
          }
        },
        currentTime: '',
        tabPageHeight: 1334,
        loadingFlag: true,
        toHeaderBtnFlag: true
      }
    },
    computed: {
      
    },
    created() {
      this.tabPageHeight = Utils.env.getPageHeight() - 50;

      let date = new Date();
      this.currentTime = this.formatTime(date)

      const dateArr = this.currentTime.split(' ')
      const beforeTimeDate = this.formatTime(new Date(new Date(`${dateArr[0]}`) - 24*3600*1000)).split(' ')[0]
      const afterTimeDate = this.formatTime(new Date(new Date(`${dateArr[0]}`) - 0 + 24*3600*1000)).split(' ')[0]

      let timeHour = parseInt(dateArr[1].split(':')[0]) 
      let timeArr = []
      for(let i = 0; i < this.timeArr.length; i++) {
        if(this.timeArr[i] > timeHour) {
          timeArr.push(this.timeArr[i])
        }            
      }
      
      let titleHour = ''
      for(let i = 0;i < timeArr.length; i++) {
        titleHour = timeArr[i] < 10 ? `0${timeArr[i]}:00` : `${timeArr[i]}:00`
        this.tabTitles.push({
          title: titleHour,
          slogan: '已开抢',
          timeHour: timeArr[i],
          startTime: `${beforeTimeDate} ${titleHour}:00`,
          endTime: `${beforeTimeDate} ${titleHour.split(':')[0]}:10:00`
        })
      }

      for(let i = 0; i < this.timeArr.length; i++) {
        timeArr.push(this.timeArr[i])         
      }

      let slogan = ''
      
      for(let i = this.tabTitles.length;i < timeArr.length; i++) {
        titleHour = timeArr[i] < 10 ? `0${timeArr[i]}:00` : `${timeArr[i]}:00`
        if(timeArr[i] < timeHour) {
          slogan = '已开抢'
        }else {
          slogan = '即将开场'
        }
        if(i === 12) {
          slogan = '抢购进行中'                
        }
        this.tabTitles.push({
          title: titleHour,
          slogan: slogan,
          timeHour: timeArr[i],
          startTime: `${dateArr[0]} ${titleHour}:00`,
          endTime: `${dateArr[0]} ${titleHour.split(':')[0]}:10:00`
        })
      }

      for(let i = 0; i < this.timeArr.length; i++) {
        timeArr.push(this.timeArr[i])         
      }

      for(let i = this.tabTitles.length;i < timeArr.length; i++) {
        titleHour = timeArr[i] < 10 ? `0${timeArr[i]}:00` : `${timeArr[i]}:00`
        this.tabTitles.push({
          title: titleHour,
          slogan: '明日开场',
          timeHour: timeArr[i],
          startTime: `${afterTimeDate} ${titleHour}:00`,
          endTime: `${afterTimeDate} ${titleHour.split(':')[0]}:10:00`
        })
      }
    },
    beforeMount(){
     
    },
    mounted() {
      this.items = [...Array(this.tabTitles.length).keys()].map(i => []); 
      this.$refs['wxc-tab-page'].setPage(12);
    },
    methods: {
      onback() {       
        navigator.pop({
          animated: "false"
        })
      },
      wxcTabPageCurrentTabSelected (e) {                    
        this.currentPage = e.page

        this.requestOptions.apiOptions.start_time = this.tabTitles[e.page].startTime
        this.requestOptions.apiOptions.end_time = this.tabTitles[e.page].endTime

        let items = this.items
        
        if(!items[e.page].length){
          let url = formatURL(this.requestOptions.method,this.requestOptions.apiOptions)

          try {
            stream.fetch(
              {
                method: "GET",
                url: url,
                type: "json"            
              },
              res => {              
                if (res.data.tbk_ju_tqg_get_response) {              
                  let items = res.data.tbk_ju_tqg_get_response.results.results;
                  Vue.set(this.items, e.page, items);
                } else {
                  this.loadingFlag = false;
                }
              }
            );
          } catch (err) {
            this.loadingFlag = false;
          }
        } 
        
      },
      wxcPanItemPan (e) {
        if (BindEnv.supportsEBForAndroid()) {
          this.$refs['wxc-tab-page'].bindExp(e.element);
        }
      },
      formatTime(date) {
        return `${date.getFullYear()}-${date.getMonth()+1<10?'0'+(date.getMonth()+1):date.getMonth()+1}-${date.getDate()<10?'0'+date.getDate():date.getDate()} ${date.getHours()<10?'0'+date.getHours():date.getHours()}:00:00`
      },
      onToHeader(e) {
        // this.console = this.$refs[`header${this.currentPage}`]
        dom.scrollToElement(this.$refs[`header${this.currentPage}`], { offset: 0, animated: 'true' });
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
                if (res.data.tbk_ju_tqg_get_response) {              
                  let items = res.data.tbk_ju_tqg_get_response.results.results;
                  this.items[this.currentPage].push.apply(this.items[this.currentPage],items)
                } else {
                  this.loadingFlag = false;
                }
              }
            );
          } catch (err) {
            this.loadingFlag = false;
          }
        }       
      }
    },
  }
</script>

<style scoped>
  .iconfont {    
    font-family: iconfont;
  }

  .header {
    position: sticky;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    /* background-color: #000; */
    background-image: linear-gradient(to right,#FE6B1B,#FE3710)
  }

  .size {
    width: 750px;
    height: 130px;
  }

  .back-btn {
    position: absolute;
    left: 10px;
    font-size: 40px; 
    color: #fff;
    margin: 0 20px; 
  }

  .tab-title {
    background-color: #fff;
    justify-content: center;
    align-items: center;
    width: 130px;
    
  }

  .tab-title-hour {
    padding: 10px 25px;    
    margin-bottom: 10px;
    height: 50px;
    justify-content: center;
    align-items: center;
  }

  .tab-title-hour-txt {
    font-size: 28px; 
    color: #333;
  }

  .tab-title-active-before {    
    background-color: rgba(250,81,58,.9);
    border-radius: 25px;
  }

  .tab-title-active-after {
    background-color: rgba(1,216,103,.9);
    border-radius: 25px;
  }

  .tab-title-txt-active {
    font-size: 32px; 
    color: #fff;
  }

  .tab-title-slogan {
    font-size: 24px; 
    color: #bbb;
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