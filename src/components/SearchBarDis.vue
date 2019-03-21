<template>  
  <div class="search-bar">
    <!-- <input v-if="searchAble" 
      :disabled="false" 
      :autofocus="autoFocus" 
      type="text" 
      return-key-type="search" 
      singleline="true" 
      class="search" 
      placeholder="搜索商品、店铺等" /> -->
    <div class="search search-fake-box" @click="onSearch">
      <text class="search-fake-text">搜索商品、店铺等</text>
    </div>
    <div class="search-btn">
      <text class="iconfont">&#xe639;</text>
    </div>
  </div>
</template>

<script>

  import { getJumpBaseUrl } from "../util/getJumpBaseUrl.js";

  const navigator = weex.requireModule("navigator-pri");

	export default {
		props:{
      // searchAble: Boolean,
      autoFocus: Boolean
    },
    methods: {
      onSearch(e) {
        e.stopPropagation()
        navigator.push({
          url: getJumpBaseUrl("searchPage"),
          animated: "false"
        });
      }
    }
	}
</script>

<style scoped>
	.iconfont {
    color: #bbb;
    font-size: 32px;
    font-family: iconfont;
  }
  
  .search-bar {
    position: relative;
    justify-content: center;
    text-align: center;
  }

  .search {
    width: 590px;
    height: 60px;
    margin-left: 25px;
    padding-left: 60px;
    border-radius: 40px;
    background-color: #fff;
    font-size: 28px;
    color: #333;
    placeholder-color: #bbb;
  }

  .search-btn {
    position: absolute;
    left: 30px;
    top: 14px;
    /* border-left-style: solid;
    border-left-width: 2px;
    border-left-color: #bbb; */
    padding-left: 10px;
  }

  .search-fake-box {
    justify-content: center;
  }

  .search-fake-text {
    color: #bbb;
    font-size: 28px;
  }
  
</style>
