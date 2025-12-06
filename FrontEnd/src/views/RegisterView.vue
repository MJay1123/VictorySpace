<template>
  <div class="register-container">
    <div class="register-card">
      <h2 class="register-title">회원가입</h2>
      <div v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </div>
      <div v-if="successMessage" class="success-message">
        {{ successMessage }}
      </div>
      <form @submit.prevent="handleRegister" class="register-form">
        <div class="form-group">
          <label for="reg-email">이메일</label>
          <input id="reg-email" v-model="formData.email" type="email" placeholder="이메일을 입력하세요" required
            :disabled="isLoading" />
        </div>
        <div class="form-group">
          <label for="name">이름</label>
          <input id="name" v-model="formData.name" type="text" placeholder="이름을 입력하세요" required :disabled="isLoading" />
        </div>
        <div class="form-group">
          <label for="gender">성별</label>
          <select id="gender" v-model="formData.gender" required :disabled="isLoading">
            <option value="">선택하세요</option>
            <option value="M">남성</option>
            <option value="F">여성</option>
          </select>
        </div>
        <div class="form-group">
          <label for="birthday">생년월일</label>
          <input id="birthday" v-model="formData.birthday" type="date" required :disabled="isLoading"
            max="9999-12-31" />
        </div>
        <div class="form-group">
          <label for="nickname">닉네임</label>
          <input id="nickname" v-model="formData.nickname" type="text" placeholder="닉네임을 입력하세요" required
            :disabled="isLoading" />
        </div>
        <div class="form-group">
          <label for="reg-password">비밀번호</label>
          <input id="reg-password" v-model="formData.password" type="password" placeholder="비밀번호를 입력하세요" required
            :disabled="isLoading" />
        </div>
        <button type="submit" class="register-button" :disabled="isLoading">
          {{ isLoading ? '처리 중...' : '회원가입' }}
        </button>
      </form>
      <div class="login-link-container">
        <router-link to="/login" class="login-link">
          로그인으로 돌아가기
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import authApi from '../api/authApi'

const router = useRouter()

const formData = ref({
  email: "",
  name: "",
  gender: "",
  birthday: "",
  nickname: "",
  password: ""
});

const isLoading = ref(false)
const errorMessage = ref('')
const successMessage = ref('')

const handleRegister = async () => {
  isLoading.value = true
  errorMessage.value = ''
  successMessage.value = ''

  try {
    const response = await authApi.register(formData.value)

    successMessage.value = '회원가입이 완료되었습니다!'

    setTimeout(() => {
      router.push('/login')
    }, 2000)

  } catch (error) {
    console.error("회원가입 오류:", error)

    if (error.response?.data?.message) {
      errorMessage.value = error.response.data.message
    } else {
      errorMessage.value = '회원가입에 실패했습니다.'
    }

  } finally {
    isLoading.value = false
  }
}
</script>

<style scoped>
.register-container {
  width: 100%;
  max-width: 500px;
  margin: 0 auto;
}

.register-card {
  background: white;
  border-radius: 12px;
  padding: 2rem;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.register-title {
  font-size: 1.75rem;
  font-weight: 600;
  margin-bottom: 1.5rem;
  text-align: center;
  color: #333;
}

.register-form {
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

.form-group input,
.form-group select {
  padding: 0.75rem;
  border: 2px solid #e0e0e0;
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.3s;
}

.form-group input:focus,
.form-group select:focus {
  outline: none;
  border-color: #667eea;
}

.register-button {
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

.register-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

.register-button:active {
  transform: translateY(0);
}

.register-button:disabled {
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

.success-message {
  background: #efe;
  color: #3c3;
  padding: 1rem;
  border-radius: 8px;
  margin-bottom: 1rem;
  border: 1px solid #cfc;
}

.login-link-container {
  margin-top: 1.5rem;
  text-align: center;
}

.login-link {
  color: #667eea;
  text-decoration: none;
  font-size: 0.9rem;
  font-weight: 500;
  transition: color 0.3s;
}

.login-link:hover {
  color: #764ba2;
  text-decoration: underline;
}
</style>
