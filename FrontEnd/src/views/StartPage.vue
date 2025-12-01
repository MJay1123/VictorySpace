<template>
  <div class="main-container">
    <header class="welcome-header">
      <img :src="logo1" alt="VictorySpace Logo" class="logo" />
      <h1>VictorySpace에 오신 것을 환영합니다!</h1>
    </header>
    <main class="main-content">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import logo1 from '@/assets/images/logo1.jpg'
</script>

<style scoped>
.main-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.welcome-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 2rem;
  text-align: center;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.welcome-header h1 {
  font-size: 2rem;
  font-weight: 600;
  margin: 0;
}

.main-content {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem;
}

.logo {
  width: 140px; /* 원하면 100px ~ 200px 사이로 조절 */
  display: block;
  margin: 0 auto 1rem; /* 가운데 정렬 & 아래 간격 */
}
</style>

