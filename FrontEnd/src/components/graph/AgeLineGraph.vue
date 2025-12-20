<template>
    <section>
        <h3>
            연령대별 분포
            <span v-if="selectedType === 'total'" class="hint">(전체 기준)</span>
        </h3>

        <div v-if="ageTotal === 0" class="empty">
            아직 투표가 없습니다
        </div>

        <svg v-else viewBox="0 0 300 150" preserveAspectRatio="none" class="line-chart">
            <!-- 꺾은선 -->
            <polyline :points="linePoints" fill="none" stroke="#6366f1" stroke-width="2" />

            <!-- 점 + 숫자 -->
            <g v-for="(p, i) in displayPoints" :key="i">
                <circle :cx="p.x" :cy="p.y" r="3" fill="#6366f1" />

                <text v-if="p.count > 0" :x="p.x + 2" :y="p.y - 8" font-size="6" font-weight="700" fill="#374151">
                    {{ p.count }}
                </text>
            </g>

            <!-- 가이드라인 + x축 -->
            <g v-for="(label, i) in ageLabels" :key="label">
                <!-- 세로 가이드라인 -->
                <line :x1="PADDING_X + i * xGap" y1="30" :x2="PADDING_X + i * xGap" y2="120" stroke="#e5e7eb"
                    stroke-dasharray="4 4" />

                <!-- x축 라벨 -->
                <text :x="PADDING_X + i * xGap" y="138" text-anchor="middle" font-size="7" fill="#6b7280">
                    {{ label }}
                </text>
            </g>

            <!-- x축 베이스라인 -->
            <line :x1="PADDING_X" y1="120" :x2="CHART_WIDTH - PADDING_X" y2="120" stroke="#9ca3af" />
        </svg>
    </section>
</template>

<script setup>
import { ref, watch, computed, nextTick } from 'vue'

const animated = ref(false)

const props = defineProps({
    voters: {
        type: Array,
        required: true
    },
    selectedType: String
})

/* -----------------------------
   연령 구간
------------------------------*/
const ageBuckets = [10, 15, 20, 25, 30, 35, 40, 45, 50]

const ageLabels = computed(() =>
    ageBuckets.map((start, i) => {
        const end = ageBuckets[i + 1]
        return end ? `${start}–${end - 1}` : `${start}+`
    })
)

/* -----------------------------
   차트 상수
------------------------------*/
const CHART_WIDTH = 300
const PADDING_X = 40

const xGap = computed(() =>
    (CHART_WIDTH - PADDING_X * 2) / (ageBuckets.length - 1)
)

/* -----------------------------
   나이 계산
------------------------------*/
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

/* -----------------------------
   연령대 통계
------------------------------*/
const ageStats = computed(() => {
    const result = {}
    ageBuckets.forEach(a => (result[a] = 0))

    props.voters.forEach(v => {
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

const maxAgeCount = computed(() => {
    const max = Math.max(...Object.values(ageStats.value))
    return max === 0 ? 1 : max
})

/* -----------------------------
   그래프 좌표 계산
------------------------------*/
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

const realPointList = computed(() =>
    ageBuckets.map((age, i) => ({
        x: PADDING_X + i * xGap.value,
        y: Math.max(
            30,
            120 - (ageStats.value[age] / maxAgeCount.value) * 80
        ),
        count: ageStats.value[age]
    }))
)

const displayPoints = ref([])

const animateGraph = async () => {
    // 1️⃣ 전부 바닥으로 초기화
    displayPoints.value = realPointList.value.map(p => ({
        ...p,
        y: 120
    }))

    animated.value = false

    // 2️⃣ DOM 반영 기다림
    await nextTick()

    // 3️⃣ 다음 프레임에서 실제 위치로
    requestAnimationFrame(() => {
        displayPoints.value = realPointList.value
        animated.value = true
    })
}

watch(
    () => [props.voters, props.selectedType],
    animateGraph,
    { immediate: true, deep: true }
)

const linePoints = computed(() =>
    displayPoints.value.map(p => `${p.x},${p.y}`).join(' ')
)

</script>

<style scoped>
.line-chart {
    width: 100%;
    margin-top: 12px;
}


.line-chart text {
    paint-order: stroke;
    stroke: #ffffff;
    stroke-width: 2px;
}

.hint {
    color: #6366f1;
    font-size: 13px;
    margin-left: 6px;
}

.empty {
    color: #9ca3af;
    font-size: 14px;
    padding: 12px;
    text-align: center;
}
</style>