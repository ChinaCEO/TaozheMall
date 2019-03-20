
<template>
  <div class="nav-footer">
    <div class="nav-item" @click="navTo('guessLike')">
      <text class="iconfont nav-icon" :class="[this.isActive('guessLike')]">&#xe659;</text>
      <text class="nav-title" :class="[this.isActive('guessLike')]">猜你喜欢</text>
    </div>
    <div class="nav-item nav-icon" @click="navTo('home')" >
      <text class="iconfont" :class="[this.isActive('home')]">&#xe635;</text>
      <text class="nav-title" :class="[this.isActive('home')]">主页</text>
    </div>
    <div class="nav-item nav-icon" @click="navTo('category')">
      <text class="iconfont" :class="[this.isActive('category')]">&#xe636;</text>
      <text class="nav-title" :class="[this.isActive('category')]">分类</text>
    </div class="nav-item nav-icon">
    <div class="nav-item" @click="navTo('find')">
      <text class="iconfont" :class="[this.isActive('find')]">&#xe638;</text>
      <text class="nav-title" :class="[this.isActive('find')]">发现</text>
    </div>
    <div class="nav-item nav-icon nav-item-last" @click="navTo('mine')">
      <text class="iconfont" :class="[this.isActive('mine')]">&#xe634;</text>
      <text class="nav-title" :class="[this.isActive('mine')]">我的</text>
    </div>
    
  </div>
</template>

<script>
  import { store } from '../store.js'
  export default {
    data() {
      return {
        actionPage: "home"
      }      
    },
    methods: {
      isActive(page) {   
            
        return this.actionPage ==  page ? "isActive" : ""
      },
      navTo(page) {
        this.actionPage = page
    
        store.commit('to', page)
        this.$emit('navTo',{
          page: page
        })
      }
    }
  }
</script>

<style scoped>
  .nav-footer {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    height: 90px;
    flex-direction: row;
    justify-content: space-between;
    background-color: #f8f8f8;
    border-width: 2px;
    border-style: solid;
    border-color: #ebecee;
  }
  
  .nav-item {
    justify-content: center;
    align-items: center; 
    margin-left: 42px; 
  }
  
  .nav-item-last {
    margin-right: 45px;
  }

  .iconfont {
    color: #666;
    font-size: 40px;
    font-family: iconfont;
    
  }
  
  .nav-title {
    color: #5F646E;
    font-weight: 300;
    font-size: 28px;
  }

  .isActive{
    color:#fa313a;
  }
</style>

