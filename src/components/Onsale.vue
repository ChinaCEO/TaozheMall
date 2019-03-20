<template>  
  <div class="onsale">
    <div class="title">
        <text class="title-txt">活动专区</text>
      </div>
    <div class="left-box"> 
      <div class="box-title">
        <text class="box-title-top">限时抢购</text>
        <text class="box-title-bottom">实惠好物等你来抢</text>
      </div> 
      <div class="buy-btn">
        <text class="buy-btn-txt">立即抢购</text>
      </div>
      <div>
        <image src="file:///android_asset/images/sale1.png" resize="cover" class="left-img"></image>
      </div>
    </div>
    <div class="right-box">
      <div class="right-top">
        <div class="right-img-box">
          <image src="https://gd3.alicdn.com/imgextra/i3/47058671/TB28X.4buGSBuNjSspbXXciipXa_!!47058671.jpg_400x400.jpg_.webp" resize="cover" class="right-img"></image>
        </div>
        <div class="box-title right-top-title">
          <text class="box-title-top">实惠团购</text>
          <text class="box-title-bottom">拼着买更便宜</text>
        </div> 
        <div class="buy-btn">
          <text class="buy-btn-txt">立即抢购</text>
        </div>
        
      </div>
      <div class="right-bottom">
        <div class="right-img-box">
          <image src="http://img.alicdn.com/imgextra/i1/725677994/O1CN01n5as6O28vIdtVlESK_!!0-item_pic.jpg_430x430q90.jpg" resize="cover" class="right-img"></image>
        </div>
        <div class="box-title right-top-title">
          <text class="box-title-top">超大额券</text>
          <text class="box-title-bottom">让实惠更实惠</text>
        </div> 
        <div class="buy-btn">
          <text class="buy-btn-txt">立即抢购</text>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  
	export default {
		
	}
</script>

<style scoped>
	.onsale {
    /*height: 450px;*/
    margin: 20px;
    background-color: #fff;
    border-radius: 30px;
    flex-direction: row;
    /*justify-content: center;*/
    position: relative;
    padding-bottom: 20px;
  }
  
  .title {
    position: absolute;
    left: 0;
    top: 18px;
    background-image: linear-gradient(to top,#EB6A12,#FFA42A);
    width: 180px;
    height: 60px;
    border-top-right-radius: 60px;
    border-bottom-right-radius: 60px;
    justify-content: center;
    align-items: center;
    border-width: 2px;
    border-color: #F5B488;
    border-style: solid;
  }

  .title-txt {
    color: #fff;
    font-size: 32px;
  }

  .left-box {
    border-right-width: 2px;
    border-right-color: #ebecee;
    margin: 20px;
    /*position: relative;*/
    /*background-color: red;*/
    width: 340px;

  }

  .box-title {
    /*position: absolute;
    left: 50px;
    top: 100px;*/
    margin-top: 65px;
  }

  .box-title-top {
    font-size: 36px;
    /*margin-bottom: 5px;*/
    color: #fa513a;
  }

  .box-title-bottom {
    font-size: 24px;
    margin-bottom: 15px;
    color: #666;
  }

  .buy-btn {
    width: 140px;
    height: 50px;
    background-color: #fb3519;
    border-radius: 10px;
    justify-content: center;
    align-items: center;
    margin-bottom: 15px;
  }

  .buy-btn-txt {
    color: #fff;
    font-size: 28px;
  }

  .left-img {
    width: 320px;
    height: 200px;
  }

  .right-box {
    width: 320px;
  }
  
  .right-top {
    
    border-bottom-width: 2px;
    border-bottom-color: #ebecee;
    border-bottom-style: solid;
    padding-bottom: 35px;
    position: relative;
  }

  .right-top-title {
    margin-top: 40px;
  }

  .right-bottom {    
    position: relative;
  }

  .right-img-box {
    position: absolute;
    right: 5px;
    top: 5px;
  }

  .right-img {
    width: 165px;
    height: 200px;
  }
</style>
