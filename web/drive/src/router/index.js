import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/login'
import Admin from '@/views/admin'
import Home from '@/views/level2/home'
import Student from '@/views/level2/student.vue'
import Teacher from '@/views/level2/teacher.vue'
import Payment from '@/views/level2/payment.vue'
import CourseRecord from '@/views/level2/courseRecord.vue'
import Exam from '@/views/level2/exam.vue'
import TheStudent from '@/views/H5Student.vue'
import TheTeacher from '@/views/H5Teacher.vue'
import Operation from '@/views/h5leve2/operation.vue'
import Person from '@/views/h5leve2/person.vue'
import PassExam from '@/views/level2/passExam.vue'

Vue.use(Router)

const routerPush = Router.prototype.push
Router.prototype.push = function push (location) {
  return routerPush.call(this, location).catch(error => error)
}

export default new Router({
  routes: [
    {
      path: '/TheTeacher',
      name: 'TheTeacher',
      component: TheTeacher
    },
    {
      path: '/TheStudent',
      name: 'TheStudent',
      component: TheStudent,
      redirect: {name: 'Operation'},
      children: [
        {
          path: '/Operation',
          name: 'Operation',
          component: Operation
        },
        {
          path: '/Person',
          name: 'Person',
          component: Person
        },
        ]
    },
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
        {
          path: '/CourseRecord',
          name: 'CourseRecord',
          component: CourseRecord
        },
        {
          path: '/Exam',
          name: 'Exam',
          component: Exam
        },{
          path: '/PassExam',
          name: 'PassExam',
          component: PassExam
        }
      ]
    },
  ]
})
