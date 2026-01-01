<template>
    <section class="playground">
      <h2 class="title">🎮 미니게임 놀이터</h2>
  
      <!-- 게임 선택 메뉴 -->
      <nav class="menu">
        <RouterLink to="/playground/christmasTree">🎄 크리스마스 트리</RouterLink>
        <RouterLink to="/playground/christmasTree2">🎄 크리스마스 트리2</RouterLink>
        <RouterLink to="/playground/numberGuess">🔢 숫자 맞추기</RouterLink>
        <RouterLink to="/playground/clickSpeed">⚡ 클릭 속도 테스트</RouterLink>
      </nav>
  
      <!-- 선택한 게임 표시 -->
      <div class="game-area">
        <RouterView />
      </div>
    </section>
  </template>
  
  <script setup>
  </script>
  
  <style scoped>
  .playground {
    padding: 24px;
  }
  
  .title {
    font-size: 22px;
    font-weight: 800;
    margin-bottom: 16px;
  }
  
  .menu {
    display: flex;
    gap: 12px;
    margin-bottom: 24px;
  }
  
  .menu a {
    padding: 10px 14px;
    border-radius: 8px;
    background: #1f2937;
    color: #fff;
    font-weight: 600;
    text-decoration: none;
  }
  
  .menu a.router-link-active {
    background: #3b82f6;
  }
  
  .game-area {
    margin-top: 20px;
  }
  </style>
  