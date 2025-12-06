<template>
  <div class="login-container">
    <!-- 환영 메시지 모달 -->
    <div v-if="showWelcome" class="welcome-modal">
      <div class="welcome-content">
        <h2>환영합니다!</h2>
        <p>메인화면으로 이동합니다...</p>
      </div>
    </div>

    <div class="login-card">
      <h2 class="login-title">로그인</h2>
      <div v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </div>
      <form @submit.prevent="handleLogin" class="login-form">
        <div class="form-group">
          <label for="email">이메일</label>
          <input id="email" v-model="email" type="email" placeholder="이메일을 입력하세요" required :disabled="isLoading" />
        </div>
        <div class="form-group">
          <label for="password">비밀번호</label>
          <input id="password" v-model="password" type="password" placeholder="비밀번호를 입력하세요" required
            :disabled="isLoading" />
        </div>
        <button type="submit" class="login-button" :disabled="isLoading">
          {{ isLoading ? '로그인 중...' : '로그인' }}
        </button>
      </form>
      <div class="register-link-container">
        <router-link to="/register" class="register-link">
          회원가입
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import authApi from '@/api/authApi.js'

const router = useRouter()
const email = ref('')
const password = ref('')
const showWelcome = ref(false)
const isLoading = ref(false)
const errorMessage = ref('')

const handleLogin = async () => {
  isLoading.value = true;
  errorMessage.value = '';

  try {
    const response = await authApi.login(email.value, password.value);

    if (response.status === 200) {

      // 헤더에서 토큰 꺼내기
      const token = response.headers['authorization'] || response.headers['Authorization'];

      if (token) {
        // "Bearer " 붙어서 오니까 그대로 저장
        localStorage.setItem("token", token);
      }

      // response.data 안에 이메일, role 등 있으면 저장
      if (response.data) {
        localStorage.setItem("userInfo", JSON.stringify(response.data));
      }

      showWelcome.value = true;

      setTimeout(() => {
        router.push('/main')
      }, 1500);
    }

  } catch (error) {
    console.error('로그인 오류:', error);
    errorMessage.value = error.response?.data?.message || '로그인에 실패했습니다.';
  } finally {
    isLoading.value = false;
  }
};



</script>

<style scoped>
.login-container {
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
}

.login-card {
  background: white;
  border-radius: 12px;
  padding: 2rem;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.login-title {
  font-size: 1.75rem;
  font-weight: 600;
  margin-bottom: 1.5rem;
  text-align: center;
  color: #333;
}

.login-form {
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-group label {
  font-weight: 500;
  color: #555;
  font-size: 0.9rem;
}

.form-group input {
  padding: 0.75rem;
  border: 2px solid #e0e0e0;
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.3s;
}

.form-group input:focus {
  outline: none;
  border-color: #667eea;
}

.login-button {
  padding: 0.875rem;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
  margin-top: 0.5rem;
}

.login-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

.login-button:active {
  transform: translateY(0);
}

.login-button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.error-message {
  background: #fee;
  color: #c33;
  padding: 1rem;
  border-radius: 8px;
  margin-bottom: 1rem;
  border: 1px solid #fcc;
}

.register-link-container {
  margin-top: 1.5rem;
  text-align: left;
}

.register-link {
  color: #667eea;
  text-decoration: none;
  font-size: 0.9rem;
  font-weight: 500;
  transition: color 0.3s;
}

.register-link:hover {
  color: #764ba2;
  text-decoration: underline;
}

.welcome-modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  animation: fadeIn 0.3s;
}

.welcome-content {
  background: white;
  padding: 3rem;
  border-radius: 12px;
  text-align: center;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
  animation: slideUp 0.3s;
}

.welcome-content h2 {
  font-size: 2rem;
  color: #667eea;
  margin-bottom: 1rem;
}

.welcome-content p {
  font-size: 1.1rem;
  color: #666;
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}

@keyframes slideUp {
  from {
    transform: translateY(20px);
    opacity: 0;
  }

  to {
    transform: translateY(0);
    opacity: 1;
  }
}
</style>
