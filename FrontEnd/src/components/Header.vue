<template>
  <header class="app-header">
    <div class="header-container">

      <!-- 로고 추가 -->
      <img :src="logo2" alt="logo" class="logo" />

      <div class="left-buttons">
        <button @click="goHome" class="home-button">홈</button>
        <button @click="goProfile" class="profile-button">내 정보</button>
      </div>

      <button @click="openLogoutModal" class="logout-button">로그아웃</button>
      <LogoutModal v-if="showLogoutModal" @close="closeLogoutModal" />
    </div>
  </header>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { ref } from 'vue'
import logo2 from '@/assets/images/logo2.png'
import LogoutModal from '@/components/modal/LogoutModal.vue'

const router = useRouter()

const goHome = () => {
  router.push('/main/votes')
}

const goProfile = () => {
  router.push('/main/profile')
}

const showLogoutModal = ref(false)

const openLogoutModal = () => {
  showLogoutModal.value = true
}
const closeLogoutModal = () => {
  showLogoutModal.value = false
}
</script>

<style scoped>
.app-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 1rem 2rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* 🔥 로고 스타일 */
.logo {
  height: 42px;
  width: auto;
  cursor: pointer;
  transition: transform 0.2s;
}

.logo:hover {
  transform: scale(1.05);
}

.left-buttons {
  display: flex;
  gap: 0.75rem;
}

.home-button,
.profile-button {
  padding: 0.75rem 1.5rem;
  background: white;
  color: #667eea;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s;
}

.home-button:hover,
.profile-button:hover {
  background: #f0f0f0;
  transform: translateY(-2px);
}

.home-button:active,
.profile-button:active {
  transform: translateY(0);
}

.logout-button {
  padding: 0.75rem 1.5rem;
  background: #dc3545;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s;
}

.logout-button:hover {
  background: #c82333;
  transform: translateY(-2px);
}

.logout-button:active {
  transform: translateY(0);
}
</style>

