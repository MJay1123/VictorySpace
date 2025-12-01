import { createRouter, createWebHistory } from 'vue-router'
import StartPage from '../views/StartPage.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import MainPage from '../views/MainPage.vue'
import ProfileView from '../views/ProfileView.vue'
import VoteListView from '../views/VoteListView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      component: StartPage,
      children: [
        {
          path: '',
          redirect: '/login'
        },
        {
          path: 'login',
          name: 'login',
          component: LoginView
        },
        {
          path: 'register',
          name: 'register',
          component: RegisterView
        }
      ]
    },
    {
      path: '/main',
      component: MainPage,
      children: [
        {
          path: '',
          redirect: '/main/votes'
        },
        {
          path: 'votes',
          name: 'votes',
          component: VoteListView
        },
        {
          path: 'profile',
          name: 'profile',
          component: ProfileView
        }
      ]
    }
  ]
})

export default router

