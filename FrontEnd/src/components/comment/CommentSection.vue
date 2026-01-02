<template>
    <section class="comment-section">
        <h2 class="section-title">💬 댓글 {{ comments.length }}</h2>

        <div class="comment-form">
            <textarea v-model="content" placeholder="댓글을 입력하세요" rows="3" />

            <button @click="submitComment">등록</button>
        </div>

        <div class="comment-list">
            <div v-for="comment in comments" :key="comment.id" class="comment-item">

                <UpdateCommentModal v-if="editingCommentId === comment.id" :comment="comment"
                    @close="editingCommentId = null" @updated="handleUpdate" />

                <DeleteCommentModal v-if="deletingCommentId === comment.id" :commentId="comment.id"
                    @close="deletingCommentId = null" @deleted="handleDelete" />

                <div class="comment-header">
                    <div class="meta">
                        <b class="nickname">{{ comment.memberNickname ?? '익명' }}</b>
                        <span class="date">
                            {{ comment.updatedAt
                                ? formatDate(comment.updatedAt) + ' · 수정됨'
                                : formatDate(comment.createdAt)
                            }}
                        </span>
                    </div>

                    <!-- 액션 메뉴 -->
                    <CommentActionMenu v-if="comment.memberId === userId" @edit="openUpdateModal(comment.id)"
                        @delete="openDeleteModal(comment.id)" />
                </div>
                <p class="content">{{ comment.content }}</p>
            </div>

            <p v-if="comments.length === 0" class="empty">
                아직 댓글이 없습니다.
            </p>
        </div>
    </section>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import commentApi from '@/api/commentApi'
import CommentActionMenu from './CommentActionMenu.vue'
import UpdateCommentModal from './UpdateCommentModal.vue'
import DeleteCommentModal from './DeleteCommentModal.vue'

const props = defineProps({
    voteId: {
        type: Number,
        required: true,
    },
})

const userId = JSON.parse(localStorage.getItem('userInfo')).id

const comments = ref([])
const content = ref('')
const editingCommentId = ref(null)
const deletingCommentId = ref(null)

const fetchComments = async () => {
    try {
        const res = await commentApi.findByVoteId(props.voteId)
        comments.value = res.data

    } catch (e) {
        console.error('댓글 조회 실패', e)
    }
}

const submitComment = async () => {
    if (!content.value.trim()) return

    try {
        await commentApi.createComment({
            voteId: props.voteId,
            memberId: userId,
            content: content.value,
        })
        content.value = ''
        fetchComments()
    } catch (e) {
        console.error('댓글 작성 실패', e)
    }
}

const openUpdateModal = (commentId) => {
    editingCommentId.value = commentId
}

const openDeleteModal = (commentId) => {
    deletingCommentId.value = commentId
}

const handleUpdate = async ({ id, content: newContent }) => {
    try {
        const updatedDto = {
            content: newContent,
        }

        await commentApi.updateComment(id, updatedDto)

        const comment = comments.value.find(c => c.id === id)
        if (comment) {
            comment.content = newContent
            comment.updatedAt = new Date().toISOString()
        }

        editingCommentId.value = null
        fetchComments() // 댓글 목록 새로고침
    } catch (err) {
        console.error(err)
        alert('댓글 수정 중 오류가 발생했습니다.')
    }
}

const handleDelete = async (commentId) => {
    try {
        await commentApi.deleteComment(commentId)
        deletingCommentId.value = null
        fetchComments() // 댓글 목록 새로고침
    } catch (err) {
        console.error(err)
        alert('댓글 삭제 중 오류가 발생했습니다.')
    }
}

const formatDate = (dateString) => {
    if (!dateString) return ''
    const d = new Date(dateString)
    return `${d.getMonth() + 1}/${d.getDate()} ${d.getHours()}:${String(d.getMinutes()).padStart(2, '0')}`
}

onMounted(fetchComments)
watch(() => props.voteId, fetchComments)
</script>

<style scoped>
.comment-section {
    margin-top: 32px;
    padding: 32px;
    background: white;
    border-radius: 16px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}

.section-title {
    font-size: 24px;
    font-weight: 700;
    margin-bottom: 24px;
    color: #1f2937;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
}

.comment-form {
    display: flex;
    flex-direction: column;
    gap: 12px;
    margin-bottom: 32px;
    padding: 20px;
    background: linear-gradient(135deg, #f8f9ff 0%, #f0f4ff 100%);
    border-radius: 12px;
    border: 1px solid #e5e7eb;
}

textarea {
    resize: none;
    padding: 14px 16px;
    border-radius: 10px;
    border: 2px solid #e5e7eb;
    font-size: 15px;
    font-family: inherit;
    transition: all 0.3s ease;
    background: white;
    color: #1f2937;
}

textarea:focus {
    outline: none;
    border-color: #667eea;
    box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

textarea::placeholder {
    color: #9ca3af;
}

button {
    align-self: flex-end;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    padding: 10px 24px;
    border-radius: 10px;
    border: none;
    cursor: pointer;
    font-weight: 600;
    font-size: 15px;
    transition: all 0.3s ease;
    box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}

button:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 16px rgba(102, 126, 234, 0.4);
}

button:active {
    transform: translateY(0);
}

.comment-list {
    display: flex;
    flex-direction: column;
    gap: 0;
}

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

/* 액션 메뉴 스타일 */
.menu-wrapper {
    position: relative;
}

.menu-btn {
    background: none;
    border: none;
    font-size: 26px;
    cursor: pointer;
    padding: 4px 8px;
    color: #6b7280;
    transition: color 0.2s ease;
}

.menu-btn:hover {
    color: #374151;
}

.menu-dropdown {
    position: absolute;
    top: 36px;
    right: 0;
    background: white;
    border-radius: 10px;
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
    display: flex;
    flex-direction: column;
    min-width: 120px;
    overflow: hidden;
    z-index: 10;
}

.menu-dropdown button {
    padding: 14px 14px;
    border: none;
    background: none;
    cursor: pointer;
    text-align: left;
    font-size: 15px;
    transition: background-color 0.2s ease;
}

.menu-dropdown button:hover {
    background: #f3f4f6;
}

.empty {
    color: #9ca3af;
    font-size: 15px;
    margin-top: 20px;
    text-align: center;
    padding: 40px;
    background: #f9fafb;
    border-radius: 12px;
}
</style>