<template>
    <section class="comment-section">
        <h2 class="section-title">💬 댓글</h2>

        <div class="comment-form">
            <textarea v-model="content" placeholder="댓글을 입력하세요" rows="3" />

            <button @click="submitComment">등록</button>
        </div>

        <div class="comment-list">
            <CommentItem v-for="comment in comments" :key="comment.id" :comment="comment" @updated="handleUpdated"
                @deleted="handleDeleted" />

            <p v-if="comments.length === 0" class="empty">
                아직 댓글이 없습니다.
            </p>
        </div>
    </section>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import commentApi from '@/api/commentApi'
import CommentItem from './CommentItem.vue'

const user = computed(() => {
    try {
        return JSON.parse(localStorage.getItem('userInfo'))
    } catch {
        console.log("유저 정보 로딩 실패")
        return null
    }
})

const props = defineProps({
    voteId: {
        type: Number,
        required: true,
    },
})

const comments = ref([])
const content = ref('')

const fetchComments = async () => {
    try {
        const res = await commentApi.findByVoteId(props.voteId)
        comments.value = res.data.filter(comment => comment.deletedAt === null)
    } catch (e) {
        console.error('댓글 조회 실패', e)
    }
}

const submitComment = async () => {
    if (!content.value.trim()) return

    try {
        await commentApi.createComment({
            voteId: props.voteId,
            memberId: user.value.id,
            content: content.value,
        })
        content.value = ''
        fetchComments()
    } catch (e) {
        console.error('댓글 작성 실패', e)
    }
}

const handleUpdated = () => {
    fetchComments()
}

const handleDeleted = () => {
    fetchComments()
}

onMounted(fetchComments)
watch(() => props.voteId, fetchComments)
</script>

<style scoped>
.comment-section {
    margin-top: 32px;
    padding-top: 24px;
    border-top: 1px solid #e5e7eb;
}

.comment-form {
    display: flex;
    flex-direction: column;
    gap: 8px;
    margin-bottom: 20px;
}

textarea {
    resize: none;
    padding: 10px;
    border-radius: 6px;
    border: 1px solid #d1d5db;
}

button {
    align-self: flex-end;
    background: #2563eb;
    color: white;
    padding: 6px 14px;
    border-radius: 6px;
    border: none;
    cursor: pointer;
}

.section-title {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 16px;
}

.empty {
    color: #777;
    font-size: 14px;
    margin-top: 10px;
}
</style>