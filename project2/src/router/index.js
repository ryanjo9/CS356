import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Add from '../views/Add.vue'
import Result from '../views/Result.vue'
import Edit from '../views/Edit.vue'
import Register from '../views/Register.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/add',
    name: 'add',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: Add
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/result/:input',
    name: 'result',
    component: Result,
    props: true
  },
  {
    path: '/edit/:id',
    name: 'edit',
    component: Edit,
    props: true
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
