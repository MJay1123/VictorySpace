<template>
    <div class="modal-backdrop" @click.self="$emit('close')">
        <div class="modal">
            <header class="modal-header">
                <h2>📊 투표 결과 분석</h2>
                <button class="close-btn" @click="$emit('close')">✕</button>
            </header>

            <!-- 전체 득표 -->
            <section>
                <h3>전체 득표</h3>
                <div class="bar-group">
                    <div class="bar home" :style="{ width: rate('home') + '%' }">
                        Home {{ totalStats.home }}
                    </div>
                    <div class="bar away" :style="{ width: rate('away') + '%' }">
                        Away {{ totalStats.away }}
                    </div>
                    <div class="bar neutral" :style="{ width: rate('neutral') + '%' }">
                        Neutral {{ totalStats.neutral }}
                    </div>
                </div>
            </section>

            <!-- 성별 -->
            <section>
                <h3>성별별 투표</h3>
                <div class="grid">
                    <div v-for="gender in ['M', 'W']" :key="gender" class="card">
                        <h4>{{ gender === 'M' ? '남성' : '여성' }}</h4>
                        <!-- <GenderBars :stats="genderStats[gender]" /> -->
                    </div>
                </div>
            </section>

            <!-- 연령 -->
            <section>
                <h3>연령대별 투표</h3>
                <div class="grid">
                    <div v-for="(stats, age) in ageStats" :key="age" class="card">
                        <h4>{{ age }}</h4>
                        <!-- <GenderBars :stats="stats" /> -->
                    </div>
                </div>
            </section>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import memberApi from '@/api/memberApi'
// import GenderBars from './GenderBars.vue'

const props = defineProps({
    voteId: Number,
    voters: {
        type: Array,
        required: true // [{ memberId, content }]
    }
})

const enrichedVoters = ref([])

/* -----------------------------
   member 정보 합치기
------------------------------*/
onMounted(async () => {
    enrichedVoters.value = await Promise.all(
        props.voters.map(async v => {
            const res = await memberApi.findById(v.memberId)
            return {
                ...v,
                gender: res.data.gender,      // 'M' | 'W'
                birth: res.data.birthDate    // '1999-03-01'
            }
        })
    )
})

/* -----------------------------
   전체 득표
------------------------------*/
const totalStats = computed(() => {
    return {
        home: enrichedVoters.value.filter(v => v.content === 'home').length,
        away: enrichedVoters.value.filter(v => v.content === 'away').length,
        neutral: enrichedVoters.value.filter(v => v.content === 'neutral').length
    }
})

const totalCount = computed(() =>
    totalStats.value.home +
    totalStats.value.away +
    totalStats.value.neutral
)

const rate = (type) => {
    if (!totalCount.value) return 0
    return Math.round((totalStats.value[type] / totalCount.value) * 100)
}

/* -----------------------------
   성별 통계
------------------------------*/
const genderStats = computed(() => {
    const stats = {
        M: { home: 0, away: 0, neutral: 0 },
        W: { home: 0, away: 0, neutral: 0 }
    }

    enrichedVoters.value.forEach(v => {
        if (stats[v.gender]) {
            stats[v.gender][v.content]++
        }
    })

    return stats
})

/* -----------------------------
   연령대 통계
------------------------------*/
const getAgeGroup = (birth) => {
    const age = new Date().getFullYear() - new Date(birth).getFullYear()
    if (age < 20) return '10대'
    if (age < 30) return '20대'
    if (age < 40) return '30대'
    if (age < 50) return '40대'
    return '50대+'
}

const ageStats = computed(() => {
    const groups = {}

    enrichedVoters.value.forEach(v => {
        const group = getAgeGroup(v.birth)
        if (!groups[group]) {
            groups[group] = { home: 0, away: 0, neutral: 0 }
        }
        groups[group][v.content]++
    })

    return groups
})
</script>

<style scoped>
.modal-backdrop {
    position: fixed;
    inset: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
}

.modal {
    background: #fff;
    width: 800px;
    max-height: 90vh;
    overflow-y: auto;
    border-radius: 12px;
    padding: 20px;
}

.modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.close-btn {
    background: none;
    border: none;
    font-size: 20px;
    cursor: pointer;
}

.bar-group {
    margin-top: 10px;
}

.bar {
    color: #fff;
    padding: 6px 10px;
    margin-bottom: 6px;
    border-radius: 6px;
    font-weight: 600;
}

.bar.home {
    background: #2ecc71;
}

.bar.away {
    background: #e74c3c;
}

.bar.neutral {
    background: #95a5a6;
}

.grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
    gap: 12px;
}

.card {
    background: #f8f9fa;
    padding: 12px;
    border-radius: 8px;
}
</style>