<template>
    <div class="click-speed-game">
        <div class="game-card">
            <h2 class="game-title">⚡ 클릭 속도 테스트</h2>
            <p class="game-description">5초 동안 최대한 많이 클릭하세요!</p>

            <div class="timer-container">
                <div class="timer-circle" :class="{ 'active': !done, 'finished': done }">
                    <div class="timer-text">{{ timeLeft }}</div>
                </div>
            </div>

            <div class="stats">
                <div class="stat-item">
                    <div class="stat-label">클릭 수</div>
                    <div class="stat-value">{{ count }}</div>
                </div>
                <div class="stat-item">
                    <div class="stat-label">초당 클릭</div>
                    <div class="stat-value">{{ cps.toFixed(1) }}</div>
                </div>
            </div>

            <button 
                class="click-button" 
                @click="click" 
                :disabled="done"
                :class="{ 'disabled': done, 'pulse': !done }"
            >
                {{ done ? '게임 종료!' : 'CLICK!' }}
            </button>

            <div v-if="done" class="result">
                <div class="result-title">결과</div>
                <div class="result-stats">
                    <div class="result-item">
                        <span class="result-label">총 클릭:</span>
                        <span class="result-value">{{ count }}회</span>
                    </div>
                    <div class="result-item">
                        <span class="result-label">초당 클릭:</span>
                        <span class="result-value">{{ cps.toFixed(1) }} CPS</span>
                    </div>
                </div>
                <button class="restart-button" @click="restart">다시 시작</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'

const count = ref(0)
const done = ref(false)
const timeLeft = ref(5)
let timer = null
let countdown = null

const cps = computed(() => {
    return count.value / 5
})

const click = () => {
    if (!done.value) {
        count.value++
    }
}

const startGame = () => {
    count.value = 0
    done.value = false
    timeLeft.value = 5

    countdown = setInterval(() => {
        timeLeft.value--
        if (timeLeft.value <= 0) {
            clearInterval(countdown)
            done.value = true
        }
    }, 1000)
}

const restart = () => {
    if (countdown) clearInterval(countdown)
    startGame()
}

onMounted(() => {
    startGame()
})

onUnmounted(() => {
    if (countdown) clearInterval(countdown)
})
</script>

<style scoped>
.click-speed-game {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 500px;
    padding: 40px 20px;
}

.game-card {
    background: white;
    border-radius: 24px;
    padding: 40px;
    box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
    max-width: 500px;
    width: 100%;
    text-align: center;
    animation: fadeIn 0.5s ease;
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: scale(0.95);
    }
    to {
        opacity: 1;
        transform: scale(1);
    }
}

.game-title {
    font-size: 32px;
    font-weight: 700;
    margin-bottom: 8px;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
}

.game-description {
    color: #6b7280;
    font-size: 16px;
    margin-bottom: 32px;
}

.timer-container {
    margin: 32px 0;
    display: flex;
    justify-content: center;
}

.timer-circle {
    width: 120px;
    height: 120px;
    border-radius: 50%;
    border: 8px solid #e5e7eb;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.3s ease;
    position: relative;
}

.timer-circle.active {
    border-color: #3b82f6;
    animation: pulse 1s infinite;
}

.timer-circle.finished {
    border-color: #10b981;
}

@keyframes pulse {
    0%, 100% {
        box-shadow: 0 0 0 0 rgba(59, 130, 246, 0.4);
    }
    50% {
        box-shadow: 0 0 0 20px rgba(59, 130, 246, 0);
    }
}

.timer-text {
    font-size: 48px;
    font-weight: 700;
    color: #1f2937;
}

.stats {
    display: flex;
    gap: 24px;
    justify-content: center;
    margin: 32px 0;
}

.stat-item {
    flex: 1;
    padding: 20px;
    background: linear-gradient(135deg, #f8f9ff 0%, #f0f4ff 100%);
    border-radius: 16px;
}

.stat-label {
    font-size: 14px;
    color: #6b7280;
    margin-bottom: 8px;
    font-weight: 600;
}

.stat-value {
    font-size: 32px;
    font-weight: 700;
    color: #667eea;
}

.click-button {
    width: 100%;
    padding: 20px;
    font-size: 24px;
    font-weight: 700;
    color: white;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
    border-radius: 16px;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 8px 24px rgba(102, 126, 234, 0.4);
    margin-top: 24px;
}

.click-button.pulse {
    animation: buttonPulse 0.3s infinite;
}

@keyframes buttonPulse {
    0%, 100% {
        transform: scale(1);
    }
    50% {
        transform: scale(1.05);
    }
}

.click-button:hover:not(.disabled) {
    transform: translateY(-2px);
    box-shadow: 0 12px 32px rgba(102, 126, 234, 0.5);
}

.click-button.disabled {
    background: #9ca3af;
    cursor: not-allowed;
    box-shadow: none;
}

.result {
    margin-top: 32px;
    padding: 24px;
    background: linear-gradient(135deg, #f0fdf4 0%, #dcfce7 100%);
    border-radius: 16px;
    animation: slideUp 0.5s ease;
}

@keyframes slideUp {
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.result-title {
    font-size: 20px;
    font-weight: 700;
    color: #059669;
    margin-bottom: 16px;
}

.result-stats {
    display: flex;
    flex-direction: column;
    gap: 12px;
    margin-bottom: 20px;
}

.result-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px;
    background: white;
    border-radius: 12px;
}

.result-label {
    font-weight: 600;
    color: #374151;
}

.result-value {
    font-weight: 700;
    font-size: 18px;
    color: #059669;
}

.restart-button {
    width: 100%;
    padding: 14px;
    font-size: 16px;
    font-weight: 600;
    color: white;
    background: #10b981;
    border: none;
    border-radius: 12px;
    cursor: pointer;
    transition: all 0.3s ease;
}

.restart-button:hover {
    background: #059669;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(16, 185, 129, 0.4);
}
</style>