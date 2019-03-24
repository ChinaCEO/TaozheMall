<template>
  <div class="index"> 
    <!-- <text>{{console}}</text>    -->
    <router-view />
    <nav-footer @navTo="onTabTo"></nav-footer>    
  </div> 
</template>

<script>
import NavFooter from "./components/NavFooter.vue";
import { store } from './store.js'

export default {
    name: 'App',
    data() {
      return {
        console: ''
      }
    },
    
    created() {
      
      
      var domModule = weex.requireModule('dom');
      domModule.addRule('fontFace', {
        'fontFamily': "iconfont",
        'src': "url('http://at.alicdn.com/t/font_1035245_ho111l8f11g.ttf')"
      }); 
      
    },
    
    components: {
      NavFooter
    },
    methods: {
      onTabTo(result) {
        this.$router.push(store.state.currentPage)        
      }      
    }
  }
</script>

<style scoped>

</style>
