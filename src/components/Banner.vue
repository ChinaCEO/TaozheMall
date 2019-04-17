<template>
	<div class="banner">
    
		<slider class="slider size" auto-play="true">

			<div v-for="(src,i) in posters" :key="i">
				<image class="size" resize="stretch" :src="posters[i]"></image>
			</div>
			<indicator class="indicator"></indicator>
		</slider>
    <!-- <div class="footer-mask"></div> -->
    <div class="footer-mask">
      <image :src="sliderMask" class="footer-mask-img" resize="cover"></image>
    </div>
    <sticky-header></sticky-header>
	</div>
</template>

<script>
import StickyHeader from './StickyHeader.vue';
import imgLocationSrc from '../util/imgLocationSrc.js';

export default {
    data() {
      return {
      	posters: imgLocationSrc.banners.homeTopBanners,
        sliderMask: imgLocationSrc.banners.homeTopBannersBottomMask
      }
    },
    methods: {
			           
    },
    components: {
      StickyHeader
    }
  }
</script>

<style scoped>
	.banner {
    align-items: center;
    position: relative;
    /* border-bottom-left-radius: 100px;
    border-bottom-right-radius: 100px; */
	}
  .size {
  	width: 750px;
  	height: 500px;
    border-radius: -100px;
  }
  .slider {
  	position: relative;
  }
  .indicator {
  	position: absolute;
  	left: 0;
  	right: 0;
  	bottom: 40px;
  	height: 40px;
  	/*background-color: rgba(0,0,0,.2);*/
  	item-color: #f8f8f8;
  	item-selected-color: #fa513a;
    item-size: 8px;
  }

  /* .footer-mask {
    position: absolute;
    left: 0;
    right: 0;
    bottom: -900px;
    width: 10000px;
    height: 10000px;
    background-color: #fff;
    border-radius: 10000px;
    border-top-left-radius: 500px;
    border-top-right-radius: 500px; 
    background-image: linear-gradient(to top,#fff,transparent);
  } */

  .footer-mask {    
    align-items: center;
    position: absolute;
    bottom: -2px;
    left: 0;
  }

  .footer-mask-img {
    width: 800px;
    height: 40px;
  }
</style>