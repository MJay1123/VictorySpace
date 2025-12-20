<template>
    <div class="modal-overlay" @click.self="$emit('close')">
        <div class="modal-card">
            <h2 class="modal-title">댓글 수정</h2>

            <textarea v-model="content" class="content-input" placeholder="댓글을 수정하세요" />

            <div class="actions">
                <button class="cancel-btn" @click="$emit('close')">
                    취소
                </button>
                <button class="save-btn" @click="handleUpdate">
                    수정
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
    comment: {
        type: Object,
        required: true
    }
})

const emit = defineEmits(['close', 'updated'])

const content = ref(props.comment.content)

const handleUpdate = () => {
    emit('updated', {
        id: props.comment.id,
        content: content.value
    })
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
    font-size: 20px;
    font-weight: 700;
    margin-bottom: 14px;
    text-align: center;
}

.content-input {
    width: 100%;
    min-height: 120px;
    padding: 12px;
    border-radius: 8px;
    border: 1px solid #d1d5db;
    font-size: 14px;
    resize: none;
    margin-bottom: 20px;
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

.save-btn {
    flex: 1;
    padding: 12px;
    border-radius: 8px;
    border: none;
    background: #6366f1;
    color: white;
    font-weight: 700;
    cursor: pointer;
}

.save-btn:hover {
    background: #4f46e5;
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