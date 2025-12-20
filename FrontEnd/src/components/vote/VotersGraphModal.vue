<template>
    <div class="modal-backdrop" @click.self="$emit('close')">
        <div class="modal">
            <header class="modal-header">
                <h2>📊 투표 결과 분석</h2>
                <button class="close-btn" @click="$emit('close')">✕</button>
            </header>

            <!-- 🔥 위: 막대 + 원그래프 -->
            <div class="top-graphs">
                <VotersSection :voters="enrichedVoters" v-model:selectedType="selectedType" />

                <GenderGraph :voters="filteredVoters" />
            </div>

            <!-- 🔥 아래: 연령대 그래프 -->
            <div class="bottom-graph">
                <AgeLineGraph :voters="filteredVoters" :selectedType="selectedType" />
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import memberApi from '@/api/memberApi'

import VotersSection from '../graph/VotersSection.vue'
import GenderGraph from '../graph/GenderGraph.vue'
import AgeLineGraph from '../graph/AgeLineGraph.vue'

const props = defineProps({
    voters: Array
})

const enrichedVoters = ref([])
const selectedType = ref('total')

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

const filteredVoters = computed(() => {
    if (selectedType.value === 'total') return enrichedVoters.value
    return enrichedVoters.value.filter(v => v.content === selectedType.value)
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

/* 위: 막대 + 원그래프 */
.top-graphs {
    display: grid;
    grid-template-columns: 2fr 1fr;
    /* 막대 넓게, 원그래프 좁게 */
    gap: 24px;
    margin-bottom: 32px;
    align-items: start;
}

/* 아래: 연령대 */
.bottom-graph {
    margin-top: 12px;
}
</style>