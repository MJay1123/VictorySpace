<template>
    <div class="modal-overlay" @click.self="$emit('close')">
        <div class="modal-card">
            <h2 class="modal-title">투표 삭제</h2>

            <p class="modal-desc">
                정말로 이 투표를 삭제하시겠습니까?<br />
                <strong>삭제된 투표는 복구할 수 없습니다.</strong>
            </p>

            <div class="actions">
                <button class="cancel-btn" @click="$emit('close')">
                    취소
                </button>
                <button class="delete-btn" @click="deleteVote">
                    삭제
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import voteApi from '@/api/voteApi'

const props = defineProps({
    voteId: Number
})

const emit = defineEmits(['close', 'deleted'])

const deleteVote = async () => {
    try {
        await voteApi.deleteVote(props.voteId)
        emit('deleted')
        emit('close')
    } catch (err) {
        alert('삭제 중 오류가 발생했습니다.')
        console.error(err)
    }
}
</script>

<style scoped>
/* 🔥 배경 */
.modal-overlay {
    position: fixed;
    inset: 0;
    background: rgba(0, 0, 0, 0.55);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

/* 🔥 카드 */
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

/* 🔥 버튼 영역 */
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

/* 🔥 애니메이션 */
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