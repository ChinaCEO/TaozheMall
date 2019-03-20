<template>
  <div class="tohead" @click="$emit('onToHeader')">
    <text class="iconfont">&#xe666;</text>
  </div>
</template>

<script>

  export default {
    data() {
      return {
       
      }
    },
   
    methods: {
      
    }
  }
</script>
<style scoped>
  .iconfont {
    color: #ff6000;
    font-size: 40px;
    font-family: iconfont;
  }

  .tohead {
    position: fixed;
    bottom: 130px;
    right: 20px;
    width: 80px;
    height: 80px;
    background-color: #fff;
    border-width: 2px;
    border-color: #ebecee;
    border-radius: 80px;
    align-items: center;
    justify-content: center;
  } 
</style>