<template>
    <div class="comment-item">
        <CommentActionMenu v-if="canEdit" @edit="showUpdate = true" @delete="showDelete = true" />

        <UpdateCommentModal v-if="showUpdate" :comment="comment" @close="showUpdate = false" @updated="handleUpdate" />

        <DeleteCommentModal v-if="showDelete" :commentId="comment.id" @close="showDelete = false"
            @deleted="handleDelete" />
        <div class="meta">
            <b>{{ comment.nickname ?? '익명' }}</b>
            <span class="date">{{ formatDate(comment.createdAt) }}</span>
        </div>
        <p class="content">{{ comment.content }}</p>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import CommentActionMenu from './CommentActionMenu.vue'
import UpdateCommentModal from './UpdateCommentModal.vue'
import DeleteCommentModal from './DeleteCommentModal.vue'
import commentApi from '../../api/commentApi'

const props = defineProps({
    comment: {
        type: Object,
        required: true
    },
})

const emit = defineEmits(["updated", "deleted"])

const user = computed(() => {
    try {
        return JSON.parse(localStorage.getItem('userInfo'))
    } catch {
        console.log("유저 정보 로딩 실패")
        return null
    }
})

const canEdit = computed(() =>
    user.value && props.comment.memberId === user.value.id
)

const formatDate = (dateString) => {
    if (!dateString) return ''
    const d = new Date(dateString)
    return `${d.getMonth() + 1}/${d.getDate()} ${d.getHours()}:${String(d.getMinutes()).padStart(2, '0')}`
}

const showUpdate = ref(false)
const showDelete = ref(false)

const handleUpdate = async ({ id, content }) => {
    try {
        const updatedDto = {
            content,      
        }

        await commentApi.updateComment(id, updatedDto)

        props.comment.content = content
        emit('updated')
        showUpdate.value = false
    } catch (err) {
        console.error(err)
        alert('댓글 수정 중 오류가 발생했습니다.')
    }
}

// 🔹 댓글 삭제 처리
const handleDelete = async () => {
    try {
        await commentApi.deleteComment(props.comment.id)
        emit('deleted')
        showDelete.value = false
    } catch (err) {
        console.error(err)
        alert('댓글 삭제 중 오류가 발생했습니다.')
    }
}

</script>

<style scoped>
.comment-item {
    position: relative;
    padding: 12px 0;
    border-bottom: 1px solid #eee;
}

.action-menu {
    position: absolute;
    top: 8px;
    right: 0;
}

.meta {
    font-size: 13px;
    color: #555;
    display: flex;
    justify-content: space-between;
}

.content {
    margin-top: 6px;
    font-size: 14px;
}
</style>