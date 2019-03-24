<template>
  <scoller class="scoller">
    <header class="header">
      <div class="header-warpper size">
        <image src="file:///android_asset/images/loadin-bg.png" class="header-bgi size" resize="cover"></image>
        <div class="loading">
          <text class="iconfont loading-icon">&#xe629;</text>
        </div>        
        <text class="loading-txt">登&nbsp;陆</text>        
      </div>
      <div class="row">
        <div class="row-item">
          <text class="iconfont row-item-icon row-item-order-icon">&#xe652;</text>
          <text class="row-item-txt">我的订单</text> 
        </div>
        <div class="row-item">
          <text class="iconfont row-item-icon row-item-collect-icon">&#xe62e;</text>
          <text class="row-item-txt">收藏</text> 
        </div>
        <div class="row-item">
          <text class="iconfont row-item-icon row-item-record-icon">&#xe64a;</text>
          <text class="row-item-txt">浏览记录</text> 
        </div>
      </div>      
    </header>
    <div class="column">      
      <div class="column-item">
        <text class="iconfont column-item-icon">&#xe600;</text>
        <div class="column-item-wrapper">
          <text class="column-item-txt">设置</text>
          <text class="iconfont column-item-arrow">&#xe631;</text>
        </div>        
      </div>
      <div class="column-item">
        <text class="iconfont column-item-icon">&#xe663;</text>
        <div class="column-item-wrapper">
          <text class="column-item-txt">分享给朋友</text>
          <text class="iconfont column-item-arrow">&#xe631;</text>
        </div>        
      </div>
      <div class="column-item">
        <text class="iconfont column-item-icon">&#xe601;</text>
        <div class="column-item-wrapper">
          <text class="column-item-txt">关于我们</text>
          <text class="iconfont column-item-arrow">&#xe631;</text>
        </div>        
      </div>
      
    </div>

  </scoller>
</template>

<script>
  export default {
    data() {
      return {
       
      }
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

  .loading {
    border-width: 2px;
    border-color: #ebecee;
    border-style: solid;
    border-radius: 130px;
    justify-content: center;
    align-items: center;    
    padding: 5px;
    margin-top: 150px;
    margin-bottom: 20px;
  }

  .loading-icon {
    font-size: 100px;
    color: #bbb;
  }

  .loading-txt {
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
</style>