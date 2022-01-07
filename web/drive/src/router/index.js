import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/login'
import Admin from '@/views/admin'
import Home from '@/views/level2/home'
import Student from '@/views/level2/student.vue'
import Teacher from '@/views/level2/teacher.vue'
import Payment from '@/views/level2/payment.vue'




Vue.use(Router)

const routerPush = Router.prototype.push
Router.prototype.push = function push (location) {
  return routerPush.call(this, location).catch(error => error)
}

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/admin',
      name: 'Admin',
      component: Admin,
      redirect: {name: 'Home'},
      children: [
        {
          path: '/Home',
          name: 'Home',
          component: Home
        },
        {
          path: '/Student',
          name: 'Student',
          component: Student
        },
        {
          path: '/Teacher',
          name: 'Teacher',
          component: Teacher
        },
        {
          path: '/Payment',
          name: 'Payment',
          component: Payment
        },
      ]
    },
  ]
})
