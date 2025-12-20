<template>
    <section>
        <h3>전체 득표</h3>
        <div class="bar-group">
            <div v-for="type in ['home', 'away', 'neutral']" :key="type" class="bar" :class="type"
                :style="{ width: rate(type) + '%' }">
                <span class="bar-label text-outline">
                    {{ type.toUpperCase() }} {{ stats[type] }}
                </span>
            </div>
        </div>

        <div class="selector">
            <button v-for="type in ['total', 'home', 'away', 'neutral']" :key="type"
                :class="{ active: selectedType === type }" @click="$emit('update:selectedType', type)">
                {{ type.toUpperCase() }}
            </button>
        </div>
    </section>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
    voters: Array,
    selectedType: String
})

defineEmits(['update:selectedType'])

const stats = computed(() => ({
    home: props.voters.filter(v => v.content === 'home').length,
    away: props.voters.filter(v => v.content === 'away').length,
    neutral: props.voters.filter(v => v.content === 'neutral').length
}))

const total = computed(() =>
    stats.value.home + stats.value.away + stats.value.neutral
)

const rate = type =>
    total.value ? Math.round((stats.value[type] / total.value) * 100) : 0
</script>

<style scoped>

.bar-group {
    margin-top: 10px;
}

.bar {
    min-width: 90px;
    /* 🔥 최소 길이 보장 */
    height: 50px;
    display: flex;
    align-items: center;
    padding-left: 10px;
    border-radius: 6px;
    transition: width 0.8s ease;
    box-sizing: border-box;
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

.bar-label {
    white-space: nowrap;
    font-weight: 700;
    font-size: 14px;
    color: #fff;
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

.text-outline {
    text-shadow:
        -1px -1px 0 #666,
        1px -1px 0 #666,
        -1px 1px 0 #666,
        1px 1px 0 #666;
}

</style>