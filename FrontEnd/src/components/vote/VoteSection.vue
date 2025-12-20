<template>
    <section class="vote-section">
        <!-- ===== Header ===== -->
        <div class="header">
            <div></div>
            <h1 class="title">{{ vote.title }}</h1>
            <VoteActionMenu v-if="canEdit" class="action-menu" @edit="openUpdate" @delete="openDelete" />
        </div>

        <p v-if="category" class="category">{{ category.name }}</p>

        <!-- 메타 정보 -->
        <div class="meta">
            <span>📌 생성자: <strong>{{ ownerNickname }}</strong></span>
            <span v-if="challengerNickname">
                ⚔️ 도전자: <strong>{{ challengerNickname }}</strong>
            </span>
        </div>

        <!-- ===== 투표 UI ===== -->
        <div class="competition">
            <!-- HOME -->
            <div class="side home" :class="{ selected: userVote?.content === 'home' }">
                <h3>Home</h3>
                <p>{{ vote.content }}</p>
                <p class="count">{{ counts.home }}표</p>

                <label>
                    <input type="radio" value="home" v-model="selectedOption" :disabled="Boolean(userVote)" />
                    선택
                </label>
            </div>

            <!-- AWAY -->
            <div class="side away" :class="{ selected: userVote?.content === 'away' }">
                <h3>Away</h3>
                <p>{{ vote.challengerContent || '도전자가 없습니다.' }}</p>
                <p class="count">{{ counts.away }}표</p>

                <label v-if="vote.challengerId">
                    <input type="radio" value="away" v-model="selectedOption" :disabled="Boolean(userVote)" />
                    선택
                </label>

                <button v-else class="challenge-btn" @click="challengeVote(selectedOption || 'away')">
                    도전하기
                </button>
            </div>

            <!-- NEUTRAL -->
            <div class="side neutral" :class="{ selected: userVote?.content === 'neutral' }">
                <h3>Neutral</h3>
                <p class="count">{{ counts.neutral }}표</p>

                <label>
                    <input type="radio" value="neutral" v-model="selectedOption" :disabled="Boolean(userVote)" />
                    선택
                </label>
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
import memberApi from '@/api/memberApi'
import categoryApi from '@/api/categoryApi'

const router = useRouter()
const props = defineProps({
    voteId: Number
})

const vote = ref({})
const category = ref(null)
const ownerNickname = ref('')
const challengerNickname = ref('')
const voters = ref([])
const userVote = ref(null)
const selectedOption = ref(null)

const counts = ref({ home: 0, away: 0, neutral: 0 })

const user = JSON.parse(localStorage.getItem('userInfo'))

const canEdit = computed(
    () => user && vote.value.memberId === user.id && !vote.value.challengerId
)

const showGraphModal = ref(false)
const showUpdateModal = ref(false)
const showDeleteModal = ref(false)

const openUpdate = () => (showUpdateModal.value = true)
const openDelete = () => (showDeleteModal.value = true)

const refresh = async () => {
    const { data } = await voteApi.findById(props.voteId)
    vote.value = data

    if (vote.value.categoryId) {
        const categoryRes = await categoryApi.findAll()
        category.value = categoryRes.data.find(c => c.id === vote.value.categoryId)
    }

    const voteRes = await voterApi.findByVoteId(props.voteId)
    voters.value = voteRes.data

    counts.value = {
        home: voters.value.filter(v => v.content === 'home').length,
        away: voters.value.filter(v => v.content === 'away').length,
        neutral: voters.value.filter(v => v.content === 'neutral').length
    }

    if (user) {
        try {
            const uv = await voterApi.findByVoteAndMemberId(props.voteId, user.id)
            userVote.value = uv.data
            selectedOption.value = uv.data?.content || null
        } catch {
            userVote.value = null
        }
    }

    const owner = await memberApi.findById(vote.value.memberId)
    ownerNickname.value = owner.data.nickname

    if (vote.value.challengerId) {
        const challenger = await memberApi.findById(vote.value.challengerId)
        challengerNickname.value = challenger.data.nickname
    }
}

const handleVote = async content => {
    await voterApi.createVoter({
        voteId: props.voteId,
        memberId: user.id,
        content
    })
    refresh()
}

const cancelVote = async () => {
    await voterApi.deleteVoter(userVote.value.id)
    refresh()
}

const challengeVote = async content => {
    await voteApi.challengeVote(vote.value.id, {
        challengerId: user.id,
        challengerContent: content
    })
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
.competition {
    display: flex;
    gap: 20px;
    margin: 35px 0;
}

.side {
    flex: 1;
    padding: 25px;
    border-radius: 16px;
    text-align: center;
    font-size: 20px;
    border: 2px solid transparent;
    transition: .3s;
}

.side.home {
    background: #e3efff;
}

.side.away {
    background: #ffe4e4;
}

.side.neutral {
    background: #e4ffe6;
}

.side.selected {
    transform: scale(1.05);
    border-width: 4px;
}

.side.home.selected {
    border-color: #2a65ff;
}

.side.away.selected {
    border-color: #ff3b3b;
}

.side.neutral.selected {
    border-color: #1bbf4b;
}

.count {
    font-weight: bold;
    margin-top: 10px;
    font-size: 22px;
}

input[type="radio"] {
    transform: scale(1.6);
    margin-top: 10px;
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