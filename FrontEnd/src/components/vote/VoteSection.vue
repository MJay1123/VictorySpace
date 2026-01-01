<template>
    <section class="vote-section">
        <!-- ===== Header ===== -->
        <div class="header">
            <div></div>
            <h1 class="title">{{ vote.title }}</h1>
            <VoteActionMenu v-if="canEdit" class="action-menu" @edit="openUpdate" @delete="openDelete" />
        </div>

        <p v-if="category" class="category">{{ vote.categoryName }}</p>

        <!-- 메타 정보 -->
        <div class="meta">
            <span>📌 생성자: <strong>{{ vote.memberNickname }}</strong></span>
            <span>⚔️ 도전자: <strong>{{ vote.challengerNickname ?? '도전자가 없습니다.' }}</strong>
            </span>
        </div>

        <!-- ===== 투표 UI ===== -->
        <div class="competition-wrapper">
            <div class="competition">
                <!-- HOME -->
                <div class="side home" :class="{ selected: userVote?.content === 'home' }">
                    <div class="side-header">
                        <h3>🏠 Home</h3>
                        <div class="vote-count">{{ vote.homeCount }}표</div>
                    </div>
                    <div class="side-content">
                        <p>{{ vote.content }}</p>
                    </div>
                    <label class="select-label">
                        <input type="radio" value="home" v-model="selectedOption" :disabled="Boolean(userVote)" />
                        <span>선택</span>
                    </label>
                </div>

                <!-- VS 표시 -->
                <div class="vs-divider">
                    <span class="vs-text">VS</span>
                </div>

                <!-- AWAY -->
                <div class="side away" :class="{ selected: userVote?.content === 'away' }">
                    <div class="side-header">
                        <h3>⚔️ Away</h3>
                        <div class="vote-count">{{ vote.awayCount }}표</div>
                    </div>
                    <div class="side-content">
                        <p>{{ vote.challengerContent || '도전자가 없습니다.' }}</p>
                    </div>
                    <!-- 도전자가 이미 있을 때 -->
                    <div v-if="vote.challengerNickname">
                        <label class="select-label">
                            <input type="radio" value="away" v-model="selectedOption" :disabled="Boolean(userVote)" />
                            <span>선택</span>
                        </label>
                    </div>

                    <!-- 도전자가 없을 때 -->
                    <div v-else>
                        <input v-model="challengerContent" placeholder="도전자 내용을 입력하세요" />
                        <button class="challenge-btn" @click="challengeVote">
                            도전하기
                        </button>
                    </div>
                </div>
            </div>

            <!-- NEUTRAL -->
            <div class="neutral-section">
                <div class="side neutral" :class="{ selected: userVote?.content === 'neutral' }">
                    <div class="neutral-header">
                        <h4>⚖️ Neutral</h4>
                        <span class="neutral-count">{{ vote.neutralCount }}표</span>
                    </div>
                    <label class="neutral-select">
                        <input type="radio" value="neutral" v-model="selectedOption" :disabled="Boolean(userVote)" />
                        <span>선택</span>
                    </label>
                </div>
            </div>
        </div>

        <!-- 투표 버튼 -->
        <button class="vote-btn" :disabled="!userVote && !selectedOption"
            @click="userVote ? cancelVote() : handleVote(selectedOption)">
            {{ userVote ? '투표 취소' : '투표하기' }}
        </button>

        <!-- 결과 -->
        <button class="detail-btn" @click="showGraphModal = true">
            📊 결과 자세히 보기
        </button>

        <!-- ===== 모달 ===== -->
        <VotersGraphModal v-if="showGraphModal" :voteId="voteId" :voters="voters" @close="showGraphModal = false" />

        <UpdateVoteModal v-if="showUpdateModal" :vote="vote" @close="showUpdateModal = false" @updated="refresh" />

        <DeleteVoteModal v-if="showDeleteModal" :voteId="vote.id" @close="showDeleteModal = false"
            @deleted="handleDeleted" />
    </section>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'

import VoteActionMenu from './VoteActionMenu.vue'
import UpdateVoteModal from './UpdateVoteModal.vue'
import DeleteVoteModal from './DeleteVoteModal.vue'
import VotersGraphModal from './VotersGraphModal.vue'

import voteApi from '@/api/voteApi'
import voterApi from '../../api/voterApi'

const router = useRouter()
const props = defineProps({
    voteId: Number
})

const vote = ref({})
const category = ref(null)
const voters = ref([])
const userVote = ref(null)
const selectedOption = ref(null)
const challengerContent = ref('')

const user = computed(() => {
    try {
        return JSON.parse(localStorage.getItem('userInfo'))
    } catch {
        console.log("유저 정보 로딩 실패")
        return null
    }
})

const canEdit = computed(() =>
    user.value && user.value.id === vote.value.memberId && !vote.value.challengerId
)

const showGraphModal = ref(false)
const showUpdateModal = ref(false)
const showDeleteModal = ref(false)

const openUpdate = () => (showUpdateModal.value = true)
const openDelete = () => (showDeleteModal.value = true)

const refresh = async () => {
    const { data } = await voteApi.findById(props.voteId)
    vote.value = data

    if (user.value) {
        try {
            const uv = await voterApi.findByVoteAndMemberId(props.voteId, user.value.id)
            userVote.value = uv.data
            selectedOption.value = uv.data?.content || null
        } catch {
            userVote.value = null
        }
    }
}

const handleVote = async content => {
    console.log('user:', user.value)
    console.log('userId:', user.value?.id)
    console.log('voteId:', props.voteId)
    console.log('content:', content)
    await voterApi.createVoter({
        voteId: props.voteId,
        memberId: user.value.id,
        content
    })
    refresh()
}

