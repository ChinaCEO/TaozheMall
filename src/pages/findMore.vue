<template>
  <waterfall 
    class="water-fall" 
    ref="waterfall" 
    :column-width="columnWidth" 
    :column-count="columnCount" 
    :column-gap="columnGap" 
    :left-gap='leftGap' 
    :right-gap='rightGap' 
    @scroll="onScroll" >
    <header class="header size">
      <image src="http://img.alicdn.com/tps/i4/TB1tyZqLVXXXXXKXFXXx6Mp7VXX-1440-380.png" class="size header-bgi" resize="cover"></image>
    </header>
    <cell>
      <Item></Item>
    </cell>
  </waterfall>
</template>

<script>
  import Item from '../components/waterSingleItem.vue';

  export default {
    components: {
      Item
    }
  }
</script>

<style scoped>
  .iconfont {    
    font-family: iconfont;
  }

  .header {
    position: sticky;
  }

  .size {
    width: 750px;
    height: 200px;
  }
</style>