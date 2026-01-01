<template>
    <div class="home">
        <div class="welcome-card">
            <div class="icon">🎮</div>
            <h2 class="title">미니게임 놀이터에 오신 것을 환영합니다!</h2>
            <p class="description">위에서 플레이할 미니게임을 선택하세요</p>
            <div class="game-preview">
                <div class="preview-item" @click="goChristmasTree">
                    <span class="preview-icon">🎄</span>
                    <span>크리스마스 트리</span>
                </div>
                <div class="preview-item" @click="goChristmasTree2">
                    <span class="preview-icon">🎄</span>
                    <span>크리스마스 트리2</span>
                </div>
                <div class="preview-item" @click="goNumberGuess">
                    <span class="preview-icon">🔢</span>
                    <span>숫자 맞추기</span>
                </div>
                <div class="preview-item" @click="goClickSpeed">
                    <span class="preview-icon">⚡</span>
                    <span>클릭 속도 테스트</span>
                </div>
            </div>
        </div>
        <RouterView/>
    </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter()
const goChristmasTree = () => {
    router.push('/playground/christmasTree')
}
const goChristmasTree2 = () => {
    router.push('/playground/christmasTree2')
}
const goNumberGuess = () => {
    router.push('/playground/numberGuess')
}
const goClickSpeed = () => {
    router.push('/playground/clickSpeed')
}

</script>

<style scoped>
.home {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 400px;
    padding: 40px 20px;
}

.welcome-card {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border-radius: 24px;
    padding: 48px 40px;
    text-align: center;
    box-shadow: 0 20px 60px rgba(102, 126, 234, 0.3);
    max-width: 600px;
    width: 100%;
    animation: fadeInUp 0.6s ease;
}

@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.icon {
    font-size: 64px;
    margin-bottom: 20px;
    animation: bounce 2s infinite;
}

@keyframes bounce {
    0%, 100% {
        transform: translateY(0);
    }
    50% {
        transform: translateY(-10px);
    }
}

.title {
    font-size: 28px;
    font-weight: 700;
    color: white;
    margin-bottom: 12px;
}

.description {
    font-size: 18px;
    color: rgba(255, 255, 255, 0.9);
    margin-bottom: 32px;
}

.game-preview {
    display: flex;
    justify-content: center;
    gap: 24px;
    flex-wrap: wrap;
    margin-top: 32px;
}

.preview-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 8px;
    padding: 16px 24px;
    background: rgba(255, 255, 255, 0.2);
    backdrop-filter: blur(10px);
    border-radius: 16px;
    color: white;
    font-weight: 600;
    transition: all 0.3s ease;
    cursor: pointer;
}

.preview-item:hover {
    background: rgba(255, 255, 255, 0.3);
    transform: translateY(-4px);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
}

.preview-icon {
    font-size: 32px;
}
</style>