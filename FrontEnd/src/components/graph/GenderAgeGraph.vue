<template>
    <section class="gender-age-section">
        <h3 class="section-title">남녀 연령대 비율</h3>

        <div v-if="total === 0" class="empty">
            아직 투표가 없습니다
        </div>

        <div v-else class="chart-container">
            <svg viewBox="0 0 400 220" class="gender-age-chart">
                <!-- 범례 (그래프 상단 중앙) -->
                <g class="legend" :transform="`translate(${CHART_WIDTH / 2 - 55}, 5)`">
                    <rect x="0" y="0" width="12" height="12" fill="#3b82f6" rx="3" />
                    <text x="20" y="10" font-size="10" fill="#374151" font-weight="600">남성</text>

                    <rect x="70" y="0" width="12" height="12" fill="#ec4899" rx="3" />
                    <text x="90" y="10 " font-size="10" fill="#374151" font-weight="600">여성</text>
                </g>


                <!-- 그리드 라인 -->
                <g v-for="(label, i) in ageLabels" :key="label">
                    <line :x1="PADDING_X + i * xGap" y1="25" :x2="PADDING_X + i * xGap" y2="140" stroke="#e5e7eb"
                        stroke-dasharray="2 2" :class="{ 'animate-in': animated }" />
                </g>

                <!-- 남성 막대 -->
                <g v-for="(point, i) in malePoints" :key="'male-' + i" class="bar-group">
                    <rect :x="point.x - barWidth / 2" :y="point.y" :width="barWidth" :height="point.height"
                        fill="#3b82f6" :class="{ 'animate-in': animated }"
                        :style="{ 'animation-delay': `${i * 0.05}s` }" rx="4" />
                    <text v-if="point.count > 0" :x="point.x" :y="point.y - 5" text-anchor="middle" font-size="8"
                        font-weight="700" fill="#1e40af">
                        {{ point.count }}
                    </text>
                </g>

                <!-- 여성 막대 -->
                <g v-for="(point, i) in femalePoints" :key="'female-' + i" class="bar-group">
                    <rect :x="point.x + barWidth / 2" :y="point.y" :width="barWidth" :height="point.height"
                        fill="#ec4899" :class="{ 'animate-in': animated }"
                        :style="{ 'animation-delay': `${i * 0.05 + 0.1}s` }" rx="4" />
                    <text v-if="point.count > 0" :x="point.x + barWidth" :y="point.y - 5" text-anchor="middle"
                        font-size="8" font-weight="700" fill="#be185d">
                        {{ point.count }}
                    </text>
                </g>

                <!-- 베이스라인 -->
                <line :x1="PADDING_X" y1="140" :x2="CHART_WIDTH - PADDING_X" y2="140" stroke="#9ca3af"
                    stroke-width="2" />


                <!-- x축 라벨 -->
                <g v-for="(label, i) in ageLabels" :key="label">
                    <text :x="PADDING_X + i * xGap" y="185" text-anchor="middle" font-size="10" fill="#6b7280"
                        font-weight="500">
                        {{ label }}
                    </text>
                </g>
            </svg>
        </div>
    </section>
</template>

<script setup>
import { ref, computed, watch, nextTick } from 'vue'

const props = defineProps({
    voters: {
        type: Array,
        required: true
    }
})

const animated = ref(false)

const ageBuckets = [10, 15, 20, 25, 30, 35, 40, 45, 50]

const ageLabels = computed(() =>
    ageBuckets.map((start, i) => {
        const end = ageBuckets[i + 1]
        return end ? `${start}–${end - 1}` : `${start}+`
    })
)

const CHART_WIDTH = 400
const PADDING_X = 50
const barWidth = 8

const xGap = computed(() =>
    (CHART_WIDTH - PADDING_X * 2) / (ageBuckets.length - 1)
)

const getAge = birth => {
    const today = new Date()
    const b = new Date(birth)
    let age = today.getFullYear() - b.getFullYear()
    const m = today.getMonth() - b.getMonth()
    if (m < 0 || (m === 0 && today.getDate() < b.getDate())) {
        age--
    }
    return age
}

const ageGenderStats = computed(() => {
    const result = {}
    ageBuckets.forEach(a => {
        result[a] = { male: 0, female: 0 }
    })

    props.voters.forEach(v => {
        const age = getAge(v.birth)
        const gender = v.gender

        for (let i = ageBuckets.length - 1; i >= 0; i--) {
            if (age >= ageBuckets[i]) {
                if (gender === 'M') {
                    result[ageBuckets[i]].male++
                } else if (gender === 'F') {
                    result[ageBuckets[i]].female++
                }
                break
            }
        }
    })

    return result
})

const maxCount = computed(() => {
    let max = 0
    Object.values(ageGenderStats.value).forEach(stat => {
        max = Math.max(max, stat.male, stat.female)
    })
    return max === 0 ? 1 : max
})

const total = computed(() => {
    let sum = 0
    Object.values(ageGenderStats.value).forEach(stat => {
        sum += stat.male + stat.female
    })
    return sum
})

const malePoints = computed(() =>
    ageBuckets.map((age, i) => {
        const count = ageGenderStats.value[age].male
        const height = (count / maxCount.value) * 110
        return {
            x: PADDING_X + i * xGap.value,
            y: 140 - height,
            height: height,
            count: count
        }
    })
)

const femalePoints = computed(() =>
    ageBuckets.map((age, i) => {
        const count = ageGenderStats.value[age].female
        const height = (count / maxCount.value) * 110
        return {
            x: PADDING_X + i * xGap.value,
            y: 140 - height,
            height: height,
            count: count
        }
    })
)

const animateChart = async () => {
    animated.value = false
    await nextTick()
    requestAnimationFrame(() => {
        animated.value = true
    })
}

watch(
    () => props.voters,
    animateChart,
    { immediate: true, deep: true }
)
</script>

<style scoped>
.gender-age-section {
    display: flex;
    flex-direction: column;
}

.section-title {
    font-size: 18px;
    font-weight: 700;
    margin-bottom: 16px;
    color: #1f2937;
}

.chart-container {
    width: 100%;
    padding: 12px;
    background: #f9fafb;
    border-radius: 12px;
}

.gender-age-chart {
    width: 100%;
    height: auto;
}

.bar-group {
    transition: all 0.6s cubic-bezier(0.4, 0, 0.2, 1);
}

.bar-group rect {
    transform-origin: bottom;
    transition: all 0.6s cubic-bezier(0.4, 0, 0.2, 1);
}

.bar-group.animate-in rect {
    animation: slideUp 0.6s cubic-bezier(0.4, 0, 0.2, 1) forwards;
}

@keyframes slideUp {
    from {
        transform: scaleY(0);
        opacity: 0;
    }

    to {
        transform: scaleY(1);
        opacity: 1;
    }
}

.empty {
    color: #9ca3af;
    font-size: 14px;
    padding: 24px;
    text-align: center;
    background: #f9fafb;
    border-radius: 12px;
}
</style>
