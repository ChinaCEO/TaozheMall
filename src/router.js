/*global Vue*/
import Router from 'vue-router'

import index from "@/index.vue";
import guessLike from '@/pages/guessLike.vue';
import home from '@/pages/home.vue';
import find from '@/pages/find.vue';
import category from '@/pages/category.vue';


Vue.use(Router)

export const router = new Router({
  routes: [
    {
      path: '/',
      name: 'index',
      component: index
    },
    {
      path: '/home',
      name: 'home',
      component: home
    },
    {
      path: '/guessLike',
      name: 'guessLike',
      component: guessLike
    },
    {
      path: '/find',
      name: 'find',
      component: find
    },
    {
      path: '/category',
      name: 'category',
      component: category
    }
  ]
})
