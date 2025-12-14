<template>
    <section class="vote-section">
        <div class="header">
            <div class="left-spacer"></div>

            <h1 class="title">{{ vote.title }}</h1>

            <VoteActionMenu v-if="canEdit" class="action-menu" @edit="openUpdate" @delete="openDelete" />
        </div>

        <p v-if="category" class="category">{{ category.name }}</p>

        <!-- 생성자 / 도전자 -->
        <div class="meta">
            <span>📌 생성자: <strong>{{ ownerNickname }}</strong></span>
            <span v-if="challengerNickname">⚔️ 도전자: <strong>{{ challengerNickname }}</strong></span>
        </div>

        <!-- 투표 UI -->
        <VoteCompetition :vote="vote" :counts="counts" :userVote="userVote" @vote="handleVote" @cancel="cancelVote"
            @challenge="challengeVote" />

        <!-- 수정 / 삭제 모달 -->
        <UpdateVoteModal v-if="showUpdateModal" :vote="vote" @close="showUpdateModal = false" @updated="refresh" />

        <DeleteVoteModal v-if="showDeleteModal" :voteId="vote.id" @close="showDeleteModal = false"
            @deleted="handleDeleted" />
    </section>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import VoteCompetition from './VoteCompetition.vue'
import VoteActionMenu from './VoteActionMenu.vue'
import UpdateVoteModal from './UpdateVoteModal.vue'
import DeleteVoteModal from './DeleteVoteModal.vue'

import voteApi from '@/api/voteApi'
import voterApi from '../../api/voterApi'
import memberApi from '@/api/memberApi'
import categoryApi from '@/api/categoryApi'

const props = defineProps({
    voteId: Number
})

const vote = ref({})
const category = ref(null)
const ownerNickname = ref('')
const challengerNickname = ref('')
const voters = ref([])
const userVote = ref(null)
const counts = ref({ home: 0, away: 0, neutral: 0 })

const canEdit = computed(() => {
    return user && vote.value.memberId === user.id && !vote.value.challengerId
    // return true;
})

const showUpdateModal = ref(false)
const showDeleteModal = ref(false)
const openUpdate = () => (showUpdateModal.value = true)
const openDelete = () => (showDeleteModal.value = true)

const user = JSON.parse(localStorage.getItem('userInfo'))

const refresh = async () => {
    const res = await voteApi.findById(props.voteId)
    vote.value = res.data

    if (vote.value.categoryId) {
        const cats = await categoryApi.findAll()
        category.value = cats.data.find(c => c.id === vote.value.categoryId)
    }

    const votersRes = await voterApi.findByVoteId(props.voteId)
    voters.value = votersRes.data
    counts.value = {
        home: voters.value.filter(v => v.content === 'home').length,
        away: voters.value.filter(v => v.content === 'away').length,
        neutral: voters.value.filter(v => v.content === 'neutral').length
    }

    if (user) {
        try {
            const uv = await voterApi.findByVoteAndMemberId(props.voteId, user.id)
            userVote.value = uv.data
        } catch {
            userVote.value = null
        }
    }

    const ownerRes = await memberApi.findById(vote.value.memberId)
    ownerNickname.value = ownerRes.data.nickname

    if (vote.value.challengerId) {
        const challengerRes = await memberApi.findById(vote.value.challengerId)
        challengerNickname.value = challengerRes.data.nickname;
    }
}

const handleVote = async (content) => {
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

const challengeVote = async (content) => {
    await voteApi.challengeVote(vote.value.id, {
        challengerId: user.id,
        challengerContent: content
    })
    refresh()
}

const handleDeleted = () => {
    alert('투표가 삭제되었습니다')
}

onMounted(refresh)
</script>

<style scoped>
.vote-section {
    padding: 32px;
}

.header {
    display: grid;
    grid-template-columns: 1fr auto 1fr;
    align-items: center;
    margin-bottom: 16px;
}

.title {
    grid-column: 2;
    text-align: center;
    font-size: 32px;
    font-weight: 700;
}

.action-menu {
    grid-column: 3;
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
</style>