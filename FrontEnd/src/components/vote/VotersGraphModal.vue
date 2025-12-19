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
                        <span class="bar-label text-outline">
                            Home {{ totalStats.home }}
                        </span>
                    </div>

                    <div class="bar away" :style="{ width: rate('away') + '%' }">
                        <span class="bar-label text-outline">
                            Away {{ totalStats.away }}
                        </span>
                    </div>

                    <div class="bar neutral" :style="{ width: rate('neutral') + '%' }">
                        <span class="bar-label text-outline">
                            Neutral {{ totalStats.neutral }}
                        </span>
                    </div>
                </div>

                <div class="selector">
                    <button v-for="type in ['total', 'home', 'away', 'neutral']" :key="type"
                        :class="{ active: selectedType === type }" @click="selectedType = type">
                        {{ type.toUpperCase() }}
                    </button>
                </div>
            </section>

            <!-- 성별 -->
            <section>
                <h3>성별 비율</h3>

                <div v-if="genderTotal === 0" class="empty">
                    아직 투표가 없습니다
                </div>

                <div class="pie-wrapper">
                    <svg viewBox="0 0 36 36" class="pie">

                        <!-- 배경 -->
                        <circle cx="18" cy="18" r="16" fill="none" stroke="#e5e7eb" stroke-width="4" />

                        <circle v-if="femaleDash > 0" cx="18" cy="18" r="16" fill="none" stroke-width="4"
                            class="pie-female" :stroke-dasharray="`${femaleDash} ${CIRCUMFERENCE}`" />

                        <circle v-if="maleDash > 0" cx="18" cy="18" r="16" fill="none" stroke-width="4" class="pie-male"
                            :stroke-dasharray="`${maleDash} ${CIRCUMFERENCE}`" :stroke-dashoffset="`-${femaleDash}`" />
                    </svg>

                    <div class="pie-legend">
                        <div>👨 남 {{ genderRatio.M }}명</div>
                        <div>👩 여 {{ genderRatio.F }}명</div>
                    </div>
                </div>
            </section>

            <!-- 연령 -->
            <section>
                <h3>
                    연령대별 분포
                    <span v-if="selectedType === 'total'" style="color:#6366f1;font-size:13px">
                        (전체 기준)
                    </span>
                </h3>

                <div v-if="ageTotal === 0" class="empty">
                    아직 투표가 없습니다
                </div>

                <svg v-else viewBox="0 0 300 150" preserveAspectRatio="none" class="line-chart">
                    <!-- 선 -->
                    <polyline :points="linePoints" fill="none" stroke="#6366f1" stroke-width="3" />

                    <!-- 점 -->
                    <g v-for="(p, i) in pointList" :key="i">
                        <circle :cx="p.x" :cy="p.y" r="4" fill="#6366f1" />

                        <!-- 숫자 -->
                        <text v-if="p.count > 0" :x="p.x + 6" :y="p.y - 8" font-size="10" font-weight="700"
                            fill="#374151">
                            {{ p.count }}
                        </text>
                    </g>

                    <g v-for="(label, i) in ageLabels" :key="label">
                        <!-- 세로 가이드라인 -->
                        <line :x1="PADDING_X + i * xGap" y1="30" :x2="PADDING_X + i * xGap" y2="120" stroke="#e5e7eb"
                            stroke-dasharray="4 4" />

                        <line x1="PADDING_X" y1="120" :x2="CHART_WIDTH - PADDING_X" y2="120" stroke="#9ca3af" />

                        <!-- x축 라벨 -->
                        <text :x="PADDING_X + i * xGap" y="138" text-anchor="middle" font-size="7" fill="#6b7280">
                            {{ label }}
                        </text>
                    </g>

                </svg>
            </section>

        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import memberApi from '@/api/memberApi'

const props = defineProps({
    voteId: Number,
    voters: {
        type: Array,
        required: true // [{ memberId, content }]
    }
})

const selectedType = ref('home') // home | away | neutral
const enrichedVoters = ref([])
const ageLabels = computed(() =>
    ageBuckets.map((start, i) => {
        const end = ageBuckets[i + 1]
        return end ? `${start}–${end - 1}` : `${start}+`
    })
)


const filteredVoters = computed(() => {
    if (selectedType.value === 'total') {
        return enrichedVoters.value
    }
    return enrichedVoters.value.filter(v => v.content === selectedType.value)
})

const RADIUS = 16
const CIRCUMFERENCE = 2 * Math.PI * RADIUS

