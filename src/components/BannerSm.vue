<template>
	<div class="banner">   
		<slider class="slider size" auto-play="true">
			<div v-for="(src,i) in posters" :key="i">
				<image class="size" resize="stretch" :src="'file:///android_asset/images/'+src"></image>
			</div>
			<indicator class="indicator"></indicator>
		</slider>
	</div>
</template>

<script>


export default {
    data() {
      return {
      	posters: [
      		'111.jpg',
          '444.jpg',
          '333.jpg',
      		'222.jpg'
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
  	height: 300px;
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