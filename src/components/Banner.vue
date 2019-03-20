<template>
	<div class="banner">
    <!-- <slot></slot> -->
    
		<slider class="slider size" auto-play="true">

			<div v-for="(src,i) in posters" :key="i">
				<image class="size" resize="stretch" :src="'file:///android_asset/images/'+src"></image>
			</div>
			<indicator class="indicator"></indicator>
		</slider>
    <div class="footer-mask">
      <image src="file:///android_asset/images/slider-mask.png" class="footer-mask-img" resize="cover"></image>
    </div>
    <sticky-header></sticky-header>
	</div>
</template>

<script>
import StickyHeader from './StickyHeader.vue';
import { store } from '../store.js';

export default {
    data() {
      return {
      	posters: [
      		'444.jpg',
          '444.jpg',
          '444.jpg',
      		'444.jpg'
      	],
        scrollFlag: !store.state.scrollFlag
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
		/*border-style: solid;
		border-color: #D71E21;
		border-width: 2px; */
		/*position: fixed;*/
   /* position: relative;
    border-bottom-width: 1px;
    border-bottom-color: transparent;
    border-bottom-style: solid;*/
    align-items: center;
    position: relative;
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

  /*.footer-mask {
    position: absolute;
    left: 0;
    right: 0;
    bottom: 0px;
    height: 10px;
    background-color: #fff;
    background-image: linear-gradient(to top,#fff,transparent);
  }*/

  .footer-mask {    
    align-items: center;
    position: absolute;
    bottom: -2px;
  }

  .footer-mask-img {
    width: 800px;
    height: 20px;
  }
</style>