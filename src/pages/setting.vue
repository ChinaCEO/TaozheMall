<template>
  <scoller class="scoller">
    <header class="header"> 
      <div class="header-box">
        <text class="back-btn iconfont" @click="onback">&#xe625;</text>
        <text class="title">设置</text> 
      </div>              
    </header>
    <!-- <text>{{console}}</text> -->
    <div class="chunk">
      <selectBar :icon="'\ue650'" title="清除缓存" :tailInfo="cacheSize" @onclick="onClearCache"></selectBar>  
    </div>
    <div class="chunk logout" @click="onlogout" v-if="loginFlag">
      <text class="logout-txt">退出当前账号</text>
    </div> 
  </scoller>
</template>

<script>
  import selectBar from "../components/ListSelectBar.vue";

  const navigator = weex.requireModule('navigator')
  const login = weex.requireModule("login")
  const modal = weex.requireModule("modal")
  const storage = weex.requireModule("storage");
  const cache = weex.requireModule("cache");
  export default {
    components: {
      selectBar,
    },
    data() {
      return {
        console: '',
        cacheSize: '',
        loginFlag: false,       
      }
    },
    created() {
      storage.getItem("loginFlag",e => {        
        if(e.result === "success") {
          if(e.data == 'true') {
            this.loginFlag = true
          }else {
            this.loginFlag = false
          }
        }else {
          this.loginFlag = false
        }
      })

      cache.getCacheSize(res => {
        this.cacheSize = res
      })
    },
    methods: {
      onback() {
        navigator.pop({
          animated: "true"
        })
      },
      onClearCache() {
        cache.clearCache()
        cache.getCacheSize(res => {
          this.cacheSize = res
        })
      },
      onlogout() {
        modal.confirm({
            message: '确定退出当前账号登录吗？',
            okTitle: '确定',
            cancelTitle: '取消'
        }, res => {
          if(res === '确定') {
            login.logout(res => {
              if(res.ok) {
                modal.toast({
                  message: '退出登录成功',
                  duration: 0.5
                })
                this.loginFlag = false
                storage.setItem("loginFlag",this.loginFlag) 
                storage.removeItem("loginData")
                const login = new BroadcastChannel('login')
                login.postMessage(false)
                login.close()
                navigator.pop({
                  animated: "true"
                })
                
              }else{
                modal.toast({
                  message: '退出登录失败',
                  duration: 0.5
                })
              }        
            })
          }
        })
        
      },
    },
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
  }

  .header-box {
    flex-direction: row;
    align-items: center;
    justify-content: center;
    background-color: #f8f8f8;
    position: relative;
    padding-top: 80px;
    padding-bottom: 20px;
  }

  .back-btn {
    position: absolute;
    left: 30px;
    top: 50px;
    color: #FF6000;
    font-size: 40px;
    line-height: 100px;
  }

  .close-btn {
    font-size: 36px;
    left: 100px;
  }

  .title {
    font-size: 36px;
    color: #fa513a;
  }

  .chunk {
    margin-top: 20px;
    background-color: #fff;
  }

  .logout {
    align-items: center;
    justify-content: center;
    height: 80px;
  }

  .logout-txt {
    font-size: 28px;
    color: #fb3519;
  }
</style>

