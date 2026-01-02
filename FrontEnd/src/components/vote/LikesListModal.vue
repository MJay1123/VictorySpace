<template>
    <div class="modal-overlay" @click.self="$emit('close')">
        <div class="modal-card">
            <header class="modal-header">
                <h3>❤️ 좋아요한 사람</h3>
                <button class="close-btn" @click="$emit('close')">✕</button>
            </header>

            <div class="likes-list">
                <div v-for="(nickname, index) in likes" :key="index" class="like-item">
                    {{ nickname }}
                </div>
                <div v-if="likes.length === 0" class="empty">
                    아직 좋아요가 없습니다.
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import likesApi from '@/api/likesApi'

const props = defineProps({
    voteId: {
        type: Number,
        required: true
    }
})

const likes = ref([])

const fetchLikes = async () => {
    try {
        const res = await likesApi.findByVoteId(props.voteId)
        likes.value = res.data.map(item => item.memberNickname)
    } catch (e) {
        console.error('좋아요 목록 조회 실패', e)
    }
}

onMounted(fetchLikes)
</script>

<style scoped>
.modal-overlay {
    position: fixed;
    inset: 0;
    background: rgba(0, 0, 0, 0.45);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
}

.modal-card {
    width: 360px;
    max-height: 420px;
    background: white;
    border-radius: 14px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
    display: flex;
    flex-direction: column;
    animation: fadeUp 0.25s ease;
}

.modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1rem 1.2rem;
    border-bottom: 1px solid #eee;
}

.modal-header h3 {
    margin: 0;
    font-size: 1.1rem;
}

.close-btn {
    background: none;
    border: none;
    font-size: 1.2rem;
    cursor: pointer;
}

.likes-list {
    padding: 0.75rem 1.2rem;
    overflow-y: auto;
    flex: 1;
}

.like-item {
    padding: 0.6rem 0;
    border-bottom: 1px solid #f0f0f0;
    font-weight: 500;
}

.like-item:last-child {
    border-bottom: none;
}

.empty {
    text-align: center;
    color: #888;
    padding: 2rem 0;
}

@keyframes fadeUp {
    from {
        opacity: 0;
        transform: translateY(12px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}
</style>