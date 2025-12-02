<template>
    <div class="modal-overlay" @click.self="closeModal">
      <div class="modal">
        <p class="message">로그아웃하시겠습니까?</p>
  
        <div class="buttons">
          <button class="cancel" @click="closeModal">아니오</button>
          <button class="confirm" @click="logout">예</button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { useRouter } from 'vue-router'
  
  const emit = defineEmits(['close'])
  const router = useRouter()
  
  // 아니오 → 모달 닫기
  const closeModal = () => {
    emit('close')
  }
  
  // 예 → 로그아웃 + 모달 닫기 + 로그인 페이지 이동
  const logout = () => {
    localStorage.removeItem('token') // 또는 stored user info
    emit('close')
    router.push('/login') 
  }
  </script>
  
  <style scoped>
  .modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0,0,0,0.5);
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .modal {
    background: white;
    padding: 20px;
    border-radius: 10px;
    width: 300px;
  }
  .buttons {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
  }
  button {
    padding: 8px 20px;
    border-radius: 5px;
    cursor: pointer;
  }
  .cancel {
    background: #ddd;
  }
  .confirm {
    background: #ff4444;
    color: white;
  }
  </style>
  