const cancelVote = async () => {
    await voterApi.deleteVoter(userVote.value.id)
    refresh()
}

const challengeVote = async () => {
    if (!challengerContent.value.trim()) {
        alert('도전자 내용을 입력해주세요')
        return
    }

    await voteApi.challengeVote(vote.value.id, {
        challengerId: user.value.id,
        challengerContent: challengerContent.value
    })

    challengerContent.value = ''
    refresh()
}

const handleDeleted = () => {
    alert('투표가 삭제되었습니다')
    router.push('/main/votes')
}

onMounted(refresh)
</script>

<style scoped>
/* ===== Layout ===== */
.vote-section {
    padding: 32px;
}

.header {
    display: grid;
    grid-template-columns: 1fr auto 1fr;
    align-items: center;
}

.title {
    text-align: center;
    font-size: 32px;
    font-weight: 700;
}

.action-menu {
    justify-self: end;
}

.category {
    text-align: center;
    margin-top: 8px;
}

.meta {
    display: flex;
    justify-content: center;
    gap: 32px;
    margin: 20px 0;
}

/* ===== 투표 UI ===== */
.competition-wrapper {
    margin: 35px 0;
    position: relative;
}

.competition {
    display: flex;
    gap: 24px;
    align-items: stretch;
    position: relative;
}

.side {
    flex: 1;
    padding: 32px 28px;
    border-radius: 20px;
    border: 3px solid transparent;
    transition: all 0.3s ease;
    display: flex;
    flex-direction: column;
    position: relative;
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
}

.side.home {
    background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
    color: white;
}

.side.away {
    background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);
    color: white;
}

.side.selected {
    transform: translateY(-8px);
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.2);
}

.side.home.selected {
    border-color: #1e40af;
    box-shadow: 0 8px 24px rgba(59, 130, 246, 0.4);
}

.side.away.selected {
    border-color: #991b1b;
    box-shadow: 0 8px 24px rgba(239, 68, 68, 0.4);
}

.side-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
}

.side-header h3 {
    font-size: 24px;
    font-weight: 700;
    margin: 0;
}

.vote-count {
    background: rgba(255, 255, 255, 0.25);
    backdrop-filter: blur(10px);
    padding: 8px 16px;
    border-radius: 20px;
    font-weight: 700;
    font-size: 18px;
}

.side-content {
    flex: 1;
    margin-bottom: 20px;
}

.side-content p {
    font-size: 18px;
    line-height: 1.6;
    margin: 0;
    min-height: 60px;
}

.select-label {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
    cursor: pointer;
    padding: 12px;
    background: rgba(255, 255, 255, 0.2);
    border-radius: 12px;
    transition: all 0.3s ease;
    font-weight: 600;
    font-size: 16px;
}

.select-label:hover {
    background: rgba(255, 255, 255, 0.3);
    transform: scale(1.05);
}

.select-label input[type="radio"] {
    width: 20px;
    height: 20px;
    cursor: pointer;
    margin: 0;
}

.vs-divider {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    z-index: 10;
    background: white;
    width: 80px;
    height: 80px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.2);
    border: 4px solid #f3f4f6;
}

.vs-text {
    font-size: 28px;
    font-weight: 900;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
}

.neutral-section {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
}

.side.neutral {
    flex: 0 0 auto;
    width: 280px;
    padding: 16px 20px;
    background: linear-gradient(135deg, #22c55e 0%, #16a34a 100%);
    color: white;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    gap: 12px;
}

.neutral-header {
    display: flex;
    align-items: center;
    gap: 12px;
    flex: 1;
}

.neutral-header h4 {
    font-size: 16px;
    font-weight: 700;
    margin: 0;
}

.neutral-count {
    background: rgba(255, 255, 255, 0.25);
    backdrop-filter: blur(10px);
    padding: 4px 12px;
    border-radius: 12px;
    font-weight: 700;
    font-size: 14px;
}

.neutral-select {
    display: flex;
    align-items: center;
    gap: 6px;
    cursor: pointer;
    padding: 8px 16px;
    background: rgba(255, 255, 255, 0.2);
    border-radius: 10px;
    transition: all 0.3s ease;
    font-weight: 600;
    font-size: 14px;
    white-space: nowrap;
}

.neutral-select:hover {
    background: rgba(255, 255, 255, 0.3);
}

.neutral-select input[type="radio"] {
    width: 16px;
    height: 16px;
    cursor: pointer;
    margin: 0;
}

.side.neutral.selected {
    border-color: #15803d;
    box-shadow: 0 4px 16px rgba(34, 197, 94, 0.4);
    transform: translateY(-4px);
}

.challenge-btn {
    padding: 12px 24px;
    background: rgba(255, 255, 255, 0.25);
    backdrop-filter: blur(10px);
    border: 2px solid rgba(255, 255, 255, 0.5);
    border-radius: 12px;
    color: white;
    font-weight: 700;
    font-size: 16px;
    cursor: pointer;
    transition: all 0.3s ease;
}

.challenge-btn:hover {
    background: rgba(255, 255, 255, 0.35);
    transform: scale(1.05);
}

/* ===== Buttons ===== */
.vote-btn {
    width: 100%;
    padding: 20px;
    border-radius: 15px;
    font-size: 22px;
    font-weight: bold;
    background: #3b82f6;
    color: #fff;
    border: none;
    cursor: pointer;
}

.detail-btn {
    margin-top: 12px;
    padding: 10px 18px;
    border-radius: 999px;
    border: 1.5px solid #6366f1;
    background: #eef2ff;
    color: #4338ca;
    font-weight: 600;
    cursor: pointer;
}
</style>