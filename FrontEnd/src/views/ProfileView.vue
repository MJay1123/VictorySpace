<template>
  <div class="profile-view">
    <div class="profile-container">
      <h2 class="page-title">내 정보</h2>

      <div class="user-info-card">
        <div class="profile-header">
          <img :src="profileImage" class="profile-image" />

          <div class="profile-main">
            <div class="name-row">
              <h3 class="user-name">{{ user?.name }}</h3>
              <span class="grade">
                {{ user?.gradeSymbol }} {{ user?.gradeName }}
              </span>
              <span class="point"> Point : {{ user?.point }} </span>
            </div>

            <p class="nickname">@{{ user?.nickname }}</p>
            <p class="email">{{ user?.email }}</p>
          </div>

          <button class="rank-btn" @click="showRankModal = true">
            🏆 전체 랭킹
          </button>

          <RankModal v-if="showRankModal" @close="showRankModal = false" />
        </div>

        <div class="info-content">
          <div class="info-item">
            <span class="info-label">성별</span>
            <span class="info-value">{{ getGenderText(user?.gender) }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">생년월일</span>
            <span class="info-value">{{ user?.birthday }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">가입일</span>
            <span class="info-value">{{ joinDate }}</span>
          </div>
        </div>
      </div>


      <!-- 내가 만든 매치업 -->
      <div class="matchup-section">
        <h3 class="section-title">내가 만든 매치업</h3>
        <div v-if="isLoadingCreated" class="loading">로딩 중...</div>
        <div v-else-if="createdVotes.length === 0" class="empty-message">만든 매치업이 없습니다.</div>
        <div v-else class="matchup-list">
          <div v-for="vote in createdVotes" :key="vote.id" class="matchup-item" @click="goVoteDetail(vote.id)">
            <h4 class="matchup-title">{{ vote.title }}</h4>
            <p class="matchup-preview">{{ truncateContent(vote.content) }}</p>
            <span class="matchup-date">{{ formatDate(vote.createdAt) }}</span>
          </div>
        </div>
      </div>

      <!-- 내가 참여한 매치업 -->
      <div class="matchup-section">
        <h3 class="section-title">내가 참여한 매치업</h3>
        <div v-if="isLoadingChallenged" class="loading">로딩 중...</div>
        <div v-else-if="challengedVotes.length === 0" class="empty-message">참여한 매치업이 없습니다.</div>
        <div v-else class="matchup-list">
          <div v-for="vote in challengedVotes" :key="vote.id" class="matchup-item" @click="goVoteDetail(vote.id)">
            <h4 class="matchup-title">{{ vote.title }}</h4>
            <p class="matchup-preview">{{ truncateContent(vote.content) }}</p>
            <span class="matchup-date">{{ formatDate(vote.createdAt) }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import voteApi from '../api/voteApi'
import memberApi from '../api/memberApi'
import logo2 from '../assets/images/logo2.png'
import RankModal from '../components/member/RankModal.vue'

const router = useRouter()

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

const userId = computed(() => {
  const raw = localStorage.getItem('userInfo')
  if (!raw) return null

  try {
    return JSON.parse(raw)?.id ?? null
  } catch (e) {
    console.log('유저id 정보 로딩 실패', e)
    return null
  }
})

const user = ref(null)
const createdVotes = ref([])
const challengedVotes = ref([])
const isLoadingCreated = ref(false)
const isLoadingChallenged = ref(false)
const showRankModal = ref(false)

const fetchUserInfo = async () => {
  if (!userId.value) return

  try {
    const res = await memberApi.findById(userId.value)
    user.value = res.data
  } catch (e) {
    console.error('유저 정보 로딩 실패', e)
  }
}

const profileImage = computed(() => {
  return user.value?.profile || logo2
})

const joinDate = computed(() => {
  if (!user.value?.createdAt) return ''
  return new Date(user.value.createdAt).toLocaleDateString('ko-KR')
})

const fetchCreatedVotes = async () => {
  if (!userId.value) return

  isLoadingCreated.value = true
  try {
    const res = await voteApi.findByMemberId(userId.value)
    createdVotes.value = res.data
  } catch (e) {
    console.error('내가 생성한 매치업 조회 실패', e)
  } finally {
    isLoadingCreated.value = false
  }
}

const fetchChallengedVotes = async () => {
  if (!userId.value) return

  isLoadingChallenged.value = true
  try {
    const res = await voteApi.findByChallengerId(userId.value)
    challengedVotes.value = res.data
  } catch (e) {
    console.error('내가 참여한 매치업 조회 실패', e)
  } finally {
    isLoadingChallenged.value = false
  }
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

const goVoteDetail = (voteId) => {
  router.push(`/main/votes/${voteId}`)
}

onMounted(async () => {
  await fetchUserInfo()
  await fetchCreatedVotes()
  await fetchChallengedVotes()
})
</script>

<style scoped>
.profile-view {
  min-height: calc(100vh - 80px);
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  padding: 2rem;
}

.profile-header {
  display: flex;
  align-items: center;
  gap: 1.5rem;
  margin-bottom: 1.5rem;
}

.profile-image {
  width: 96px;
  height: 96px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid #e0e0e0;
}

.profile-main {
  flex: 1;
}

.name-row {
  display: flex;
  align-items: center;
  gap: 0.75rem;
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

.user-name {
  font-size: 2.4rem;
  font-weight: 700;
  margin: 0;
}

.grade {
  background: #f1f3f5;
  padding: 0.25rem 0.6rem;
  border-radius: 999px;
  font-size: 1.2rem;
}

.point {
  background: #85c2ff;
  padding: 0.5rem 0.7rem;
  border-radius: 999px;
  font-size: 1.2rem;
}

.nickname {
  font-size: 1.2rem;
  margin: 0.25rem 0;
  color: #666;
}

.email {
  font-size: 1.3rem;
  color: #888;
}

.rank-btn {
  background: linear-gradient(135deg, #ffd700, #ffb703);
  border: none;
  border-radius: 999px;
  padding: 0.6rem 1.2rem;
  font-size: 0.95rem;
  font-weight: 600;
  cursor: pointer;
  white-space: nowrap;
}

.rank-btn:hover {
  opacity: 0.9;
}

.info-content {
  border-top: 1px solid #eee;
  padding-top: 1rem;
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
  font-size: 1.3rem;
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

.loading,
.empty-message {
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
</style>