/* -----------------------------
   member 정보 합치기
------------------------------*/
onMounted(async () => {
    enrichedVoters.value = await Promise.all(
        props.voters.map(async v => {
            const res = await memberApi.findById(v.memberId)
            return {
                ...v,
                gender: res.data.gender,
                birth: res.data.birthday
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
const femaleDash = computed(() =>
    (femaleRate.value / 100) * CIRCUMFERENCE
)

const maleDash = computed(() =>
    (maleRate.value / 100) * CIRCUMFERENCE
)

const maleRate = computed(() =>
    genderTotal.value
        ? Math.round((genderRatio.value.M / genderTotal.value) * 100)
        : 0
)

const femaleRate = computed(() =>
    genderTotal.value
        ? Math.round((genderRatio.value.F / genderTotal.value) * 100)
        : 0
)

const genderRatio = computed(() => {
    const stats = { M: 0, F: 0 }
    filteredVoters.value.forEach(v => {
        if (stats[v.gender] !== undefined) {
            stats[v.gender]++
        }
    })
    return stats
})

const genderTotal = computed(() =>
    genderRatio.value.M + genderRatio.value.F
)

/* -----------------------------
   연령대 통계
------------------------------*/

const ageBuckets = [10, 15, 20, 25, 30, 35, 40, 45, 50]

const CHART_WIDTH = 300
const PADDING_X = 40

const xGap = computed(() =>
    (CHART_WIDTH - PADDING_X * 2) / (ageBuckets.length - 1)
)


const maxAgeCount = computed(() => {
    const max = Math.max(...Object.values(ageStats.value))
    return max === 0 ? 1 : max
})

const pointList = computed(() =>
    ageBuckets.map((age, i) => ({
        x: PADDING_X + i * xGap.value,
        y: Math.max(
            30,
            120 - (ageStats.value[age] / maxAgeCount.value) * 80
        ),
        count: ageStats.value[age]
    }))
)

const linePoints = computed(() =>
    pointList.value.map(p => `${p.x},${p.y}`).join(' ')
)

const getAge = (birth) => {
    const today = new Date()
    const b = new Date(birth)
    let age = today.getFullYear() - b.getFullYear()

    const m = today.getMonth() - b.getMonth()
    if (m < 0 || (m === 0 && today.getDate() < b.getDate())) {
        age--
    }
    return age
}

const ageStats = computed(() => {
    const result = {}
    ageBuckets.forEach(a => (result[a] = 0))

    filteredVoters.value.forEach(v => {
        const age = getAge(v.birth)

        for (let i = ageBuckets.length - 1; i >= 0; i--) {
            if (age >= ageBuckets[i]) {
                result[ageBuckets[i]]++
                break
            }
        }
    })

    return result
})

const ageTotal = computed(() =>
    Object.values(ageStats.value).reduce((a, b) => a + b, 0)
)
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
    width: 1200px;
    max-width: 95vw;
    max-height: 90vh;
    padding: 28px;
    overflow-y: auto;
    border-radius: 12px;
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

.selector {
    display: flex;
    gap: 10px;
    margin-top: 12px;
}

.selector button {
    flex: 1;
    padding: 12px 0;
    font-size: 15px;
    font-weight: 700;
    border-radius: 8px;
    border: 1px solid #d1d5db;
    background: #f9fafb;
    cursor: pointer;
    transition: all 0.3s ease;
}

.selector button.active {
    background: #6366f1;
    color: white;
    border-color: #6366f1;
}

.bar-group {
    margin-top: 10px;
}

.bar {
    min-width: 90px;
    /* 🔥 최소 길이 보장 */
    height: 28px;
    display: flex;
    align-items: center;
    padding-left: 10px;
    border-radius: 6px;
    transition: width 0.8s ease;
    box-sizing: border-box;
}

.bar-label {
    white-space: nowrap;
    font-weight: 700;
    font-size: 14px;
    color: #fff;
}

.bar.male {
    background: #3b82f6;
}

.bar.female {
    background: #ec4899;
}


.bar.home {
    background: #22c55e;
}


.bar.away {
    background: #ef4444;
}


.bar.neutral {
    background: #3b82f6;
}

.pie-wrapper {
    display: flex;
    align-items: center;
    gap: 20px;
}

.pie {
    width: 120px;
    height: 120px;
    transform: rotate(-90deg);
}

.pie circle {
    transition: stroke-dasharray 0.8s ease;
}

.pie-male {
    stroke: #3b82f6;
}

.pie-female {
    stroke: #ec4899;
}

.pie-legend {
    font-size: 14px;
    line-height: 1.6;
}

@keyframes draw-line {
    to {
        stroke-dashoffset: 0;
    }
}

.line-chart {
    width: 100%;
    margin-top: 12px;
}


.line-chart text {
    paint-order: stroke;
    stroke: #ffffff;
    stroke-width: 2px;
}

.empty {
    color: #9ca3af;
    font-size: 14px;
    padding: 12px;
    text-align: center;
}

.text-outline {
    text-shadow:
        -1px -1px 0 #666,
        1px -1px 0 #666,
        -1px 1px 0 #666,
        1px 1px 0 #666;
}
</style>