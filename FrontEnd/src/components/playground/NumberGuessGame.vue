<template>
    <div class="number-guess-game">
        <div class="game-card">
            <h2 class="game-title">🔢 숫자 맞추기</h2>
            <p class="game-description">1부터 100까지의 숫자를 맞춰보세요!</p>

            <div class="range-info">
                <div class="range-item">
                    <span class="range-label">최소</span>
                    <span class="range-value">{{ min }}</span>
                </div>
                <div class="range-item">
                    <span class="range-label">최대</span>
                    <span class="range-value">{{ max }}</span>
                </div>
            </div>

            <div class="attempts">
                <span class="attempts-label">시도 횟수:</span>
                <span class="attempts-value">{{ attempts }}</span>
            </div>

            <div class="input-container">
                <input 
                    v-model.number="guess" 
                    type="number" 
                    :min="min"
                    :max="max"
                    placeholder="숫자를 입력하세요"
                    class="number-input"
                    @keyup.enter="check"
                    :disabled="gameWon"
                />
                <button 
                    class="check-button" 
                    @click="check"
                    :disabled="gameWon || !guess"
                >
                    확인
                </button>
            </div>

            <div v-if="message" class="message" :class="messageClass">
                <div class="message-icon">{{ messageIcon }}</div>
                <div class="message-text">{{ message }}</div>
            </div>

            <div v-if="gameWon" class="celebration">
                <div class="confetti" v-for="i in 20" :key="i" :style="getConfettiStyle(i)"></div>
                <div class="celebration-text">축하합니다! 🎉</div>
                <button class="restart-button" @click="restart">다시 시작</button>
            </div>

            <div v-if="hint" class="hint">
                <span class="hint-icon">💡</span>
                <span class="hint-text">{{ hint }}</span>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const min = ref(1)
const max = ref(100)
const answer = ref(Math.floor(Math.random() * 100) + 1)
const guess = ref(null)
const message = ref('')
const attempts = ref(0)
const gameWon = ref(false)

const messageClass = computed(() => {
    if (gameWon.value) return 'success'
    if (message.value && !gameWon.value) return 'error'
    return ''
})

const messageIcon = computed(() => {
    if (gameWon.value) return '🎉'
    if (message.value && !gameWon.value) return '❌'
    return ''
})

const hint = computed(() => {
    if (!guess.value || gameWon.value) return ''
    if (guess.value < answer.value) {
        return '더 큰 숫자입니다!'
    } else if (guess.value > answer.value) {
        return '더 작은 숫자입니다!'
    }
    return ''
})

const check = () => {
    if (!guess.value || gameWon.value) return

    attempts.value++

    if (guess.value === answer.value) {
        gameWon.value = true
        message.value = `정답입니다! ${attempts.value}번 만에 맞췄어요! 🎉`
    } else {
        if (guess.value < answer.value) {
            min.value = Math.max(min.value, guess.value + 1)
        } else {
            max.value = Math.min(max.value, guess.value - 1)
        }
        message.value = '틀렸어요! 다시 시도해보세요 😢'
    }
}

const restart = () => {
    answer.value = Math.floor(Math.random() * 100) + 1
    guess.value = null
    message.value = ''
    attempts.value = 0
    gameWon.value = false
    min.value = 1
    max.value = 100
}

const getConfettiStyle = (i) => {
    const colors = ['#ff4d4d', '#ffd93d', '#4dd2ff', '#c77dff', '#5cff8d', '#ff6b6b']
    const left = (i * 5) % 100
    const delay = (i * 0.1) % 2
    const duration = 2 + (i % 3)
    return {
        left: `${left}%`,
        backgroundColor: colors[i % colors.length],
        animationDelay: `${delay}s`,
        animationDuration: `${duration}s`
    }
}
</script>

<style scoped>
.number-guess-game {
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
    position: relative;
    overflow: hidden;
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

.range-info {
    display: flex;
    gap: 24px;
    justify-content: center;
    margin-bottom: 24px;
}

.range-item {
    flex: 1;
    padding: 16px;
    background: linear-gradient(135deg, #f8f9ff 0%, #f0f4ff 100%);
    border-radius: 12px;
}

.range-label {
    display: block;
    font-size: 12px;
    color: #6b7280;
    margin-bottom: 4px;
    font-weight: 600;
}

.range-value {
    display: block;
    font-size: 24px;
    font-weight: 700;
    color: #667eea;
}

.attempts {
    margin-bottom: 24px;
    padding: 12px 20px;
    background: #f3f4f6;
    border-radius: 12px;
    display: inline-block;
}

.attempts-label {
    font-size: 14px;
    color: #6b7280;
    margin-right: 8px;
    font-weight: 600;
}

.attempts-value {
    font-size: 20px;
    font-weight: 700;
    color: #1f2937;
}

.input-container {
    display: flex;
    gap: 12px;
    margin-bottom: 24px;
}

.number-input {
    flex: 1;
    padding: 16px 20px;
    font-size: 18px;
    border: 2px solid #e5e7eb;
    border-radius: 12px;
    transition: all 0.3s ease;
    font-weight: 600;
    text-align: center;
}

.number-input:focus {
    outline: none;
    border-color: #667eea;
    box-shadow: 0 0 0 4px rgba(102, 126, 234, 0.1);
}

.number-input:disabled {
    background: #f3f4f6;
    cursor: not-allowed;
}

.check-button {
    padding: 16px 32px;
    font-size: 16px;
    font-weight: 700;
    color: white;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
    border-radius: 12px;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}

.check-button:hover:not(:disabled) {
    transform: translateY(-2px);
    box-shadow: 0 6px 16px rgba(102, 126, 234, 0.4);
}

.check-button:disabled {
    opacity: 0.5;
    cursor: not-allowed;
}

.message {
    padding: 20px;
    border-radius: 12px;
    margin-bottom: 16px;
    animation: slideDown 0.3s ease;
}

@keyframes slideDown {
    from {
        opacity: 0;
        transform: translateY(-10px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.message.success {
    background: linear-gradient(135deg, #f0fdf4 0%, #dcfce7 100%);
    border: 2px solid #10b981;
}

.message.error {
    background: linear-gradient(135deg, #fef2f2 0%, #fee2e2 100%);
    border: 2px solid #ef4444;
}

.message-icon {
    font-size: 32px;
    margin-bottom: 8px;
    display: block;
}

.message-text {
    font-size: 18px;
    font-weight: 600;
    color: #1f2937;
}

.hint {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
    padding: 12px 20px;
    background: #fef3c7;
    border-radius: 12px;
    margin-top: 16px;
}

.hint-icon {
    font-size: 20px;
}

.hint-text {
    font-size: 14px;
    font-weight: 600;
    color: #92400e;
}

.celebration {
    position: relative;
    margin-top: 24px;
    padding: 32px;
    background: linear-gradient(135deg, #f0fdf4 0%, #dcfce7 100%);
    border-radius: 16px;
    border: 2px solid #10b981;
    overflow: hidden;
}

.confetti {
    position: absolute;
    width: 10px;
    height: 10px;
    top: -10px;
    animation: confettiFall linear infinite;
}

@keyframes confettiFall {
    0% {
        transform: translateY(0) rotate(0deg);
        opacity: 1;
    }
    100% {
        transform: translateY(600px) rotate(720deg);
        opacity: 0;
    }
}

.celebration-text {
    font-size: 24px;
    font-weight: 700;
    color: #059669;
    margin-bottom: 20px;
    position: relative;
    z-index: 1;
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
    position: relative;
    z-index: 1;
}

.restart-button:hover {
    background: #059669;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(16, 185, 129, 0.4);
}
</style>