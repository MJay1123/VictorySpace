<template>
    <div class="modal-backdrop" @click.self="$emit('close')">
        <div class="modal">
            <header class="modal-header">
                <h2>🏆 전체 랭킹</h2>
                <button class="close-btn" @click="$emit('close')">✕</button>
            </header>

            <div class="rank-scroll">
                <div v-for="(member, index) in topTwenty" :key="member.id" class="rank-item">
                    <span class="rank">{{ index + 1 }}</span>
                    <img :src="member.profile || defaultImage" class="avatar" :class="{ default: !member.profile }" />
                    <span class="nickname">{{ member.nickname }}</span>
                    <span class="grade">
                        {{ member.gradeSymbol }} {{ member.gradeName }}
                    </span>
                    <span class="point">{{ member.point }} P</span>
                </div>
            </div>

            <!-- 내 랭킹 -->
            <div v-if="myRank" class="my-rank">
                <span class="ellipsis">...</span>
                <div class="rank-item me">
                    <span class="rank">{{ myRank.rank }}</span>
                    <img :src="myRank.profile || defaultImage" class="avatar" />
                    <span class="nickname">{{ myRank.nickname }} (나)</span>
                    <span class="grade">
                        {{ myRank.gradeSymbol }} {{ myRank.gradeName }}
                    </span>
                    <span class="point">{{ myRank.point }} P</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import memberApi from '@/api/memberApi'
import defaultImage from '@/assets/images/logo2.png'

const rankList = ref([])
const userId = JSON.parse(localStorage.getItem('userInfo')).id

const topTwenty = computed(() => rankList.value.slice(0, 20))

const myRank = computed(() => {
    const index = rankList.value.findIndex(m => m.id === userId)
    if (index === -1 || index < 10) return null

    return {
        ...rankList.value[index],
        rank: index + 1
    }
})

onMounted(async () => {
    const res = await memberApi.orderRank()
    rankList.value = res.data
})
</script>

<style scoped>
.modal-backdrop {
    position: fixed;
    inset: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

.modal {
    width: 420px;
    max-height: 70vh;
    background: white;
    border-radius: 12px;
    padding: 1.5rem;
    display: flex;
    flex-direction: column;
}

.rank-scroll {
    overflow-y: auto;
    max-height: 360px;
    padding-right: 4px;
}

/* 스크롤바 예쁘게 (선택) */
.rank-scroll::-webkit-scrollbar {
    width: 6px;
}

.rank-scroll::-webkit-scrollbar-thumb {
    background: #ccc;
    border-radius: 4px;
}


.rank-item {
    display: flex;
    align-items: center;
    gap: 0.75rem;
    padding: 0.6rem 0;
    border-bottom: 1px solid #eee;
}

.rank {
    width: 24px;
    font-weight: 700;
}

.avatar {
    width: 32px;
    height: 32px;
    border-radius: 50%;
}

.point {
    margin-left: auto;
    font-weight: 600;
}

.me {
    background: #fff3cd;
    border-radius: 8px;
    padding: 0.6rem;
}

.ellipsis {
    text-align: center;
    color: #999;
    margin: 0.5rem 0;
}
</style>