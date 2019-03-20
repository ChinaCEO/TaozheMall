<template>
	<div class="chunks">
		<list class="list">
      <cell class="cell">
        <div class="B-box"  v-for="(item,i) in row1" :key="i">
          <image class="B-icon" :src="item.icon"></image>
          <text class="B-title">{{item.title}}</text>
        </div>
      </cell>
    </list>
	</div>
</template>

<script>
    export default {
    data () {
      return {
        row1: [{
          title: '腔调掌柜',
          icon: 'http://img.alicdn.com/tfs/TB1sWLoRVXXXXbdXXXXXXXXXXXX-140-140.png',
        }, {
          title: '腔调掌柜',
          icon: 'http://gw.alicdn.com/tfs/TB10.R_SpXXXXbtXXXXXXXXXXXX-140-140.png',
        }, {
          title: '腔调掌柜',
          icon: 'http://img.alicdn.com/tfs/TB1fRVASpXXXXXdXXXXXXXXXXXX-140-140.png',
        }, {
          title: '腔调掌柜',
          icon: 'http://img.alicdn.com/tfs/TB1_TkdPVXXXXcJXXXXXXXXXXXX-140-140.png',
        },{
          title: '腔调掌柜',
          icon: 'http://img.alicdn.com/tfs/TB1vRGhSpXXXXaLXpXXXXXXXXXX-140-140.png',
        }]       
      }
    }
  }
</script>

<style scoped>
	.chunks {
    /*width: 690px;*/
    height: 160px;
    background-color: #fff;
    margin: 20px;
    border-radius: 30px;
    /*box-shadow: 3px 5px 3px rgba(0, 0, 0, .3);*/
    /*margin-top: -25px;*/
  }

  .cell {
    flex-direction: row;
    justify-content: center;
    align-items: center;
  }
  .B-box {
    width: 140px;
    justify-content: center;
    align-items: center;
    /*padding-top: 5px;
    padding-bottom: 5px;
    padding-left: 5px;
    padding-right: 5px;*/
    padding-top: 10px;
  }
  .B-title {
    width: 140px;
    font-size: 20px;
    text-align: center;
    padding-top: 15px;
    color: #999999;
  }
  .B-icon {
    width: 100px;
    height: 100px;
  }
</style>