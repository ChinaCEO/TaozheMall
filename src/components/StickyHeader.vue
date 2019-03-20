<template>  
  <div class="stickyHeader">
    <div class="logo">
      <text class="logo-letter">淘折</text>      
    </div>
    <searchBar search-disable="false"></searchBar>    
  </div>
</template>

<script>
  import SearchBar from './SearchBar.vue';
	export default {
		components: {
      SearchBar
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
    top: 25px;
    flex-direction: row;
    width: 750px;
  	height: 110px; 
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
    top: 20px;
  }

  .logo-letter {
    font-size: 20px;
    color: #fff;
  }

</style>
