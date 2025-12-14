<template>
    <section class="comment-section">
        <h2 class="section-title">💬 댓글</h2>

        <CommentCreateForm :vote-id="voteId" @created="fetchComments" />

        <CommentList :comments="comments" />
    </section>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import CommentList from './CommentList.vue'
import CommentCreateForm from './CommentCreateForm.vue'
import commentApi from '@/api/commentApi'

const props = defineProps({
    voteId: {
        type: Number,
        required: true,
    },
})

const comments = ref([])

const fetchComments = async () => {
    try {
        const res = await commentApi.findByVoteId(props.voteId)
        comments.value = res.data
    } catch (e) {
        console.error('댓글 조회 실패', e)
    }
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

.section-title {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 16px;
}
</style>