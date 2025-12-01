<template>
  <div class="profile-view">
    <div class="profile-container">
      <h2 class="page-title">내 정보</h2>
      
      <div class="user-info-card">
        <div class="info-content">
          <div class="info-item">
            <span class="info-label">이메일:</span>
            <span class="info-value">{{ userInfo.email || '없음' }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">이름:</span>
            <span class="info-value">{{ userInfo.name || '없음' }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">닉네임:</span>
            <span class="info-value">{{ userInfo.nickname || '없음' }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">성별:</span>
            <span class="info-value">{{ getGenderText(userInfo.gender) }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">생년월일:</span>
            <span class="info-value">{{ userInfo.birthday || '없음' }}</span>
          </div>
        </div>
      </div>

      <!-- 내가 만든 매치업 -->
      <div class="matchup-section">
        <h3 class="section-title">내가 만든 매치업</h3>
        <div v-if="isLoadingCreated" class="loading">로딩 중...</div>
        <div v-else-if="createdMatchups.length === 0" class="empty-message">만든 매치업이 없습니다.</div>
        <div v-else class="matchup-list">
          <div 
            v-for="matchup in createdMatchups" 
            :key="matchup.id" 
            class="matchup-item"
            @click="showMatchupDetail(matchup)"
          >
            <h4 class="matchup-title">{{ matchup.title }}</h4>
            <p class="matchup-preview">{{ truncateContent(matchup.content) }}</p>
            <span class="matchup-date">{{ formatDate(matchup.createdAt) }}</span>
          </div>
        </div>
      </div>

      <!-- 내가 참여한 매치업 -->
      <div class="matchup-section">
        <h3 class="section-title">내가 참여한 매치업</h3>
        <div v-if="isLoadingChallenged" class="loading">로딩 중...</div>
        <div v-else-if="challengedMatchups.length === 0" class="empty-message">참여한 매치업이 없습니다.</div>
        <div v-else class="matchup-list">
          <div 
            v-for="matchup in challengedMatchups" 
            :key="matchup.id" 
            class="matchup-item"
            @click="showMatchupDetail(matchup)"
          >
            <h4 class="matchup-title">{{ matchup.title }}</h4>
            <p class="matchup-preview">{{ truncateContent(matchup.content) }}</p>
            <span class="matchup-date">{{ formatDate(matchup.createdAt) }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 매치업 상세 보기 모달 -->
    <div v-if="selectedMatchup" class="modal-overlay" @click="selectedMatchup = null">
      <div class="modal-content" @click.stop>
        <h3 class="modal-title">{{ selectedMatchup.title }}</h3>
        <div class="matchup-detail">
          <p class="matchup-content">{{ selectedMatchup.content }}</p>
          <p v-if="selectedMatchup.challengerContent" class="challenger-content">
            <strong>도전 내용:</strong> {{ selectedMatchup.challengerContent }}
          </p>
          <p class="matchup-date-info">작성일: {{ formatDate(selectedMatchup.createdAt) }}</p>
        </div>
        <button @click="selectedMatchup = null" class="close-button">닫기</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const userInfo = ref({
  email: '',
  name: '',
  nickname: '',
  gender: '',
  age: null,
  id: null
})

const createdMatchups = ref([])
const challengedMatchups = ref([])
const isLoadingCreated = ref(false)
const isLoadingChallenged = ref(false)
const selectedMatchup = ref(null)

const getGenderText = (gender) => {
  const genderMap = {
    'male': '남성',
    'female': '여성',
    'other': '기타',
    'M': '남성',
    'F': '여성'
  }
  return genderMap[gender] || '없음'
}

const fetchCreatedMatchups = async () => {
  if (!userInfo.value.id) return

  isLoadingCreated.value = true
  try {
    const response = await fetch(`http://localhost:8080/api/vote/member/${userInfo.value.id}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      }
    })

    if (response.ok) {
      const data = await response.json()
      createdMatchups.value = Array.isArray(data) 
        ? data.filter(vote => !vote.deletedAt && vote.deletedAt === null)
        : []
    } else {
      console.error('내가 만든 매치업 조회 실패')
    }
  } catch (error) {
    console.error('내가 만든 매치업 조회 오류:', error)
  } finally {
    isLoadingCreated.value = false
  }
}

const fetchChallengedMatchups = async () => {
  if (!userInfo.value.id) return

  isLoadingChallenged.value = true
  try {
    const response = await fetch(`http://localhost:8080/api/vote/challenger/${userInfo.value.id}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      }
    })

    if (response.ok) {
      const data = await response.json()
      challengedMatchups.value = Array.isArray(data) 
        ? data.filter(vote => !vote.deletedAt && vote.deletedAt === null)
        : []
    } else {
      console.error('내가 참여한 매치업 조회 실패')
    }
  } catch (error) {
    console.error('내가 참여한 매치업 조회 오류:', error)
  } finally {
    isLoadingChallenged.value = false
  }
}

const showMatchupDetail = (matchup) => {
  selectedMatchup.value = { ...matchup }
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleString('ko-KR')
}

const truncateContent = (content) => {
  if (!content) return ''
  return content.length > 100 ? content.substring(0, 100) + '...' : content
}

onMounted(() => {
  // localStorage에서 사용자 정보 불러오기
  const savedUserInfo = localStorage.getItem('userInfo')
  if (savedUserInfo) {
    userInfo.value = JSON.parse(savedUserInfo)
    // 매치업 목록 조회
    fetchCreatedMatchups()
    fetchChallengedMatchups()
  }
})
</script>

<style scoped>
.profile-view {
  min-height: calc(100vh - 80px);
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  padding: 2rem;
}

.profile-container {
  max-width: 800px;
  margin: 0 auto;
}

.page-title {
  font-size: 2rem;
  font-weight: 600;
  color: #333;
  margin-bottom: 2rem;
  text-align: center;
}

.user-info-card {
  background: white;
  border-radius: 12px;
  padding: 2rem;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.info-content {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.info-item {
  display: flex;
  align-items: center;
  padding: 0.75rem 0;
  border-bottom: 1px solid #f0f0f0;
}

.info-item:last-child {
  border-bottom: none;
}

.info-label {
  font-weight: 600;
  color: #555;
  min-width: 100px;
  margin-right: 1rem;
}

.info-value {
  color: #333;
  font-size: 1rem;
}

.matchup-section {
  margin-top: 2rem;
  background: white;
  border-radius: 12px;
  padding: 2rem;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.section-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: #333;
  margin-bottom: 1.5rem;
  padding-bottom: 0.75rem;
  border-bottom: 2px solid #e0e0e0;
}

.loading, .empty-message {
  text-align: center;
  padding: 2rem;
  color: #666;
  font-size: 1rem;
}

.matchup-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.matchup-item {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 1.25rem;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
  border: 1px solid #e0e0e0;
}

.matchup-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.matchup-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: #333;
  margin: 0 0 0.5rem 0;
}

.matchup-preview {
  color: #666;
  margin: 0 0 0.5rem 0;
  line-height: 1.5;
}

.matchup-date {
  font-size: 0.85rem;
  color: #999;
}

.modal-overlay {
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

.modal-content {
  background: white;
  border-radius: 12px;
  padding: 2rem;
  max-width: 600px;
  width: 90%;
  max-height: 80vh;
  overflow-y: auto;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
  animation: slideUp 0.3s;
}

.modal-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: #333;
  margin-bottom: 1.5rem;
}

.matchup-detail {
  margin-bottom: 1rem;
}

.matchup-content {
  color: #333;
  line-height: 1.6;
  margin-bottom: 1rem;
  white-space: pre-wrap;
}

.challenger-content {
  color: #333;
  line-height: 1.6;
  margin-bottom: 1rem;
  padding: 1rem;
  background: #f8f9fa;
  border-radius: 8px;
  border-left: 3px solid #f44336;
}

.matchup-date-info {
  color: #999;
  font-size: 0.9rem;
}

.close-button {
  background: #667eea;
  color: white;
  border: none;
  border-radius: 8px;
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  width: 100%;
  margin-top: 1rem;
  transition: background-color 0.3s;
}

.close-button:hover {
  background: #5568d3;
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

