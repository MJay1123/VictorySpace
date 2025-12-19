<template>
    <div>
        <div class="competition">
            <!-- HOME -->
            <div class="side home" :class="{ selected: userVote?.content === 'home' }">
                <h3>Home</h3>
                <p>{{ vote.content }}</p>
                <p class="count">{{ counts.home }}표</p>

                <label>
                    <input type="radio" value="home" v-model="selectedOption" :disabled="Boolean(userVote?.id)" />
                    선택
                </label>
            </div>

            <!-- AWAY -->
            <div class="side away" :class="{ selected: userVote?.content === 'away' }">
                <h3>Away</h3>
                <p>{{ vote.challengerContent || '도전자가 없습니다.' }}</p>
                <p class="count">{{ counts.away }}표</p>

                <label v-if="vote.challengerId">
                    <input type="radio" value="away" v-model="selectedOption" :disabled="Boolean(userVote?.id)" />
                    선택
                </label>

                <button v-else class="challenge-btn" @click="$emit('challenge')">
                    도전하기
                </button>
            </div>

            <!-- NEUTRAL -->
            <div class="side neutral" :class="{ selected: userVote?.content === 'neutral' }">
                <h3>Neutral</h3>
                <p class="count">{{ counts.neutral }}표</p>

                <label>
                    <input type="radio" value="neutral" v-model="selectedOption" :disabled="Boolean(userVote?.id)" />
                    선택
                </label>
            </div>
        </div>

        <button class="vote-btn" :disabled="!userVote && !selectedOption"
            @click="$emit(userVote ? 'cancel' : 'vote', selectedOption)">
            {{ userVote ? '투표 취소' : '투표하기' }}
        </button>

        <div class="summary-graph">
            <div class="bar home" :style="{ width: homeRate + '%' }">
                Home {{ counts.home }}
            </div>
            <div class="bar away" :style="{ width: awayRate + '%' }">
                Away {{ counts.away }}
            </div>
            <div class="bar neutral" :style="{ width: neutralRate + '%' }">
                Neutral {{ counts.neutral }}
            </div>
        </div>

        <button class="detail-btn" @click="$emit('detail')">
            📊 결과 자세히 보기
        </button>
    </div>
</template>

<script setup>

import { ref, computed, nextTick, onMounted } from 'vue'

const props = defineProps({
    vote: Object,
    userVote: {
        type: Object,
        default: null
    },
    counts: {
        type: Object,
        required: true,
    }
})

defineEmits(['vote', 'cancel', 'challenge', 'detail'])

const selectedOption = ref(null)

const total = computed(() =>
    props.counts.home + props.counts.away + props.counts.neutral
)


const homeRate = computed(() => total.value ? props.counts.home / total.value * 100 : 0)
const awayRate = computed(() => total.value ? props.counts.away / total.value * 100 : 0)
const neutralRate = computed(() => total.value ? props.counts.neutral / total.value * 100 : 0)

onMounted(async () => {
    enrichedVoters.value = await Promise.all(
        props.voters.map(async v => {
            const res = await memberApi.findById(v.memberId)
            return {
                ...v,
                gender: res.data.gender,
                birth: res.data.birthDate
            }
        })
    )
})

</script>

<style scoped>
/* ===== 투표 UI ===== */

.competition {
    display: flex;
    justify-content: space-between;
    gap: 20px;
    margin: 35px 0;
}

.side {
    flex: 1;
    padding: 25px;
    border-radius: 16px;
    background: #f6f7fb;
    text-align: center;
    font-size: 20px;
    transition: .3s;
    cursor: pointer;
    border: 2px solid transparent;
}

/* Home / Away / Neutral 기본 색 */
.side.home {
    background: #e3efff;
}

.side.away {
    background: #ffe4e4;
}

.side.neutral {
    background: #e4ffe6;
}

/* 선택 시 강조 */
.side.home.selected {
    border: 4px solid #2a65ff;
    background: #d8e4ff;
    transform: scale(1.05);
}

.side.away.selected {
    border: 4px solid #ff3b3b;
    background: #ffd6d6;
    transform: scale(1.05);
}

.side.neutral.selected {
    border: 4px solid #1bbf4b;
    background: #d8ffdf;
    transform: scale(1.05);
}

/* 라디오 버튼 크게 */
input[type="radio"] {
    transform: scale(1.8);
    margin-top: 10px;
    cursor: pointer;
}

.count {
    font-weight: bold;
    margin-top: 10px;
    font-size: 22px;
}

/* ===== 투표 버튼 ===== */
.vote-btn {
    width: 100%;
    background: #3b82f6;
    color: white;
    padding: 20px;
    border-radius: 15px;
    font-size: 22px;
    font-weight: bold;
    border: none;
    margin-top: 28px;
    cursor: pointer;
    transition: .25s;
}

.vote-btn:hover {
    background: #245ad8;
    transform: translateY(-3px);
}

/* ==== 투표율 그래프 */
.summary-graph {
    margin-top: 24px;
}

.bar {
    color: #fff;
    padding: 6px 10px;
    margin-bottom: 6px;
    border-radius: 6px;
    font-weight: 600;
    width: 0;
    transition: width 0.5s ease-out;
}

.bar.home {
    background: #2a65ff
}

.bar.away {
    background: #ff3b3b
}

.bar.neutral {
    background: #1bbf4b
}

.detail-btn {
    margin-top: 12px;
    padding: 10px 18px;
    border-radius: 999px;
    border: 1.5px solid #6366f1;
    background: linear-gradient(to right, #eef2ff, #ffffff);
    color: #4338ca;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.25s ease;
}

.detail-btn:hover {
    background: linear-gradient(to right, #6366f1, #4f46e5);
    color: #fff;
    transform: translateY(-2px);
    box-shadow: 0 6px 16px rgba(99, 102, 241, 0.35);
}
</style>