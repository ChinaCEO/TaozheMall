<template>
  <scoller class="scoller">
    <header class="header">
      <div class="header-warpper size">
        <image :src="imgSrc.headerBgi" class="header-bgi size" resize="cover"></image>
        <div class="login" @click="onlogin">
          <div class="login-icon-wrapper" :style="{padding: loginFlag ? '0':'5px'}">
            <text class="iconfont login-icon" v-if="!loginFlag">&#xe629;</text>
            <image :src="avatarUrl" style="width:130px;height:130px;" resize="cover" v-else></image>
          </div>        
          <text class="login-txt">{{loginFlag ? nickName : '登&nbsp;陆'}}</text>
        </div>               
      </div>
      <!-- <text>{{console}}</text> -->
      <div class="row">
        <div class="row-item" @click="onShowOrder">
          <text class="iconfont row-item-icon row-item-order-icon">&#xe652;</text>
          <text class="row-item-txt">我的订单</text> 
        </div>
        <div class="row-item" @click="onShowCart">
          <text class="iconfont row-item-icon row-item-collect-icon">&#xe61b;</text>
          <text class="row-item-txt">购物车</text> 
        </div>
        <div class="row-item">
          <text class="iconfont row-item-icon row-item-record-icon">&#xe64a;</text>
          <text class="row-item-txt">浏览记录</text> 
        </div>
      </div>      
    </header>
    <div class="column">      
      <selectBar :icon="'\ue600'" title="设置" @onclick="onSettingClick"></selectBar>  
      <selectBar :icon="'\ue663'" title="分享给朋友" @onclick="onAboutClick"></selectBar>  
      <selectBar :icon="'\ue601'" title="关于我们" @onclick="onAboutClick"></selectBar>  
    </div>
  </scoller>
</template>

<script>
  import { formatURL } from "../util/formatURL.js";
  import { getJumpBaseUrl } from "../util/getJumpBaseUrl.js";
  import imgLocationSrc from '../util/imgLocationSrc.js';
  import selectBar from "../components/ListSelectBar.vue";

  const navigator = weex.requireModule("navigator-pri");
  const login = weex.requireModule("login");
  const storage = weex.requireModule("storage");
  const modal = weex.requireModule("modal");
  const order = weex.requireModule("order");
 
  export default {
    components: {
      selectBar
    },
    data() {
      return {
        console: '',
        imgSrc: {
          headerBgi: imgLocationSrc.header.loadinBg
        },
        loginFlag: false,
        avatarUrl: '',       
        nickName: '',      
      }
    },
    created() {     
      storage.getItem("loginData",e => {
        if(e.result === "success") {
          this.loginFlag = true

          let dataArr = e.data.split("$$")
          this.avatarUrl = dataArr[0]
          this.nickName = dataArr[1]        
        }else {
          this.loginFlag = false
        }
      })
      const login = new BroadcastChannel('login')
      login.onmessage = e => {
        this.loginFlag = e.data
      }
    },
    updated() {
      
    },
    methods: {
      onlogin() {
        if(!this.loginFlag) {
          login.login(res => {
            if(res) {
              modal.toast({
                message: '登录成功',
                duration: 0.5
              })
              this.loginFlag = true
              this.avatarUrl = res.avatarUrl
              this.nickName = res.nick
              storage.setItem("loginFlag",this.loginFlag)         
              storage.setItem("loginData",`${this.avatarUrl}$$${this.nickName}`)            
            }else {
              modal.toast({
                message: '登录失败',
                duration: 0.5
              })

            }
          })
        }       
      },
      onShowOrder() {
        order.showOrders()
      },
      onShowCart() {
        order.showCart()
      },
      onSettingClick() {
       navigator.push({
          url: getJumpBaseUrl("setting", ''),
          animated: "true"
        });
      },
      onAboutClick() {
        navigator.push({
          url: getJumpBaseUrl("about", ''),
          animated: "true"
        });
      }

    }
  }
</script>

<style scoped>
  .iconfont {    
    font-family: iconfont;
  }

  .scoller {
    background-color: #ebecee;
  }

  .header {
    position: sticky;  
    background-color: #fff;
    padding-bottom: 20px;  
  }

  .size {
    width: 750px;
    height: 400px;
  }

  .header-warpper {
    position: relative;
    align-items: center;
  }

  .header-bgi {
    position: absolute;
    top: 0;
  }

  .login {
    margin-top: 160px;
    margin-bottom: 20px;
    justify-content: center;
    align-items: center; 
  }

  .login-icon-wrapper {
    border-width: 2px;
    border-color: #ebecee;
    border-style: solid;
    border-radius: 130px;
    justify-content: center;
    align-items: center;    
    /* padding: 5px; */
    margin-bottom: 20px;   
  }

  .login-icon {
    font-size: 100px;
    color: #bbb;
  }

  .login-txt {
    font-size: 32px;
    color: #fff;
  }

  .row {
    width: 750px;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 0 80px;  
    margin-top: 20px; 
  }

  .row-item {
    justify-content: flex-start;
    align-items: center;
  }

  .row-item-icon {
    height: 100px;
  }

  .row-item-order-icon {
    font-size: 80px;
    color: #FCB64C;
  }

  .row-item-collect-icon {
    font-size: 60px;
    color: #FF7183;
    padding-top: 12px;
  }

  .row-item-record-icon {
    font-size: 70px;
    color: #56A0FA;
    padding-top: 5px;
  }

  .row-item-txt {
    font-size: 28px;
    color: #666;
  }

  .column {
    margin-top: 30px;
    background-color: #fff;
    
  }

  .column-item {
    position: relative;
  }

  .column-item-wrapper {
    flex-direction: row;
    height: 100px;
    align-items: center;
    justify-content: space-between;
    margin-left: 80px;
    border-bottom-width: 2px;
    border-bottom-color: #ebecee;
    border-bottom-style: solid;
       
  }

  .column-item-icon {
    position: absolute;
    left: 20px;
    top: 22px;
    font-size: 50px;
    color: #bbb;    
  }

  .column-item-txt {
    font-size: 28px;
    color: #333;
    margin-left: 20px;  
  }

  .column-item-arrow {
    font-size: 28px;
    color: #bbb; 
    margin-right: 30px;
  }

  .logout {
    margin: 160px 80px 120px 80px;
    background-color: #fb3519;
    border-radius: 20px;
    justify-content: center;
    align-items: center;
    height: 80px;
  }

  .logout-txt {
    font-size: 36px;
    color: #fff;
  }
</style>