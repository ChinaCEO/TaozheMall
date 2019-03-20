<template>
  <loading :display="loadingFlag ? 'show' : 'hide'" class="loading" @loading="onloading" ref="loading">
      <text class="indicator-text" v-if="!loadingFlag">加载更多...</text>
      <loading-indicator class="loading-indicator" v-if="loadingFlag"></loading-indicator>
      <text class="indicator-text" v-if="loadingFlag">玩命加载中...</text>
      <text class="indicator-text" v-if="footerFlag">没有更多了...</text>
  </loading>
</template>

<script>
	export default {
		data() {
      return {
        loadingFlag: false
      }
    },
    methods: {
      onloading(e) {
        this.loadingFlag = true;
        // this.footerFlag = false;
        this.requestOptions.apiOptions.page_no++;
        let self = this;
        let url = formatURL(
          this.requestOptions.method,
          this.requestOptions.apiOptions
        );

        setTimeout(() => {
          self.loadingFlag = false;
          // this.footerFlag = true
        }, 1000);
      }
    }
	}
</script>

<style scoped>
	.loading {
    width: 750px;
    height: 200px;
    -ms-flex-align: center;
    -webkit-align-items: center;
    -webkit-box-align: center;
    align-items: center;
  }

  .indicator-text {
    color: #bbb;
    font-size: 20px;
    text-align: center;
  }

  .loading-indicator {
    width: 100px;
    height: 100px;
    color: #bbb;
  }
</style>
