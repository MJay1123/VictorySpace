<template>
    <div class="comment-item">
        <UpdateCommentModal v-if="showUpdate" :comment="comment" @close="showUpdate = false" @updated="handleUpdate" />

        <DeleteCommentModal v-if="showDelete" :commentId="comment.id" @close="showDelete = false"
            @deleted="handleDelete" />

        <div class="comment-header">
            <div class="meta">
                <b class="nickname">{{ commentNickname ?? '익명' }}</b>
                <span class="date">
                    {{ comment.updatedAt !== comment.createdAt
                        ? formatDate(comment.updatedAt) + ' · 수정됨'
                        : formatDate(comment.createdAt)
                    }}
                </span>
            </div>
            <CommentActionMenu v-if="canEdit" @edit="showUpdate = true" @delete="showDelete = true" />
        </div>
        <p class="content">{{ comment.content }}</p>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import CommentActionMenu from './CommentActionMenu.vue'
import UpdateCommentModal from './UpdateCommentModal.vue'
import DeleteCommentModal from './DeleteCommentModal.vue'
import commentApi from '../../api/commentApi'
import memberApi from '../../api/memberApi'

const props = defineProps({
    comment: {
        type: Object,
        required: true
    },
})

const commentNickname = ref('')
const fetchNickname = async () => {
    try {
        const res = await memberApi.findById(props.comment.memberId)
        commentNickname.value = res.data.nickname
    } catch (e) {
        console.error('닉네임 조회 실패', e)
    }
}

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

onMounted(fetchNickname)

</script>

<style scoped>
.comment-item {
    position: relative;
    padding: 20px;
    border-bottom: 1px solid #e5e7eb;
    transition: background-color 0.2s ease;
}

.comment-item:hover {
    background-color: #f9fafb;
}

.comment-item:last-child {
    border-bottom: none;
}

.comment-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 12px;
}

.meta {
    display: flex;
    align-items: center;
    gap: 12px;
    flex: 1;
}

.nickname {
    font-size: 15px;
    font-weight: 600;
    color: #1f2937;
}

.date {
    font-size: 13px;
    color: #6b7280;
}

.content {
    margin-top: 0;
    font-size: 15px;
    line-height: 1.6;
    color: #374151;
    word-break: break-word;
}
</style>