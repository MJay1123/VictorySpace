<template>
    <section>
        <h3>전체 득표</h3>

        <div class="grid">
            <template v-for="type in ['home', 'away', 'neutral']" :key="type">
                <!-- 1열 -->
                <div class="label-box">
                    {{ type.toUpperCase() }}
                </div>

                <!-- 2열 -->
                <div class="bar-track">
                    <div class="bar" :class="type" :style="{ width: rate(type) + '%' }" />
                </div>

                <!-- 3열 -->
                <div class="value-box">
                    {{ stats[type] }}명 {{ rate(type) }}%
                </div>
            </template>
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

const stats = computed(() => ({
    home: props.voters.filter(v => v.content === 'home').length,
    away: props.voters.filter(v => v.content === 'away').length,
    neutral: props.voters.filter(v => v.content === 'neutral').length
}))

const total = computed(() =>
    stats.value.home + stats.value.away + stats.value.neutral
)

const rate = (type) => {
    return total.value ? Math.round((stats.value[type] / total.value) * 100, 1) : 0
}
</script>

<style scoped>
section {
    background: #ffffff;
    padding: 16px;
    border-radius: 12px;
}

.grid {
    display: grid;
    grid-template-columns: 90px 1fr 120px;
    row-gap: 12px;
    align-items: center;
}

.label-box {
    width: 90px;
    height: 50px;
    background: #727272a1;
    color: #000000;
    font-weight: 700;
    font-size: 16px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 6px;
}

.bar-track {
    height: 44px;
    background: #ffffff;
    border-radius: 6px;
    overflow: hidden;
}

.bar {
    height: 100%;
    border-radius: 5px;
    transition: width 1s ease;
}

.bar.home {
    background: #3b82f6;
}

.bar.away {
    background: #ef4444;
}

.bar.neutral {
    background: #22c55e;
}

.value-box {
    color: #000000;
    font-size: 18px;
    font-weight: 700;
    opacity: 0;
    animation: fadeIn 1s ease forwards;
    animation-delay: 1s;
}

@keyframes fadeIn {
    to {
        opacity: 1;
    }
}

.bar-label {
    display: flex;
    align-items: center;
    gap: 30px;
    white-space: nowrap;
    font-weight: 700;
    font-size: 20px;
    color: #fff;
}

.label-count {
    font-size: 20px;
}

.label-rate {
    font-size: 20px;
    opacity: 0.9;
}

.text-outline {
    text-shadow:
        -1px -1px 0 #666,
        1px -1px 0 #666,
        -1px 1px 0 #666,
        1px 1px 0 #666;
}
</style>