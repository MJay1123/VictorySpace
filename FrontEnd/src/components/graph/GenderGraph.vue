<template>
    <section class="gender-section">
        <h3 class="section-title">성별 비율</h3>

        <div v-if="total === 0" class="empty">
            아직 투표가 없습니다
        </div>

        <div v-else class="pie-wrapper">
            <svg viewBox="0 0 36 36" class="pie">
                <circle cx="18" cy="18" r="16" fill="none" stroke="#e5e7eb" stroke-width="4" />

                <circle v-if="female > 0" cx="18" cy="18" r="16" fill="none" stroke="#ec4899" stroke-width="4"
                    :stroke-dasharray="femaleDash + ' ' + CIRCUMFERENCE" />

                <circle v-if="male > 0" cx="18" cy="18" r="16" fill="none" stroke="#3b82f6" stroke-width="4"
                    :stroke-dasharray="maleDash + ' ' + CIRCUMFERENCE" :stroke-dashoffset="`-${femaleDash}`" />

                <!-- 👨 남자 (위) -->
                <text x="18" y="14" text-anchor="middle" class="center-label male">
                    👨 {{ male }} ({{ malePercent }}%)
                </text>

                <!-- 👩 여자 (아래) -->
                <text x="18" y="22" text-anchor="middle" class="center-label female">
                    👩 {{ female }} ({{ femalePercent }}%)
                </text>
            </svg>
        </div>
    </section>
</template>


<script setup>
import { computed } from 'vue'

const props = defineProps({
    voters: {
        type: Array,
        required: true
    }
})

const male = computed(() =>
    props.voters.filter(v => v.gender === 'M').length
)

const female = computed(() =>
    props.voters.filter(v => v.gender === 'F').length
)

const total = computed(() => male.value + female.value)

const malePercent = computed(() =>
    total.value ? Math.round((male.value / total.value) * 100) : 0
)

const femalePercent = computed(() =>
    total.value ? Math.round((female.value / total.value) * 100) : 0
)

const RADIUS = 16
const CIRCUMFERENCE = 2 * Math.PI * RADIUS

const maleDash = computed(() =>
    total.value ? (male.value / total.value) * CIRCUMFERENCE : 0
)

const femaleDash = computed(() =>
    total.value ? (female.value / total.value) * CIRCUMFERENCE : 0
)
</script>

<style scoped>
.gender-section {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.section-title {
    font-size: 16px;
    font-weight: 700;
    margin-bottom: 8px;
}

.pie-wrapper {
    display: flex;
    justify-content: center;
    align-items: center;
}

.pie {
    width: 180px;
    height: 180px;
    transform-origin: center;
}

circle {
    transition: stroke-dasharray 0.8s ease, stroke-dashoffset 0.8s ease;
}

.center-label {
    font-size: 3.8px;
    font-weight: 700;
    fill: #374151;
}

.center-label.male {
    fill: #3b82f6;
}

.center-label.female {
    fill: #ec4899;
}

.empty {
    color: #9ca3af;
    font-size: 14px;
    padding: 12px;
    text-align: center;
}
</style>
