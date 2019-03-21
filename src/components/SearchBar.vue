<template>
  <div class="search-bar">
      <text class="back-btn iconfont" @click="onback" >&#xe625;</text>
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
</template>

<script>
  export default {
    data() {
      return {
        value: '',
        showInputClearBtn: false
      }
    },
    methods() {
      
    }
  }
</script>

<style scoped>
  .iconfont {   
    font-family: iconfont;
  }

  .search-bar {
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
</style>



