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