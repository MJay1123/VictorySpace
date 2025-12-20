<template>
    <div class="modal-overlay" @click.self="$emit('close')">
        <div class="modal-card">
            <h2 class="modal-title">댓글 삭제</h2>

            <p class="modal-desc">
                정말로 이 댓글을 삭제하시겠습니까?<br />
                <strong>삭제된 댓글은 복구할 수 없습니다.</strong>
            </p>

            <div class="actions">
                <button class="cancel-btn" @click="$emit('close')">
                    아니오
                </button>
                <button class="delete-btn" @click="handleDelete">
                    예
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import commentApi from '@/api/commentApi'

const props = defineProps({
    commentId: {
        type: Number,
        required: true
    }
})

const emit = defineEmits(['close', 'deleted'])

const handleDelete = () => {
    emit('deleted', props.commentId)
}
</script>

<style scoped>
.modal-overlay {
    position: fixed;
    inset: 0;
    background: rgba(0, 0, 0, 0.55);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

.modal-card {
    background: #fff;
    border-radius: 14px;
    padding: 28px;
    width: 420px;
    max-width: 90%;
    box-shadow: 0 20px 40px rgba(0, 0, 0, 0.25);
    animation: scaleIn 0.25s ease;
}

.modal-title {
    font-size: 22px;
    font-weight: 700;
    margin-bottom: 12px;
    text-align: center;
}

.modal-desc {
    text-align: center;
    font-size: 15px;
    color: #444;
    line-height: 1.6;
    margin-bottom: 28px;
}

.actions {
    display: flex;
    gap: 12px;
}

.cancel-btn {
    flex: 1;
    padding: 12px;
    border-radius: 8px;
    border: none;
    background: #e5e7eb;
    font-weight: 600;
    cursor: pointer;
}

.cancel-btn:hover {
    background: #d1d5db;
}

.delete-btn {
    flex: 1;
    padding: 12px;
    border-radius: 8px;
    border: none;
    background: #ef4444;
    color: white;
    font-weight: 700;
    cursor: pointer;
}

.delete-btn:hover {
    background: #dc2626;
}

@keyframes scaleIn {
    from {
        opacity: 0;
        transform: scale(0.95);
    }

    to {
        opacity: 1;
        transform: scale(1);
    }
}
</style>