<template>
	<div class="banner">   
		<slider class="slider size" auto-play="true">
			<div v-for="(src,i) in posters" :key="i">
				<image class="size" resize="stretch" :src="posters[i]"></image>
			</div>
			<indicator class="indicator"></indicator>
		</slider>
	</div>
</template>

<script>
import imgLocationSrc from '../util/imgLocationSrc.js';

export default {
    data() {
      return {
      	posters: [
          'https://img.alicdn.com/tfs/TB1bQGNQAvoK1RjSZPfXXXPKFXa-750-275.jpg',
          'https://img.alicdn.com/tfs/TB1EEeXLY2pK1RjSZFsXXaNlXXa-440-180.jpg',
          'https://img.alicdn.com/tfs/TB1Ry4rxrPpK1RjSZFFXXa5PpXa-440-180.jpg'
        ]
      }
    },
    methods: {
			           
    },
    components: {
      
    }
  }
</script>

<style scoped>
	.banner {
    align-items: center;
    position: relative;    
	}

  .slider {
  	position: relative;
  }

  .size {
    width: 710px;
  	height: 260px;
    margin-bottom: 20px;    
  }

  .indicator {
  	position: absolute;
  	left: 0;
  	right: 0;
  	bottom: 10px;
  	height: 40px;
  	item-color: #f8f8f8;
  	item-selected-color: #fa513a;
    item-size: 8px;
  }

</style>