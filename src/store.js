import Vue from "vue";
import Vuex from 'vuex'

if(WXEnvironment.platform!=='Web'){
    Vue.use(Vuex)
} 

Vue.use(Vuex);

export const store = new Vuex.Store({
  state: {
  	haoquanApi: 'taobao.tbk.dg.item.coupon.get',
  	searchApi: 'taobao.tbk.item.get',
  	xuanpinkuApi: 'taobao.tbk.uatm.favorites.item.get',
  	taoqianggouApi: 'taobao.tbk.ju.tqg.get',
    currentPage: 'home',
    scrollFlag: false,
    couponUrl: '',
    deviceHeight: WXEnvironment.deviceHeight,
    categotySearchWord: ''
  },
  mutations: {
    to (state,_pageName) {
      state.currentPage = _pageName
    },
    changeScrollFlag(state,_flag) {
    	state.scrollFlag = _flag
    },
    setCouponUrl(state,_url) {
      state.couponUrl = _url
    },
    setDeviceHeight(state,height) {
      state.deviceHeight = height
    },
    setCategotySearchWord(state,word) {
      state.categotySearchWord = word
    }
  }
})