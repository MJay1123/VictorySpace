import { createRouter, createWebHistory } from 'vue-router'
import StartPage from '../views/StartPage.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import MainPage from '../views/MainPage.vue'
import ProfileView from '../views/ProfileView.vue'
import VoteListView from '../views/VoteListView.vue'
import VoteDetailView from '../views/VoteDetailView.vue'
import PlaygroundView from '../views/PlaygroundView.vue'

const router = createRouter({
  history: createWebHistory(),
  scrollBehavior(to, from, savedPosition) {
    // 뒤로가기면 이전 위치 유지
    if (savedPosition) {
      return savedPosition
    }

    // 그 외엔 항상 맨 위
    return { top: 0 }
  },
  routes: [
    {
      path: '/', component: StartPage,
      children: [
        { path: '', redirect: '/login' },
        { path: 'login', name: 'login', component: LoginView },
        { path: 'register', name: 'register', component: RegisterView }
      ]
    },
    {
      path: '/main', component: MainPage,
      children: [
        { path: '', redirect: '/main/votes' },
        { path: 'votes', name: 'VoteList', component: VoteListView, },
        { path: 'votes/:id', name: 'VoteDetail', component: VoteDetailView, props: true, }
      ]
    },
    {
      path: '/profile', name: 'Profile', component: ProfileView
    },
    {
      path: '/playground', component: PlaygroundView,
      children: [
        { path: '', name: 'PlaygroundHome', component: () => import('@/components/playground/PlaygroundHome.vue') },
        { path: 'christmasTree', name: 'ChristmasTree', component: () => import('@/components/playground/ChristmasTree.vue') },
        { path: 'christmasTree2', name: 'ChristmasTree2', component: () => import('@/components/playground/ChristmasTree2.vue') },
        { path: 'numberGuess', name: 'NumberGuess', component: () => import('@/components/playground/NumberGuessGame.vue') },
        { path: 'clickSpeed', name: 'ClickSpeed', component: () => import('@/components/playground/ClickSpeedGame.vue') }
      ]
    }
  ]
})

export default router

