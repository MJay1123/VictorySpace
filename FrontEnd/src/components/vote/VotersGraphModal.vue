<template>
    <div class="modal-backdrop" @click.self="$emit('close')">
        <div class="modal">
            <header class="modal-header">
                <h2>📊 투표 결과 분석</h2>
                <button class="close-btn" @click="$emit('close')">✕</button>
            </header>

            <!-- 필터 컨트롤 -->
            <div class="filter-controls">
                <div class="filter-group">
                    <label class="filter-label">투표 타입:</label>
                    <div class="filter-buttons">
                        <button v-for="type in ['total', 'home', 'away', 'neutral']" :key="type"
                            :class="{ active: selectedType === type }" @click="updateSelectedType(type)"
                            class="filter-btn">
                            {{ getTypeLabel(type) }}
                        </button>
                    </div>
                </div>
            </div>

            <!-- 🔥 위: 막대 + 원그래프 -->
            <div class="top-graphs">
                <div class="graph-card">
                    <VotersSection :voters="voters" />
                </div>
                <div class="graph-card">
                    <GenderGraph :voters="filteredVoters" />
                </div>
            </div>

            <!-- 🔥 중간: 연령대 그래프 -->
            <div class="middle-graph">
                <div class="graph-card">
                    <AgeLineGraph :voters="filteredVoters" :selectedType="selectedType" />
                </div>
            </div>

            <!-- 🔥 아래: 남녀 연령대 비율 그래프 -->
            <div class="bottom-graph">
                <div class="graph-card">
                    <GenderAgeGraph :voters="filteredVoters" />
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import voterApi from '@/api/voterApi'

import VotersSection from '../graph/VotersSection.vue'
import GenderGraph from '../graph/GenderGraph.vue'
import AgeLineGraph from '../graph/AgeLineGraph.vue'
import GenderAgeGraph from '../graph/GenderAgeGraph.vue'

const props = defineProps({
    voteId: {
        type: Number,
        required: true
    }
})

const voters = ref([])
const selectedType = ref('total')

const getTypeLabel = (type) => {
    const labels = {
        total: '전체',
        home: 'Home',
        away: 'Away',
        neutral: 'Neutral'
    }
    return labels[type] || type
}

const updateSelectedType = (type) => {
    if (selectedType.value === type) return
    selectedType.value = type
}

const fetchVoters = async () => {
    try {
        const res = await voterApi.findByVoteId(props.voteId)
        voters.value = res.data
    } catch (error) {
        console.error('투표자 조회 실패', error)
    }
}

const filteredVoters = computed(() => {
    if (selectedType.value === 'total') return voters.value
    return voters.value.filter(v => v.content === selectedType.value)
})

onMounted(fetchVoters)
</script>

<style scoped>
.modal-backdrop {
    position: fixed;
    inset: 0;
    background: rgba(0, 0, 0, 0.6);
    backdrop-filter: blur(4px);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
    animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
    from {
        opacity: 0;
    }

    to {
        opacity: 1;
    }
}

.modal {
    background: #fff;
    width: 1400px;
    max-width: 95vw;
    max-height: 92vh;
    padding: 32px;
    overflow-y: auto;
    border-radius: 20px;
    box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
    animation: slideUp 0.3s ease;
}

@keyframes slideUp {
    from {
        transform: translateY(20px);
        opacity: 0;
    }

    to {
        transform: translateY(0);
        opacity: 1;
    }
}

.modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 24px;
    padding-bottom: 20px;
    border-bottom: 2px solid #e5e7eb;
}

.modal-header h2 {
    font-size: 28px;
    font-weight: 700;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
    margin: 0;
}

.close-btn {
    background: #f3f4f6;
    border: none;
    font-size: 24px;
    width: 40px;
    height: 40px;
    border-radius: 50%;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.3s ease;
    color: #6b7280;
}

.close-btn:hover {
    background: #e5e7eb;
    transform: rotate(90deg);
    color: #374151;
}

/* 필터 컨트롤 */
.filter-controls {
    margin-bottom: 28px;
    padding: 20px;
    background: linear-gradient(135deg, #f8f9ff 0%, #f0f4ff 100%);
    border-radius: 16px;
    border: 1px solid #e5e7eb;
}

.filter-group {
    display: flex;
    align-items: center;
    gap: 16px;
}

.filter-label {
    font-weight: 700;
    font-size: 16px;
    color: #374151;
    white-space: nowrap;
}

.filter-buttons {
    display: flex;
    gap: 12px;
    flex-wrap: wrap;
}

.filter-btn {
    padding: 10px 20px;
    border-radius: 10px;
    border: 2px solid #d1d5db;
    background: white;
    color: #6b7280;
    font-weight: 600;
    font-size: 14px;
    cursor: pointer;
    transition: all 0.3s ease;
    white-space: nowrap;
}

.filter-btn:hover {
    border-color: #667eea;
    color: #667eea;
    transform: translateY(-2px);
    box-shadow: 0 4px 8px rgba(102, 126, 234, 0.2);
}

.filter-btn.active {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    border-color: transparent;
    box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

/* 그래프 카드 */
.graph-card {
    background: white;
    padding: 24px;
    border-radius: 16px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
    border: 1px solid #e5e7eb;
    transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
}

.graph-card:hover {
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.12);
    transform: translateY(-2px);
}

/* 위: 막대 + 원그래프 */
.top-graphs {
    display: grid;
    grid-template-columns: 2fr 1fr;
    gap: 24px;
    margin-bottom: 24px;
    align-items: start;
}

/* 중간: 연령대 */
.middle-graph {
    margin-bottom: 24px;
}

/* 아래: 남녀 연령대 비율 */
.bottom-graph {
    margin-top: 0;
}

/* 애니메이션 */
.graph-card {
    animation: fadeInGraph 0.6s ease;
}

@keyframes fadeInGraph {
    from {
        opacity: 0;
        transform: translateY(10px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}
</style>