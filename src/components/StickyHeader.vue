<template>  
  <div class="stickyHeader">
    <div class="logo">
      <text class="logo-letter">淘折</text>      
    </div>
    <search-bar-dis search-disable="false"></search-bar-dis>    
  </div>
</template>

<script>
  import SearchBarDis from './SearchBarDis.vue';
	export default {
		components: {
      SearchBarDis
    }
	}
</script>

<style scoped>
	.iconfont {
    color: #fff;
    font-size: 50px;
    font-family: iconfont;
  }

  .stickyHeader {
    position: absolute;
    top: 35px;
    flex-direction: row;
    width: 750px;
  	height: 120px; 
    align-items: center;
  	justify-content: center;
  	/*background-image: linear-gradient(to top,#F51F23,#D41E21);*/
    background-color: transparent;
  }

  .logo {
    background-color: rgba(51,51,51,.5);
    width: 70px;
    height: 70px;
    align-items: center;
    justify-content: center;
    border-radius: 70px;
    position: absolute;
    left: 10px;
    top: 25px;
  }

  .logo-letter {
    font-size: 20px;
    color: #fff;
  }

</style>
