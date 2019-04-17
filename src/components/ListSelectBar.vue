<template>
  <div class="column-item" @click="$emit('onclick')">
    <text class="iconfont column-item-icon">{{icon}}</text>
    <div class="column-item-wrapper">
      <text class="column-item-txt">{{title}}</text>
      <text class="column-item-tail-txt" v-if="tailInfo">{{tailInfo}}</text>
      <text class="iconfont column-item-arrow">&#xe631;</text>
    </div>        
  </div>
</template>

<script>

  export default {
    props: ['icon','title','tailInfo']    
  }
</script>

<style scoped>
  .iconfont {    
    font-family: iconfont;
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

  .column-item-tail-txt {
    position: absolute;
    right: 100px;
    font-size: 28px;
    color: #333;
  }

  .column-item-arrow {
    font-size: 28px;
    color: #bbb; 
    margin-right: 30px;
  }
</style>